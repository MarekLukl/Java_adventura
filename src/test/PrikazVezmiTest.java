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
        hra.zpracujPrikaz("jdi šatna");
        assertEquals("Vzal jsi předmět: pistol" + "\n" + "Tvůj inventář: rubly(2000) euro(200) pistol(20 damage) ",
                hra.zpracujPrikaz("vezmi pistol"));
    }
    @Test
    public void plnaKapacita() {
        hra.zpracujPrikaz("jdi šatna");
        Vec vec1 = new Vec("věc1", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec vec2 = new Vec("věc2", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec vec3 = new Vec("věc3", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec vec4 = new Vec("věc4", Vec.Status.ZVEDNUTELNE,"jidlo");
        hra.getInventar().vlozitDoInvent("věc1",vec1);
        hra.getInventar().vlozitDoInvent("věc2",vec2);
        hra.getInventar().vlozitDoInvent("věc3",vec3);
        hra.getInventar().vlozitDoInvent("věc4",vec4);
        assertEquals("Uneseš jen 4 věci (kromě peněz) musíš něco položit",
                hra.zpracujPrikaz("vezmi pistol"));
    }
}