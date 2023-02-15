package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mariusz Bal
 */
@Controller
@RequestMapping("/rooms")
class WebsiteController {

    @Autowired
    private AuthService authService;

    @GetMapping
//todo @RequestHeader("Authorization") String token
    String loadRoomsPage() {
        return "/rooms.html";
    }

    @GetMapping("/{roomId}")
    String loadGivenRoomPage(@PathVariable String roomId) {
        return "/room.html";
    }
}
