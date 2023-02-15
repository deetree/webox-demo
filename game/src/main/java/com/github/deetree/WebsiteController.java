package com.github.deetree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mariusz Bal
 */
@Controller
@RequestMapping("/games")
class WebsiteController {

    @GetMapping("/{gameplayId}")
    String loadGamePage(@PathVariable String gameplayId) {
        return "/game.html";
    }
}
