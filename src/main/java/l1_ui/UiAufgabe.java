package l1_ui;

import multex.Exc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UiAufgabe extends JPanel {

    protected final JLabel label1, label2, label3, label4, label5, label6, label7, label8;
    protected final JTextField id, titel, rest, ist, status, jtf_erledigt;
    protected final JComboBox jcb_vorhaben;
    protected final JTextArea jta_beschreibung;
    protected final JButton button_erfassen, button_aendern, button_erledigt;

    protected UiAufgabe(final String frameTitel){
        label1 = new JLabel("ID:");
        label2 = new JLabel("Titel:");
        label3 = new JLabel("Beschreibung:");
        label4 = new JLabel("Teil von Vorhaben:");
        label5 = new JLabel("Rest Stunden zu Arbeiten:");
        label6 = new JLabel("Ist-Stunden gearbeitet:");
        label7 = new JLabel("Status:");
        label8 = new JLabel();
        status = new JTextField();
        status.setEditable(false);
        id = new JTextField();
        id.setText("99");
        id.setEditable(false);
        titel = new JTextField();
        titel.setText("Asdf Test");
        rest = new JTextField();
        ist = new JTextField();
        jtf_erledigt = new JTextField();
        jtf_erledigt.setText("noch nicht");
        final String[] combostrings = {"1", "2", "3", "4"};
        jcb_vorhaben = new JComboBox(combostrings);
        jta_beschreibung = new JTextArea(5,10);
        final JScrollPane sp = new JScrollPane(jta_beschreibung);

        button_erfassen = new JButton(erfassen);
        button_aendern = new JButton(aendern);
        button_erledigt = new JButton(erledigt);

        final Container c = new Container();
        c.setLayout(new FlowLayout());
        c.add(button_erfassen);
        c.add(button_aendern);
        c.add(button_erledigt);

        final Container labels = new Container();
        labels.setLayout(new GridLayout(17,1));
        labels.add(label1);
        labels.add(id);
        labels.add(label2);
        labels.add(titel);
        labels.add(label3);
        labels.add(sp);
        labels.add(label4);
        labels.add(jcb_vorhaben);
        labels.add(label5);
        labels.add(rest);
        labels.add(label6);
        labels.add(ist);
        labels.add(label7);
        labels.add(status);
        labels.add(label8);
        labels.add(jtf_erledigt);
        labels.add(c);
        createFrame(frameTitel, labels);
    }

    protected void createFrame(final String titel,final Container c){
        final JFrame frame = new JFrame(titel);
        final Container container = frame.getContentPane();
        final DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        final Date date = new Date();
        container.add(c);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,1000);
        frame.setVisible(true);
        System.err.println(df.format(date)+" "+getClass().getName()+" INFORMATION:");
    }

    final Action erfassen = new ExceptionReportingSwingAction("Erfassen"){
        @Override public void actionPerformedWithThrows(ActionEvent ev) {
            System.out.println("Erfassen des Schrittes: " + titel.getText());
            throw new Exc("Diese Aufgabe ist zu Schwer!");
        }
    };

    final Action aendern = new ExceptionReportingSwingAction("Aendern"){
        @Override public void actionPerformedWithThrows(ActionEvent ev) {
            System.out.println("Aendern des Schrittes: " + titel.getText());
        }
    };

    final Action erledigt = new ExceptionReportingSwingAction("Erledigt"){
        @Override public void actionPerformedWithThrows(ActionEvent ev) {
            System.out.println("Erledigen des Schrittes: " + titel.getText());
        }
    };
}
