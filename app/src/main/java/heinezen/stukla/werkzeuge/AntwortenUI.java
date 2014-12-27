package heinezen.stukla.werkzeuge;

import android.graphics.Color;
import android.text.Editable;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import heinezen.stukla.fachwerte.enums.Fragetyp;

class AntwortenUI
{
    /**
     * Der Antwortenbereich der UI
     */
    private LinearLayout _antwortenBereich;

    /**
     * Wird auf true gesetzt sobald der Test beendet wurde.
     */
    private boolean _istBeendet;

    public AntwortenUI(LinearLayout antwortenBereich)
    {
        _istBeendet = false;
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
     * Aktualisiert den Antwortenbereich. Bedienelemente passen sich dem jeweiligen Fragetyp an.
     *
     * @param antwortTexte Die Texte der Antworten.
     * @param antwortenWerte Die tatsächlichen Werte der Antworten.
     * @param testAntwortenWerte Die Werte der im Test gegebenen Antworten.
     * @param fragetyp Der Fragetyp der Frage.
     */
    public void aktualisiereAntworten(String[] antwortTexte, Object antwortenWerte,
                                      Object testAntwortenWerte, Fragetyp fragetyp)
    {
        switch(fragetyp)
        {
            case KLICK:
            {
                _antwortenBereich.removeAllViews();

                boolean[] neueAntwortWerte = (boolean[]) testAntwortenWerte;

                for(int i = 0; i < antwortTexte.length; ++i)
                {
                    CheckBox neueCheckBox = new CheckBox(_antwortenBereich.getContext());
                    neueCheckBox.setText(antwortTexte[i]);
                    neueCheckBox.setChecked(neueAntwortWerte[i]);
                    _antwortenBereich.addView(neueCheckBox);

                    if(_istBeendet)
                    {
                        neueCheckBox.setEnabled(false);

                        boolean[] werte = (boolean[]) antwortenWerte;
                        if(werte[i] && neueAntwortWerte[i])
                        {
                            neueCheckBox.setBackgroundColor(Color.GREEN);
                        }
                        else if(werte[i])
                        {
                            neueCheckBox.setBackgroundColor(Color.CYAN);
                        }
                        else if(!werte[i] && neueAntwortWerte[i])
                        {
                            neueCheckBox.setBackgroundColor(Color.RED);
                        }
                    }
                }
                break;
            }
            case AUSWAHL:
            {
                _antwortenBereich.removeAllViews();

                boolean[] neueAntwortWerte = (boolean[]) testAntwortenWerte;
                RadioGroup radioGruppe = new RadioGroup(_antwortenBereich.getContext());

                RadioGroup.LayoutParams param = new RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                for(int i = 0; i < antwortTexte.length; ++i)
                {
                    RadioButton neuerRadioButton = new RadioButton(_antwortenBereich.getContext());
                    neuerRadioButton.setLayoutParams(param);
                    neuerRadioButton.setText(antwortTexte[i]);
                    radioGruppe.addView(neuerRadioButton);
                    neuerRadioButton.setChecked(neueAntwortWerte[i]);

                    if(_istBeendet)
                    {
                        neuerRadioButton.setEnabled(false);

                        boolean[] werte = (boolean[]) antwortenWerte;
                        if(werte[i])
                        {
                            neuerRadioButton.setBackgroundColor(Color.GREEN);
                        }
                        else if(werte[i])
                        {
                            neuerRadioButton.setBackgroundColor(Color.CYAN);
                        }
                        else if(!werte[i] && neueAntwortWerte[i])
                        {
                            neuerRadioButton.setBackgroundColor(Color.RED);
                        }
                    }
                }
                _antwortenBereich.addView(radioGruppe);

                break;
            }
            case TEXT:
            {
                _antwortenBereich.removeAllViews();

                String neueAntwort = (String) testAntwortenWerte;
                EditText textfeld = new EditText(_antwortenBereich.getContext());
                textfeld.setText(neueAntwort);

                _antwortenBereich.addView(textfeld);

                if(_istBeendet)
                {
                    textfeld.setEnabled(false);

                    String[] werte = (String[]) antwortenWerte;
                    boolean richtigeAntwort = false;
                    for(String antwort : werte)
                    {
                        if(!antwort.toLowerCase().equals(neueAntwort.toLowerCase()))
                        {
                            TextView antwortTextView = new TextView(_antwortenBereich.getContext());
                            antwortTextView.setText(antwort);
                            antwortTextView.setTextColor(Color.CYAN);

                            _antwortenBereich.addView(antwortTextView);
                        }
                        else
                        {
                            richtigeAntwort = true;
                        }
                    }

                    if(richtigeAntwort)
                    {
                        textfeld.setTextColor(Color.GREEN);
                    }
                    else
                    {
                        textfeld.setTextColor(Color.RED);
                    }
                }

                break;
            }
        }
    }

    /**
     * Gibt die Eingaben des Spielers zur derzeitigen Antwort zur�ck. dabei wird je nach Fragetyp
     * ein unterschiedlicher R�ckgabetyp verwendet. Beim casten ist auf den Fragetyp zu achten.
     *
     * @param fragetyp Der Fragetyp der Frage.
     *
     * @return Ein Object mit den derzeitigen Antwortwerten.
     */
    public Object getEingaben(Fragetyp fragetyp)
    {
        switch(fragetyp)
        {
            case KLICK:
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
            case AUSWAHL:
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
            case TEXT:
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
        _istBeendet = true;
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
