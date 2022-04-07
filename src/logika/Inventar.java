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
    public Map<String, Vec> getInventar(){return inventar;}
    // komentar
    public static String getStavPenezenky(Inventar inventar){
        String stavPenezenky = "";
        if(inventar.getInventar()==null){return "jsi švorc";}

        for(String s: inventar.getInventar().keySet()){
            if(inventar.getInventar().get(s).getNazev()=="euro"){
                stavPenezenky += "euro" + "(" + inventar.getInventar().get(s).getMnozstvi() + ")" + "\n";
            }
            else if(inventar.getInventar().get(s).getNazev()=="rubly"){
                stavPenezenky += "     " + inventar.getInventar().get(s).getMnozstvi() + "rublů";
            }
        }
        return  stavPenezenky;
    }

}
