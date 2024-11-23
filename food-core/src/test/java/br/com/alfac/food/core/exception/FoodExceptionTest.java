package br.com.alfac.food.core.exception;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FoodExceptionTest {
    @Test
    public void testFoodExceptionWithFoodErrors() {
        FoodError mockError1 = Mockito.mock(FoodError.class);
        FoodError mockError2 = Mockito.mock(FoodError.class);
        Mockito.when(mockError1.getErrorMessage()).thenReturn("Error 1");
        Mockito.when(mockError2.getErrorMessage()).thenReturn("Error 2");

        List<FoodError> foodErrors = List.of(mockError1, mockError2);
        FoodException exception = new FoodException(foodErrors);

        assertEquals("Error 1,Error 2", exception.getMessage());
        assertEquals(foodErrors, exception.getFoodErrors());
        assertNull(exception.getFoodErros());
    }

    @Test
    public void testFoodExceptionWithSingleFoodError() {
        FoodError mockError = Mockito.mock(FoodError.class);
        Mockito.when(mockError.getErrorMessage()).thenReturn("Single Error");

        FoodException exception = new FoodException(mockError);

        assertEquals("Single Error", exception.getMessage());
        assertEquals(mockError, exception.getFoodErros());
        assertNull(exception.getFoodErrors());
    }

    @Test
    public void testFoodExceptionWithFoodErrorsAndThrowable() {
        FoodError mockError1 = Mockito.mock(FoodError.class);
        FoodError mockError2 = Mockito.mock(FoodError.class);
        Mockito.when(mockError1.getErrorMessage()).thenReturn("Error 1");
        Mockito.when(mockError2.getErrorMessage()).thenReturn("Error 2");

        List<FoodError> foodErrors = List.of(mockError1, mockError2);
        Throwable cause = new Throwable("Cause");
        FoodException exception = new FoodException(foodErrors, cause);

        assertEquals("Error 1,Error 2", exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(foodErrors, exception.getFoodErrors());
        assertNull(exception.getFoodErros());
    }

    @Test
    public void testFoodExceptionWithSingleFoodErrorAndThrowable() {
        FoodError mockError = Mockito.mock(FoodError.class);
        Mockito.when(mockError.getErrorMessage()).thenReturn("Single Error");

        Throwable cause = new Throwable("Cause");
        FoodException exception = new FoodException(mockError, cause);

        assertEquals("Single Error", exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(mockError, exception.getFoodErros());
        assertNull(exception.getFoodErrors());
    }

    @Test
    public void testFoodExceptionWithFormattedMessage() {
        FoodError mockError = Mockito.mock(FoodError.class);
        Mockito.when(mockError.getErrorMessage()).thenReturn("Error %s");

        FoodException exception = new FoodException(mockError, "Formatted");

        assertEquals("Error Formatted", exception.getMessage());
        assertEquals(mockError, exception.getFoodErros());
        assertNull(exception.getFoodErrors());
    }

    @Test
    public void testFoodExceptionWithFormattedMessageAndThrowable() {
        FoodError mockError = Mockito.mock(FoodError.class);
        Mockito.when(mockError.getErrorMessage()).thenReturn("Error %s");

        Throwable cause = new Throwable("Cause");
        FoodException exception = new FoodException(mockError, cause, "Formatted");

        assertEquals("Error Formatted", exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(mockError, exception.getFoodErros());
        assertNull(exception.getFoodErrors());
    }
}
