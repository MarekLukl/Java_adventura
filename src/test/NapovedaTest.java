import logika.HerniPlan;
import logika.Hra;
import logika.Prostor;
import logika.SeznamPrikazu;
import org.junit.*;

import static org.junit.Assert.*;

public class NapovedaTest {
    private Hra hra;
    private SeznamPrikazu platnePrikazy;
    private HerniPlan plan;

    @Before
    public void setUp() {
        hra = new Hra();
        plan = hra.getHerniPlan();
        platnePrikazy = hra.getPlatnePrikazy();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void vypsaniNapovedy() {
        Prostor prostor = plan.getAktualniProstor();
        assertEquals("Tvým úkolem je vyzbrojit se a dostat se do komnaty\n"
                + "kde vykonáš spravedlnost na Putinovi.\n"
                + "\n"
                + "Můžeš zadat tyto příkazy:\n"
                + platnePrikazy.vratNazvyPrikazu() + "\n"
                + prostor.dlouhyPopis() + "\n"
                + prostor.vypisSeznamuVeci() + "\n"
                + prostor.vypisSeznamuPostav(),hra.zpracujPrikaz("nápověda"));
    }
}