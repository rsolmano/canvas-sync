package org.example.canvassync.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/callback/canvas")
public class OauthCallbackController {

    private final OauthService oauthService;

    public OauthCallbackController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/{canvasHost}")
    public String exchangeAuthCode(@PathVariable String canvasHost, @RequestParam String code) {
        oauthService.exchangeAuthCode(CanvasHost.from(canvasHost), code);
        return "redirect:/";
    }
}
