package logika;

import java.util.Scanner;
/**
 *  Třída PrikazPouzij implementuje pro hru příkaz použij.
 *  Tato třída je součástí jednoduché textové hry.
 *  Provede interakci s použitelnou věcí v aktuálním prostoru.
 *
 *@author     Marek Lukl
 *@version  1.0
 */

public class PrikazPouzij implements IPrikaz{

    private static final String NAZEV = "použij";
    private Inventar inventar;
    private Hra hra;

    public PrikazPouzij(Inventar inventar, Hra hra) {
        this.inventar = inventar;
        this.hra = hra;
    }

    /**
     * Zjistí zda danou věc může použít a pomocí metod uzitAutomatJidlo, uzitExchangeAutomat a uzitGamblingAutomat
     * provede interakci s vybraným automatem.
     *
     * @param parametry - jako  parametr obsahuje název věci co má použít.
     * @return zprávu o použití
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Výborný \"použij\" ale co??";
        }
        String nazevVeci = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Vec vec = prostor.getVec(nazevVeci);
        if(vec == null){
            return "Taková věc v této místnosti není." + "\n" + prostor.dlouhyPopis() + "\n" +
                    prostor.vypisSeznamuVeci();
        } else if(vec.lzePouzit()){

            if(vec.getNazev()=="automat_na_jídlo"){
                uzitAutomatJidlo();
            }else if(vec.getNazev()=="exchange_automat"){
                uzitExchangeAutomat();
            }else if(vec.getNazev()=="gambling_automat"){
                uzitGamblingAutomat();
            }

            return "Stav tvé peněženky: " + Inventar.getStavPenezenky();
        }
        return null;
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
     * Metoda vypíše nabídku invetáře a nechá postavu si vybrat, zda si chce něco koupit nebo odejít.
     * Zvaliduje input a podle něj provede buď konec interakce, nebo odečtení peněz a přidání předmětu do inventáře.
     */
    public void uzitAutomatJidlo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("bageta(20hp) stojí 5 euro" + "\n" + "banán(50hp) stojí 10 euro");
        if(inventar.getInventar().size()>5){
            System.out.println("Uneseš jen 4 věci (kromě peněz) musíš něco položit");
            return;
        }
        System.out.println("Pro koupi bagety napiště '1' pro koupi banánu napiště '2' pro konec nákupu napište '3'");
        String vyber ="";
        while(!(vyber.equals("3"))){
            vyber = sc.nextLine();
            if(vyber.equals("1")){
                if(inventar.getInventar().containsKey("bageta")){
                    System.out.println("Můžeš mít jen jednu bagetu.");
                    break;
                }
                Vec bageta = new Vec("bageta", Vec.Status.ZVEDNUTELNE,"jidlo");
                bageta.setHp(20);
                if(!(inventar.nakup(5,"euro"))){break;}
                inventar.getInventar().put(bageta.getNazev(),bageta);
                System.out.println("Bageta koupena");
                break;
            }else if(vyber.equals("2")){
                if(inventar.getInventar().containsKey("banán")){
                    System.out.println("Můžeš mít jen jeden banán.");
                    break;
                }
                Vec banan = new Vec("banán", Vec.Status.ZVEDNUTELNE,"jidlo");
                banan.setHp(50);
                if((!inventar.nakup(10,"euro"))){break;}
                inventar.getInventar().put(banan.getNazev(),banan);
                System.out.println("Banán koupen");
                break;
            }else if(vyber.equals("3")){
                break;
            } else{
                System.out.println("Neplatný příkaz");
            }
        }
        System.out.println("Konec interakce s automatem");
    }

    /**
     * Metoda se zeptá jakou měnu si hráč přeje a jaké množstí. Jeho input validuje a pokud je vše správně
     * převede rubly ne eura nebo obráceně.
     *
     */
    public void uzitExchangeAutomat(){
        Scanner sc = new Scanner(System.in);
        String input = "";
        int pozadovanaCastka;
        while(!(input.equals("3"))){
            System.out.println("Jakou měnu si přejete?  pro eura napište '1'   pro rubly '2'    pro ukončení '3'");
            input = sc.nextLine();
            if(input.equals("1")){
                pozadovanaCastka = pozadovanaCastka(1);
                inventar.getVec("euro").upravitMnozstvi(inventar,pozadovanaCastka,"euro"); //zvyší počet euro o požadovanou částku
                inventar.getVec("rubly").upravitMnozstvi(inventar,-(pozadovanaCastka * 100),"rubly"); // odebere směnené rubly
                break;
            }else if(input.equals("2")){
                pozadovanaCastka = pozadovanaCastka(2);
                inventar.getVec("rubly").upravitMnozstvi(inventar,pozadovanaCastka,"rubly"); //zvyší počet rublů o požadovanou částku
                inventar.getVec("euro").upravitMnozstvi(inventar,-((int) Math.round(pozadovanaCastka * 0.01)),"euro"); // odebere směnené eura
                break;
            }
        }
        System.out.println("Konec interakce s exchange automatem");
    }

    /**
     * Metoda se ptá na množství požadované měny a hodnotí zda má hráč dostatečné prostředky.
     *
     * @param mena
     * @return int požadované množství druhé měny
     */
    public int pozadovanaCastka(int mena){
        Scanner sc = new Scanner(System.in);
        String input;
        int pozadovanaCastka = 0;
        boolean nesplneno = true;

        if(mena ==1){

            int maxEur = (int) Math.round((inventar.getVec("rubly").
                    getMnozstvi())* 0.01); //vypočítaná a zaokrouhlená maximální hodnota vlastněných rublů v eurech
            System.out.println("Kolik euro chceš? Maximálně: " + maxEur);
            while(nesplneno){
                input = sc.nextLine();
                if(parseIntOrNull(input)==null){
                    System.out.println("musíš zadat celočíselné číslo");
                    continue;
                }
                pozadovanaCastka = parseIntOrNull(input);
                if(pozadovanaCastka > maxEur){
                    System.out.println("Na to nemáš. Maximálně: " + maxEur);
                    continue;
                }
                else{
                    nesplneno = false;
                }
            }
        }
        else if (mena == 2){
            int maxRublu = Math.round((inventar.getVec("euro").getMnozstvi())* 100);
            System.out.println("Kolik rublů chceš? Maximálně: " + maxRublu);
            while(nesplneno){
                input = sc.nextLine();
                if(parseIntOrNull(input)==null){
                    System.out.println("musíš zadat celočíselné číslo");
                    continue;
                }
                pozadovanaCastka = parseIntOrNull(input);
                if(pozadovanaCastka > maxRublu){
                    System.out.println("Na to nemáš. Maximálně: " + maxRublu);
                    continue;
                }
                else{
                    nesplneno = false;
                }
            }
        }
        return pozadovanaCastka;
    }

    /**
     * Metoda zkouší převádět input ve Stringu na Integer, pokud se nepovede vrátí null
     *
     * @param value
     * @return Integer požadované množství
     */
    public Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Metoda hráči popíše fungování automatu a provede ho celým procesem sázení.
     */
    public void uzitGamblingAutomat(){
        System.out.println("Vítej gamblere, tato hra funguje následovně, nejprve si vybereš kolik chceš vsadit." +
                "\n" + "Poté si tipneš jestli náhodně vygenerované číslo bude liché nebu sudé," +
                        "\n" + "pokud se trefíš obdržíš dvakrát tolik co si vsadil. Hodně štestí (a peněz)!!");
        Scanner sc = new Scanner(System.in);
        boolean konec = false;
        int sazenaCastka;
        String vybranaStrana;
        while(!konec){
            System.out.println("Stav vaší peněženky: " + inventar.getStavPenezenky() + "\n" +
                    "sázet můžete jen rubly");
            sazenaCastka = sazenaCastka();
            //pokud sázená častka je nula ukončit interakci
            if(sazenaCastka==0){
                continue;
            }
            vybranaStrana = vyberStrany();
            if(vybranaStrana.equals("3")){
                konec = true;
                continue;
            }
            gamble(sazenaCastka, vybranaStrana);
            konec = pokracovatGamble();
        }
        System.out.println("Konec interakce s gambling automatem.");
    }

    /**
     * Metoda se hráče ptá na výši sázené částky a číslo následně validuje.
     *
     * @return int vsazená částka
     */
    public int sazenaCastka(){
        Scanner sc = new Scanner(System.in);
        boolean konec = false;
        int castka = 0;
        String input;
        while(!konec){
            System.out.println("Zadej kolik chceš vsadit: " + "\n" + "(pokud nic zadej 'O')");
            input = sc.nextLine();
            if(parseIntOrNull(input)==null){
                System.out.println("musíš zadat celočíselné číslo");
                continue;
            }
            castka = parseIntOrNull(input);
            if(castka > inventar.getVec("rubly").getMnozstvi()){
                System.out.println("Na to nemáš. Maximálně: " + inventar.getVec("rubly").getMnozstvi());
                continue;
            }else{
                konec = true;
            }
        }
        return castka;
    }

    /**
     * Metoda se hráče ptá na výběr strany na vsazení a dává mu možnost od sázky ustoupit.
     *
     * @return String vybraná strana
     */
    public String vyberStrany(){
        Scanner sc = new Scanner(System.in);
        String strana = "";
        boolean konec = false;
        while(!konec){
            System.out.println("Vyber si stranu, pro liché napiš '1'   pro sudé '2'   pro konec '3'");
            strana = sc.nextLine();
            if(strana.equals("1") || strana.equals("2") || strana.equals("3")){
                konec = true;
            }
        }
        return strana;
    }

    /**
     * Metoda vyhodnocuje zda si hráč vsadil správně a podle výsledku buď zdvojnásobuje vsazené peníze nebo je odečítá.
     *
     * @param sazenaCastka
     * @param vybranaStrana
     */
    public void gamble(int sazenaCastka, String vybranaStrana){
        int randomCislo = getRandomCislo(1, 100);
        System.out.println("Výsledné číslo je: " + randomCislo);
        boolean sudost = (randomCislo % 2 == 0);
        if((sudost && vybranaStrana.equals("2") ||
        (!sudost && vybranaStrana.equals("1")))){ // zjišťuje zda se odhad sudosti shoduje se sudostí výsledného náhodného čísla
            System.out.println("Vyhráli jste " + sazenaCastka * 2 + "!!" + "\n");
            inventar.getVec("rubly").upravitMnozstvi(inventar,sazenaCastka,"rubly");
        }else{
            System.out.println("Prohráli jste." +"\n" + "Příště to určitě vyjde!" + "\n");
            inventar.getVec("rubly").upravitMnozstvi(inventar,-sazenaCastka,"rubly");
        }
    }

    /**
     * Metoda vrací random zaokrouhlené číslo v rozmezí, které se určí vloženými parametry.
     *
     * @param min
     * @param max
     * @return int random zaokrouhlené číslo
     */
    public static int getRandomCislo(int min, int max) {
        return (int) Math.round(((Math.random() * (max - min)) + min));
    }

    /**
     * Metoda hráči nabízí pokračovat v sázení nebo odejít
     *
     * @return hráčovo rozhodnutí o pokračování v sázení
     */
    public boolean pokracovatGamble(){
        Scanner sc = new Scanner(System.in);
        System.out.println("pro konec napiš '1'   pro pokračování cokoliv jiného: ");
        String input = sc.nextLine();
        if(input.equals("1")){
            return true;
        }
        return false;
    }
}
