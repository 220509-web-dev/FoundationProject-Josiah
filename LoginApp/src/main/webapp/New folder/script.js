window.onload = function() {
    document.getElementById("login-btn").addEventListener("click", login);
}

function login() {

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch('/login-service/login', {
        method: 'POST',
        body: JSON.stringify({username, password})
    }).then(function () {
        console.log('login success!');
    }).catch(() => {
        console.log("oops");
    });

}