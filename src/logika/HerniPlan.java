package logika;


/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan {
    
    private Prostor aktualniProstor;
    private Inventar inventar;
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();

    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor vstupniHala = new Prostor("vstupní_hala","vsupní hala s automatem na bagety", false);
        Prostor satna = new Prostor("šatna", "smrdutá šatna",false);
        Prostor spolecMistnost = new Prostor("společenská_místnost","společenská místnost se střážemi a automat na gambling", false);
        Prostor toalety = new Prostor("toalety","voňavé toalety s automatem na směnu peněz", false);
        Prostor chodba = new Prostor("chodba","prázdná chodba",true);
        Prostor vezeni = new Prostor("vězení","věznice pro Putinovu opozici", false);
        Prostor zbrojnice = new Prostor("zbrojnice","zbrojnice s možností zakoupit si zbraně",false);
        Prostor komnata = new Prostor("komnata","komnata s pozlacenými zdmi a nečetně obrazy Putina",true);
        
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

        Vec pistol = new Vec("pistol",true, false, "zbran");
        Vec euro = new Vec("euro",true,false,"penize");
        euro.setMnozstvi(100);
        Vec rubly = new Vec("rubly",true,false,"penize");
        Vec automatNaJidlo = new Vec("automat_na_jídlo", false, true,"stroj");
        Vec automatNaSmenu = new Vec("exchange_automat",false,true,"stroj");
        Vec automatNaGamble = new Vec("gambling_automat",false,true,"stroj");
        Vec desertEagle = new Vec("desert_eagle",true,false,"zbran");
        Vec ak47 = new Vec("AK-47",true,false,"zbran");

        vstupniHala.pridejVec(automatNaJidlo);
        satna.pridejVec(euro);
        satna.pridejVec(pistol);
        spolecMistnost.pridejVec(automatNaGamble);
        toalety.pridejVec(automatNaSmenu);
        zbrojnice.pridejVec(desertEagle);
        zbrojnice.pridejVec(ak47);

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
