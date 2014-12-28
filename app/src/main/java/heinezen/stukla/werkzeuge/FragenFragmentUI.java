package heinezen.stukla.werkzeuge;

import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

/**
 * UI für das Anzeigen einer Frage (ohne Antworten).
 */
class FragenFragmentUI
{
    /**
     * Die Felder in den Informationen angezeigt werden.
     */
    private TextView _fragetextLabel;
    private ImageView _bildLabel;
    private TextView _quelltextLabel;

    /**
     * Die Container der UI.
     */
    private LinearLayout _anzeige;
    private LinearLayout _bildBereich;

    /**
     * Erstellt eine neue GUI für ein FragenWerkzeug und fügt dieses zu den Observern hinzu.
     *
     * @param anzeige Der Frame in dem das Fragment platziert wird.
     */
    public FragenFragmentUI(LinearLayout anzeige)
    {
        initGUI(anzeige);
    }

    /**
     * Initialisiert die Interfaceelemente der GUI.
     */
    private void initGUI(LinearLayout anzeige)
    {
        _anzeige = anzeige;

        erzeugeFrageBereich();
        erzeugeBildBereich();
        erzeugeQuelltextBereich();
    }

    /**
     * Erzeugt einen Fragebereich mit einem JLabel
     */
    private void erzeugeFrageBereich()
    {
        LinearLayout _frageBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_frageBereich, 0);

        _fragetextLabel = new TextView(_anzeige.getContext());

        _frageBereich.addView(_fragetextLabel);
    }

    /**
     * Erzeugt einen Bildbereich mit einem JLabel.
     */
    private void erzeugeBildBereich()
    {
        _bildBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_bildBereich, 1);

        _bildLabel = new ImageView(_anzeige.getContext());

        _bildBereich.addView(_bildLabel);
    }

    /**
     * Erzeugt einen Quelltextbereich mit einem JLabel.
     */
    private void erzeugeQuelltextBereich()
    {
        LinearLayout _quelltextBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_quelltextBereich, 2);

        _quelltextLabel = new TextView(_anzeige.getContext());
        _quelltextLabel.setTextColor(Color.BLUE);

        _quelltextBereich.addView(_quelltextLabel);
        _quelltextBereich.setEnabled(false);
    }

    /**
     * Aktualisiert den Text im Fragebereich.
     *
     * @param neuerFragetext Der neue Fragetext.
     */
    public void aktualisiereFrage(String neuerFragetext)
    {
        _fragetextLabel.setText(neuerFragetext);
        _fragetextLabel.setTextSize(16f);
        _fragetextLabel.setTextColor(Color.BLACK);
    }

    /**
     * Aktualisiert den Quelltextbereich.
     *
     * @param neuerQuelltext Der neue Quelltext.
     */
    public void aktualisiereQuelltext(String neuerQuelltext)
    {
        _quelltextLabel.setText(neuerQuelltext);
    }

    /**
     * Aktualisiert den Bildbereich mit einem Bild.
     *
     * @param neuesBild Das neue Bild.
     */
    public void aktualisiereBild(File neuesBild)
    {
        assert neuesBild != null : "Vorbedingung verletzt : neuesBild != null";

        _bildBereich.removeView(_bildLabel);
        _bildBereich.addView(_bildLabel);
        _bildLabel.setImageURI(Uri.fromFile(neuesBild));
    }

    /**
     * Entfernt das Bild aus dem Bildbereich.
     */
    public void entferneBild()
    {
        _bildBereich.removeView(_bildLabel);
    }

    /**
     * Erzeugt eine Übersicht für die Frage und zeigt die erreichten Punkte an.
     *
     * @param endPunktzahl Erreichte Punktzahl.
     * @param maxPunktzahl Maximale Punktzahl.
     */
    public void setzeBeendet(int endPunktzahl, int maxPunktzahl)
    {
        TextView ergebnis = new TextView(_anzeige.getContext());
        ergebnis.setText(String.format("Erreichte Punktzahl: %s/%s", endPunktzahl, maxPunktzahl));

        _anzeige.addView(ergebnis);
    }
}
