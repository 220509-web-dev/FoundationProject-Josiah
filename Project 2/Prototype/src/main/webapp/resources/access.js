window.onload = function() {
    console.log('The page loaded!');
    let button = document.getElementById('logout-btn');
    button.addEventListener('click', logout);
}

function logout() {
    if (true) {
        let respData = fetch('http://localhost:8080/notecard/userauth', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({})
        })
}