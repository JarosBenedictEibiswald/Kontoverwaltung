class Girokonto extends Konto {
    private double ueberziehungsrahmen;

    public Girokonto(String kontoinhaber, String bankleitzahl, String kontonummer, double ueberziehungsrahmen) {
        super(kontoinhaber, bankleitzahl, kontonummer, "Girokonto");
        this.ueberziehungsrahmen = ueberziehungsrahmen;
    }

    @Override
    public void abheben(double betrag) {
        if (kontostand + ueberziehungsrahmen >= betrag) {
            kontostand -= betrag;
            System.out.println(betrag + " € abgehoben.");
        } else {
            System.out.println("Abhebung nicht möglich. Limit überschritten!");
        }
    }
}