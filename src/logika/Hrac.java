package logika;
/**
 *  Třída Hrac - třída představující postavu hráče.
 *
 *  Nastaví hráči neměnitelné maximální možné hp, aktuální hp a aktuální damage.
 *
 *@author     Marek Lukl
 */

public class Hrac extends Postava{
    private static int hp = 100;
    private final static int maxHp = 100;
    private static int damage = 100;

    public Hrac(String nazev, int hp, int damage) {
        super(nazev, hp, damage);
    }


    public int getHp() {return hp;}

    public int getDamage() {return damage;}


    @Override
    public void superschopnost(Postava postava) {

    }

    public void setHp(int hp) {this.hp = hp;}

    public void setDamage(int damage) {this.damage = damage;}

    public void upravitHp(int hp){
        setHp(getHp()+hp);
        if(getHp()>maxHp){
            setHp(maxHp);
        }
    }
}
