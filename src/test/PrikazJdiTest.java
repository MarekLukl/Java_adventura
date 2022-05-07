import logika.Hra;
import org.junit.*;

import static org.junit.Assert.*;

public class PrikazJdiTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void premisteni() {
        // začíná se ve vstupní hale; jdi satna
       assertEquals("Jsi v mistnosti/prostoru smrdutá satna." + "\n" + "východy: vstupni_hala" + "\n"
               + "V místnosti je: euro(100), pistol(20 damage)" + "\n" + "Nikdo tu není.", hra.zpracujPrikaz("jdi satna"));
       assertFalse(hra.konecHry());
       assertEquals("satna", hra.getHerniPlan().getAktualniProstor().getNazev());
    }
}