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
            uzitAutomatJidlo();

            return "Stav tvé peněženky: " + Inventar.getStavPenezenky(inventar);
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
                System.out.println("funguje");
                Vec bageta = new Vec("bageta",true,false,"jidlo");
                inventar.vlozitDoInvent(bageta.getNazev(),bageta);
                break;
            }else if(vyber.equals("2")){
                Vec banan = new Vec("banán",true,false,"jidlo");
                inventar.vlozitDoInvent(banan.getNazev(),banan);
                break;
            }else{
                System.out.println("Neplatný příkaz");
            }
        }
        System.out.println("Konec interakce s automatem");
    }
}
