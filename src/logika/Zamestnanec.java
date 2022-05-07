package logika;
/**
 *  Class Zamestnanec - třída představuje postavu zaměstnance.
 *
 *  Dědí ze třídy Postava.
 *  Nedeklaruje žádné nové atributy, všechny dědí z Postavy.
 *  Přepisuje metodu superschopnost pro zaměstnance.
 *
 *@author     Marek Lukl
 *@version    1.0
 */
public class Zamestnanec extends Postava{
    public Zamestnanec(String nazev, int hp, int damage) {
        super(nazev, hp, damage);
    }

    /**
     * Metoda vypíše, že zaměstnanci nemají žádnou superschopnost.
     *
     * @param postava
     */
    @Override
    public void superschopnost(Postava postava) {
        System.out.println("Zaměstnanci nemají superschopnost.");
    }
}
