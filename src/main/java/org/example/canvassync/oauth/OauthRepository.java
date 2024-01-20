package org.example.canvassync.oauth;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.canvassync.db.tables.OauthTokens.OAUTH_TOKENS;

@Repository
public class OauthRepository {

    public DSLContext jooq;

    public OauthRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public CanvasOauthEntity saveTokens(CanvasHost canvasHost, String accessToken, String refreshToken) {
        return jooq.insertInto(OAUTH_TOKENS)
                .set(OAUTH_TOKENS.CANVAS_HOST, canvasHost.name())
                .set(OAUTH_TOKENS.ACCESS_TOKEN, accessToken)
                .set(OAUTH_TOKENS.REFRESH_TOKEN, refreshToken)
                .returning()
                .fetchOneInto(CanvasOauthEntity.class);
    }

    public CanvasOauthEntity updateAccessToken(CanvasHost canvasHost, String accessToken) {
        return jooq.update(OAUTH_TOKENS)
                .set(OAUTH_TOKENS.ACCESS_TOKEN, accessToken)
                .where(OAUTH_TOKENS.CANVAS_HOST.eq(canvasHost.name()))
                .returning()
                .fetchOneInto(CanvasOauthEntity.class);
    }

    public Optional<CanvasOauthEntity> getTokens(CanvasHost canvasHost) {
        return jooq.selectFrom(OAUTH_TOKENS)
                .where(OAUTH_TOKENS.CANVAS_HOST.eq(canvasHost.name()))
                .fetchOptionalInto(CanvasOauthEntity.class);
    }


}
