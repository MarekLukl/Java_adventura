package logika;

import java.util.*;

public class PrikazUtok implements IPrikaz{

    private static final String NAZEV = "útoč";
    private final Hra hra;
    private final Hrac hrac;
    private HerniPlan plan;

    public PrikazUtok(Hra hra, Hrac hrac, HerniPlan plan) {
        this.hra = hra;
        this.hrac = hrac;
        this.plan = plan;
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
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Nádherný útok, ale musíš si nejdříve vybrat na koho budeš útočit";
        }
        String nazevPostavy = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Map<String, Postava> seznamPostav = prostor.getSeznamPostav();

        if(seznamPostav.containsKey(nazevPostavy)){
            if(jednoKoloBoje(seznamPostav,nazevPostavy, prostor)){
                return "";
            }
        }else{
            return "Tato postava v této místnosti není." + prostor.vypisSeznamuPostav();
        }
        return "Konec kola.";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    @Override
    public String getNazev() {return NAZEV;}

    public boolean jednoKoloBoje(Map<String,Postava> seznamPostav,String nazevPostavy, Prostor prostor) {
        Scanner sc = new Scanner(System.in);
        seznamPostav.get(nazevPostavy).upravitHp(-hrac.getDamage());
         System.out.println("Útok na " + nazevPostavy + ", ubral si " + hrac.getDamage());
         if(seznamPostav.get(nazevPostavy).getHp()<1){  // pokud má postava menší hp než 1, odebere ji
             System.out.println(nazevPostavy + " je mrtev");
             seznamPostav.remove(nazevPostavy);
             if(seznamPostav.isEmpty() && prostor.getNazev().equals("společenská_místnost")){
                 Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor("chodba");
                 sousedniProstor.setZamceno(false);   //pokud jsou všechny stráže mrtvé, chodba se otveře
                 System.out.println("Vstup do chodby je volný");
             }
             if(nazevPostavy.equals("Putin") && seznamPostav.isEmpty()){
                 dodelatPutina();
                 hra.setKonecHry(true);
                 return true;
             }
         }
         for(String s: seznamPostav.keySet()){   // odebere damage každé postavy v seznamu od hp hráče
             hrac.upravitHp(-seznamPostav.get(s).getDamage());
             System.out.println("Útok " + seznamPostav.get(s).getNazev() + ",ubral ti " +
                     seznamPostav.get(s).getDamage() + "\n" + "Tvoje hp: " + hrac.getHp() + "   (zmáčkni enter)");
             sc.nextLine();
             if(hrac.getHp()<1){
                 System.out.println("Jsi mrtev." +  " Konec hry.");
                 hra.setKonecHry(true);
                 return true;
             }
         }
        for(String s: seznamPostav.keySet()){
            System.out.println(seznamPostav.get(s).getNazev() +
                    " damage: " + seznamPostav.get(s).getDamage() + " hp: " + seznamPostav.get(s).getHp());
        }
        return false;
    }
    public void dodelatPutina(){
        Scanner sc = new Scanner(System.in);
        String [] moznosti = {"1","2","3","4"};
        String input = "";
        do{
            System.out.println("Dokázals to! Putin je poražen! \nTeď je jen na tobě co s ním událáš. " +
                    "\nPro předhození polárním medvědům napiš '1'   pro předvedení před soud v Haagu '2'  " +
                    "\npro předání možnosti volby ukrajinskému lidu '3'   pro penetraci mozku kulkou '4'");
            input =sc.nextLine();
        }while(!Arrays.stream(moznosti).anyMatch(input::equals)); // určí zda input se shoduje s jednou z možností
        System.out.println("Jak si přeješ. \nTeď utíkej na tiskovku a všem o tom popovídej.");;
    }
}
