class Kreditkonto extends Konto {
    private double kapital;

    public Kreditkonto(String kontoinhaber, String bankleitzahl, String kontonummer, double kapital) {
        super(kontoinhaber, bankleitzahl, kontonummer, "Kreditkonto");
        this.kapital = kapital;
    }

    @Override
    public void abheben(double betrag) {
        System.out.println("Abheben ist auf einem Kreditkonto nicht erlaubt.");
    }
}