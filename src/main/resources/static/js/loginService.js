const email = {email:""};

const password = {password:""};


function ingresar() {
    const email = document.getElementById('email').value.toLowerCase();
    const password = document.getElementById('password').value;

    if (email === "" || password === "") {
        window.alert("Debe completar ambos campos!")
    } else {

        // fetch("/ingresar?email="+email+"&password="+password, {
        //     method: 'POST' ,
        //     headers: {
        //         'content-Type': 'application/json'
        //     },
        //     body:{}
        //
        // }, ).then(response => response.text())
        //     .then(data => {
        //         // console.log("Resp del server: " + data);
        //     })
        //     .catch(error => console.error('Error: ' + error));
    }

}

document.getElementById('ingresarBtn').addEventListener('click', ingresar);