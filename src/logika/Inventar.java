package logika;
import java.util.HashMap;
import java.util.Map;

/**
 *  Představuje inventář hráče. Inicializuje inventář jako HashMapu.
 *  Umí vložit a odebrat objekt z inventáře.
 *  Umí vypsat celý inventář, či z něj získat konkrétní věc.
 *  Vypisuje monožství peněz v inventáři a umí posoudit, zda máš dostatek peněz k nákupu a následně je odebrat.
 *
 *@author     Marek Lukl
 */

public class Inventar {

    private static Map<String,Vec > inventar;

    public Inventar() {
        inventar = new HashMap<String, Vec>();
    }
    public static Map<String, Vec> getInventar() {
        return inventar;
    }

    public static boolean vlozitDoInvent (String nazevVeci, Vec vec){
        inventar.put(nazevVeci, vec);
        return true;
    }

    public void odebratZInvent(String nazev){
        inventar.remove(nazev);
    }

    public String vypisInventare(){
        String celyInventar = "";
        for(String s: Inventar.inventar.keySet()) {
            if (inventar.get(s).getTyp().equals("penize")) {
                celyInventar += inventar.get(s).getNazev() + "(" + inventar.get(s).getMnozstvi() + ")" + " ";
            } else if (inventar.get(s).getTyp().equals("zbran")) {
                celyInventar += inventar.get(s).getNazev() + "(" + inventar.get(s).getDamage() + " damage)" + " ";
            } else if (inventar.get(s).getTyp().equals("jidlo")) {
                celyInventar += inventar.get(s).getNazev() + "(" + inventar.get(s).getHp() + " hp)" + " ";
            } else{
                celyInventar += inventar.get(s).getNazev() + " ";
            }
        }
        return "Tvůj inventář: " + celyInventar;
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
