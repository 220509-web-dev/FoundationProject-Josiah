window.onload = function() {
    let button = document.getElementById('register-button');
    button.addEventListener('click', register);

    let areaField = document.getElementById('register-postal-code');
    areaField.addEventListener('keyup', function(e) {
        if (e.key === 'Enter') {
            register();
        }
    });
}

function register() {

    // Convenience references
    let u = document.getElementById('register-username').value;
    let p = document.getElementById('register-password').value;
    let f = document.getElementById('register-first-name').value;
    let l = document.getElementById('register-last-name').value;
    let a1 = document.getElementById('register-address1').value;
    let a2 = document.getElementById('register-address2').value;
    let c = document.getElementById('register-city').value;
    let s = document.getElementById('register-state').value;
    let z = document.getElementById('register-postal-code').value;

    let errorContainer = document.getElementById('error-message');

    if (u && p && f && l) {
        
        // If the error message is being displayed, hide it
        errorContainer.setAttribute('hidden', true);

        let respData = fetch('http://localhost:8080/login-service/userauth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({u,p,f,l,a1,a2,c,s,z})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);

            if (Math.floor(resp.status/100) != 2) {
                errorContainer.removeAttribute('hidden');
                errorContainer.innerText = "Login failed!";
                return;
            }

            return resp.json();
        })
        
        if (respData) {
            respData.then(data => {
                let successMsgContainer = document.createElement('p');
                successMsgContainer.setAttribute('class', 'alert alert-success');
                successMsgContainer.innerText = `Register successful! Welcome, ${data['firstname']}!`;
                document.getElementById('login-container').appendChild(successMsgContainer);
            });
        }

    } else {
        let m = "Missing ";
        if (!u) {m+="a username,";}
        if (!p) {m+="a password, ";}
        if (!f) {m+="a first name, "}
        if (!l) {m+="a last name, "}
        // Show the error message
        errorContainer.removeAttribute('hidden');
        errorContainer.innerText = m;
    }
}