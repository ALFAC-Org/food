package br.com.alfac.food.infra.config.exception;

import br.com.alfac.food.core.exception.FoodException;
import br.com.alfac.food.core.exception.item.ItemError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApiErrorTest {

    @Test
    void testCreateErrorWithFoodException() {
        int statusCode = HttpStatus.BAD_REQUEST.value();
        FoodException foodException = new FoodException(new ItemError(String.valueOf(statusCode), "TEST_CODE"));
        ApiError apiError = ApiError.createError(foodException);

        assertEquals(statusCode, apiError.getStatus());
        assertEquals("TEST_CODE", apiError.getMessage());
        assertEquals("ITEM-400", apiError.getCode());
        assertNotNull(apiError.getDate());
    }

    @Test
    void testCreateDefaultApiValidationError() {
        ApiError apiError = ApiError.createDefaultApiValidationError();

        assertEquals(HttpStatus.BAD_REQUEST.value(), apiError.getStatus());
        assertEquals(ApiError.REQUISICAO_INVALIDA, apiError.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), apiError.getCode());
        assertNotNull(apiError.getDate());
    }

    @Test
    void testAddArguments() {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Test message", "TEST_CODE");
        ApiErrorItem apiErrorItem = new ApiErrorItem("ARG_CODE", "Argument message");

        apiError.addArguments(apiErrorItem);

        assertEquals(1, apiError.getArguments().size());
        assertEquals(apiErrorItem, apiError.getArguments().get(0));
    }
}