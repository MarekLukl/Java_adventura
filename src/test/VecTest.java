import logika.Hra;
import logika.Vec;
import org.junit.*;

import static org.junit.Assert.*;

public class VecTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }


    @After
    public void tearDown() {
    }


    @Test
    public void zvednutelne() {
        Vec vecZvednutelna = new Vec("věcZ", Vec.Status.ZVEDNUTELNE,"jidlo");
        assertTrue(vecZvednutelna.lzeVzit());
        Vec vecNezvednutelna =new Vec("věcN", Vec.Status.POUZITELNE,"automat");
        assertFalse(vecNezvednutelna.lzeVzit());
    }
    @Test
    public void pouzitelne() {
        Vec vecZvednutelna = new Vec("věcZ", Vec.Status.ZVEDNUTELNE,"jidlo");
        assertFalse(vecZvednutelna.lzePouzit());
        Vec vecNezvednutelna =new Vec("věcN", Vec.Status.POUZITELNE,"automat");
        assertTrue(vecNezvednutelna.lzePouzit());
    }
}