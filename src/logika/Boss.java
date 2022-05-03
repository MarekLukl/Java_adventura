package logika;

public class Boss extends Postava{
    private static int odbojovanychKol = 0;

    public Boss(String nazev, int hp, int damage) {
        super(nazev, hp, damage);
    }
    /**
     *Pokud se jedná o 4 kolo souboje zvedne bossovi hp o 10%
     *
     *@param postava
     */

    @Override
    public void superschopnost(Postava postava){
        odbojovanychKol++;

        if(odbojovanychKol % 4 == 0){
            int desetProcentHp = (int) Math.round(postava.getHp() * 0.1);
            postava.setHp(postava.getHp() + desetProcentHp);
            System.out.println(postava.getNazev() + " se napil a doplnil si životy o " + desetProcentHp);
        }
    }
}
