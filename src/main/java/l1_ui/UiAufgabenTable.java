package l1_ui;

import l4_dm.DmAufgabeStatus;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;


public class UiAufgabenTable extends JPanel{
    protected final String[] tableHead;
    protected final Object[][] tableData;

    public UiAufgabenTable(String[] tableHead, Object[][] tableData){
        this.tableHead = tableHead;
        this.tableData = tableData;
    }

    public static void main(String[] args) {
        final String[] columnNames = {"ID",
                "Titel",
                "#Teile",
                "Status"};

        final Object[][] data = {
                {new Integer(1), "Schritt 1: Vorbereitung",
                        new Integer(0), DmAufgabeStatus.erledigt.toString()},
                {new Integer(2), "Vorhaben: Bearbeiten",
                        new Integer(2), DmAufgabeStatus.inBearbeitung.toString()},
                {new Integer(3), "Schritt 3: Beenden",
                        new Integer(0), DmAufgabeStatus.inBearbeitung.toString()}
        };
        final UiAufgabenTable table = new UiAufgabenTable(columnNames, data);
        table.fillWithTestData();
        final JFrame frame = new JFrame("Test UiAufgabenTable");
        final Container container = frame.getContentPane();
        final JScrollPane scrollPane = new JScrollPane(table);
        container.add(scrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550,150);
        frame.setVisible(true);
    }

    private void fillWithTestData() {
        setLayout(new BorderLayout());
        final JTable table = new JTable(tableData, tableHead);
        final JTableHeader header = table.getTableHeader();
        add(header, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
