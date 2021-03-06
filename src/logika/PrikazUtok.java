package logika;

import java.time.LocalDate;
import java.util.*;

/**
 *  Třída PrikazUtok implementuje pro hru příkaz utoc.
 *  Tato třída je součástí jednoduché textové hry.
 *  Vybrané postavě sníží hp o aktuální damage postavy.
 *  Napadené postavy následně snižují hp hráče, podle výše svého damage.
 *
 * @author     Marek Lukl
 * @version   1.0
 */

public class PrikazUtok implements IPrikaz{

    private static final String NAZEV = "utoc";
    private Hra hra;
    private Hrac hrac;
    private HerniPlan plan;

    public PrikazUtok(Hra hra, Hrac hrac, HerniPlan plan) {
        this.hra = hra;
        this.hrac = hrac;
        this.plan = plan;
    }

    /**
     *  Zjistí zda si zadal na koho utocit a zda se postava vyskytuje v aktuálním prostoru.
     *  Vybrané postavě sníží hp o aktuální damage postavy.
     *  Napadené postavy následně snižují hp hráče, podle výše svého damage.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Nádherný útok, ale musíš si nejdříve vybrat na koho budeš utocit";
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

    /**
     * Metoda odečte od postavy hráčův damage, vrátí výší jejího hp, v případě dosažení nuly postavu odebere.
     * Dále odebírá damage všech postav v místnosti od hráčova hp. A kontroluje zda je stále na živu.
     * Volá metodu superschopnost jednotlivých postav.
     * Pokud všechny straze zemřou otevře chodbu.
     * Pokud zabiješ uklízečku vypadne z ní kód k otevření komnaty.
     *
     * @param seznamPostav
     * @param nazevPostavy
     * @param prostor
     * @return true pokud hra soubojem skončila
     */
    public boolean jednoKoloBoje(Map<String,Postava> seznamPostav,String nazevPostavy, Prostor prostor) {
        Scanner sc = new Scanner(System.in);
        seznamPostav.get(nazevPostavy).upravitHp(-hrac.getDamage());
         System.out.println("Útok na " + nazevPostavy + ", ubral si " + hrac.getDamage());

         // pokud má postava menší hp než 1, odebere ji
         if(seznamPostav.get(nazevPostavy).getHp()<1){
             System.out.println(nazevPostavy + " je zneškodněn");
             seznamPostav.remove(nazevPostavy);

             //pokud jsou všechny straze mrtvé, chodba se otveře
             if(seznamPostav.isEmpty() && prostor.getNazev().equals("spolecenska_mistnost")){
                 Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor("chodba");
                 sousedniProstor.setZamceno(false);
                 System.out.println("Vstup do chodby je volný");
             }
             // pokud zabiješ uklízečku vypadne z ní kód ke komnatě
             if(nazevPostavy.equals("uklizecka")){
                 System.out.println("Zabil si nevinnou uklízečku a vypadl jí z kapsy paírek s číslem '42069'.");
             }
             // pokud porazíš Putina, dá ti možnost volby co s ním
             if(nazevPostavy.equals("Putin")){
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
                 System.out.println("Jsi mrtev. RIP Volodoymyr Zelenskyj 1978-01-25 - " + LocalDate.now() +  " Konec hry.");
                 hra.setKonecHry(true);
                 return true;
             }
             // zavolá strazníkovu superschopnost
             if(seznamPostav.get(s).getClass() == Straz.class){
                 seznamPostav.get(s).superschopnost(seznamPostav.get(s));
             }
             // zavolá superschopnost bosse
             if(seznamPostav.get(s).getClass() == Boss.class){
                 seznamPostav.get(s).superschopnost(seznamPostav.get(s));
             }
         }
        for(String s: seznamPostav.keySet()){
            System.out.println(seznamPostav.get(s).getNazev() +
                    " damage: " + seznamPostav.get(s).getDamage() + " hp: " + seznamPostav.get(s).getHp());
        }
        return false;
    }

    /**
     * Metoda dá hráči možnost vybrat si osud poraženého Putina.
     * A vypíše finální zprávy na konec hry.
     */
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
        System.out.println("Jak si přeješ. \n Teď vzhůru na tiskovku a všem o tom popovídej.");;
    }
}
