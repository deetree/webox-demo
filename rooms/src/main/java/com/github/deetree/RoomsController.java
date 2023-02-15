package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Mariusz Bal
 */
@RestController
@RequestMapping("/api/rooms")
class RoomsController {

    @Autowired
    private RoomsService roomsService;

    @GetMapping
    ResponseEntity<Map<Integer, RoomDTO>> loadStoredRooms() {
        return ResponseEntity.ok(roomsService.seeRooms());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createRoom(@RequestBody RoomDTO room) {
        roomsService.createRoom(room);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roomId}")
    ResponseEntity<Map<Sign, String>> loadRoomPlayers(@PathVariable int roomId) {
        return ResponseEntity.ok(roomsService.seePlayers(roomId));
    }

    @PostMapping(value = "/{roomId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> processSeatAction(@PathVariable int roomId, @RequestBody SeatAction seatAction) {
        roomsService.handleSeatAction(roomId, seatAction);
        return ResponseEntity.ok().build();
    }
}
