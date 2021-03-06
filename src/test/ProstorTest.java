import logika.Hra;
import org.junit.*;

import static org.junit.Assert.*;

public class ProstorTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
        hra.zpracujPrikaz("jdi spolecenska_mistnost");
    }

    @After
    public void tearDown() {
    }


    @Test
    public void dlouhyPopis() {
        assertEquals("Jsi v mistnosti/prostoru společenská místnost pro volné chvíle ruských bratrů." + "\n"
                + "východy: toalety chodba vstupni_hala", hra.getHerniPlan().getAktualniProstor().dlouhyPopis());
    }
}