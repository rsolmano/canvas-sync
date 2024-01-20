package org.example.canvassync.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "canvas")
public record CanvasProperties(String host, String clientId, String clientSecret, String scope, String callbackUrl) {
}
