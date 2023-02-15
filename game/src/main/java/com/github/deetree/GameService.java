package com.github.deetree;

import com.github.deetree.gamerun.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Random;
import java.util.UUID;

/**
 * @author Mariusz Bal
 */
@Service
class GameService {

    @Autowired
    private Games games;

    @Value("${GATEWAY_ENTRYPOINT:localhost}")
    private String gatewayEntrypoint;

    String createGame(Room room, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        games.create(new Game(uuid, room, new Board(room.dimensions(),
                Judge.of(new Config(room.dimensions(), new Threshold(room.wsl())))),
                new Turn(new Random())));
        return composeGameUri(uuid, request);
    }

    private String composeGameUri(String uuid, HttpServletRequest request) {
        URI forwarded = createForwardedURI(request);
        return UriComponentsBuilder
                .newInstance()
                .scheme(forwarded.getScheme())
                .host(/*forwarded.getHost()*/gatewayEntrypoint)
                .port(forwarded.getPort())
                .pathSegment("games", uuid)
                .toUriString();
    }

    private URI createForwardedURI(HttpServletRequest request) {
        return URI.create(request.getHeader("x-forwarded-proto")
                + "://"
                + request.getHeader("x-forwarded-host"));
    }

    GameDto loadGameData(String gameplayId, String requestUser) {
        return games.findGame(gameplayId).gameRoom().createDto(requestUser);
    }
}
