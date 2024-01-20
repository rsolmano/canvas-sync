package org.example.canvassync.pages;

import lombok.val;
import org.example.canvassync.oauth.CanvasOauthEntity;
import org.example.canvassync.oauth.CanvasProperties;
import org.example.canvassync.oauth.OauthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CanvasSyncPageController {

    private final OauthService oauthService;
    private final CanvasProperties canvasProperties;

    public CanvasSyncPageController(OauthService oauthService, CanvasProperties canvasProperties) {
        this.oauthService = oauthService;
        this.canvasProperties = canvasProperties;
    }

    @GetMapping
    public String getCanvasSyncPage(Model model) {
        val host = canvasProperties.host();
        final Optional<CanvasOauthEntity> oauthEntity = oauthService.getTokens(host);
        oauthEntity.ifPresentOrElse(
                entity -> {
                    model.addAttribute("isAuthed", true);
                },
                () -> {
                    model.addAttribute("isAuthed", false);
                    model.addAttribute("oauthUrl", oauthService.getAuthUrl(host));
                });
        model.addAttribute("canvasHost", host);
        return "canvas";
    }
}
