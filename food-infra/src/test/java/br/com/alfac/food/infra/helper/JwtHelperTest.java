package br.com.alfac.food.infra.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtHelperTest {

    // Método auxiliar para criar um token JWT de teste
    private String createTestToken(Long id, String who) {
        return "Bearer " + JWT.create()
                .withClaim("id", id)
                .withClaim("who", who)
                .sign(Algorithm.HMAC256("secret"));
    }

    @Test
    public void testGetID() {
        Long expectedId = 12345L;
        String token = createTestToken(expectedId, "user");

        Long actualId = JwtHelper.getID(token);

        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetWho() {
        String expectedWho = "user";
        String token = createTestToken(12345L, expectedWho);

        String actualWho = JwtHelper.getWho(token);

        assertEquals(expectedWho, actualWho);
    }

    @Test
    public void testGetIDWithInvalidToken() {
        String invalidToken = "Bearer invalid.token.here";

        assertThrows(Exception.class, () -> JwtHelper.getID(invalidToken));
    }

    @Test
    public void testGetWhoWithInvalidToken() {
        String invalidToken = "Bearer invalid.token.here";

        assertThrows(Exception.class, () -> JwtHelper.getWho(invalidToken));
    }
}