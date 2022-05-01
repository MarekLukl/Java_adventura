package logika;

/**
 *  Třída Postava - třída představující NPC.
 *
 *  Nastaví posavě neměnitelné aktuální hp, damage a název postavy.
 *  Vrací jednotlivé údaje o postavě a upravuje její hp během souboje.
 *
 *@author     Marek Lukl
 */

public class Postava {

    private String nazev;
    private int hp;
    private int damage;

    public Postava(String nazev, int hp, int damage) {
        this.nazev = nazev;
        this.hp = hp;
        this.damage = damage;
    }
    public String getNazev() {return nazev;}

    public int getHp() {return hp;}

    public void setHp(int hp) {this.hp = hp;}

    public void upravitHp(int hp){
        setHp(getHp()+hp);
    }

    public int getDamage() {return damage;}

    public void setDamage(int damage) {this.damage = damage;}

}
