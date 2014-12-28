package heinezen.stukla.werkzeuge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import heinezen.stukla.R;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.OptionalFrage;

/**
 * Für eine Frage kann ein solches FragenFragment erstellt werden, bei dem eine Frage verwaltet und
 * angezeigt wird.
 */
public class FragenFragment extends Fragment
{
    /**
     * Erkennungsstrings für das Weitergeben von Informationen an andere Prozesse
     */
    private static final String ARG_FRAGE = "Frage";
    private static final String ARG_BEENDET = "Beendet";

    /**
     * Die Frage des Fragments.
     */
    private Frage _frage;

    /**
     * Der Status des Fragments.
     */
    private boolean _istBeendet;

    /**
     * Das AntwortenWerkzeug zum Anzeigen der Antworten
     */
    private AntwortenWerkzeug _antwortenWerkzeug;

    /**
     * Das UI des Fragments.
     */
    private FragenFragmentUI _uiFragenFragment;

    /**
     * Leerer Konstruktor des FragenFragments.
     */
    public FragenFragment()
    {
    }

    /**
     * Erzeugt eine neue Instanz des FragenFragments.
     *
     * @param frage Die Frage des Fragments.
     * @param beendet Der Status des Fragments. Ist beendet auf true gesetzt, können keine Antworten
     * mehr gegeben werden.
     *
     * @return Ein FragenFragment.
     */
    public static FragenFragment newInstance(Frage frage, boolean beendet)
    {
        FragenFragment fragment = new FragenFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FRAGE, frage);
        args.putBoolean(ARG_BEENDET, beendet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            _frage = getArguments().getParcelable(ARG_FRAGE);
            _istBeendet = getArguments().getBoolean(ARG_BEENDET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_fragen, container, false);

        _antwortenWerkzeug = new AntwortenWerkzeug(
                (LinearLayout) rootView.findViewById(R.id._antwortenBereich));

        _antwortenWerkzeug.setAktuellenFragetyp(_frage.getFragetyp());

        _uiFragenFragment = new FragenFragmentUI(
                (LinearLayout) rootView.findViewById(R.id._anzeige));

        if(_istBeendet)
        {
            _antwortenWerkzeug.setzeBeendet();
            _uiFragenFragment.setzeBeendet(_frage.vergleicheAntworten(), _frage.getMaxPunktzahl());
        }

        erzeugeElemente();

        return rootView;
    }

    /**
     * Erzeugt die GUI-Elemente und passt sie an die Frage an.
     */
    private void erzeugeElemente()
    {
        erzeugeFrage();
        erzeugeQuelltext();
        erzeugeBild();
        erzeugeAntworten();
    }

    /**
     * Erzeugt den angezeigten Fragebereich.
     */
    private void erzeugeFrage()
    {
        _uiFragenFragment.aktualisiereFrage(_frage.getFragetext());
    }

    /**
     * Erzeugt den angezeigten Quelltext.
     */
    private void erzeugeQuelltext()
    {
        if(_frage instanceof OptionalFrage)
        {
            _uiFragenFragment.aktualisiereQuelltext(
                    ((OptionalFrage) _frage).getQuelltext());
        }
        else
        {
            _uiFragenFragment.aktualisiereQuelltext("");
        }
    }

    /**
     * Erzeugt das Bild der Frage.
     */
    private void erzeugeBild()
    {
        if(_frage instanceof OptionalFrage)
        {
            if(((OptionalFrage) _frage).hatBild())
            {
                _uiFragenFragment.aktualisiereBild(((OptionalFrage) _frage).getBild());
            }
            else
            {
                _uiFragenFragment.entferneBild();
            }
        }
        else
        {
            _uiFragenFragment.entferneBild();
        }
    }

    /**
     * Erzeugt den angezeigten Antwortenbereich.
     */
    private void erzeugeAntworten()
    {
        _antwortenWerkzeug
                .aktualisiereAntworten(_frage.getAntworttexte(), _frage.getAntwortenWerte(),
                        _frage.getTesterAntworten());
    }

    @Override
    public void onPause()
    {
        _frage.aktualisiereTesterAntworten(_antwortenWerkzeug.getEingaben());
        super.onDestroy();
    }
}