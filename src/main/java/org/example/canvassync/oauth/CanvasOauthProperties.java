package org.example.canvassync.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "canvas")
public record CanvasOauthProperties(Map<CanvasHost, Config> config) {
}

record Config(String clientId, String clientSecret, String scope, String callbackUrl) {
}