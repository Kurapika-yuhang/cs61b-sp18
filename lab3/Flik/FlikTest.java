import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void isSameNumberTest() {
        int a = 126;
        int b = 126;
        assertTrue(Flik.isSameNumber(a,b));
    }
}
