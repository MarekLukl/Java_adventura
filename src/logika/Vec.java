package logika;


public class Vec {
    private String nazev;
    private int mnozstvi;
    private int damage;
    private int hp;
    private final boolean zvednutelne;
    private final boolean pouzitelne;
    private final String typ;

    public Vec(String nazev, boolean zvednutelne, boolean pouzitelne, String typ) {
        this.nazev = nazev;
        this.zvednutelne = zvednutelne;
        this.pouzitelne = pouzitelne;
        this.typ = typ;
    }
    public String getNazev () { return nazev;}

    public int getMnozstvi() {return mnozstvi;}

    public void setMnozstvi(int mnozstvi) {this.mnozstvi = mnozstvi;}

    public void upravitMnozstvi(Inventar inventar, int mnozstvi, String nazevMeny){
        inventar.getVec(nazevMeny).setMnozstvi(inventar.getVec(nazevMeny)
                .getMnozstvi()+ mnozstvi);
    }

    public String getTyp() {return typ;}

    public boolean lzeVzit (){return zvednutelne;}

    public boolean lzePouzit (){return  pouzitelne;}

    public int getDamage() {return damage;}

    public void setDamage(int damage) {this.damage = damage;}

    public int getHp() {return hp;}

    public void setHp(int hp) {this.hp = hp;}

    public boolean lzeBojovat (Vec vec){
        if(vec.typ == "boj"){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean lzePlatit (Vec vec){
        if(vec.typ == "penize"){
            return true;
        }
        else{
            return false;
        }
    }
}

