package logika;

public class PrikazSnez implements IPrikaz{

    private static final String NAZEV = "sněz";
    private final Hra hra;
    private final Hrac hrac;
    private final Inventar inventar;

    public PrikazSnez(Hra hra, Hrac hrac, Inventar inventar) {
        this.hra = hra;
        this.hrac = hrac;
        this.inventar = inventar;
    }

    /**
     * Metoda pro provedení příkazu ve hře.
     * Počet parametrů je závislý na konkrétním příkazu,
     * např. příkazy konec a napoveda nemají parametry
     * příkazy jdi, seber, polož mají jeden parametr
     * příkaz pouzij může mít dva parametry.
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
