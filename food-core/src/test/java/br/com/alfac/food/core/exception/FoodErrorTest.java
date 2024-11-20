package br.com.alfac.food.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FoodErrorTest {
        @Test
    void testGetErrorCode() {
        FoodError foodError = mock(FoodError.class);
        when(foodError.getErrorCode()).thenReturn("ERROR_CODE");

        assertEquals("ERROR_CODE", foodError.getErrorCode());
    }

    @Test
    void testGetErrorMessage() {
        FoodError foodError = mock(FoodError.class);
        when(foodError.getErrorMessage()).thenReturn("Error message");

        assertEquals("Error message", foodError.getErrorMessage());
    }

    @Test
    void testGetStatusCode() {
        FoodError foodError = mock(FoodError.class);
        when(foodError.getStatusCode()).thenReturn(400);

        assertEquals(400, foodError.getStatusCode());
    }
}
