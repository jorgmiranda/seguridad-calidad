document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.carousel');
    var instances = M.Carousel.init(elems, {
        duration: 200, // Duración del cambio entre imágenes
        indicators: true // Mostrar indicadores
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const elems = document.querySelectorAll('select');
    const instances = M.FormSelect.init(elems);
});

document.addEventListener('DOMContentLoaded', function() {
    // Inicializar todos los textareas con Materialize
    M.textareaAutoResize(document.querySelectorAll('.materialize-textarea'));
});