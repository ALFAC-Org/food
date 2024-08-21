package core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CoreExampleTest {

    @Test
    public void testAssertTrue() {
        assertTrue(true);
    }
}
