package logika;

/**
 *  Věc reprezentuje objekt nacházející se v některém z prostorů nebo v inventáři.
 *  Třída deklaruje základní atributy věci.
 *
 *  Tato třída je součástí jednoduché textové hry.
 *
 * @author     Marek Lukl
 * @version    1.0
 */

public class Vec {
    private String nazev;
    private int mnozstvi;
    private int damage;
    private int hp;
    private final Status STATUS;
    private final String TYP;
    private int cena;

    public enum Status{
        POUZITELNE,
        ZVEDNUTELNE
    }

    public Vec(String nazev, Status status, String typ) {
        this.nazev = nazev;
        this.STATUS = status;
        this.TYP = typ;
    }
    public String getNazev () { return nazev;}

    public int getMnozstvi() {return mnozstvi;}

    public void setMnozstvi(int mnozstvi) {this.mnozstvi = mnozstvi;}

    /**
     * Metoda přidá atribut množství k aktuálnímu množství dané věci.
     *
     * @param inventar
     * @param mnozstvi
     * @param nazevMeny
     */
    public void upravitMnozstvi(Inventar inventar, int mnozstvi, String nazevMeny){
        inventar.getVec(nazevMeny).setMnozstvi(inventar.getVec(nazevMeny)
                .getMnozstvi()+ mnozstvi);
    }

    public String getTyp() {return TYP;}

    public Status getStatus() {
        return STATUS;
    }

    /**
     * Metoda vrátí zda je možné věci zvednout nebo ne.
     *
     * @return zvednutelnost věci
     */
    public boolean lzeVzit (){
        if(getStatus() == Status.ZVEDNUTELNE){
            return true;
        }
        return false;
    }

    /**
     * Metoda vrátí zda je možné danou věc použít.
     *
     * @return použitelnost věci
     */
    public boolean lzePouzit (){
        if(getStatus() == Status.POUZITELNE){
            return true;
        }
        return false;
    }

    public int getDamage() {return damage;}

    public void setDamage(int damage) {this.damage = damage;}

    public int getHp() {return hp;}

    public void setHp(int hp) {this.hp = hp;}

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
}

