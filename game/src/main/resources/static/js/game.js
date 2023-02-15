var gameplayId = window.location.href.split("/").pop();
var playerName = sessionStorage.getItem("playerName"); //(Math.random() + 1).toString(36).substring(2);
var stompClient;

const GameplayRole = {
    PARTICIPANT: "PARTICIPANT",
    SPECTATOR: "SPECTATOR"
}

const GameEvent = {
    CONTINUE: "CONTINUE",
    WIN: "WIN",
    DRAW: "DRAW",
    INFO: "INFO"
}

const Sign = {
    O: "O",
    X: "X"
}

var role = GameplayRole.SPECTATOR;

class Dimensions {
    constructor(rows, cols) {
        this.rows = rows;
        this.cols = cols;
    }
}

class GameDto {
    constructor(wsl, dimensions, oPlayer, xPlayer, role, currentTurnPlayer) {
        this.wsl = wsl;
        this.dimensions = dimensions;
        this.oPlayer = oPlayer;
        this.xPlayer = xPlayer;
        this.role = role;
        this.currentTurnPlayer = currentTurnPlayer;
    }
}

class CellClickAction {
    constructor(playerName, cellId) {
        this.playerName = playerName;
        this.cellId = cellId;
    }
}

class Event {
    constructor(gameEvent, payload) {
        this.gameEvent = gameEvent;
        this.payload = payload;
    }
}

class ActionPerformed {
    constructor(cellId, sign, nextTurnPlayer) {
        this.cellId = cellId;
        this.sign = sign;
        this.nextTurnPlayer = nextTurnPlayer;
    }
}

function createBoardCell(id) {
    let cell = document.createElement("div");
    cell.innerText = (id);
    cell.id = "c" + id;
    if (role == GameplayRole.PARTICIPANT) {
        cell.className = "grid-item available";
        cell.onclick = function(cell) {
            sendCellClickAction(new CellClickAction(playerName, id));
        };
    }
    return cell;
}

function createBoard(rows, cols) {
    let board = document.getElementById("board");
    board.style.setProperty('--grid-rows', rows);
    board.style.setProperty('--grid-cols', cols);
    for (i = 1; i <= (rows * cols); i++) {
        board.appendChild(createBoardCell(i));
    }
}

function createElements(gameplay) {
    role = gameplay.role;
    createBoard(gameplay.dimensions.rows, gameplay.dimensions.cols);
}

function initializeGame(user) {
    return fetch('/api/games/' + gameplayId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
}

function init() {
    initializeGame(playerName).then(response => {
        if (response.status == 200) {
            response.json().then(json => createElements(json));
        }
    });
}

function handleGameContinue(action) {
    document.getElementById("c" + action.cellId).innerText = action.sign;
    console.log("nex turn: " + action.nextTurnPlayer);
}

function readResponse(event) {
    switch (event.event) {
        case GameEvent.CONTINUE:
            handleGameContinue(event.payload);
            break;
        case GameEvent.WIN:
            break;
        case GameEvent.DRAW:
            break;
        case GameEvent.INFO:
            break;
    }
}

function connect() {
    var socket = new SockJS('/games-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/games/' + gameplayId, function(response) {
            readResponse(JSON.parse(response.body));
        });
    });
}

function sendCellClickAction(action) {
    stompClient.send("/app/gameplays/" + gameplayId, {}, JSON.stringify(action));
}

init();
connect();