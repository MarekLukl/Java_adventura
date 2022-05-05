import logika.Hra;
import logika.Hrac;
import logika.Inventar;
import logika.Vec;
import org.junit.*;

import org.junit.Test;

import static logika.Vec.Status.ZVEDNUTELNE;
import static org.junit.jupiter.api.Assertions.*;

public class InventarTest {
    private Hra hra;
    private Hrac hrac;
    private Inventar inventar;

    @Before
    public void setUp() {
        hra = new Hra();
        hrac = new Hrac("hráč",100,100);
        inventar = new Inventar();
    }

    @Test
    public void vlozitDoInvent() {
    assertFalse(inventar.getInventar().containsKey("věc"));
    Vec vec = new Vec("věc",ZVEDNUTELNE,"ochrana");
    inventar.vlozitDoInvent("věc",vec);
    assertTrue(inventar.getInventar().containsKey("věc"));
    }
}
