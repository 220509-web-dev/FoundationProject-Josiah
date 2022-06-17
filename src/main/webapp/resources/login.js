window.onload = function() {
    console.log('The page loaded!');
    let button = document.getElementById('login-button');
    button.addEventListener('click', login);

    let button1 = document.getElementById('logout-btn');
    button1.addEventListener('click', logout);

    let button2 = document.getElementById('close-response');
    button2.addEventListener('click', closeResponse);

    let passwordField = document.getElementById('login-password');
    passwordField.addEventListener('keyup', function(e) {
        if (e.key === 'Enter') {
            login();
        }
    });
}

function closeResponse() {
    let responseContainer = document.getElementById('response-container');
    responseContainer.setAttribute('hidden', true);
}

function showResponse(message) {
    let responseContainer = document.getElementById('response-container');
    responseContainer.removeAttribute('hidden');

    let msgElem = document.getElementById('response-text');
    msgElem.innerText = message;
}

function login() {

    // Convenience references
    let usernameInput = document.getElementById('login-username');
    let passwordInput = document.getElementById('login-password');
    let errorContainer = document.getElementById('error-message');
    
    let u = usernameInput.value;
    let p = passwordInput.value;

    if (u && p) {
        
        // If the error message is being displayed, hide it
        errorContainer.setAttribute('hidden', true);

        let respData = fetch('http://localhost:8080/login-service/userauth', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({u, p})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);

//            if(resp.status == 406) {
//                errorContainer.removeAttribute('hidden');
//                errorContainer.innerText = "Already Logged in!";
//                return;
//            }
//            if (Math.floor(resp.status/100) != 2) {
//                errorContainer.removeAttribute('hidden');
//                errorContainer.innerText = "Login failed!";
//                return;
//            }


            return resp.json();
        });
        
        if (respData) {
            respData.then(data => {
                window.showResponse(`Code ${data['code']} : ${data['message']}`);
                //let successMsgContainer = document.createElement('p');
                //successMsgContainer.setAttribute('class', 'alert alert-success');
                //successMsgContainer.innerText = `Login successful! Welcome, ${data['firstName']}!`;
                //document.getElementById('login-container').appendChild(successMsgContainer);

            });
        }

    } else {

        // Show the error message
        errorContainer.removeAttribute('hidden');
        errorContainer.innerText = "You must provide a username and password!";
    }
}

function logout() {
    if (true) {
        let respData = fetch('http://localhost:8080/login-service/userauth', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }//,
            //body: JSON.stringify({})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);
            return resp.json();
        })
        if (respData) {
            respData.then(data => {
                window.showResponse(`Code ${data['code']} : ${data['message']}`);
            });
        }
    }
}