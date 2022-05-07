import logika.Hra;
import logika.Inventar;
import logika.Vec;
import org.junit.*;

import static org.junit.Assert.*;

public class PrikazPolozTest {
    private Hra hra;
    private Inventar inventar;

    @Before
    public void setUp() {
        hra = new Hra();
        inventar = hra.getInventar();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void polozeniVeci(){
        Vec vec = new Vec("věc", Vec.Status.ZVEDNUTELNE,"jidlo");
        hra.getInventar().getInventar().put("věc",vec);
        assertEquals("polozil jsi věc: " + vec.getNazev() + "\n" + "Tvůj inventář: rubly(2000) euro(0) ",
                hra.zpracujPrikaz("poloz věc"));
    }
    @Test
    public void polozeniPenez(){
        assertEquals("Peníze nepokládej, místo ti nezabírají a někdo by ti je mohl vzít.",
                hra.zpracujPrikaz("poloz euro"));
    }
    @Test
    public void neuplnyPrikaz(){
        assertEquals("Peníze nepokládej, místo ti nezabírají a někdo by ti je mohl vzít.",
                hra.zpracujPrikaz("poloz euro"));
    }
}