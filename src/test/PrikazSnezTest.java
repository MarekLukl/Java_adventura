import logika.Hra;
import logika.Vec;
import org.junit.*;

import static org.junit.Assert.*;

public class PrikazSnezTest {
    private Hra hra;


    @Before
    public void setUp() {
        hra = new Hra();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void nemasJidlo() {
        assertEquals("Takové jídlo nemáš" + "\n" + hra.getInventar().vypisInventare(),
                hra.zpracujPrikaz("sněz jídlo"));
    }
    @Test
    public void snezeniJidla() {
        Vec banan = new Vec("banán", Vec.Status.ZVEDNUTELNE,"jidlo");
        banan.setHp(50);
        hra.getInventar().getInventar().put("banán",banan);
        hra.getHrac().setHp(20);
        assertEquals("Tvé hp: 70", hra.zpracujPrikaz("sněz banán"));
    }
    @Test
    public void neuplnyPrikaz() {
        assertEquals("Copak by jsi rád papinkal?", hra.zpracujPrikaz("sněz"));
    }
}