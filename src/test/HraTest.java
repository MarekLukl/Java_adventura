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
    public void uspesnyKonecHry() {
        //uvítání
        assertEquals("Vítejte!\n" +
                "Vaše jméno je Volodymyr Zelenskyj. \n" +
                "Nacházíte se v protiatomovém krytu na severu Ruska, ve kterém se ukrývá Vladimír Putin. \n" +
                "Vaším cílem je speciální vojenskou operací denacifikovat tento kryt a osvobodit ho od již zmiňovaného diktátora.\n" +
                "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
                "\n" +
                hra.getHerniPlan().getAktualniProstor().dlouhyPopis(), hra.vratUvitani());

        assertFalse(hra.konecHry());
        assertEquals("vstupní_hala", hra.getHerniPlan().getAktualniProstor().getNazev());

        //1. začíná se ve vstupní hale; jdi šatna
        assertEquals("Jsi v mistnosti/prostoru smrdutá šatna." + "\n" + "východy: vstupní_hala" + "\n"
                + "V místnosti je: euro(100), pistol(20 damage)",
                hra.zpracujPrikaz("jdi šatna"));
        assertFalse(hra.konecHry());
        assertEquals("šatna", hra.getHerniPlan().getAktualniProstor().getNazev());

        //2. vezmi pistol
        assertEquals("Vzal jsi předmět: pistol" + "\n" +
                "Tvůj inventář: rubly(0) euro(0) pistol(20 damage) ", hra.zpracujPrikaz("vezmi pistol"));

        //3. vezmi euro
        assertEquals("Vzal jsi předmět: euro" + "\n" +
                "Tvůj inventář: rubly(0) euro(100) pistol(20 damage) ", hra.zpracujPrikaz("vezmi euro"));

        //4. jdi vstupní hala
        assertEquals("Jsi v mistnosti/prostoru rudá vstupní hala." + "\n" + "východy: šatna společenská_místnost" + "\n"
                        + "V místnosti je: automat_na_jídlo",
                hra.zpracujPrikaz("jdi vstupní_hala"));
        assertFalse(hra.konecHry());
        assertEquals("vstupní_hala", hra.getHerniPlan().getAktualniProstor().getNazev());

//        //5. použij automat na jídlo
//        assertEquals("bageta(20hp) stojí 5 euro" + "\n" + "banán(50hp) stojí 10 euro" + "\n" +
//                        "Pro koupi bagety napiště '1' pro koupi banánu napiště '2' pro konec nákupu napište '3'",
//                hra.zpracujPrikaz("použij automat_na_jídlo"));
//
//        //6. kup bagetu
//        assertEquals("Bageta koupena" +" \n" + "Konec interakce s automatem", hra.zpracujPrikaz("1"));
//
//        //7. použij automat na jídlo
//        assertEquals("bageta(20hp) stojí 5 euro" + "\n" + "banán(50hp) stojí 10 euro" +
//                        "Pro koupi bagety napiště '1' pro koupi banánu napiště '2' pro konec nákupu napište '3'",
//                hra.zpracujPrikaz("použij automat_na_jídlo"));
//
//        //8. kup banán
//        assertEquals("Banán koupena" + "\n" + "Konec interakce s automatem", hra.zpracujPrikaz("2"));
        hra.zpracujPrikaz("použij automat_na_jídlo");

        //9. jdi společenská místnost
        assertEquals("Jsi v mistnosti/prostoru společenská místnost pro volné chvíle ruských bratrů."
                        + "\n" + "východy: toalety chodba vstupní_hala" + "\n"
                        + "V místnosti je: gambling_automat" + "\n" +
                        "Osoby v místnosti:  stráž3 damage: 7 hp: 50, stráž2 damage: 6 hp: 40, stráž1 damage: 5 hp: 40",
                hra.zpracujPrikaz("jdi společenská_místnost"));
        assertFalse(hra.konecHry());
        assertEquals("společenská_místnost", hra.getHerniPlan().getAktualniProstor().getNazev());

        //10. jdi toalety
        assertEquals("Jsi v mistnosti/prostoru voňavé toalety." + "\n" + "východy: společenská_místnost" + "\n"
                        + "V místnosti je: exchange_automat",
                hra.zpracujPrikaz("jdi toalety"));
        assertFalse(hra.konecHry());
        assertEquals("toalety", hra.getHerniPlan().getAktualniProstor().getNazev());
    }
}