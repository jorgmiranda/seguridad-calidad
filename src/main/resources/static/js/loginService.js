

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
        } else {
            window.alert("Comprueba tus credenciales!")
        }
    }

}

document.getElementById('ingresarBtn').addEventListener('click', ingresar);