package logika;

import java.util.Map;

public class PrikazUtok implements IPrikaz{

    private static final String NAZEV = "útoč";
    private final Hra hra;
    private final Hrac hrac;

    public PrikazUtok(Hra hra, Hrac hrac) {
        this.hra = hra;
        this.hrac = hrac;
    }

    /**
     * Metoda pro provedení příkazu ve hře.
     * Počet parametrů je závislý na konkrétním příkazu,
     * např. příkazy konec a napoveda nemají parametry
     * příkazy jdi, seber, polož mají jeden parametr
     * příkaz pouzij může mít dva parametry.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Nádherný útok, ale musíš si nejdříve vybrat na koho budeš útočit";
        }
        String nazevPostavy = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Map<String, Postava> seznamPostav = prostor.getSeznamPostav();

        if(seznamPostav.containsKey(nazevPostavy)){
            jednoKoloBoje(seznamPostav,nazevPostavy);
        }else{
            return "Tato postava v této místnosti není.";
        }
        return "Konec kola.";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    @Override
    public String getNazev() {return NAZEV;}

    public void jednoKoloBoje(Map<String,Postava> seznamPostav,String nazevPostavy) {
         seznamPostav.get(nazevPostavy).upravitHp(-hrac.getDamage());
         System.out.println("Útok na " + nazevPostavy + ", ubral si " + hrac.getDamage());
         if(seznamPostav.get(nazevPostavy).getHp()<1){  // pokud má postava menší hp než 1, odebere ji
             System.out.println(nazevPostavy + " je mrtev");
             seznamPostav.remove(nazevPostavy);
         }
         for(String s: seznamPostav.keySet()){   // odebere damage každé postavy v seznamu od hp hráče
             hrac.upravitHp(-seznamPostav.get(s).getDamage());
             System.out.println("Útok " + seznamPostav.get(s).getNazev() + ",ubral ti " +
                     seznamPostav.get(s).getDamage() + "\n" + "Tvoje hp: " + hrac.getHp());
             if(hrac.getHp()<1){
                 System.out.println("Jsi mrtev." +  " Konec hry.");
                 hra.setKonecHry(true);
                 return;
             }
         }
        for(String s: seznamPostav.keySet()){
            System.out.println(seznamPostav.get(s).getNazev() +
                    " damage: " + seznamPostav.get(s).getDamage() + " hp: " + seznamPostav.get(s).getHp());
        }
    }
}
