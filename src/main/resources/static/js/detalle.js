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
        recetaDiv.innerHTML = `
            <div class="receta">
                <h3>${receta.nombre}</h3>
                <img src="${receta.urlImagen}" alt="${receta.nombre}" class="receta-imagen" />
                <p><strong>Tipo de Cocina:</strong> ${receta.tipoDeCocina}</p>
                <p><strong>Ingredientes:</strong> ${receta.ingredientes.join(', ')}</p>
                <p><strong>Dificultad:</strong> ${receta.dificultadElaboracion}</p>
                <p><strong>Tiempo de Cocción:</strong> ${receta.tiempoCoccion}</p>
                <p><strong>Fecha de Creación:</strong> ${receta.fechaCreacion}</p>
                <p><strong>Popularidad:</strong> ${receta.popularidad} / 5</p>
            </div>
        `;
        contenedor.appendChild(recetaDiv);
    });
}

// Mostrar las recetas más populares
function mostrarRecetasDetalladas() {
    const contenedor = document.getElementById('recetas-detalle-container'); 
    const recetasDestacadas = recetas.sort((a, b) => b.popularidad - a.popularidad).slice(0, 4);
    mostrarRecetas(recetasDestacadas, contenedor);
}


// Llamar a las funciones al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    //mostrarRecetasDetalladas();
});




