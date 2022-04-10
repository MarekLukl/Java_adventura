package logika;

public class PrikazVezmi implements IPrikaz {

    private static final String NAZEV = "vezmi";
    private final Inventar inventar;
    private final Hra hra;
    private final Hrac hrac;

    public PrikazVezmi(Inventar inventar, Hra hra, Hrac hrac) {
        this.inventar = inventar;
        this.hra = hra;
        this.hrac = hrac;
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
            if(vec.getTyp()=="penize"){
                pridaniPenez(vec.getNazev(),vec);
            }else if(vec.getTyp()=="zbran"){
            zmenaDamage(vec);
            }else{
                inventar.vlozitDoInvent(vec.getNazev(), vec);
            }
            prostor.odstranPredmet(vec);
            inventar.vypisInventare();
            return "Vzal jsi předmět: " + vec.getNazev();
        }

        return null;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }

    public void pridaniPenez(String mena, Vec vec){
        if(mena=="euro"){
            vec.upravitMnozstvi(inventar,vec.getMnozstvi(),vec.getNazev());
        }else if (mena=="rubly"){
            vec.upravitMnozstvi(inventar,vec.getMnozstvi(),vec.getNazev());
        }
    }
    public void zmenaDamage(Vec vec){
        if(hrac.getDamage()<vec.getDamage()){
            hrac.setDamage(vec.getDamage());
        }
        inventar.vlozitDoInvent(vec.getNazev(),vec);
    }
}
