package str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrTest {
    private final StrStr str = new StrStr();

    @Test
    public void testStrStr() {
        assertEquals(str.sunday("mississippi", "issi"), 1);
    }
}