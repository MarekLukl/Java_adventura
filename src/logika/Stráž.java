package logika;
/**
 *  Class Stráž - třída představuje postavu stráže.
 *
 *  Dědí ze třídy Postava.
 *  Nedeklaruje žádné nové atributy, všechny dědí z Postavy.
 *  Přepisuje metodu superschopnost pro stráž.
 *
 *@author     Marek Lukl
 *@version    1.0
 */
public class Stráž extends Postava{

    public Stráž(String nazev, int hp, int damage) {
        super(nazev, hp, damage);
    }
    /**
     *Stráž má každé kolo 20% šanci, že se mu zvedne damage o 2
     *
     *@param postava
     */
    @Override
    public void superschopnost(Postava postava) {
        if(PrikazPouzij.getRandomCislo(1,5)==1){
            postava.setDamage(postava.getDamage() + 2);
            System.out.println(postava.getNazev() + " si vzal silnější zbraň a zvedl se mu damage o 2");
        }
    }
}
