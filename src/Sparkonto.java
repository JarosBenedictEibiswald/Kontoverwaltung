class Sparkonto extends Konto {
    public Sparkonto(String kontoinhaber, String bankleitzahl, String kontonummer) {
        super(kontoinhaber, bankleitzahl, kontonummer, "Sparkonto");
    }

    @Override
    public void abheben(double betrag) {
        if (kontostand >= betrag) {
            kontostand -= betrag;
            System.out.println(betrag + " € abgehoben.");
        } else {
            System.out.println("Nicht genügend Guthaben!");
        }
    }
}