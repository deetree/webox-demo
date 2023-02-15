class User {
    constructor(name, email, password, confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
    return isValid(user.name) && isValid(user.email) && isValidEmailPattern(user.email) &&
        isValid(user.password) && isValid(user.confirmPassword) &&
        (user.password == user.confirmPassword);
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

function addLoginButton(result) {
    let link = document.createElement("a");
    link.href = "/signin";
    let button = document.createElement("button");
    button.innerText = "Sign In";
    link.appendChild(button);
    setTimeout(function() {
        result.appendChild(link);
    }, 1000);
}

function redirectToLoginPage() {
    window.setTimeout(function() {
        location.href = "/signin";
    }, 10000);
}

function registerInBackend(user) {
    postData('/api/register', user).
    then(response => {
        response.text().then(text => showResult(text));
        if (response.status == 200) {
            let formElement = getElementById("form");
            clearInputs(formElement.getElementsByTagName('input'));
            formElement.className += " hide";
            document.body.className = "success";
            addLoginButton(getElementById("result"));
            redirectToLoginPage();
        } else {
            colorError();
            removeResult();
        }
    });
}

function register() {
    let user = new User(
        getElementById("name").value,
        getElementById("email").value,
        getElementById("password").value,
        getElementById("confirmPassword").value
    );

    if (validate(user))
        registerInBackend(user);
    else {
        let timeout = 10000;
        colorError(timeout);
        showResult("Something's wrong with the data you provided.\n" +
            "The fields cannot be empty, the email address has to be in valid format and passwords must match");
        removeResult(timeout);
    }
}

document.getElementsByClassName("message")[0].getElementsByTagName("a")[0].href = "/signin";