class Kreditkonto extends Konto {
    private double gebuehr;

    public Kreditkonto(String kontoinhaber, String bankleitzahl, String kontonummer, double gebuehr) {
        super(kontoinhaber, bankleitzahl, kontonummer, "Kreditkonto");
        this.gebuehr = gebuehr;
    }

    @Override
    public void abheben(double betrag) {
        kontostand -= (betrag + gebuehr);
        System.out.println(betrag + " € abgehoben. Gebühren: " + gebuehr + " €");
    }
}