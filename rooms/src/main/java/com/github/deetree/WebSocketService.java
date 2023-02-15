package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Mariusz Bal
 */
@Service
class WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    void broadcastRooms(Map<Integer, RoomDTO> rooms) {
        simpMessagingTemplate.convertAndSend("/topic/rooms", rooms);
    }

    void broadcastToRoom(int roomId, Message<?> payload) {
        simpMessagingTemplate.convertAndSend("/topic/rooms/" + roomId, payload);
    }
}
