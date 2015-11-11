package l1_ui;


public class UiSchritt extends UiAufgabe{

    protected UiSchritt(String frameTitel) {
        super(frameTitel);
        jtf_erledigt.setEditable(false);
    }

    public static void main(String[] args){
        final UiAufgabe ui = new UiAufgabe("Schritt erfassen/aendern");
        ui.label8.setText("Erledigt-Zeitpunkt:");
        ui.status.setText("Neu");
    }
}
