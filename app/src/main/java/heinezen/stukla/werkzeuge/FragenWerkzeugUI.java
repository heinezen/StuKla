package heinezen.stukla.werkzeuge;

import android.graphics.Color;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

class FragenWerkzeugUI
{
    public static final String NAME = "SE1-Test";

    private TextView _fragetextLabel;
    private ImageView _bildLabel;

    private TextView _quelltextLabel;

    private Button _naechsteFrageButton;
    private Button _vorherigeFrageButton;
    private Button _abgabeButton;

    private LinearLayout _anzeige;
    private LinearLayout _frageBereich;
    private LinearLayout _bildBereich;
    private LinearLayout _quelltextBereich;
    private LinearLayout _antwortenBereich;
    private LinearLayout _abgabeBereich;
    private LinearLayout _bedienBereich;

    /**
     * Erstellt eine neue GUI f�r ein FragenWerkzeug und f�gt dieses
     * zu den Observern hinzu.
     *
     * @param antwortenPanel
     */
    public FragenWerkzeugUI(LinearLayout anzeige, LinearLayout antwortenPanel)
    {
        initGUI(anzeige, antwortenPanel);
    }

    /**
     * Initilisiert die Interfaceelemente der GUI.
     */
    private void initGUI(LinearLayout anzeige, LinearLayout antwortenPanel)
    {
        _anzeige = anzeige;

        erzeugeFrageBereich();
        erzeugeBildBereich();
        erzeugeQuelltextBereich();
        erzeugeAntwortenBereich(antwortenPanel);
        erzeugeBedienBereich();
        erzeugeAbgabeBereich();
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
     * Erzeugt einen Fragenbereich mit einem JLabel
     */
    private void erzeugeFrageBereich()
    {
        _frageBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_frageBereich, 0);

        _fragetextLabel = new TextView(_anzeige.getContext());

        _frageBereich.addView(_fragetextLabel);
    }

    /**
     * Erzeugt einen Quelltextbereich mit einem JLabel.
     */
    private void erzeugeQuelltextBereich()
    {
        _quelltextBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_quelltextBereich, 2);

        _quelltextLabel = new TextView(_anzeige.getContext());
        _quelltextLabel.setTextColor(Color.BLUE);

        _quelltextBereich.addView(_quelltextLabel);
        _quelltextBereich.setEnabled(false);
    }

    /**
     * Erzeugt einen Antwortenbereich.
     */
    private void erzeugeAntwortenBereich(LinearLayout antwortenPanel)
    {
        _antwortenBereich = antwortenPanel;
    }

    /**
     * Erzeugt einen BedienBereich mit Vorw�rts- und Zur�ck-Knopf.
     */
    private void erzeugeBedienBereich()
    {
        _bedienBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_bedienBereich);

        _vorherigeFrageButton = new Button(_anzeige.getContext());
        _vorherigeFrageButton.setText("Vorherige Frage");

        _naechsteFrageButton = new Button(_anzeige.getContext());
        _naechsteFrageButton.setText("Nächste Frage");

        _bedienBereich.addView(_vorherigeFrageButton);
        _bedienBereich.addView(_naechsteFrageButton);
    }

    /**
     * Erzeug einen AbgabeBereich mit Abgabebutton.
     */
    private void erzeugeAbgabeBereich()
    {
        _abgabeBereich = new LinearLayout(_anzeige.getContext());
        _anzeige.addView(_abgabeBereich);

        _abgabeButton = new Button(_anzeige.getContext());
        _abgabeButton.setText("Test abgeben");

        _abgabeBereich.addView(_abgabeButton);
    }

    /**
     * Aktualisiert den Text im Fragebereich.
     *
     * @param neuerFragetext Der neue Fragetext.
     */
    public void aktualisiereFrage(String neuerFragetext)
    {
        _fragetextLabel.setText(neuerFragetext);
    }

    /**
     * Beendet den Test und gibt die erreichte Punktzahl aus.
     *
     * @param endergebnis  Erreichte Punktzahl
     * @param maxPunktzahl Die maximal erreichbare Punktzahl
     */
    public void beendeTest(int endergebnis, int maxPunktzahl)
    {
        TextView ergebnis = new TextView(_anzeige.getContext());
        ergebnis.setText("Erreichte Punktzahl: " + endergebnis + "/" + maxPunktzahl);

        _abgabeBereich.addView(ergebnis);
        _abgabeBereich.removeView(_abgabeButton);
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
     * @require neuesBild != null
     */
    public void aktualisiereBild(File neuesBild)
    {
        assert neuesBild != null : "Vorbedingung verletzt : neuesBilde != null";

        _bildLabel.setEnabled(true);
        _bildLabel.setImageURI(Uri.fromFile(neuesBild));
    }

    /**
     * Entfernt das Bild aus dem Bildbereich.
     */
    public void entferneBild()
    {
        _bildLabel.setEnabled(false);
    }

    /**
     * Gibt den Button der zur n�chsten Frage springt zur�ck.
     *
     * @return Der JButton
     */
    public Button getVorwaertsButton()
    {
        return _naechsteFrageButton;
    }

    /**
     * Gibt den Button der zur vorigen Frage springt zur�ck.
     *
     * @return Der JButton
     */
    public Button getRueckwaertsButton()
    {
        return _vorherigeFrageButton;
    }

    /**
     * Gibt den Button f�r die Abgabe zur�ck.
     *
     * @return Der JButton
     */
    public Button getAbgabeButton()
    {
        return _abgabeButton;
    }
}
