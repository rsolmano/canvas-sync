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
    private final CanvasProperties canvasProperties;

    public OauthCallbackController(OauthService oauthService, CanvasProperties canvasProperties) {
        this.oauthService = oauthService;
        this.canvasProperties = canvasProperties;
    }

    @GetMapping
    public String exchangeAuthCode(@RequestParam String code) {
        oauthService.exchangeAuthCode(canvasProperties.host(), code);
        return "redirect:/";
    }
}
