class LoginUser {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }
}

class RequestUser {
    constructor(email, name) {
        this.email = email;
        this.name = name;
    }
}

function getElementById(id) {
    return document.getElementById(id);
}

function isValid(value) {
    return value != null && value != "";
}

function isValidEmailPattern(email) {
    let validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    return email.match(validRegex);
}

function validate(user) {
    return isValid(user.email) && isValidEmailPattern(user.email) &&
        isValid(user.password);
}

function postData(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}

function clearInputs(collection) {
    for (let element of collection) {
        element.value = "";
    }
}

function colorError(timeout) {
    document.body.className = "error";
    setTimeout(function() {
        document.body.className = "normal";
    }, timeout == null ? 2000 : timeout);
}

function showResult(text) {
    let resultElement = getElementById("result");
    let className = "";
    if (text != "" && text != null)
        className = "show";
    else
        className = "";
    resultElement.className = className;
    resultElement.innerText = text;
}

function removeResult(timeout) {
    setTimeout(function() {
        showResult("");
    }, timeout == null ? 2000 : timeout);
}

function redirectToRooms(user, token) {//todo send request to rooms with token?
    window.location.replace("/rooms");
}

function loginInBackend(user) {
    postData('/api/signin', user)
        .then(response => {
            response.json().then(json => {
                let name = json.name;
                showResult(json.body);
                let token = json.token;
                let redirect = json.redirect;

               if (response.status == 200) {
                   let formElement = getElementById("form");
                   clearInputs(formElement.getElementsByTagName('input'));
                   formElement.className += " hide";
                   document.body.className = "success";
                   redirectToRooms(new RequestUser(user.email, name), token);
               } else {
                   colorError();
                   removeResult();
               }
            });
        });
}

function login() {
    let user = new LoginUser(
        getElementById("email").value,
        getElementById("password").value
    );

    if (validate(user))
        loginInBackend(user);
    else {
        let timeout = 10000;
        colorError(timeout);
        showResult("Something's wrong with the data you provided.\n" +
            "The fields cannot be empty and the email address has to be in valid format.");
        removeResult(timeout);
    }
}

document.getElementsByClassName("message")[0].getElementsByTagName("a")[0].href = "/register";