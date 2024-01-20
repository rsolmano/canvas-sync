package org.example.canvassync.oauth;

import lombok.Getter;

@Getter
public enum CanvasHost {

    DEV("http://3.231.151.15"),
    PROD("https://canvas.instructure.com"),

    ;
    private final String host;

    CanvasHost(String host) {
        this.host = host;
    }

    public static CanvasHost from(String host) {
        return switch (host) {
            case "dev" -> CanvasHost.DEV;
            case "prod" -> CanvasHost.PROD;
            default -> throw new IllegalArgumentException("Unknown host: " + host);
        };
    }

}
