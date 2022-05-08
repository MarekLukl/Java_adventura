package logika;

import java.time.LocalDate;
import java.util.Scanner;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Mění aktuální polohu.
 *  Vyhodnocuje zda do dane mistnosti hrác muze jít. Reší odemykání uzamknutých místností.
 *  
 *@author     Marek Lukl, Jarmila Pavlickova, Luboš Pavlíček
 *@version    1.0
 */

public class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;
    private Inventar inventar;
    private Hra hra;
    
    /**
    *  Konstruktor třídy
    *  @param plan herní plán, ve kterém se bude ve hře "chodit"
    * @param inventar
     * @param hra
     */
    public PrikazJdi(HerniPlan plan, Inventar inventar, Hra hra) {
        this.plan = plan;
        this.inventar = inventar;
        this.hra = hra;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        } else if(sousedniProstor.getZamceno()==true){
            if(sousedniProstor.getNazev()=="chodba"){
                return odemykaniChodby(sousedniProstor);
            }else{
                if(odemkniKomnatu(sousedniProstor)==true){
                    System.out.println("Komnata otevřena");
                    plan.setAktualniProstor(sousedniProstor);
                }else{
                    return "Komnata je zamčena";
                }
            }
        }
        plan.setAktualniProstor(sousedniProstor);
        return sousedniProstor.dlouhyPopis() + "\n" + sousedniProstor.vypisSeznamuVeci() + "\n" +
                    sousedniProstor.vypisSeznamuPostav();
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

    /**
     *  Metoda se ptá hráče na na kód k otevření komnaty. Pokud zadá správně komnata se otevře.
     *  Pokud špatně vypíše se zpráva "Nesprávný kód"
     *
     * @param sousedniProstor
     * @return boolean podle toho zda se komnata otevře
     */
    public boolean odemkniKomnatu(Prostor sousedniProstor){
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadej kód pro otevření dveří: ");
        String kód = sc.nextLine();
        if(kód.equals("42069")){
            sousedniProstor.setZamceno(false);
            return true;
        }else{
            System.out.println("Nesprávný kód");
            return false;
        }
    }

    /**
     * Metoda hráči oznámí,že je chodba zamčená a nabídne mu 4 možnosti.
     * Hráč může zaplatit, zkusit štěstí u ruské rulety, bojovat, nebo odejít.
     *
     * @param sousedniProstor
     * @return zpávu podle hráčovi volby
     */
    public String odemykaniChodby(Prostor sousedniProstor){
        Scanner sc = new Scanner(System.in);
        System.out.println("Vchod střeží straze. Pustí vás pokud je podplatíte, zahrajete si s nimi" +
                "\n" + "ruskou ruletu a nebo pokud je odstraníte.");
        boolean konec;
        do{
            System.out.println("Pro úplatek (5000 rublů) napište '1'   " +
                    "pro ruskou ruletu '2'  pro boj '3' a pro odchod '4'");
            String input = sc.nextLine();
            if(input.equals("1")){
                if(inventar.getVec("rubly").getMnozstvi()<5000){
                    konec = false;
                    System.out.println("Nemáš dostatek rublů");
                }else{
                    inventar.getVec("rubly").upravitMnozstvi(inventar,-5000,"rubly");
                    konec = true;
                }
            }else if(input.equals("2")){
                boolean stesti = ruskaRuleta();
                if(!stesti){
                    return "";
                }
                konec = true;
            }else if(input.equals("3")){
                Prostor prostor = plan.getAktualniProstor();
                return "Vyber si na koho zautocíš jako první. Zadej příkaz \"utoc\" a jméno straze" + prostor.vypisSeznamuPostav();
            }else if(input.equals("4")){
                return "Odešel si";
            }else{
                konec = false;
            }
        }while(!konec);
        sousedniProstor.setZamceno(false);
        plan.setAktualniProstor(sousedniProstor);
        return "Chodba odemčena \n" + sousedniProstor.dlouhyPopis() + "\n" + sousedniProstor.vypisSeznamuVeci() + "\n" +
                sousedniProstor.vypisSeznamuPostav();
    }

    /**
     * Metoda třikrát projede cyklus během, kterého má hráč pokaždé 50% šanci na smrt.
     * Pokud přežije chodba se otevře, pokud ne hra končí.
     *
     * @return boolean podle toh zda je živý nebo ne.
     */
    public boolean ruskaRuleta(){
        Scanner sc = new Scanner(System.in);
        for(int i=0; i < 3; i++) {
            System.out.println("výstřel " + (i+1) + "/3 (zmáčkni enter)");
            sc.nextLine();
            if(PrikazPouzij.getRandomCislo(1,2)==1){
                System.out.println("Zabil ses.  RIP Volodoymyr Zelenskyj 1978-01-25 - " + LocalDate.now());
                hra.setKonecHry(true);
                return false;
            }
            System.out.println("Přežil si!");
        }
        System.out.println("Velice stupidní, ale přežil si.");
        return true;
    }
}
