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
 *@version    1.0
 */

public class HerniPlan {
    
    private Prostor aktualniProstor;
    
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
        Prostor vstupniHala = new Prostor("vstupni_hala","rudá vstupní hala", false);
        Prostor satna = new Prostor("satna", "smrdutá satna",false);
        Prostor spolecMistnost = new Prostor("spolecenska_mistnost","společenská místnost pro volné chvíle ruských bratrů", false);
        Prostor toalety = new Prostor("toalety","voňavé toalety", false);
        Prostor chodba = new Prostor("chodba","prázdná chodba",true);
        Prostor sklad = new Prostor("sklad","poloprázdný sklad se sovětskou technikou", false);
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
        chodba.setVychod(sklad);
        chodba.setVychod(zbrojnice);
        chodba.setVychod(komnata);
        sklad.setVychod(chodba);
        zbrojnice.setVychod(chodba);
        komnata.setVychod(chodba);

        Vec pistol = new Vec("pistol", Vec.Status.ZVEDNUTELNE,"zbran");
        pistol.setDamage(20);
        Vec euro = new Vec("euro", Vec.Status.ZVEDNUTELNE,"penize");
        euro.setMnozstvi(100);
        Vec voda = new Vec("voda", Vec.Status.ZVEDNUTELNE,"jidlo");
        Vec automatNaJidlo = new Vec("automat_na_jidlo", Vec.Status.POUZITELNE,"stroj");
        Vec automatNaSmenu = new Vec("exchange_automat", Vec.Status.POUZITELNE,"stroj");
        Vec automatNaGamble = new Vec("gambling_automat", Vec.Status.POUZITELNE,"stroj");

        vstupniHala.pridejVec(automatNaJidlo);
        satna.pridejVec(euro);
        satna.pridejVec(pistol);
        spolecMistnost.pridejVec(voda);
        spolecMistnost.pridejVec(automatNaGamble);
        toalety.pridejVec(automatNaSmenu);

        Straz straz1 = new Straz("straz1",40,5);
        Straz straz2 = new Straz("straz2",40,6);
        Straz straz3 = new Straz("straz3",50,7);
        Zamestnanec prodejce = new Zamestnanec("prodejce",35,50);
        Zamestnanec uklizecka = new Zamestnanec("uklizecka",20,1);
        Boss putin = new Boss("Putin",400, 16);

        spolecMistnost.pridejPostavu(straz1);
        spolecMistnost.pridejPostavu(straz2);
        spolecMistnost.pridejPostavu(straz3);
        zbrojnice.pridejPostavu(prodejce);
        sklad.pridejPostavu(uklizecka);
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
