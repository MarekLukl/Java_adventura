package logika;

import java.util.*;
/**
 *  Třída PrikazKomunikuj implementuje pro hru příkaz komunikuj.
 *  Umožňuje interakci s některou z postav.
 *
 *@author     Marek Lukl
 *@version    1.0
 */

public class PrikazKomunikuj implements IPrikaz{

    private static final String NAZEV = "komunikuj";
    private Inventar inventar;
    private Hra hra;
    private Hrac hrac;


    public PrikazKomunikuj(Inventar inventar, Hra hra, Hrac hrac) {
        this.inventar = inventar;
        this.hra = hra;
        this.hrac = hrac;
    }

    /**
     * Metoda zajistí zda hráč zadal s kým komunikovat a zda jde o postavu nacházející se v dané místnosti.
     * Pokud ano zavolá metodu komunikovat.
     *
     * @param parametry jméno postavy
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "S kým chceš komunikovat??";
        }
        String nazevPostavy = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Postava postava = prostor.getPostavu(nazevPostavy);
        if(!prostor.getSeznamPostav().containsKey(nazevPostavy)){
            return "Tato postava v této místnosti není." + "\n" + prostor.vypisSeznamuPostav();
        }else{
            komunikace(postava);
        }
        return "Konec komunikace.";
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

    /**
     * Metoda rozliší o kterou postavu jde a podle toho zavolá správnou metodu komunikace s danou postavou.
     *
     * @param postava
     */
    public void komunikace(Postava postava){
        if(postava.getNazev().equals("uklizecka")){
            komunikaceSUklízečkou();
        }else if(postava.getNazev().equals("prodejce")){
            komunikaceSProdavačem();
        }else{
            System.out.println(postava.getNazev() + " s tebou komunikovat nechce");
        }
    }

    /**
     * Metoda vypíše zprávu o žádosti o pomoc od uklízečky a dá hráči možnost pommoci seřadit list zbraní podle abecedy.
     * Hráči možnost o opakované pokusy dokud se mu to nepodaří a nebo může kdykoliv konverzaci opustit.
     * Pokud názvy seřadí správně dostane kód k otevření komnaty.
     *
     */

    public void komunikaceSUklízečkou(){
        Scanner sc = new Scanner(System.in);
        boolean konec = false;

        Vector<String> spravneSerazeni = new Vector<>();
        spravneSerazeni.add("Negev");
        spravneSerazeni.add("Makarov");
        spravneSerazeni.add("SG-43");
        spravneSerazeni.add("Uzi");
        spravneSerazeni.add("AK-47");
        Collections.sort(spravneSerazeni);

        System.out.println("uklizecka je celá zoufalá z toho že má setřídit zbraně podle abecedy. Pomůžeš jí? \n");

        while(!konec){
            System.out.println("jedná se o: 'Negev' 'Makarov' 'SG-43' 'Uzi' 'AK-47' " +
                    "\n" + "Vypiš zbraně ve správném pořadí, nebo zadej '1' pro odmítnutí. (piš je vedle sebe, oddělené mezerou)");

            Vector<String> pokusRazeni = new Vector<>(razeniZbrani(sc.nextLine()));
            if(pokusRazeni.equals(spravneSerazeni)){
                System.out.println("Správně, uklizecka ti jako znamení vděku pošeptala číslo '42069'. Prý se ti bude hodit.");
                //po předání informace uklízečku odstraní
                hra.getHerniPlan().getAktualniProstor().getSeznamPostav().remove("uklizecka");
                konec = true;
            }else if(pokusRazeni.get(0).equals("1")){
                konec = true;
            }else{
                System.out.println("To není správně. Zkus to znovu.");
            }
        }
    }

    /**
     * Vezme hráčův input, ten rozdělí na jednotlivá slova, která postupně vloží do vektoru. Který se bude
     * porovnávat se správným řešením.
     *
     * @param radek
     * @return Vector<String> pokus o seřazení
     */
    public Vector<String> razeniZbrani(String radek){
        String [] slova = radek.split("[ \t]+");
        Vector<String> pokusSerazeni = new Vector<>();
        for(int i=0 ;i<slova.length;i++){
            pokusSerazeni.add(slova[i]);
        }
        return pokusSerazeni;
    }

    /**
     * Metoda vypisuje hráčovi nabídku prodejce a nechává ho si vybrat, zda koupí nebo odejde.
     * Vypisuje zprávy pokud po validaci nemá dostatek peněz, či už daný předmět vlastní.
     */
    public void komunikaceSProdavačem(){
        HashMap<String,Vec> nabidkaZbrojnice;
        String vybraneZbozi = "";
        Vec zbozi;
        boolean pokracovat = false;
        do{
            System.out.println("Prodejce ti nabízí: ");
            nabidkaZbrojnice = getNabidkaZbrojnice();
            vypisNabidkyZbrojnice(nabidkaZbrojnice);
            System.out.println("Stav tvé peněženky " + inventar.getStavPenezenky());
            vybraneZbozi = vyberNakupu();
            zbozi = nabidkaZbrojnice.get(vybraneZbozi);
            //pokud hráč takovou věc má nenechá ho koupit si ji znovu 
            if(!validaceUnikatnosti(vybraneZbozi)){
                return;
            }
            //pokud hráč zadal '1' ukončí se nákup
            if(vybraneZbozi.equals("1")){
                return;
            }else if(!validaceDostupnosti(vybraneZbozi,nabidkaZbrojnice)){
                System.out.println("Takové zboží není v nabídce.");
            }else if(!validaceProstredku(zbozi)){
                System.out.println("Nemáš dostatečně euro.");
                System.out.println("Máš pouze " + inventar.getStavPenezenky());
            }else{
                pokracovat = true;
            }
        }while(!pokracovat);

        nakup(zbozi);
    }

    /**
     * Metoda vytvoří nabídku zbrojnice, v podobě HashpMapy a tu pak vrátí.
     *
     * @return HashMap<String,vec> naplněná nabídkou zbrojnice
     */
    public HashMap<String,Vec> getNabidkaZbrojnice(){
        HashMap<String,Vec> nabidka = new HashMap<>();

        Vec desertEagle = new Vec("desert_eagle", Vec.Status.ZVEDNUTELNE,"zbran");
        desertEagle.setDamage(35);
        desertEagle.setCena(20);
        Vec ak47 = new Vec("AK-47", Vec.Status.ZVEDNUTELNE,"zbran");
        ak47.setDamage(50);
        ak47.setCena(30);
        Vec neprustrelnaVesta = new Vec("neprůstřelná_vesta", Vec.Status.ZVEDNUTELNE,"ochrana");
        neprustrelnaVesta.setHp(50);
        neprustrelnaVesta.setCena(30);

        nabidka.put("desert_eagle",desertEagle);
        nabidka.put("AK-47",ak47);
        nabidka.put("neprůstřelná_vesta",neprustrelnaVesta);

        return nabidka;
    }

    /**
     * Metoda vezme HashMapu plnou nabídky zbrojnice a vypíše všechny její části s důležitými atributy, hp a damage.
     *
     * @param nabidkaZbrojnice
     */
    public void vypisNabidkyZbrojnice(HashMap<String,Vec> nabidkaZbrojnice){
        String vypis = "";
        for(String s: nabidkaZbrojnice.keySet()){
            if(nabidkaZbrojnice.get(s).getTyp().equals("zbran")){
                vypis += nabidkaZbrojnice.get(s).getNazev() + " (" + nabidkaZbrojnice.get(s).getDamage() + " damage)"
                        + ": " + nabidkaZbrojnice.get(s).getCena() + "euro  ";
            }else if(nabidkaZbrojnice.get(s).getTyp().equals("ochrana")){
                vypis += nabidkaZbrojnice.get(s).getNazev() + " (+" + nabidkaZbrojnice.get(s).getHp() + " maxHp)"
                        + ": " + nabidkaZbrojnice.get(s).getCena() + "euro ";
            }
        }
        System.out.println(vypis);
    }

    /**
     * Metoda vypíše zprávu v případě, že je kapacita inventáře plná.
     * Pokud ne zeptá se co si chceš z nabídky koupit.
     * @return String vybrané zboží
     */
    public String vyberNakupu(){
        if(inventar.getInventar().size()>5){
            System.out.println("Uneseš jen 4 věci (kromě peněz) musíš něco polozit");
            return "1";
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadej název věci co si chceš koupit (pokud nic zadej '1'): ");
        return sc.nextLine();
    }

    /**
     * Metoda vypíše zprávu, pokud již vybrané zboží vlastníš.
     * @param vyber
     * @return String zpráva o vlastnictví
     */
    public boolean validaceUnikatnosti(String vyber){
        if(inventar.getInventar().containsKey(vyber)){
            System.out.println("Nemůžeš mít " + vyber + " dvakrát");
            return false;
        }
        return true;
    }

    /**
     * Metoda zjistí zda je vybrané zboží v nabídce.
     * @param vyber
     * @param nabidka
     * @return booelan podle dostupnosti zboží
     */
    public boolean validaceDostupnosti(String vyber, HashMap<String,Vec> nabidka){
        if(nabidka.containsKey(vyber)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Metoda zjistí zda máš dostatečně peněz na vybrané zboží
     * @param zbozi
     * @return boolean podle dostatku peněz
     */
    public boolean validaceProstredku(Vec zbozi){
        if(zbozi.getCena()>inventar.getVec("euro").getMnozstvi()){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Metoda odečte cenu zboží z tvých peněz a přidá ti zboží do inventáře.
     * Zkontroluju jestli se jedná o silnější zbraň než už máš a popřípadě zvedne damage.
     * Pokud se jedná o chraný předmět, zvedne tvé maximální Hp.
     * @param zbozi
     */
    public void nakup(Vec zbozi){
        inventar.getInventar().put(zbozi.getNazev(),zbozi);
        inventar.getVec("euro").upravitMnozstvi(inventar,-zbozi.getMnozstvi(),"euro");
        System.out.println("Nákup proběhl úspěšně.");
        if(zbozi.getTyp().equals("zbran") && hrac.getDamage()<zbozi.getDamage()){
            hrac.setDamage(zbozi.getDamage());
        }else if(zbozi.getTyp().equals("ochrana")){
            hrac.setMaxHp(hrac.getHp() + zbozi.getHp());
            System.out.println("Tvoje maximální hp vzrostlo na " + hrac.getMaxHp() + " tvoje aktuální hp je: " + hrac.getHp());
        }
    }
}
