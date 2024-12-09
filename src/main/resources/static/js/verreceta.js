function copiarEnlace(elemento) {
    const recetaId = elemento.dataset.id; // Obtiene el ID desde el atributo data-id
    const baseUrl = window.location.origin; // Obtiene la URL base
    const enlace = `${baseUrl}/verreceta/${recetaId}`;
    
    // Crear un elemento temporal para copiar el texto
    const tempInput = document.createElement('input');
    document.body.appendChild(tempInput);
    tempInput.value = enlace;
    tempInput.select();
    document.execCommand('copy');
    document.body.removeChild(tempInput);

    // Mostrar un mensaje de confirmación
    alert('¡Enlace copiado al portapapeles!');
}

function showCommentModal(recetaId) {
    console.log("test" + recetaId);
    document.getElementById(recetaId).style.display = 'block';
}

function hideCommentModal(recetaId) {
    document.getElementById(recetaId).style.display = 'none';
}