package l1_ui;

public class UiVorhanden extends UiAufgabe{
    protected UiVorhanden(String titel) {
        super(titel);
        rest.setEditable(false);
        ist.setEditable(false);
    }

    public static void main(String[] args){
        final UiAufgabe ui = new UiAufgabe("Vorhaben erfassen/aendern");
        ui.label8.setText("End-Termin:");
        ui.button_erledigt.setVisible(false);
    }
}
