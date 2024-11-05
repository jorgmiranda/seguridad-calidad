

const usuariosRegistrados = [
    {
        email: "pe.falfan@duocuc.cl",
        password: "123456",
    },
    {
        email: "radahn@duocuc.cl",
        password: "123456",
    },
]

const email = {email:""};

const password = {password:""};


function ingresar() {
    const email = document.getElementById('email').value.toLowerCase();
    const password = document.getElementById('password').value;


    if (email === "" || password === "") {
        window.alert("Debe completar ambos campos!")
    } else {

        const result = usuariosRegistrados.filter((usuario) => usuario.email === email && usuario.password === password)

        console.log("encontrados: " + result.length);

        if (result.length === 1) {
            window.alert("Bienvenido "  + email + "!")

            const data = JSON.stringify({email:email, password: password});

            console.log("Resp del server: " + data);


            fetch("/testing", {
                method: 'GET' ,
                headers: {
                    'content-Type': 'application/json'
                },

            }, ).then(response => response.text())
                .then(data => {
                    console.log("Resp del server: " + data);
                })
                .catch(error => console.error('Error: ' + error));

            // $.ajax({
            //     url: 'http://localhost:8080/testing?correo=pe.falfan@duocuc.cl',
            //     type: 'POST',
            //     dataType: 'json',
            //     success: function (res) {
            //         console.log(res);
            //     }
            // })

            // window.location.replace('http://localhost:8080/testing?correo=pe.falfan@duocuc.cl')



        } else {
            window.alert("Comprueba tus credenciales!")
        }
    }

}

document.getElementById('ingresarBtn').addEventListener('click', ingresar);