package com.duoc.seguridad_calidad.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ResponseModelTest {
    @Test
    public void responseModelTest() {
        
        ResponseModel responseModel = new ResponseModel();

        responseModel.setMessageResponse("Operation successful");
        responseModel.setData("Some data");
        responseModel.setError(null);

        assertEquals("Operation successful", responseModel.getMessageResponse());
        assertEquals("Some data", responseModel.getData());
        assertEquals(null, responseModel.getError());
    }

    @Test
    public void testConstructorWithParameters() {
        // Arrange
        String expectedMessage = "Success";
        Object expectedData = "Sample Data";
        String expectedError = null;

        // Act
        ResponseModel responseModel = new ResponseModel(expectedMessage, expectedData, expectedError);

        // Assert
        assertNotNull(responseModel); // Verificar que el objeto no sea null
        assertEquals(expectedMessage, responseModel.getMessageResponse()); // Verificar el mensaje
        assertEquals(expectedData, responseModel.getData()); // Verificar los datos
        assertEquals(expectedError, responseModel.getError()); // Verificar el error
    }
}
