package com.github.deetree;

/**
 * @author Mariusz Bal
 */
record Room(long id, int wsl, Dimensions dimensions, Players players) {
    RoomDTO convertToDTO() {
        return new RoomDTO(id, wsl, dimensions, players.freeSeatsCount());
    }
}
