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
        inventar = new HashMap<>();
    }
    public static Map<String, Vec> getInventar() {
        return inventar;
    }

    public void odebratZInvent(String nazev){
        inventar.remove(nazev);
    }

    /**
     * Vrátí vypsaný inventář, s ždádanými atributy u jednotlivých věcí.
     * Damage u zbraní, hp u jídla a ochrany a množství u peněz.
     *
     * @return String výpis inventáře
     */
    public String vypisInventare(){
        String celyInventar = "";
        for(String s: Inventar.inventar.keySet()) {
            if (inventar.get(s).getTyp().equals("penize")) {
                celyInventar += inventar.get(s).getNazev() + "(" + inventar.get(s).getMnozstvi() + ")" + " ";
            } else if (inventar.get(s).getTyp().equals("zbran")) {
                celyInventar += inventar.get(s).getNazev() + "(" + inventar.get(s).getDamage() + " damage)" + " ";
            } else if (inventar.get(s).getTyp().equals("jidlo") || inventar.get(s).getTyp().equals("ochrana")) {
                celyInventar += inventar.get(s).getNazev() + "(" + inventar.get(s).getHp() + " hp)" + " ";
            } else{
                celyInventar += inventar.get(s).getNazev() + " ";
            }
        }
        return "Tvůj inventář: " + celyInventar;
    }

    /**
     * Vrátí věc z inventáře, pokud v inventáří není vrátí null
     *
     * @param nazev
     * @return null nebo věc
     */
    public Vec getVec(String nazev) {
        if (inventar.containsKey(nazev)) {
            return inventar.get(nazev);
        }
        return null;
    }

    /**
     * Metoda vypíše kolik euro a rublů más v inventáři.
     *
     * @return String o stavu peněženky
     */
    public static String getStavPenezenky(){
        String stavPenezenky = "";
        if (Inventar.inventar.get("euro").getMnozstvi()==0 && Inventar.inventar.get("rubly").getMnozstvi()==0) {
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

    /**
     * Metoda určí zda máš dostatek peněz na nákup, pokud ano odečte od nich cenu kupovaného zboží.
     * @param cena
     * @param mena
     * @return boolean podle toho zda má dostatek peněz nebo ne
     */
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
