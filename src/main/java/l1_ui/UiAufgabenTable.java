package l1_ui;

import l4_dm.DmAufgabeStatus;
import l4_dm.DmSchritt;
import l4_dm.DmVorhaben;

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

        final DmSchritt schritt1 = new DmSchritt(){
            @Override
            public Long getId(){
                return (long) 1;
            }
        };
        schritt1.setTitel("Schritt 1: Vorbereitung");

        final DmVorhaben vorhaben1 = new DmVorhaben(){
            @Override
            public Long getId(){
                return (long) 2;
            }
            @Override
            public DmAufgabeStatus getStatus(){
                return DmAufgabeStatus.inBearbeitung;
            }
            @Override
            public int getAnzahlTeile(){
                return 2;
            }
        };
        vorhaben1.setTitel("Vorhaben: Bearbeiten");

        final DmSchritt schritt2 = new DmSchritt(){
            @Override
            public Long getId(){
                return (long) 3;
            }
        };
        schritt2.setTitel("Schritt 3: Beenden");

        final Object[][] data = {
                {schritt1.getId() , schritt1.getTitel(), schritt1.getAnzahlTeile()
                        , schritt1.getStatus()},
                {vorhaben1.getId(), vorhaben1.getTitel(), vorhaben1.getAnzahlTeile()
                        , vorhaben1.getStatus()},
                {schritt2.getId() , schritt2.getTitel(), schritt2.getAnzahlTeile()
                        , schritt2.getStatus()}
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
