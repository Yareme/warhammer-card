package pl.edu.us.warhammer_card.ui.mistrzgry.rozdzialy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pl.edu.us.warhammer_card.R;

public class PodRozdzialFragment extends Fragment {

    private static final String ARG_CHAPTER_INDEX = "chapter_index";

    private static final String[] CHAPTERS_TEXTS = {
            "Rozdział 1: Wstęp\n\nWstęp do książki. Krótkie wprowadzenie do tematu.",
            "Rozdział 2: Historia\n\nHistoria opisywanego zjawiska. Przegląd wydarzeń.",
            "Rozdział 3: Analiza\n\nAnaliza szczegółowa. Omówienie poszczególnych aspektów.",
            "Rozdział 4: Podsumowanie\n\nPodsumowanie całej książki, wnioski."
    };

    public static PodRozdzialFragment newInstance(int chapterIndex) {
        PodRozdzialFragment fragment = new PodRozdzialFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHAPTER_INDEX, chapterIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pod_rozdzial, container, false);
        TextView chapterContentTextView = rootView.findViewById(R.id.pod_rozdzial_text);

        int chapterIndex = getArguments() != null ? getArguments().getInt(ARG_CHAPTER_INDEX, 0) : 0;
        String chapterText = CHAPTERS_TEXTS[chapterIndex];
        chapterContentTextView.setText(chapterText);

        return rootView;
    }
}
