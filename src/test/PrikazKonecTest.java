import logika.Hra;
import org.junit.*;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazKonecTest {
    private Hra hra;

    @Before
    public void setUp() {
        hra = new Hra();
    }

    @Test
    public void konecHryPrikazem() {
        assertEquals("hra ukončena příkazem konec",hra.zpracujPrikaz("konec"));
    }
    @Test
    public void neplatnyPrikazKonec() {
        assertEquals("Ukončit co? Nechápu, proč jsi zadal druhé slovo.",hra.zpracujPrikaz("konec něčeho"));
    }


}