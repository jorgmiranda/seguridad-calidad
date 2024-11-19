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


