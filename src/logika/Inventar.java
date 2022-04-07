package logika;

import java.util.HashMap;
import java.util.Map;

public class Inventar {
    private static Map<String,Vec > inventar;

    public Inventar() {
        inventar = new HashMap<String, Vec>();
    }
    public static boolean vlozitDoInvent (String nazevVeci, Vec vec){
        inventar.put(nazevVeci, vec);
        return true;
    }
    public Map<String, Vec> getInventar(){
        if(inventar==null){
        throw new IllegalArgumentException();
    }
        return inventar;
    }
    public Vec getVec(String nazev) {
        if (inventar.containsKey(nazev)) {
            return inventar.get(nazev);
        }
        return null;
    }
    public static String getStavPenezenky(Inventar inventar){
        String stavPenezenky = "";
        if (Inventar.inventar.isEmpty()||!(Inventar.inventar.containsKey("euro")) && !(Inventar.inventar.containsKey("rubly"))) {
            return "Jsi švorc";
        }

        for(String s: Inventar.inventar.keySet()){
            if(Inventar.inventar.get(s).getNazev() == "euro"){
                stavPenezenky += "euro" + "(" + Inventar.inventar.get(s).getMnozstvi() + ")" + "\n";
            }
            else if(Inventar.inventar.get(s).getNazev() == "rubly"){
                stavPenezenky += "     " + Inventar.inventar.get(s).getMnozstvi() + "rublů";
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
