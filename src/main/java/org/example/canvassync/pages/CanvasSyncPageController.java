package org.example.canvassync.pages;

import lombok.val;
import org.example.canvassync.oauth.CanvasHost;
import org.example.canvassync.oauth.CanvasOauthEntity;
import org.example.canvassync.oauth.OauthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CanvasSyncPageController {

    private final OauthService oauthService;

    public CanvasSyncPageController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping
    public String getCanvasSyncPage(Model model) {
        val canvasHost = CanvasHost.DEV;
        final Optional<CanvasOauthEntity> oauthEntity = oauthService.getTokens(canvasHost);
        oauthEntity.ifPresentOrElse(
                entity -> {
                    model.addAttribute("isAuthed", true);
                    model.addAttribute("canvasHost", entity.getCanvasHost());
                },
                () -> {
                    model.addAttribute("isAuthed", false);
                    model.addAttribute("oauthUrl", oauthService.getAuthUrl(canvasHost));
                });
        return "canvas";
    }
}
