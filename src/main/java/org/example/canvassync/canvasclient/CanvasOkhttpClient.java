package org.example.canvassync.canvasclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.canvassync.oauth.CanvasHost;
import org.example.canvassync.oauth.OauthService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CanvasOkhttpClient implements CanvasClient {

    private final ObjectMapper objectMapper;
    private final OauthService oauthService;

    public CanvasOkhttpClient(ObjectMapper objectMapper, OauthService oauthService) {
        this.objectMapper = objectMapper;
        this.oauthService = oauthService;
    }

    @Override
    public List<AccountEntity> getAccounts(CanvasHost canvasHost) {
        return getResource(canvasHost, "accounts", new TypeReference<List<AccountEntity>>() {
        });
    }

    @Override
    public List<CourseEntity> getCourses(CanvasHost canvasHost) {
        return getResource(canvasHost, "courses", new TypeReference<List<CourseEntity>>() {
        });
    }

    @SneakyThrows(IOException.class)
    private <T> List<T> getResource(CanvasHost canvasHost, String resourceName, TypeReference<List<T>> type) {

        val tokens = oauthService.getTokens(canvasHost).orElseThrow(() -> new RuntimeException("No tokens for host " + canvasHost.name()));
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + tokens.getAccessToken())
                .url(canvasHost.getHost() + "/api/v1/" + resourceName)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 401) {
                oauthService.refreshTokens(canvasHost);
                return getResource(canvasHost, resourceName, type);
            }

            val responseAsString = response.body().string();
            return objectMapper.readValue(responseAsString, type);
        }
    }
}
