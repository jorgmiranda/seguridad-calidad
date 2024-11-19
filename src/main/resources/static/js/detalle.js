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




