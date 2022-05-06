import logika.Hra;
import org.junit.*;

import static org.junit.Assert.*;

public class PrikazUtokTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void chybejiciBojovnik() {
        assertEquals("Tato postava v této místnosti není."
                + hra.getHerniPlan().getAktualniProstor().vypisSeznamuPostav(), hra.zpracujPrikaz("útoč postava"));
    }
}