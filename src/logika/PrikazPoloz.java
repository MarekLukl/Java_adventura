package logika;
/**
 *  Třída PrikazPoloz implementuje pro hru příkaz poloz.
 *  Tato třída je součástí jednoduché textové hry.
 *  Odebere z inventáře věc.
 *
 *@author     Marek Lukl
 *
 */
public class PrikazPoloz implements IPrikaz{
    private static final String NAZEV = "poloz";
    private Inventar inventar;
    private Hra hra;
    private Hrac hrac;

    public PrikazPoloz(Inventar inventar, Hra hra, Hrac hrac) {
        this.inventar = inventar;
        this.hra = hra;
        this.hrac = hrac;
    }

    /**
     * Odebere věc z inventáře.
     * Zabrání v polození peněz, sníží damage pokud hráč polozil svou nejsilnější zbraň.
     * @param parametry - jako  parametr obsahuje název věci co má polozit.
     * @return zprávu o polození
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Co by jsi chtěl polozit?";
        }
        String nazevVeci = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Vec vec = inventar.getVec(nazevVeci);

        if(vec == null){
            return "Takovou věc nemáš." + "\n" + inventar.vypisInventare();
        }else{
            //peníze nelze polozit, není pro to důvod
            if(vec.getTyp()=="penize"){
                return "Peníze nepokládej, místo ti nezabírají a někdo by ti je mohl vzít.";
            }
            //pokud se jedná o zbraň, s momentálně největším damagem, naství damage na základních 10 a hledá zda má další zbraň
            else if(vec.getTyp()=="zbran"){
                if(hrac.getDamage()==vec.getDamage()){
                    hrac.setDamage(10);
                    for(String s: inventar.getInventar().keySet()){
                        if(inventar.getVec(s).getTyp()=="zbran" && inventar.getVec(s).getDamage()>hrac.getDamage()){
                            hrac.setDamage(inventar.getVec(s).getDamage());
                        }
                    }
                }
                //pokud pokládá ochranou věc, sníží se mu max hp o počet hp dané věci
            }else if(vec.getTyp().equals("ochrana")){
                hrac.setMaxHp(-vec.getHp());
            }
            prostor.pridejVec(vec);
            inventar.odebratZInvent(vec.getNazev());
            return "polozil jsi věc: " + vec.getNazev() + "\n" + inventar.vypisInventare();
        }
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
