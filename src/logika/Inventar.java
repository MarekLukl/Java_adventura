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

    public static String getStavPenezenky(Inventar inventar){
        String stavPenezenky = "";
        Map<String, Vec> inventar1 = inventar.getInventar();
        for(String s: inventar1.keySet()){
            if(inventar1.get(s).getNazev()=="euro"){
                stavPenezenky += "euro" + "(" + inventar1.get(s).getMnozstvi() + ")" + "\n";
            }
            else if(inventar1.get(s).getNazev()=="rubly"){
                stavPenezenky += "     " + inventar1.get(s).getMnozstvi() + "rublů";
            }
        }
        return  stavPenezenky;
    }

}
