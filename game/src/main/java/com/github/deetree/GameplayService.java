package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Mariusz Bal
 */
@Service
class GameplayService {

    @Autowired
    private Games games;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    Event<?> performAction(String gameplayId, CellClickAction action) {
        Game game = games.findGame(gameplayId);
        Event<?> eventToPublish;
        try {
            Event<Sign> event = games.play(game, action);
            eventToPublish = switch (event.event()) {
                case CONTINUE -> new Event<>(GameEvent.CONTINUE,
                        new ActionPerformed(action.cellId(), event.payload(), games.switchTurn(game)));
                case WIN -> new Event<>(GameEvent.WIN, action.playerName());
                case DRAW -> new Event<>(GameEvent.DRAW, GameEvent.DRAW.toString());
                case INFO -> new Event<>(GameEvent.INFO, GameEvent.INFO.toString());
            };
        } catch (IllegalArgumentException e) {
            eventToPublish = new Event<>(GameEvent.INFO, e.getMessage());
        }
        publishEvent(gameplayId, eventToPublish);
        return eventToPublish;
    }

    private void publishEvent(String gameplayId, Event<?> event) {
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameplayId, event);
    }
}
