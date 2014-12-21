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

    private Frage _frage;

    private AntwortenWerkzeug _antwortenWerkzeug;
    private FragenWerkzeugUI _uiFragenWerkzeug;

    /**
     * Erzeugt eine neue Instanz des FragenFragments.
     *
     * @param frage Die Frage des Fragments.
     *
     * @return Ein FragenFragment.
     */
    public static FragenFragment newInstance(Frage frage)
    {
        FragenFragment fragment = new FragenFragment();
        Bundle fragen = new Bundle();
        fragen.putParcelable(ARG_FRAGE, frage);
        fragment.setArguments(fragen);
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
        _uiFragenWerkzeug = new FragenWerkzeugUI(
                (LinearLayout) rootView.findViewById(R.id._anzeige), _antwortenWerkzeug.getPanel());

        _antwortenWerkzeug.setAktuellenFragetyp(_frage.getFragetyp());

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
                _uiFragenWerkzeug.aktualisiereBild(((OptionalFrage) _frage).getBild());
            }
            else
            {
                _uiFragenWerkzeug.entferneBild();
            }
        }
        else
        {
            _uiFragenWerkzeug.entferneBild();
        }
    }

    /**
     * �ndert den angezeigten Fragebereich.
     */
    private void aendereFrage()
    {
        _uiFragenWerkzeug.aktualisiereFrage(_frage.getFragetext());
    }

    /**
     * �ndert den angezeigten Quelltext
     */
    private void aendereQuelltext()
    {
        if(_frage instanceof OptionalFrage)
        {
            _uiFragenWerkzeug.aktualisiereQuelltext(
                    ((OptionalFrage) _frage).getQuelltext());
        }
        else
        {
            _uiFragenWerkzeug.aktualisiereQuelltext("");
        }
    }

    /**
     * �ndert den angezeigten Antwortenbereich.
     */
    private void aendereAntworten()
    {
        _antwortenWerkzeug.aktualisiereAntworten(_frage.getAntworttexte(),
                _frage.getSpielerAntworten());
    }
}
