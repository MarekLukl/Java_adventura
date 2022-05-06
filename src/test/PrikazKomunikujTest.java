import logika.Hra;
import org.junit.*;

import static org.junit.Assert.*;

public class PrikazKomunikujTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void chybejiciPostava() {
        assertEquals("Tato postava v této místnosti není." + "\n" +
                "Nikdo tu není.",hra.zpracujPrikaz("komunikuj postava"));
    }
}
