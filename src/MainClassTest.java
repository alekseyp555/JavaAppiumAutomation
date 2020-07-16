import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MainClassTest extends MainClass
{
    @Test
    public void testGetLocalNumber()
    {
        int expected = 14;
        int actual = getLocalNumber();
        assertTrue("Number !=14", actual == expected);
    }
}
