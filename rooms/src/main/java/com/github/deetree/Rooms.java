package com.github.deetree;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Mariusz Bal
 */
@Component
class Rooms {

    private final Map<Integer, Room> rooms = new HashMap<>();

    int create(RoomDTO room) {
        int id = new Random().nextInt(0, 10_000_000);
        rooms.put(id, new Room(id, room.wsl(),
                new Dimensions(room.dimensions().rows(), room.dimensions().cols()), new Players()));
        return id;
    }

    Players seePlayers(int roomId) {
        return rooms.get(roomId).players();
    }

    Map<Integer, RoomDTO> seeRooms() {
        return rooms
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().convertToDTO()));
    }

    Room findRoom(int roomId) {
        return rooms.get(roomId);
    }
}
