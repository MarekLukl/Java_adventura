package logika;

public class Hrac {
    private static int hp;
    private static int maxHp;
    private static int damage;

    public Hrac() {
        this.hp = 100;
        this.maxHp = 100;
        this.damage = 10;
    }

    public int getHp() {return hp;}

    public int getDamage() {return damage;}

    public void setHp(int hp) {this.hp = hp;}

    public void setDamage(int damage) {this.damage = damage;}

    public void upravitHp(int hp){
        setHp(getHp()+hp);
        if(getHp()>maxHp){
            setHp(maxHp);
        }
    }
}
