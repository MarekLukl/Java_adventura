import logika.Hra;
import logika.Vec;
import org.junit.*;

import static org.junit.Assert.*;

public class PrikazVezmiTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void zvednutiVeci() {
        hra.zpracujPrikaz("jdi satna");
        assertEquals("Vzal jsi předmět: pistol" + "\n" + "Tvůj inventář: rubly(2000) euro(0) pistol(20 damage) ",
                hra.zpracujPrikaz("vezmi pistol"));
    }
    @Test
    public void plnaKapacita() {
        hra.zpracujPrikaz("jdi satna");
        Vec vec1 = new Vec("věc1", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec vec2 = new Vec("věc2", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec vec3 = new Vec("věc3", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec vec4 = new Vec("věc4", Vec.Status.ZVEDNUTELNE,"jidlo");
        hra.getInventar().getInventar().put("věc1",vec1);
        hra.getInventar().getInventar().put("věc2",vec2);
        hra.getInventar().getInventar().put("věc3",vec3);
        hra.getInventar().getInventar().put("věc4",vec4);
        assertEquals("Uneseš jen 4 věci (kromě peněz) musíš něco polozit",
                hra.zpracujPrikaz("vezmi pistol"));
    }
}