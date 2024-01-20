package org.example.canvassync.sync;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.canvassync.WireMockContextInitializer;
import org.example.canvassync.oauth.CanvasProperties;
import org.example.canvassync.oauth.OauthRepository;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.example.canvassync.db.tables.CanvasAccounts.CANVAS_ACCOUNTS;
import static org.example.canvassync.db.tables.CanvasCourses.CANVAS_COURSES;
import static org.example.canvassync.db.tables.CanvasEnrollments.CANVAS_ENROLLMENTS;
import static org.example.canvassync.db.tables.OauthTokens.OAUTH_TOKENS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ContextConfiguration(initializers = {WireMockContextInitializer.class})
class CanvasSyncControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CanvasProperties canvasProperties;

    @Autowired
    private OauthRepository oauthRepository;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private DSLContext jooq;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private EnrollmentsRepository enrollmentsRepository;

    @AfterEach
    void cleanup() {
        cleanAllCanvasObjects();
        cleanupOauthTokens();
    }


    @Test
    @SneakyThrows
    void syncShouldDownloadAllCanvasAccounts() {
        //given
        val accessToken = RandomStringUtils.randomAlphanumeric(10);
        val refreshToken = RandomStringUtils.randomAlphanumeric(10);
        oauthRepository.saveTokens(canvasProperties.host(), accessToken, refreshToken);
        mockCanvasAPICalls(accessToken);

        //when
        mockMvc.perform(post("/sync").contentType("application/json"))
                .andExpect(status().isOk());

        //then
        val accounts = accountsRepository.getAll();
        Assertions.assertEquals(2, accounts.size());
    }

    @Test
    @SneakyThrows
    void syncShouldDownloadAllCanvasCourses() {
        //given
        val accessToken = RandomStringUtils.randomAlphanumeric(10);
        val refreshToken = RandomStringUtils.randomAlphanumeric(10);
        oauthRepository.saveTokens(canvasProperties.host(), accessToken, refreshToken);
        mockCanvasAPICalls(accessToken);

        //when
        mockMvc.perform(post("/sync").contentType("application/json"))
                .andExpect(status().isOk());

        //then
        val courses = coursesRepository.getAll();
        Assertions.assertEquals(5, courses.size());

        val enrolments = enrollmentsRepository.getAll();
        Assertions.assertEquals(5, enrolments.size());
    }

    private void mockCanvasAPICalls(String accessToken) {
        val accountMappings = WireMock.get(urlPathEqualTo("/api/v1/accounts"))
                .withHeader("Authorization", WireMock.equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("canvas_accounts_response.json"));
        wireMockServer.stubFor(accountMappings);

        val coursesMappings = WireMock.get(urlPathEqualTo("/api/v1/courses"))
                .withHeader("Authorization", WireMock.equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("canvas_courses_response.json"));
        wireMockServer.stubFor(coursesMappings);
    }

    private void cleanAllCanvasObjects() {
        Stream.of(CANVAS_ENROLLMENTS, CANVAS_COURSES, CANVAS_ACCOUNTS)
                .forEach(it -> jooq.deleteFrom(it).execute());
    }

    private void cleanupOauthTokens() {
        jooq.deleteFrom(OAUTH_TOKENS).execute();
    }

}