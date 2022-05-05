package logika;

import java.util.*;

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
     * Metoda pro provedení příkazu ve hře.
     * Počet parametrů je závislý na konkrétním příkazu,
     * např. příkazy konec a napoveda nemají parametry
     * příkazy jdi, seber, polož mají jeden parametr
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
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
            return "Tato postava v této místnosti není." + prostor.vypisSeznamuPostav();
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
    public void komunikace(Postava postava){
        if(postava.getNazev().equals("uklízečka")){
            komunikaceSUklízečkou();
        }else if(postava.getNazev().equals("prodejce")){
            komunikaceSProdavačem();
        }else{
            System.out.println(postava.getNazev() + " s tebou komunikovat nechce");
        }
    }
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

        System.out.println("Uklízečka je celá zoufalá z toho že má setřídit zbraně podle abecedy. Pomůžeš jí? \n");

        while(!konec){
            System.out.println("jedná se o: 'Negev' 'Makarov' 'SG-43' 'Uzi' 'AK-47' " +
                    "\n" + "Vypiš zbraně ve správném pořadí, nebo zadej '1' pro odmítnutí. (piš je vedle sebe, oddělené mezerou)");

            Vector<String> pokusRazeni = new Vector<>(razeniZbrani(sc.nextLine()));
            if(pokusRazeni.equals(spravneSerazeni)){
                System.out.println("Správně, uklízečka ti jako znamení vděku pošeptala číslo '42069'. Prý se ti bude hodit.");
                //po předání informace uklízečku odstraní
                hra.getHerniPlan().getAktualniProstor().getSeznamPostav().remove("uklízečka");
                konec = true;
            }else if(pokusRazeni.get(0).equals("1")){
                konec = true;
            }else{
                System.out.println("To není správně. Zkus to znovu.");
            }
        }
    }
    public Vector<String> razeniZbrani(String radek){
        String [] slova = radek.split("[ \t]+");
        Vector<String> pokusSerazeni = new Vector<>();
        for(int i=0 ;i<slova.length;i++){
            pokusSerazeni.add(slova[i]);
        }
        return pokusSerazeni;
    }

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
    public String vyberNakupu(){
        if(inventar.getInventar().size()>5){
            System.out.println("Uneseš jen 4 věci (kromě peněz) musíš něco položit");
            return "1";
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadej název věci co si chceš koupit (pokud nic zadej '1'): ");
        return sc.nextLine();
    }
    public boolean validaceUnikatnosti(String vyber){
        if(inventar.getInventar().containsKey(vyber)){
            System.out.println("Nemůžeš mít " + vyber + " dvakrát");
            return false;
        }
        return true;
    }
    public boolean validaceDostupnosti(String vyber, HashMap<String,Vec> nabidka){
        if(nabidka.containsKey(vyber)){
            return true;
        }else{
            return false;
        }
    }
    public boolean validaceProstredku(Vec zbozi){
        if(zbozi.getCena()>inventar.getVec("euro").getMnozstvi()){
            return false;
        }else{
            return true;
        }
    }
    public void nakup(Vec zbozi){
        inventar.vlozitDoInvent(zbozi.getNazev(),zbozi);
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
