package logika;

import java.util.Scanner;

public class PrikazPouzij implements IPrikaz{

    private static final String NAZEV = "použij";
    private final Inventar inventar;
    private final Hra hra;

    public PrikazPouzij(Inventar inventar, Hra hra) {
        this.inventar = inventar;
        this.hra = hra;
    }


    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Výborný \"použij\" ale co??";
        }
        String nazevVeci = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Vec vec = prostor.getVec(nazevVeci);
        if(vec == null){
            return "Taková věc v této místnosti není." + "\n" + prostor.dlouhyPopis();
        } else if(vec.lzePouzit()){

            if(vec.getNazev()=="automat_na_jídlo"){
                uzitAutomatJidlo();
            }else if(vec.getNazev()=="exchange_automat"){
                uzitExchangeAutomat();
            }else if(vec.getNazev()=="gambling_automat"){

            }

            return "Stav tvé peněženky: " + Inventar.getStavPenezenky();
        }
        return null;
    }


    @Override
    public String getNazev() {
        return NAZEV;
    }
    public void uzitAutomatJidlo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("bageta(20hp) stojí 5 euro" + "\n" + "banán(50hp) stojí 10 euro");
        System.out.println("Pro koupi bagety napiště '1' pro koupi banánu napiště '2' pro konec nákupu napište '3'");
        String vyber ="";
        while(!(vyber.equals("3"))){
            vyber = sc.nextLine();
            if(vyber.equals("1")){
                Vec bageta = new Vec("bageta",true,false,"jidlo");
                if(!(inventar.nakup(5,"euro"))){break;}
                inventar.vlozitDoInvent(bageta.getNazev(),bageta);
                System.out.println("Bageta koupena");
                break;
            }else if(vyber.equals("2")){
                Vec banan = new Vec("banán",true,false,"jidlo");
                if((!inventar.nakup(10,"euro"))){break;}
                inventar.vlozitDoInvent(banan.getNazev(),banan);
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
        inventar.vypisInventare();
    }
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
            int maxRublu = (int) Math.round((inventar.getVec("euro").getMnozstvi())* 100);
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
    public Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
