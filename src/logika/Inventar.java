package logika;

import java.util.HashMap;
import java.util.Map;

public class Inventar {

    private static Map<String,Vec > inventar;

    public Inventar() {
        inventar = new HashMap<String, Vec>();
    }

    public static Map<String, Vec> getInventar() {return inventar;}

    public static boolean vlozitDoInvent (String nazevVeci, Vec vec){
        inventar.put(nazevVeci, vec);
        return true;
    }

    public void odebratZInvent(String nazev){
        inventar.remove(nazev);
    }

    public void vypisInventare(){
        String celyInventar = "";
        for(String s: Inventar.inventar.keySet()){
            celyInventar += "(" + inventar.get(s).getMnozstvi() + ")" + inventar.get(s).getNazev() + " ";
        }
        System.out.println("Tvůj inventář: " + celyInventar);
    }

    public Vec getVec(String nazev) {
        if (inventar.containsKey(nazev)) {
            return inventar.get(nazev);
        }
        return null;
    }
    public static String getStavPenezenky(){
        String stavPenezenky = "";
        if (Inventar.inventar.isEmpty()||!(Inventar.inventar.containsKey("euro")) && !(Inventar.inventar.containsKey("rubly"))) {
            return "Jsi švorc";
        }

        for(String s: Inventar.inventar.keySet()){
            if(Inventar.inventar.get(s).getNazev() == "euro"){
                stavPenezenky +=  "(" + Inventar.inventar.get(s).getMnozstvi() + ")" + "euro" + " ";
            }
            else if(Inventar.inventar.get(s).getNazev() == "rubly"){
                stavPenezenky +="(" + Inventar.inventar.get(s).getMnozstvi() + ")" + "rublů" + " ";
            }
        }
        return  stavPenezenky;
    }
    public static boolean nakup(int cena, String mena){
        if(!(Inventar.inventar.containsKey(mena))){
            System.out.println("Nemáš " + mena);
            return false;
        }
        if(Inventar.inventar.get(mena).getMnozstvi() < cena){
            System.out.println("Nemas dostatek penez");
            return false;
        }else{
            Inventar.inventar.get(mena).setMnozstvi(Inventar.inventar.get(mena).getMnozstvi()-cena);
            return true;
        }
    }

}
