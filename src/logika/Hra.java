package logika;

/**
 *  Třída Hra - třída představující logiku adventury.
 * 
 *  Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 *  a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 *  Vypisuje uvítací a ukončovací text hry.
 *  Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 *@author     Marek Lukl, Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;
    private Inventar inventar;
    private Hrac hrac;
    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        inventar = new Inventar();
        hrac = new Hrac("hráč",100,100);
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, inventar, this));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz((new PrikazVezmi(inventar, this, hrac)));
        platnePrikazy.vlozPrikaz(new PrikazPouzij(inventar,this));
        platnePrikazy.vlozPrikaz(new PrikazUtok(this,hrac, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazSnez(this,hrac,inventar));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(inventar,this,hrac));
        platnePrikazy.vlozPrikaz(new PrikazKomunikuj(inventar,this, hrac));

        //vložení rublů a euro do inventáře, ale v množství 0
        Vec euro = new Vec("euro", Vec.Status.ZVEDNUTELNE,"penize");
        Vec rubly = new Vec("rubly", Vec.Status.ZVEDNUTELNE,"penize");
        euro.setMnozstvi(200);
        rubly.setMnozstvi(2000);
        inventar.vlozitDoInvent("euro",euro);
        inventar.vlozitDoInvent("rubly",rubly);
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Vítejte!\n" +
               "Vaše jméno je Volodymyr Zelenskyj. \n" +
                "Nacházíte se v protiatomovém krytu na severu Ruska, ve kterém se ukrývá Vladimír Putin. \n" +
                "Vaším cílem je speciální vojenskou operací denacifikovat tento kryt a osvobodit ho od již zmiňovaného diktátora.\n" +
               "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
               "\n" +
               herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        return "Dík, že jste si zahráli.  Ahoj.";
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
           	parametry[i]= slova[i+1];  	
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám.";
        }
        return textKVypsani;
    }

     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní Prikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
    
     /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
     public HerniPlan getHerniPlan(){
        return herniPlan;
     }
    /**
     *  Metoda vrátí odkaz na inventář, je využita hlavně v testech,
     *  kde se jejím prostřednictvím hráčův inventář
     *
     *  @return     odkaz na herní plán
     */
    public Inventar getInventar(){
        return inventar;
    }

    public SeznamPrikazu getPlatnePrikazy() {
        return platnePrikazy;
    }

    public Hrac getHrac() {
        return hrac;
    }
}

