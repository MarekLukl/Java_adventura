package logika;
/**
 *  Class straz - třída představuje postavu straze.
 *
 *  Dědí ze třídy Postava.
 *  Nedeklaruje žádné nové atributy, všechny dědí z Postavy.
 *  Přepisuje metodu superschopnost pro straz.
 *
 *@author     Marek Lukl
 *@version    1.0
 */
public class Straz extends Postava{

    public Straz(String nazev, int hp, int damage) {
        super(nazev, hp, damage);
    }
    /**
     *straz má každé kolo 20% šanci, že se mu zvedne damage o 2
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
