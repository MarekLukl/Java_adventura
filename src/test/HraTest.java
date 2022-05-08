import logika.Hra;
import org.junit.*;

import static org.junit.Assert.*;

public class HraTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void zacetekHry() {
        //uvítání
        assertEquals("Vítejte!\n" +
                "Vaše jméno je Volodymyr Zelenskyj. \n" +
                "Nacházíte se v protiatomovém krytu na severu Ruska, ve kterém se ukrývá Vladimír Putin. \n" +
                "Vaším cílem je speciální vojenskou operací denacifikovat tento kryt a osvobodit ho od již zmiňovaného diktátora.\n" +
                "Napište 'napoveda', pokud si nevíte rady, jak hrát dál.\n" +
                "\n" +
                hra.getHerniPlan().getAktualniProstor().dlouhyPopis(), hra.vratUvitani());
        assertFalse(hra.konecHry());
        assertEquals("vstupni_hala", hra.getHerniPlan().getAktualniProstor().getNazev());
    }
    @Test
    public void vratEpilog() {
        assertEquals("Dík, že jste si zahráli.  Ahoj.", hra.vratEpilog());
    }
    @Test
    public void zpracujVadnyPrikaz(){
        assertEquals("Nevím co tím myslíš? Tento příkaz neznám.",hra.zpracujPrikaz("vadny prikaz"));
    }
}