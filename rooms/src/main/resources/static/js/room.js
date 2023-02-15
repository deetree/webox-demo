const Action = {
    SEAT_TAKE: "SEAT_TAKE",
    SEAT_RELEASE: "SEAT_RELEASE"
}

const Event = {
    PLAYERS_BROADCAST_EVENT: "PLAYERS_BROADCAST_EVENT",
    GAME_EVENT: "GAME_EVENT"
}

const Sign = {
    O: "O",
    X: "X"
}

class SeatAction {
    constructor(player, sign, action) {
        this.player = player;
        this.sign = sign;
        this.action = action;
    }
}

var roomId = window.location.href.split("/").pop();
var takenSeat = "";
var playerName = (Math.random() + 1).toString(36).substring(2);

sessionStorage.setItem("playerName", playerName);

function handleSeatActionChange(name, labelId, buttonId, sign) {
    changeLabel(labelId, name);
    if (name != "" && name != playerName)
        disableSeatButton(buttonId);
    if ((name == "" && takenSeat == "") || (name == playerName && takenSeat == sign))
        enableSeatButton(buttonId);
}

function readPlayersStatus(players) {
    handleSeatActionChange(players.O, "oPlayerSectionLabel", "oPlayerSeatButton", Sign.O);
    handleSeatActionChange(players.X, "xPlayerSectionLabel", "xPlayerSeatButton", Sign.X);
}

function replacePage(url) {
    window.location.replace(url);
}

function readResponse(response) {
    if (response.event == Event.PLAYERS_BROADCAST_EVENT)
        readPlayersStatus(response.payload);
    else if (response.event == Event.GAME_EVENT)
        replacePage(response.payload);
}

function connect() {
    var socket = new SockJS('/rooms-websocket');
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/rooms/' + roomId, function(response) {
            readResponse(JSON.parse(response.body));
        });
    });
}

function sendSeatAction(action) {
    fetch('/api/rooms/' + roomId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(action)
    });
}

function changeLabel(elemId, text) {
    if (text == "" || text == null)
        text = "free";
    document.getElementById(elemId).innerText = text;
}

function enableSeatButton(seatButtonId) {
    document.getElementById(seatButtonId).disabled = false;
}

function disableSeatButton(seatButtonId) {
    document.getElementById(seatButtonId).disabled = true;
}

function oPlayerSeatAction() {
    let sign = Sign.O;
    let action;
    if (takenSeat == "") {
        takenSeat = sign;
        changeLabel("oPlayerSectionLabel", playerName);
        changeLabel("oPlayerSeatButton", "stand up");
        disableSeatButton("xPlayerSeatButton");
        action = Action.SEAT_TAKE;
    } else if (takenSeat == sign) {
        takenSeat = "";
        changeLabel("oPlayerSectionLabel", "");
        changeLabel("oPlayerSeatButton", "sit down");
        enableSeatButton("xPlayerSeatButton");
        action = Action.SEAT_RELEASE;
    }
    sendSeatAction(new SeatAction(playerName, sign, action));
}

function xPlayerSeatAction() {
    let sign = Sign.X;
    let action;
    if (takenSeat == "") {
        takenSeat = sign;
        changeLabel("xPlayerSectionLabel", playerName);
        changeLabel("xPlayerSeatButton", "stand up");
        disableSeatButton("oPlayerSeatButton");
        action = Action.SEAT_TAKE;
    } else if (takenSeat == sign) {
        takenSeat = "";
        changeLabel("xPlayerSectionLabel", "");
        changeLabel("xPlayerSeatButton", "sit down");
        enableSeatButton("oPlayerSeatButton");
        action = Action.SEAT_RELEASE;
    }
    sendSeatAction(new SeatAction(playerName, sign, action));
}

function loadRoomPlayers() {
    fetch('/api/rooms/' + roomId, {
        method: 'GET'
    }).then(r => r.json().then(json => readPlayersStatus(json)));
}

loadRoomPlayers();
connect();