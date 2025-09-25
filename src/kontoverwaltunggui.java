//new
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
    private JComboBox kontoartDropdown;
    private JComboBox kontoDropdown;
    private JButton abhebenButton;
    private JButton einzahlenButton;
    private JTextField amountInOutfield;
    private JButton kontoAufloesenButton;

    private static List<Konto> konten = new ArrayList<>();

    public static void main(String[] args) {
        kontoverwaltunggui gui = new kontoverwaltunggui();

        JFrame frame = new JFrame("Kontoverwaltung GUI");
        frame.setContentPane(gui.kontoGUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        gui.ini();
    }

    public void ini(){
        kontoanlegenBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inhaber = kontoinhaberField.getText();
                String kontonummer = kontonummerField.getText();
                String gebuerenText = gebuerenField.getText();
                double gebueren = 0;

                //wurde mithilfe von ChatGPT in double umgewandelt
                try {
                    if (!gebuerenText.isEmpty()) {
                        gebueren = Double.parseDouble(gebuerenText);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Bitte eine gültige Zahl für Gebühren eingeben!");
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
                    contractsHistory.append(k.toString() + "\n");
                    JOptionPane.showMessageDialog(null, "Konto erfolgreich angelegt!");

                    kontoinhaberField.setText("");
                    kontonummerField.setText("");
                    gebuerenField.setText("");
                }
            }
        });

    }
}
