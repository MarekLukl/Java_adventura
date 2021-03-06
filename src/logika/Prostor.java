 package logika;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Marek Lukl, Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version    1.0
 */
public class Prostor {

    private final String NAZEV;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private boolean zamceno;
    private Map<String, Vec> seznamVeci;
    private Map<String, Postava> seznamPostav;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *  @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     * @param zamceno
     */
    public Prostor(String nazev, String popis, boolean zamceno) {
        this.NAZEV = nazev;
        this.popis = popis;
        vychody = new HashSet<>();
        this.zamceno = zamceno;
        seznamVeci = new HashMap<String, Vec>();
        seznamPostav = new HashMap<String, Postava>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    public boolean getZamceno(){return zamceno;}

    public void setZamceno(boolean zamceno){this.zamceno = zamceno;}

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.NAZEV, druhy.NAZEV));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.NAZEV);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return NAZEV;
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v mistnosti/prostoru " + popis + ".\n"
                + popisVychodu();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     * Metoda vypíše jaké věci se v prostoru nachází, plus jejich důležité atributy.
     *
     * @return vypis věcí v prostoru
     */
    public String vypisSeznamuVeci() {
        String seznam1 = "";
        String seznam2 = " ";
        if (seznamVeci.isEmpty()) {
            return "Není tu nic k sebrání";
        }
        for(String s: seznamVeci.keySet()){
            seznam1 += " " + seznamVeci.get(s).getNazev();
            if(seznamVeci.get(s).getTyp()=="penize"){
                seznam1 += "(" + seznamVeci.get(s).getMnozstvi() + ")";
            }else if(seznamVeci.get(s).getTyp()=="zbran"){
                seznam1 += "(" + seznamVeci.get(s).getDamage() + " damage)";
            }else if(seznamVeci.get(s).getTyp()=="jidlo"){
                seznam1 += "(" + seznamVeci.get(s).getHp() + "hp)";
            }
            seznam1 += ",";
        }
        seznam2 = seznam1.substring(0, seznam1.length()-1);
        return "V místnosti je:" + seznam2;
    }

    /**
     * Metoda vypíše, které postavy se v místnosti nacházejí, pokud se jedná o bojovné postavy. Vypíše jejich damage a hp.
     *
     * @return seznam postav v místnosti
     */
    public String vypisSeznamuPostav(){
        if(seznamPostav.size()==0){
            return "Nikdo tu není.";
        }
        String seznam1 = "Osoby v místnosti: ";
        for(String s: seznamPostav.keySet()){
            // pokud se jedná o zaměstnance vypíše jen jméno
            if(seznamPostav.get(s).getClass().equals(Zamestnanec.class)){
                seznam1 += " " + seznamPostav.get(s).getNazev() + ",";
            }else{
                seznam1 += " " + seznamPostav.get(s).getNazev() +
                        " damage: " + seznamPostav.get(s).getDamage() + " hp: " + seznamPostav.get(s).getHp() + ",";
            }
        }
        String  seznam2 =  seznam1.substring(0, seznam1.length()-1);
        return seznam2;
    }

    /**
     * Metoda vrátí věc pokud ji seznam věcí v místnoti má. Pokud ne vráti null.
     *
     * @param nazevVeci
     * @return věc z místnosti nebo null
     */
    public Vec getVec(String nazevVeci) {
        if (seznamVeci.containsKey(nazevVeci)) {
            return seznamVeci.get(nazevVeci);
        }
        return null;
    }

    public void odstranPredmet(Vec vec){seznamVeci.remove(vec.getNazev());}

    public void pridejVec(Vec vec){seznamVeci.put(vec.getNazev(),vec);}

    public void pridejPostavu(Postava postava){seznamPostav.put(postava.getNazev(), postava);}

    public Map<String, Postava> getSeznamPostav() {return seznamPostav;}
    /**
     * Metoda vrátí postavu pokud je v dané místnoti. Pokud ne vráti null.
     *
     * @param key
     * @return věc z místnosti nebo null
     */
    public Postava getPostavu(String key) {
        for(String s: seznamPostav.keySet()){
            if(key.equals(s)){
                return seznamPostav.get(s);
            }
        }
        return null;
    }
}


