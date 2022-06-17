window.onload = function() {
    let button = document.getElementById('register-button');
    button.addEventListener('click', register);

    let button1 = document.getElementById('close-response');
    button1.addEventListener('click', closeResponse);

    let areaField = document.getElementById('register-postal-code');
    areaField.addEventListener('keyup', function(e) {
        if (e.key === 'Enter') {
            register();
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

    let feedbackContainer = document.getElementById('feedback');
    let errorContainer = document.getElementById('error-message');

    if (u && p && f) {
        
        // If the error message is being displayed, hide it
        errorContainer.setAttribute('hidden', true);

        let respData = fetch('http://localhost:8080/login-service/userauth', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({u,p,f,l,a1,a2,c,s,z})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);

            if (Math.floor(resp.status/100) == 2) {
                errorContainer.setAttribute('hidden', true);
                feedbackContainer.removeAttribute('hidden');
                feedbackContainer.innerText = "Registration Successful! You have been logged in.";
            }
            if (Math.floor(resp.status/100) >= 4) { // ADD CODE HANDLING
                errorContainer.removeAttribute('hidden');
                errorContainer.innerText = "Registration failed!";
            }
            return resp.json();
        })
        
        if (respData) {
            respData.then(data => {
                window.showResponse(`Code ${data['code']} : ${data['message']}`);
            });
        }

    } else {
        let ar = [u,p,f,l,a1,a2,c,s,z];
        let arr = [];
        let fields = ["username", "password", "first name", "last name", "address", "apt #", "city", "State", "postal code"];
        if (!ar[0]) { arr.push(fields[0]); }
        if (!ar[1]) { arr.push(fields[1]); }
        if (!ar[2]) { arr.push(fields[2]); }
        //if (!ar[3]) { arr.push(fields[3]); }
        //if (!ar[4]) { arr.push(fields[4]); }
        //if (!ar[5]) { arr.push(fields[5]); }
        //if (!ar[6]) { arr.push(fields[6]); }
        //if (!ar[7]) { arr.push(fields[7]); }
        //if (!ar[8]) { arr.push(fields[8]); }

        if (arr.every(function(e,i) {return e;})) { // if any not blank
          let message = "Missing "
          let last_e = arr.pop();

          if (arr.length == 1) {
            message += arr.pop() + " and " + last_e;
          }
          else if (arr.length != 0) {
            message += arr.join(", ");
            message += ", and " + last_e;
          }
          else {
            message += last_e;
          }
        console.log(message);
        errorContainer.removeAttribute('hidden');
        errorContainer.innerText = message;
        return;
        }
    }
}