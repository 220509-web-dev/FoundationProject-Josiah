window.onload = function() {
    console.log('The page loaded!');

    let button = document.getElementById('get-user-by-username');
    button.addEventListener('click', getUserByUsername);

    let button1 = document.getElementById('get-user-by-id');
    button1.addEventListener('click', getUserById);

    let button2 = document.getElementById('get-all-users');
    button2.addEventListener('click', getAllUsers);

    let button3 = document.getElementById('close-response');
    button3.addEventListener('click', closeResponse);
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

function getUserById() {

    // Convenience references
    let iDmaybe = document.getElementById('inputId').value;
    let id = Number(iDmaybe);
    if (id && Number.isInteger(id) && 0 < id && id <= 2147483647) {

        let errorContainer = document.getElementById('error-message');
        // If the error message is being displayed, hide it
        errorContainer.setAttribute('hidden', true);

        let url = "http://localhost:8080/login-service/users?id="+id;

        let respData = fetch(url, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }  // , body: JSON.stringify({u, p})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);
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
        let errorContainer = document.getElementById('error-message');
        // Show the error message
        errorContainer.removeAttribute('hidden');
        errorContainer.innerText = "Invalid Input!";
    }
}

function getUserByUsername() {

    // Convenience references
    let usernameMaybe = ""+document.getElementById('inputEmail').value;
    if (usernameMaybe) {

        let errorContainer = document.getElementById('error-message');
        // If the error message is being displayed, hide it
        errorContainer.setAttribute('hidden', true);

        let url = "http://localhost:8080/login-service/users?username="+usernameMaybe;

        let respData = fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }//,
            //body: JSON.stringify({u, p})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);
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
        let errorContainer = document.getElementById('error-message');
        // Show the error message
        errorContainer.removeAttribute('hidden');
        errorContainer.innerText = "Invalid Input!";
    }
}


function getAllUsers() {
    let errorContainer = document.getElementById('error-message');
    // If the error message is being displayed, hide it
    errorContainer.setAttribute('hidden', true);

    let url = "http://localhost:8080/login-service/users";

    let respData = fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }//,
        //body: JSON.stringify({u, p})
    }).then(resp => {
        console.log(`Response status: ${resp.status}`);
        console.log(`Response timestamp: ${Date.now()}`);
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
}