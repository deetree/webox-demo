class RoomDTO {
    constructor(id, wsl, dimensions, freeSeats) {
        this.id = id;
        this.wsl = wsl;
        this.dimensions = dimensions;
        this.freeSeats = freeSeats;
    }
}

class Dimensions {
    constructor(rows, cols) {
        this.rows = rows;
        this.cols = cols;
    }
}

var rooms = new Map();

function createTableTextCell(className, innerText) {
    let cell = document.createElement("td");
    cell.createTextNode
    cell.className = className;
    cell.innerText = innerText;
    return cell;
}

function createTableButtonCell(className, innerText, link) {
    let cell = document.createElement("td");
    cell.createTextNode
    cell.className = className;
    let a = document.createElement("a");
    a.href = "/rooms/" + link;
    let button = document.createElement("button");
    button.innerText = innerText;
    a.appendChild(button);
    cell.appendChild(a);
    return cell;
}

function createRoomDOMElement(elem) {
    console.log("creating " + elem.id);
    let tr = document.createElement("tr");
    tr.id = "tr" + elem.id;
    tr.appendChild(createTableTextCell("col1", elem.id));
    tr.appendChild(createTableTextCell("col2", elem.wsl));
    tr.appendChild(createTableTextCell("col3", elem.dimensions.rows + "x" + elem.dimensions.cols));
    tr.appendChild(createTableTextCell("col4", elem.freeSeats));
    tr.appendChild(createTableButtonCell("col5", "Enter", elem.id));
    document.getElementById("roomsTableBody").appendChild(tr);
}

function replaceRoomDOMElement(original, replacement) {
    console.log("replacing " + replacement.id);
    original.getElementsByClassName("col1")[0].innerText = replacement.id;
    original.getElementsByClassName("col2")[0].innerText = replacement.wsl;
    original.getElementsByClassName("col3")[0].innerText = replacement.dimensions.rows + "x" + replacement.dimensions.cols;
    original.getElementsByClassName("col4")[0].innerText = replacement.freeSeats;
    original.id = "tr" + replacement.id;
}

function readResponse(response) {
    let keys = [];
    for (const e of response) {
        if (!rooms.has(e[0])) {
            createRoomDOMElement(e[1]);
        } else {
            replaceRoomDOMElement(document.getElementById("tr" + e[0]), e[1])
        }
        rooms.set(e[0], e[1]);
        keys.push(e[0]);
    }

    for (const r of rooms) {
        if (!keys.includes(r[0])) {
            document.getElementById("tr" + r[0]).remove();
            rooms.delete(r[0]);
        }
    }
}

function connect() {
    var socket = new SockJS('/rooms-websocket');
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/rooms', function(response) {
            readResponse(Object.entries(JSON.parse(response.body)));
        });
    });
}

function sendCreateRoomRequest(room) {
    fetch('/api/rooms', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(room)
    });
}

function loadStoredRooms() {
    fetch('/api/rooms', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json()
        .then(json => readResponse(Object.entries(json))));
}

function pickMaximum(rows, cols) {
    return rows >= cols ? rows : cols;
}

function isInvalidMatrixLength(value) {
    return value < 3 || value > 10;
}

function isWslGreaterThanDimensions(wsl, rows, cols) {
    return wsl > pickMaximum(rows, cols);
}

function isCreateRoomInputValid(wsl, rows, cols) {
    if (isInvalidMatrixLength(wsl) || isInvalidMatrixLength(rows) ||
        isInvalidMatrixLength(cols) || isWslGreaterThanDimensions(wsl, rows, cols))
        return false;
    return true;
}

function createRoom() {
    let wsl = parseInt(document.getElementById("wsl").value, 10);
    let rows = parseInt(document.getElementById("rows").value, 10);
    let cols = parseInt(document.getElementById("cols").value, 10);

    if (isCreateRoomInputValid(wsl, rows, cols)) {
        let room = new RoomDTO(0, wsl, new Dimensions(rows, cols), 2);
        sendCreateRoomRequest(room);
    } else
        alert("Cannot create the room. Check input values.")
}

function changeCreateRoomDivVisibility() {
    var div = document.getElementById("createRoomDiv");
    if (div.style.display == "none")
        div.style.display = "block";
    else
        div.style.display = "none";
}

loadStoredRooms();
connect();