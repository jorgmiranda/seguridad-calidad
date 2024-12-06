package com.duoc.seguridad_calidad.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
