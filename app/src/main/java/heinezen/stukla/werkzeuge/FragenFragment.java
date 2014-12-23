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

public class FragenFragment extends Fragment
{
    private static final String ARG_FRAGE = "Frage";
    private static final String ARG_BEENDET = "Beendet";

    private Frage _frage;
    private boolean _istBeendet;

    private AntwortenWerkzeug _antwortenWerkzeug;
    private FragenFragmentUI _uiFragenFragment;

    /**
     * Erzeugt eine neue Instanz des FragenFragments.
     *
     * @param frage Die Frage des Fragments.
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

    public FragenFragment()
    {
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

        aendereElemente();

        return rootView;
    }

    /**
     * Ändert die GUI-Elemente und passt sie an die Frage an.
     */
    private void aendereElemente()
    {
        aendereFrage();
        aendereQuelltext();
        aendereBild();
        aendereAntworten();
    }

    private void aendereBild()
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
     * Ändert den angezeigten Fragebereich.
     */
    private void aendereFrage()
    {
        _uiFragenFragment.aktualisiereFrage(_frage.getFragetext());
    }

    /**
     * Ändert den angezeigten Quelltext
     */
    private void aendereQuelltext()
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
     * Ändert den angezeigten Antwortenbereich.
     */
    private void aendereAntworten()
    {
        _antwortenWerkzeug
                .aktualisiereAntworten(_frage.getAntworttexte(), _frage.getAntwortenWerte(),
                        _frage.getSpielerAntworten());
    }

    @Override
    public void onPause()
    {
        _frage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());
        super.onDestroy();
    }
}