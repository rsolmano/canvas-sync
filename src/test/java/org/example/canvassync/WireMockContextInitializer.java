package org.example.canvassync;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

public class WireMockContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        WireMockServer wmServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wmServer.start();

        applicationContext.getBeanFactory().registerSingleton("wireMock", wmServer);

        applicationContext.addApplicationListener(event -> {
            if (event instanceof ContextClosedEvent) {
                wmServer.getStubMappings().forEach(wmServer::removeStubMapping);
                wmServer.stop();
            }
        });

        TestPropertyValues
                .of("wiremock.baseUrl=" + wmServer.baseUrl())
                .applyTo(applicationContext);
    }
}
