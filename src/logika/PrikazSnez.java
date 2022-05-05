package logika;

/**
 *  Třída PrikazSnez implementuje pro hru příkaz sněz.
 *  Tato třída je součástí jednoduché textové hry.
 *  Odebere vybranou věc (jídlo) z inventáře a podle hp dané věci zvýší aktuální hp
 *
 *@author     Marek Lukl
 *
 */

public class PrikazSnez implements IPrikaz{

    private static final String NAZEV = "sněz";
    private Hra hra;
    private Hrac hrac;
    private Inventar inventar;

    public PrikazSnez(Hra hra, Hrac hrac, Inventar inventar) {
        this.hra = hra;
        this.hrac = hrac;
        this.inventar = inventar;
    }

    /**
     *
     * Zjistí zda máš vybrané jídlo, pokud ano odebere ho a doplní tvé aktuální hp podle hp daného jídla.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Copak by jsi rád papinkal?";
        }
        String nazevJidla = parametry[0];
        Vec jidlo = inventar.getVec(nazevJidla);

        if(jidlo== null){
            return "Takové jídlo nemáš" + "\n" + inventar.vypisInventare();
        }
        hrac.upravitHp(jidlo.getHp());
        inventar.odebratZInvent(nazevJidla);
        String vypisHp = "Tvé hp: " + hrac.getHp();
        return vypisHp;
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
