package logika;


/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Marek Lukl, Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */

public class HerniPlan {
    
    private Prostor aktualniProstor;
    private Inventar inventar;
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví vstupní halu.
     */
    public HerniPlan() {
        zalozProstoryHry();
    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví vstupní halu
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor vstupniHala = new Prostor("vstupní_hala","rudá vstupní hala", false);
        Prostor satna = new Prostor("šatna", "smrdutá šatna",false);
        Prostor spolecMistnost = new Prostor("společenská_místnost","společenská místnost pro volné chvíle ruských bratrů", false);
        Prostor toalety = new Prostor("toalety","voňavé toalety", false);
        Prostor chodba = new Prostor("chodba","prázdná chodba",true);
        Prostor vezeni = new Prostor("vězení","věznice pro Putinovu politickou opozici", false);
        Prostor zbrojnice = new Prostor("zbrojnice","zbrojnice s možností zakoupit si zbraně",false);
        Prostor komnata = new Prostor("komnata","komnata s pozlacenými zdmi a nesčetně obrazy Putina",true);
        
        // přiřazují se průchody mezi prostory (sousedící prostory)
        vstupniHala.setVychod(satna);
        vstupniHala.setVychod(spolecMistnost);
        satna.setVychod(vstupniHala);
        spolecMistnost.setVychod(vstupniHala);
        spolecMistnost.setVychod(toalety);
        spolecMistnost.setVychod(chodba);
        toalety.setVychod(spolecMistnost);
        chodba.setVychod(spolecMistnost);
        chodba.setVychod(vezeni);
        chodba.setVychod(zbrojnice);
        chodba.setVychod(komnata);
        vezeni.setVychod(chodba);
        zbrojnice.setVychod(chodba);
        komnata.setVychod(chodba);

        Vec pistol = new Vec("pistol", Vec.Status.ZVEDNUTELNE,"zbran");
        pistol.setDamage(20);
        Vec euro = new Vec("euro", Vec.Status.ZVEDNUTELNE,"penize");
        euro.setMnozstvi(100);
        Vec rubly = new Vec("rubly", Vec.Status.ZVEDNUTELNE,"penize");
        Vec automatNaJidlo = new Vec("automat_na_jídlo", Vec.Status.POUZITELNE,"stroj");
        Vec automatNaSmenu = new Vec("exchange_automat", Vec.Status.POUZITELNE,"stroj");
        Vec automatNaGamble = new Vec("gambling_automat", Vec.Status.POUZITELNE,"stroj");
        Vec desertEagle = new Vec("desert_eagle", Vec.Status.ZVEDNUTELNE,"zbran");
        desertEagle.setDamage(40);
        Vec ak47 = new Vec("AK-47", Vec.Status.ZVEDNUTELNE,"zbran");
        ak47.setDamage(50);

        vstupniHala.pridejVec(automatNaJidlo);
        satna.pridejVec(euro);
        satna.pridejVec(pistol);
        spolecMistnost.pridejVec(automatNaGamble);
        toalety.pridejVec(automatNaSmenu);
        zbrojnice.pridejVec(desertEagle);
        zbrojnice.pridejVec(ak47);

        Stráž straz1 = new Stráž("stráž1",40,5);
        Stráž straz2 = new Stráž("stráž2",40,6);
        Stráž straz3 = new Stráž("stráž3",50,7);
        Boss putin = new Boss("Putin",600, 16);

        spolecMistnost.pridejPostavu(straz1);
        spolecMistnost.pridejPostavu(straz2);
        spolecMistnost.pridejPostavu(straz3);
        komnata.pridejPostavu(putin);

        aktualniProstor = vstupniHala;  // hra začíná ve vstupní hale
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
    }

}
