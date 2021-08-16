package struct;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/16 19:30
 */
class LRUCacheTest {

    static LRUCache<Integer, String> cache;

    @BeforeAll
    static void setUp() {
        cache = new LRUCache<>(10);

        for (int i = 0; i < 10; i++) {
            cache.put(i, String.valueOf(i));
        }

        cache.put(201, "a");
    }

    @Test
    void testLRUCache() {
        String r1 = cache.get(9);
        assertEquals("9", r1);

        String r2 = cache.get(0);
        assertNull(r2);
    }
}