package str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrTest {
    private final Str str = new Str();

    @Test
    public void testStrStr() {
        assertEquals(str.strStr("mississippi", "issi"), 1);
    }
}