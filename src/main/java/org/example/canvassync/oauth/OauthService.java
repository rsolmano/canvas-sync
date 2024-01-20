package org.example.canvassync.oauth;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class OauthService {

    private final CanvasOauthProperties canvasOauthProperties;
    private final OauthRepository oauthRepository;

    public OauthService(CanvasOauthProperties canvasOauthProperties, OauthRepository oauthRepository) {
        this.canvasOauthProperties = canvasOauthProperties;
        this.oauthRepository = oauthRepository;
    }

    public Optional<CanvasOauthEntity> getTokens(CanvasHost canvasHost) {
        return oauthRepository.getTokens(canvasHost);
    }

    public Object getAuthUrl(CanvasHost canvasHost) {
        try (OAuth20Service oauthService = configureOauthService(canvasHost)) {
            return oauthService.getAuthorizationUrl();
        } catch (IOException e) {
            log.error("Error getting auth url", e);
            throw new RuntimeException(e);
        }
    }

    public void exchangeAuthCode(CanvasHost canvasHost, String authCode) {
        try (OAuth20Service oauthService = configureOauthService(canvasHost)) {
            val accessToken = oauthService.getAccessToken(authCode);
            oauthRepository.saveTokens(canvasHost, accessToken.getAccessToken(), accessToken.getRefreshToken());
            log.info("Successfully saved tokens for host {}", canvasHost.name());
        } catch (Exception e) {
            log.error("Error exchanging auth code", e);
            throw new RuntimeException(e);
        }
    }

    public CanvasOauthEntity refreshTokens(CanvasHost canvasHost) {
        try (OAuth20Service oauthService = configureOauthService(canvasHost)) {
            val oauthEntity = oauthRepository.getTokens(canvasHost).orElseThrow();
            val tokens = oauthService.refreshAccessToken(oauthEntity.getRefreshToken());
            val refreshedTokens = oauthRepository.updateAccessToken(canvasHost, tokens.getAccessToken());
            log.info("Successfully refreshed tokens for host {}", canvasHost.name());
            return refreshedTokens;
        } catch (Exception e) {
            log.error("Error refreshing tokens", e);
            throw new RuntimeException(e);
        }
    }

    private OAuth20Service configureOauthService(CanvasHost canvasHost) {
        val oauthConfig = canvasOauthProperties.config().get(canvasHost);
        return new ServiceBuilder(oauthConfig.clientId())
                .apiSecret(oauthConfig.clientSecret())
                .callback(oauthConfig.callbackUrl())
                .defaultScope(oauthConfig.scope())
                .build(
                        new DefaultApi20() {
                            @Override
                            public String getAccessTokenEndpoint() {
                                return canvasHost.getHost() + "/login/oauth2/token";
                            }

                            @Override
                            protected String getAuthorizationBaseUrl() {
                                return canvasHost.getHost() + "/login/oauth2/auth";
                            }
                        }
                );
    }
}
