import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Konto> konten = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int auswahl;
        do {
            System.out.println("\nWelche Aktion möchten Sie durchführen?");
            System.out.println("1 - Konto anlegen");
            System.out.println("2 - Konto auflösen");
            System.out.println("3 - Einzahlen");
            System.out.println("4 - Abheben");
            System.out.println("5 - Kontoauszug");
            System.out.println("6 - Überweisen");
            System.out.println("0 - Programm beenden");
            auswahl = scanner.nextInt();
            scanner.nextLine();

            switch (auswahl) {
                case 1 -> kontoAnlegen();
                case 2 -> kontoAufloesen();
                case 3 -> einzahlen();
                case 4 -> abheben();
                case 5 -> kontoauszug();
                case 6 -> ueberweisen();
                case 0 -> System.out.println("Programm beendet.");
                default -> System.out.println("Ungültige Eingabe!");
            }
        } while (auswahl != 0);
    }

    private static void kontoAnlegen() {
        System.out.print("Kontoinhaber: ");
        String inhaber = scanner.nextLine();
        System.out.print("BLZ: ");
        String blz = scanner.nextLine();
        System.out.print("Kontonummer: ");
        String knr = scanner.nextLine();

        System.out.println("Kontoart wählen: 1=Giro, 2=Spar, 3=Kredit");
        int art = scanner.nextInt();

        Konto k;
        switch (art) {
            case 1 -> {
                System.out.print("Überziehungsrahmen: ");
                double rahmen = scanner.nextDouble();
                k = new Girokonto(inhaber, blz, knr, rahmen);
            }
            case 2 -> k = new Sparkonto(inhaber, blz, knr);
            case 3 -> {
                System.out.print("Gebühr: ");
                double g = scanner.nextDouble();
                k = new Kreditkonto(inhaber, blz, knr, g);
            }
            default -> {
                System.out.println("Ungültige Auswahl!");
                return;
            }
        }
        konten.add(k);
        System.out.println("Konto erfolgreich angelegt!");
    }

    private static Konto findeKonto(String knr) {
        for (Konto k : konten) {
            if (k.getKontonummer().equals(knr)) {
                return k;
            }
        }
        System.out.println("Konto nicht gefunden!");
        return null;
    }

    private static void kontoAufloesen() {
        System.out.print("Kontonummer: ");
        String knr = scanner.nextLine();
        Konto k = findeKonto(knr);
        if (k != null) {
            konten.remove(k);
            System.out.println("Konto aufgelöst.");
        }
    }

    private static void einzahlen() {
        System.out.print("Kontonummer: ");
        String knr = scanner.nextLine();
        Konto k = findeKonto(knr);
        if (k != null) {
            System.out.print("Betrag: ");
            double betrag = scanner.nextDouble();
            k.einzahlen(betrag);
        }
    }

    private static void abheben() {
        System.out.print("Kontonummer: ");
        String knr = scanner.nextLine();
        Konto k = findeKonto(knr);
        if (k != null) {
            System.out.print("Betrag: ");
            double betrag = scanner.nextDouble();
            k.abheben(betrag);
        }
    }

    private static void kontoauszug() {
        System.out.print("Kontonummer: ");
        String knr = scanner.nextLine();
        Konto k = findeKonto(knr);
        if (k != null) {
            k.kontoauszug();
        }
    }

    private static void ueberweisen() {
        System.out.print("Von Kontonummer: ");
        String vonNr = scanner.nextLine();
        Konto von = findeKonto(vonNr);
        if (von == null) return;

        System.out.print("Zu Kontonummer: ");
        String zuNr = scanner.nextLine();
        Konto zu = findeKonto(zuNr);
        if (zu == null) return;

        System.out.print("Betrag: ");
        double betrag = scanner.nextDouble();

        von.abheben(betrag);
        zu.einzahlen(betrag);
        System.out.println("Überweisung erfolgreich!");
    }
}