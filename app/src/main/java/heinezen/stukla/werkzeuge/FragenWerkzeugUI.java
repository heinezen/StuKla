package heinezen.stukla.werkzeuge;

import android.widget.LinearLayout;
import android.widget.TextView;

class FragenWerkzeugUI
{
    private LinearLayout _beendetPanel;

    /**
     * Erstellt eine neue GUI f�r ein FragenWerkzeug und f�gt dieses zu den Observern hinzu.
     */
    public FragenWerkzeugUI(LinearLayout beendetPanel)
    {
        initGUI(beendetPanel);
    }

    /**
     * Initilisiert die Interfaceelemente der GUI.
     */
    private void initGUI(LinearLayout beendetPanel)
    {
        _beendetPanel = beendetPanel;
    }

    /**
     * Beendet den Test und gibt die erreichte Punktzahl aus.
     *
     * @param endergebnis Erreichte Punktzahl
     * @param maxPunktzahl Die maximal erreichbare Punktzahl
     */
    public void beendeTest(int endergebnis, int maxPunktzahl)
    {
        TextView ergebnis = new TextView(_beendetPanel.getContext());
        ergebnis.setText("Erreichte Punktzahl: " + endergebnis + "/" + maxPunktzahl);

        _beendetPanel.addView(ergebnis);
    }
}
