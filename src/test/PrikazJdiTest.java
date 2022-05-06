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
        // začíná se ve vstupní hale; jdi šatna
       assertEquals("Jsi v mistnosti/prostoru smrdutá šatna." + "\n" + "východy: vstupní_hala" + "\n"
               + "V místnosti je: euro(100), pistol(20 damage)" + "\n" + "Nikdo tu není.", hra.zpracujPrikaz("jdi šatna"));
       assertFalse(hra.konecHry());
       assertEquals("šatna", hra.getHerniPlan().getAktualniProstor().getNazev());
    }
}