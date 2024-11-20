package br.com.alfac.food.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FoodErrosImplTest {
    // @Test
    // public void testGetErrorCode() {
    //     FoodErrosImpl foodError = Mockito.mock(FoodErrosImpl.class, Mockito.CALLS_REAL_METHODS);
    //     Mockito.when(foodError.getErrorCode()).thenCallRealMethod();
    //     Mockito.when(foodError.getAppPrefix()).thenReturn("APP");
    //     Mockito.when(foodError.getErrorCode()).thenReturn("001");

    //     String expectedErrorCode = "APP-001";
    //     assertEquals(expectedErrorCode, foodError.getErrorCode());
    // }

    @Test
    public void testGetErrorMessage() {
        FoodErrosImpl foodError = Mockito.mock(FoodErrosImpl.class, Mockito.CALLS_REAL_METHODS);
        Mockito.when(foodError.getErrorMessage()).thenCallRealMethod();
        Mockito.when(foodError.getErrorMessage()).thenReturn("Sample error message");

        String expectedErrorMessage = "Sample error message";
        assertEquals(expectedErrorMessage, foodError.getErrorMessage());
    }
    
}
