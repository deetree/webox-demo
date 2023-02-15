package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Mariusz Bal
 */
@Controller
class WebsocketController {

    @Autowired
    private GameplayService gameplayService;

    @MessageMapping("/gameplays/{gameplayId}")
    void clickCell(@DestinationVariable String gameplayId, CellClickAction action) {
        gameplayService.performAction(gameplayId, action);
    }
}
