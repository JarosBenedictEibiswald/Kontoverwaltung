import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class kontoverwaltunggui {
    private JPanel kontoGUI;
    private JTextField kontonummerField;
    private JTextField kontoinhaberField;
    private JTextField gebuerenField;
    private JTextField kontostandField;
    private JButton kontoanlegenBTN;
    private JTextArea contractsHistory;
    private JComboBox<String> kontoartDropdown;
    private JComboBox<String> kontoDropdown;
    private JButton abhebenButton;
    private JButton einzahlenButton;
    private JTextField amountInOutfield;
    private JButton kontoAufloesenButton;

    private static List<Konto> konten = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            kontoverwaltunggui gui = new kontoverwaltunggui();

            JFrame frame = new JFrame("Kontoverwaltung GUI");
            frame.setContentPane(gui.kontoGUI);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);

            gui.ini();
        });
    }

    public void ini(){
        kontoanlegenBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        einzahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositMoney();
            }
        });

        abhebenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawMoney();
            }
        });

        kontoAufloesenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });

        kontoDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAccountDetails();
            }
        });
    }

    private void createAccount() {
        String inhaber = kontoinhaberField.getText().trim();
        String kontonummer = kontonummerField.getText().trim();
        String gebuerenText = gebuerenField.getText().trim();

        // schauen ob alle Felder ausgefüllt sind.
        if (inhaber.isEmpty() || kontonummer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Pflichtfelder aus (Inhaber und Kontonummer)!");
            return;
        }

        // schauen ob die ID schon exesitiert oder nicht
        for (Konto konto : konten) {
            if (konto.getKontonummer().equals(kontonummer)) {
                JOptionPane.showMessageDialog(null, "Kontonummer bereits vorhanden!");
                return;
            }
        }

        double gebueren = 0;
        try {
            if (!gebuerenText.isEmpty()) {
                gebueren = Double.parseDouble(gebuerenText);
                if (gebueren < 0) {
                    JOptionPane.showMessageDialog(null, "Gebühren/Rahmen/Kapital dürfen nicht negativ sein!");
                    return;
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bitte eine gültige Zahl für Gebühren/Rahmen/Kapital eingeben!");
            return;
        }

        String kontoart = (String) kontoartDropdown.getSelectedItem();
        Konto k = null;

        switch (kontoart) {
            case "Girokonto":
                k = new Girokonto(inhaber, "BLZ", kontonummer, gebueren);
                break;
            case "Sparkonto":
                k = new Sparkonto(inhaber, "BLZ", kontonummer);
                break;
            case "Kreditkonto":
                k = new Kreditkonto(inhaber, "BLZ", kontonummer, gebueren);
                break;
        }

        if (k != null) {
            konten.add(k);
            updateKontoDropdown();
            contractsHistory.append("Konto angelegt: " + k.toString() + "\n");
            JOptionPane.showMessageDialog(null, "Konto erfolgreich angelegt!");

            // alles auf null setzen
            kontoinhaberField.setText("");
            kontonummerField.setText("");
            gebuerenField.setText("");

        }
    }

    private void depositMoney() {
        Konto selectedKonto = getSelectedKonto();
        if (selectedKonto == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Konto aus!");
            return;
        }

        String amountText = amountInOutfield.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            selectedKonto.einzahlen(amount);
            contractsHistory.append("Einzahlung: " + amount + "€ auf Konto " + selectedKonto.getKontonummer() + "\n");
            updateAccountDetails();

            //text auf nichts setzen
            amountInOutfield.setText("");
            JOptionPane.showMessageDialog(null, "Einzahlung erfolgreich!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Zahl ein!");
        }
    }

    private void withdrawMoney() {
        Konto selectedKonto = getSelectedKonto();
        if (selectedKonto == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Konto aus!");
            return;
        }

        String amountText = amountInOutfield.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            selectedKonto.abheben(amount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Zahl ein!");
        }
    }

    private void deleteAccount() {
        Konto selectedKonto = getSelectedKonto();
        if (selectedKonto == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Konto aus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Sind Sie sicher, dass Sie das Konto " + selectedKonto.getKontonummer() + " löschen möchten?",
                "Konto löschen", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            konten.remove(selectedKonto);
            contractsHistory.append("Konto gelöscht: " + selectedKonto.getKontonummer() + "\n");
            updateKontoDropdown();
            clearAccountDetails();
            JOptionPane.showMessageDialog(null, "Konto erfolgreich gelöscht!");
        }
    }

    private Konto getSelectedKonto() {
        String selected = (String) kontoDropdown.getSelectedItem();
        if (selected == null || selected.equals("Kein Konto ausgewählt")) {
            return null;
        }

        // die nummer von dropdown heraus bekommen
        int startIndex = selected.lastIndexOf("(");
        int endIndex = selected.lastIndexOf(")");
        if (startIndex != -1 && endIndex != -1) {
            String kontonummer = selected.substring(startIndex + 1, endIndex);
            return findKontoByNumber(kontonummer);
        }
        return null;
    }

    private Konto findKontoByNumber(String kontonummer) {
        for (Konto k : konten) {
            if (k.getKontonummer().equals(kontonummer)) {
                return k;
            }
        }
        return null;
    }

    private void updateKontoDropdown() {
        kontoDropdown.removeAllItems();

        if (konten.isEmpty()) {
            kontoDropdown.addItem("Kein Konto ausgewählt");
        } else {
            for (Konto k : konten) {
                kontoDropdown.addItem(k.getInhaber() + " (" + k.getKontonummer() + ")");
            }
        }
    }

    private void updateAccountDetails() {
        // im TexField den jetzigen Kontostand anzeigen

        Konto selectedKonto = getSelectedKonto();
        if (selectedKonto != null) {
            kontostandField.setText(String.format("%.2f€", selectedKonto.getKontostand()));
        } else {
            clearAccountDetails();
        }
    }

    private void clearAccountDetails() {
        kontostandField.setText("");
    }
}