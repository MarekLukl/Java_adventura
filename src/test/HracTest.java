import logika.Hra;
import logika.Hrac;
import org.junit.*;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HracTest {
    private Hra hra;


    @Before
    public void setUp() {
        hra = new Hra();
    }
    @Test
    public void zmenaHp(){
        hra.getHrac().setHp(50);
        hra.getHrac().upravitHp(20);
        assertEquals(70,hra.getHrac().getHp());
    }
}
