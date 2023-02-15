package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Mariusz Bal
 */
@Service
class RoomsService {

    private final RestOperations restTemplate = new RestTemplate();
    @Autowired
    private WebSocketService service;
    @Autowired
    private Rooms rooms;

    void createRoom(RoomDTO room) {
        rooms.create(room);
        service.broadcastRooms(rooms.seeRooms());
    }

    void handleSeatAction(int roomId, SeatAction seatAction) {
        Action action = seatAction.action();
        if (action == Action.SEAT_TAKE) {
            rooms.seePlayers(roomId).placePlayer(seatAction.sign(), seatAction.player());
            new Thread(() -> checkPlayersCollected(roomId), "gameplay-register-thread").start();
        } else if (action == Action.SEAT_RELEASE)
            rooms.seePlayers(roomId).removePlayer(seatAction.sign(), seatAction.player());
        service.broadcastToRoom(roomId,
                new Message<>(Event.PLAYERS_BROADCAST_EVENT, rooms.seePlayers(roomId).peekPlayers()));
    }

    private void checkPlayersCollected(int roomId) {
        if (rooms.seePlayers(roomId).areBothPlayersCollected()) {
            ResponseEntity<String> response = registerGameplay(roomId);
            if (response.getStatusCode() == HttpStatus.CREATED)
                service.broadcastToRoom(roomId, new Message<>(Event.GAME_EVENT, response.getBody()));
        }
    }

    private ResponseEntity<String> registerGameplay(int roomId) {//todo exception if unavailable - ConnectException
        return restTemplate.postForEntity("http://gateway:8080/api/games", rooms.findRoom(roomId), String.class);
    }

    Map<Integer, RoomDTO> seeRooms() {
        return rooms.seeRooms();
    }

    Map<Sign, String> seePlayers(int roomId) {
        return rooms.seePlayers(roomId).peekPlayers();
    }
}
