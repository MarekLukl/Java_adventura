package logika;
/**
 *  Třída Hrac - třída představující postavu hráče.
 *
 *  Nastaví hráči neměnitelné maximální možné hp, aktuální hp a aktuální damage.
 *
 * @author     Marek Lukl
 * @version  1.0
 */

public class Hrac extends Postava{
    private int hp = 101;
    private int maxHp = 100;
    private int damage = 10;

    public Hrac(String nazev,int hp, int damage) {
        super(nazev, hp, damage);
    }


    public int getHp() {return hp;}

    public int getDamage() {return damage;}

    /**
     * Hráč zatím žádnou superschopnost nemá.
     * Je zde ale potenciál mu jich vytvořit více a nechat ho si vybírat.
     *
     * @param postava
     */
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
