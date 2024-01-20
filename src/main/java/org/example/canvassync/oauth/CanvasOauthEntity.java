package org.example.canvassync.oauth;

import lombok.Data;

@Data
public class CanvasOauthEntity {
    private CanvasHost canvasHost;
    private String accessToken;
    private String refreshToken;
}
