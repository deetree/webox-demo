package com.github.deetree;

/**
 * @author Mariusz Bal
 */
record Room(long id, int wsl, Dimensions dimensions, Players players) {

    GameDto createDto(String user) {
        return new GameDto(wsl,
                dimensions,
                players.findPlayerBySign(Sign.O),
                players.findPlayerBySign(Sign.X),
                GameplayRole.PARTICIPANT /*players.assignRole(user)*/);
    }
}
