package com.github.deetree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mariusz Bal
 */
@Controller
@RequestMapping("/register")
class WebsiteController {

    @GetMapping
    String loadRegistrationPage() {
        return "registration.html";
    }
}
