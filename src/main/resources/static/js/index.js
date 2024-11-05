const recetas = [
    {
        nombre: "Tacos al Pastor",
        tipoDeCocina: "Mexicana",
        ingredientes: [
            "Tortillas de maíz",
            "Carne de cerdo marinado",
            "Piña",
            "Cebolla",
            "Cilantro",
            "Salsa",
        ],
        paisDeOrigen: "México",
        dificultadElaboracion: "Media",
        instruccionesPreparacion: "Marinar la carne, cocinarla en un trompo, servir en tortillas con piña, cebolla y cilantro.",
        tiempoCoccion: "30 minutos",
        urlImagen: "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
        fechaCreacion: "2023-01-10",
        popularidad: 5, // Escala del 1 al 5
    },
    {
        nombre: "Paella",
        tipoDeCocina: "Española",
        ingredientes: [
            "Arroz",
            "Mariscos",
            "Pollo",
            "Pimiento rojo",
            "Azafrán",
            "Caldo de pollo",
        ],
        paisDeOrigen: "España",
        dificultadElaboracion: "Alta",
        instruccionesPreparacion: "Cocinar el arroz con el caldo y añadir los ingredientes. Cocinar a fuego lento hasta que el arroz esté tierno.",
        tiempoCoccion: "1 hora",
        urlImagen: "https://www.nestleprofessional-latam.com/sites/default/files/styles/np_recipe_detail/public/2022-07/paella.png?itok=CBvKkcsa",
        fechaCreacion: "2023-02-15",
        popularidad: 4,
    },
    {
        nombre: "Sushi",
        tipoDeCocina: "Japonesa",
        ingredientes: [
            "Arroz para sushi",
            "Alga nori",
            "Pescado crudo",
            "Aguacate",
            "Vinagre",
            "Salsa de soja",
        ],
        paisDeOrigen: "Japón",
        dificultadElaboracion: "Alta",
        instruccionesPreparacion: "Cocinar el arroz y mezclar con vinagre. Enrollar con alga nori y añadir los ingredientes. Cortar en piezas.",
        tiempoCoccion: "45 minutos",
        urlImagen: "https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/0749D9BC-260D-40F4-A07F-54814C4A82B4/Derivates/A73A7793-F3EE-4B90-ABA4-1CC1A0C3E18F.jpg",
        fechaCreacion: "2023-03-05",
        popularidad: 5,
    },
    {
        nombre: "Curry de Pollo",
        tipoDeCocina: "India",
        ingredientes: [
            "Pechuga de pollo",
            "Cebolla",
            "Tomate",
            "Curry en polvo",
            "Leche de coco",
            "Arroz",
        ],
        paisDeOrigen: "India",
        dificultadElaboracion: "Media",
        instruccionesPreparacion: "Saltear la cebolla, añadir el pollo y el curry. Incorporar el tomate y la leche de coco, cocinar a fuego lento.",
        tiempoCoccion: "40 minutos",
        urlImagen: "https://i.blogs.es/8c3360/pollo_curry/450_1000.jpg",
        fechaCreacion: "2023-04-20",
        popularidad: 4,
    },
    {
        nombre: "Pasta Carbonara",
        tipoDeCocina: "Italiana",
        ingredientes: [
            "Espagueti",
            "Huevos",
            "Panceta",
            "Queso parmesano",
            "Pimienta negra",
            "Sal",
        ],
        paisDeOrigen: "Italia",
        dificultadElaboracion: "Baja",
        instruccionesPreparacion: "Cocinar la pasta, mezclar con panceta dorada y añadir la mezcla de huevos y queso. Revolver y servir.",
        tiempoCoccion: "20 minutos",
        urlImagen: "https://www.gourmet.cl/wp-content/uploads/2016/12/Carbonara-editada.jpg",
        fechaCreacion: "2023-05-10",
        popularidad: 5,
    },
];

// Función para mostrar recetas en una sección dada
function mostrarRecetas(recetas, contenedor) {
    recetas.forEach(receta => {
        const recetaDiv = document.createElement('div');
        recetaDiv.className = 'col s4'; 
        // recetaDiv.innerHTML = `
        //     <div class="receta">
        //         <h3>${receta.nombre}</h3>
        //         <img src="${receta.urlImagen}" alt="${receta.nombre}" class="receta-imagen" />
        //         <p><strong>Tipo de Cocina:</strong> ${receta.tipoDeCocina}</p>
        //         <p><strong>Ingredientes:</strong> ${receta.ingredientes.join(', ')}</p>
        //         <p><strong>Dificultad:</strong> ${receta.dificultadElaboracion}</p>
        //         <p><strong>Tiempo de Cocción:</strong> ${receta.tiempoCoccion}</p>
        //         <p><strong>Fecha de Creación:</strong> ${receta.fechaCreacion}</p>
        //         <p><strong>Popularidad:</strong> ${receta.popularidad} / 5</p>
        //     </div>
        // `;
         recetaDiv.innerHTML = `
            <div class="receta">
                <h3>${receta.nombre}</h3>
                <img src="${receta.urlImagen}" alt="${receta.nombre}" class="receta-imagen" />
                <p><strong>Tipo de Cocina:</strong> ${receta.tipoDeCocina}</p>
                <p><strong>Dificultad:</strong> ${receta.dificultadElaboracion}</p>
                <p><strong>Tiempo de Cocción:</strong> ${receta.tiempoCoccion}</p>
               
            </div>
        `;
        contenedor.appendChild(recetaDiv);
    });
}

// Mostrar las recetas más populares
function mostrarRecetasDestacadas() {
    const contenedor = document.getElementById('recetas-destacadas-container'); // Cambiar el ID aquí
    const recetasDestacadas = recetas.sort((a, b) => b.popularidad - a.popularidad).slice(0, 3);
    mostrarRecetas(recetasDestacadas, contenedor);
}

// Mostrar las nuevas recetas
function mostrarNuevasRecetas() {
    const contenedor = document.getElementById('recetas-nuevas-container'); // Cambiar el ID aquí
    const recetasNuevas = recetas.sort((a, b) => new Date(b.fechaCreacion) - new Date(a.fechaCreacion)).slice(0, 3);
    mostrarRecetas(recetasNuevas, contenedor);
}

// Llamar a las funciones al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    //mostrarRecetasDestacadas();
   // mostrarNuevasRecetas();
});


// Función para filtrar recetas
function filtrarRecetas() {
    const nombre = document.getElementById('nombre').value.toLowerCase();
    const tipoCocina = document.getElementById('tipoCocina').value.toLowerCase();
    const ingredientes = document.getElementById('ingredientes').value.toLowerCase();
    const paisOrigen = document.getElementById('paisOrigen').value.toLowerCase();
    const dificultad = document.getElementById('dificultad').value;

    const recetasFiltradas = recetas.filter(receta => {
        return (
            (nombre === '' || receta.nombre.toLowerCase().includes(nombre)) &&
            (tipoCocina === '' || receta.tipoDeCocina.toLowerCase().includes(tipoCocina)) &&
            (ingredientes === '' || receta.ingredientes.some(ing => ing.toLowerCase().includes(ingredientes))) &&
            (paisOrigen === '' || receta.paisOrigen.toLowerCase().includes(paisOrigen)) &&
            (dificultad === '' || receta.dificultadElaboracion === dificultad)
        );
    });

    // Limpiar el contenedor y mostrar las recetas filtradas
    const contenedor = document.getElementById('recetas-filtradas-container');
    contenedor.innerHTML = ''; // Limpiar contenedor
    mostrarRecetas(recetasFiltradas, contenedor); // Mostrar recetas filtradas
}

// Llamar a la función de filtrado al hacer clic en el botón
//document.getElementById('filtrarBtn').addEventListener('click', filtrarRecetas);


