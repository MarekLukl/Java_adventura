import logika.Hra;
import logika.Hrac;
import org.junit.*;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HracTest {
    private Hra hra;
    private Hrac hrac;

    @Before
    public void setUp() {
        hra = new Hra();
        hrac = new Hrac("hráč",100,100);
    }
    @Test
    public void změnaHp(){
        hrac.setHp(50);
        hrac.upravitHp(20);
        assertEquals(70,hrac.getHp());
    }
}
