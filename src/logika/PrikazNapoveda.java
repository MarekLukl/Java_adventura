package logika;

/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 *  Tato třída je součástí jednoduché textové hry.
 *
 *@author     Marek Lukl, Jarmila Pavlickova, Luboš Pavlíček
 *@version   1.0
 *
 */
public class PrikazNapoveda implements IPrikaz {
    
    private static final String NAZEV = "napoveda";
    private SeznamPrikazu platnePrikazy;
    private HerniPlan plan;
    
    
     /**
    *  Konstruktor třídy
    *
      * @param platnePrikazy seznam příkazů,
      *                       které je možné ve hře použít,
      *                       aby je napoveda mohla zobrazit uživateli.
      * @param plan
      */
    public PrikazNapoveda(SeznamPrikazu platnePrikazy, HerniPlan plan) {
        this.platnePrikazy = platnePrikazy;
        this.plan = plan;
    }
    
    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     *  vcelku primitivní zpráva, seznam dostupných příkazů, aktuální prostor a co a kdo se v něm nachází.
     *  
     *  @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String... parametry) {
        Prostor prostor = plan.getAktualniProstor();
        return "Tvým úkolem je vyzbrojit se a dostat se do komnaty\n"
        + "kde vykonáš spravedlnost na Putinovi.\n"
        + "\n"
        + "Můžeš zadat tyto příkazy:\n"
        + platnePrikazy.vratNazvyPrikazu() + "\n"
        + prostor.dlouhyPopis() + "\n"
        + prostor.vypisSeznamuVeci() + "\n"
        + prostor.vypisSeznamuPostav();
    }
    
     /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    @Override
      public String getNazev() {
        return NAZEV;
     }

}
