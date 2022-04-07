package logika;

public class PrikazVezmi implements IPrikaz {

    private static final String NAZEV = "vezmi";
    private final Inventar inventar;
    private final Hra hra;

    public PrikazVezmi(Inventar inventar, Hra hra) {
        this.inventar = inventar;
        this.hra = hra;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Super 'vezmi' ale co??";
        }
        String nazevVeci = parametry[0];
        Prostor prostor = hra.getHerniPlan().getAktualniProstor();
        Vec vec = prostor.getVec(nazevVeci);

        if(vec == null){
            return "Taková věc v této místnosti není." + "\n" + prostor.dlouhyPopis();
        } else if(vec.lzeVzit()){
            inventar.vlozitDoInvent(vec.getNazev(), vec);
            prostor.odstranPredmet(vec);
            return "Vzal jsi předmět: " + vec.getNazev();
        }

        return null;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
