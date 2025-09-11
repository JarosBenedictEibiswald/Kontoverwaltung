abstract class Konto {
    protected String kontoinhaber;
    protected String bankleitzahl;
    protected String kontonummer;
    protected double kontostand;
    protected String kontoart;

    public Konto(String kontoinhaber, String bankleitzahl, String kontonummer, String kontoart) {
        this.kontoinhaber = kontoinhaber;
        this.bankleitzahl = bankleitzahl;
        this.kontonummer = kontonummer;
        this.kontoart = kontoart;
        this.kontostand = 0.0;
    }

    public void einzahlen(double betrag) {
        if (betrag > 0) {
            kontostand += betrag;
            System.out.println(betrag + " € eingezahlt.");
        } else {
            System.out.println("Ungültiger Betrag!");
        }
    }

    public abstract void abheben(double betrag);

    public void kontoauszug() {
        System.out.println("===========================================");
        System.out.println("Kontoinhaber: " + kontoinhaber);
        System.out.println("BLZ: " + bankleitzahl);
        System.out.println("Kontonummer: " + kontonummer);
        System.out.println("Kontoart: " + kontoart);
        System.out.println("Kontostand: " + kontostand + " €");
        System.out.println("===========================================");
    }

    public String getKontonummer() {
        return kontonummer;
    }
}