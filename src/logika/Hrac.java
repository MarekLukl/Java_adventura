package logika;
/**
 *  Třída Hrac - třída představující postavu hráče.
 *
 *  Nastaví hráči neměnitelné maximální možné hp, aktuální hp a aktuální damage.
 *
 *@author     Marek Lukl
 */

public class Hrac extends Postava{
    private int hp = 101;
    private int maxHp = 100;
    private int damage = 100;

    public Hrac(String nazev,int hp, int damage) {
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
