package br.com.alfac.food.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StatusCodeTest {
    @Test
    public void testBadRequestCode() {
        assertEquals(400, StatusCode.BAD_REQUEST.getCode());
    }

    @Test
    public void testNotFoundCode() {
        assertEquals(404, StatusCode.NOT_FOUND.getCode());
    }
}
