package heinezen.stukla.werkzeuge;

import android.text.Editable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

class AntwortenUI
{
    /**
     * Der Antwortenbereich der UI
     */
    private LinearLayout _antwortenBereich;

    /**
     * Wird auf true gesetzt sobald der Test beendet wurde.
     */
    private boolean _testBeendet;

    public AntwortenUI(LinearLayout antwortenBereich)
    {
        _testBeendet = false;
        erzeugeAntwortenBereich(antwortenBereich);
    }

    /**
     * Erzeugt einen Antwortenbereich.
     */
    private void erzeugeAntwortenBereich(LinearLayout antwortenBereich)
    {
        _antwortenBereich = antwortenBereich;
    }

    /**
     * Gibt den Antwortenbereich zur�ck.
     *
     * @return Den Antwortenbereich
     */
    public LinearLayout getAntwortenPanel()
    {
        return _antwortenBereich;
    }

    /**
     * Aktualisiert den Antwortenbereich. Bedienelemente passen sich dem
     * jeweiligen Fragetyp an.
     *
     * @param antwortTexte Die neuen Texte der Antworten.
     * @param antwortWerte Die neuen Werte der Antworten.
     * @param fragetyp
     */
    public void aktualisiereAntworten(String[] antwortTexte, Object antwortWerte, String fragetyp)
    {
        switch(fragetyp)
        {
            case "Klick":
            {
                _antwortenBereich.removeAllViews();

                boolean[] neueAntwortWerte = (boolean[]) antwortWerte;

                for(int i = 0; i < antwortTexte.length; ++i)
                {
                    CheckBox neueCheckBox = new CheckBox(_antwortenBereich.getContext());
                    neueCheckBox.setText(antwortTexte[i]);
                    neueCheckBox.setChecked(neueAntwortWerte[i]);
                    _antwortenBereich.addView(neueCheckBox);

                    if(_testBeendet)
                    {
                        neueCheckBox.setEnabled(false);
                    }
                }
                break;
            }
            case "Auswahl":
            {
                _antwortenBereich.removeAllViews();

                boolean[] neueAntwortWerte = (boolean[]) antwortWerte;
                RadioGroup radioGruppe = new RadioGroup(_antwortenBereich.getContext());

                for(int i = 0; i < antwortTexte.length; ++i)
                {
                    RadioButton neuerRadioButton = new RadioButton(_antwortenBereich.getContext());
                    neuerRadioButton.setText(antwortTexte[i]);
                    radioGruppe.addView(neuerRadioButton);
                    neuerRadioButton.setChecked(neueAntwortWerte[i]);

                    if(_testBeendet)
                    {
                        neuerRadioButton.setEnabled(false);
                    }
                }
                _antwortenBereich.addView(radioGruppe);

                break;
            }
            case "Text":
            {
                _antwortenBereich.removeAllViews();

                String neueAntwortWerte = (String) antwortWerte;
                EditText textfeld = new EditText(_antwortenBereich.getContext());
                textfeld.setText(neueAntwortWerte);

                _antwortenBereich.addView(textfeld);

                if(_testBeendet)
                {
                    textfeld.setEnabled(false);
                }

                break;
            }
        }
    }

    /**
     * Gibt die Eingaben des Spielers zur derzeitigen Antwort zur�ck.
     * dabei wird je nach Fragetyp ein unterschiedlicher R�ckgabetyp
     * verwendet. Beim casten ist auf den Fragetyp zu achten.
     *
     * @param fragetyp
     * @return Ein Object mit den derzeitigen Antwortwerten.
     */
    public Object getEingaben(String fragetyp)
    {
        switch(fragetyp)
        {
            case "Klick":
            {
                CheckBox[] klickAntworten = new CheckBox[_antwortenBereich.getChildCount()];

                for(int i = 0; i < _antwortenBereich.getChildCount(); ++i)
                {
                    klickAntworten[i] = (CheckBox) _antwortenBereich.getChildAt(i);
                }

                boolean[] werte = new boolean[klickAntworten.length];

                for(int i = 0; i < klickAntworten.length; ++i)
                {
                    werte[i] = klickAntworten[i].isChecked();
                }

                return werte;
            }
            case "Auswahl":
            {
                RadioGroup radioGruppe = (RadioGroup) _antwortenBereich.getChildAt(0);

                boolean[] werte = new boolean[radioGruppe.getChildCount()];

                for(int i = 0; i < werte.length; ++i)
                {
                    RadioButton button = (RadioButton) radioGruppe.getChildAt(i);
                    werte[i] = button.isChecked();
                }

                return werte;
            }
            case "Text":
            {
                EditText textfeld = (EditText) _antwortenBereich.getChildAt(0);

                Editable wert = textfeld.getText();
                String string = wert.toString();

                return string;
            }
            default:
                return null;
        }
    }

    /**
     * Beendet den Test, sodass keine Antworten mehr gegeben werden k�nnen.
     */
    public void setzeBeendet()
    {
        _testBeendet = true;
        sperreAntwortenBereich();
    }

    /**
     * Sperrt den Antwortenbereich für Eingaben.
     */
    private void sperreAntwortenBereich()
    {
        for(int i = 0; i < _antwortenBereich.getChildCount(); ++i)
        {
            _antwortenBereich.getChildAt(i).setEnabled(false);
        }
    }
}
