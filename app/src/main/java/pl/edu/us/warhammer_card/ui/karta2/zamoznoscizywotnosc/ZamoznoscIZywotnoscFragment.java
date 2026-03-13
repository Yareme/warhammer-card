package pl.edu.us.warhammer_card.ui.karta2.zamoznoscizywotnosc;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaZamoznosciIZywotnoscBinding;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Talent;

public class ZamoznoscIZywotnoscFragment extends Fragment {
    FragmentKartaZamoznosciIZywotnoscBinding binding;
    ZamoznoscIZywotnoscFragmentViewModel zamoznoscIZywotnoscFragmentViewModel;
    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding= FragmentKartaZamoznosciIZywotnoscBinding.inflate(inflater, container, false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId = args.getInt("KartaId");

        karta = dbHelper.getKartaById(db, kartaId);

        zamoznoscIZywotnoscFragmentViewModel =
                new ViewModelProvider(this).get(ZamoznoscIZywotnoscFragmentViewModel.class);
        zamoznoscIZywotnoscFragmentViewModel.getKarta(kartaId);

        zamoznoscIZywotnoscFragmentViewModel.getKarta(kartaId).observe(getViewLifecycleOwner(), new Observer<Karta>() {
            @Override
            public void onChanged(Karta karta) {
                binding.editKorona.setText(String.valueOf(karta.getZlotaKorona()));
                binding.editSrebro.setText(String.valueOf(karta.getSreblo()));
                binding.editPensy.setText(String.valueOf(karta.getPensy()));

                int S = karta.getCechyList().get(2).getRozw() + karta.getCechyList().get(2).getWartPo();
                int Wt = karta.getCechyList().get(3).getRozw() + karta.getCechyList().get(3).getWartPo();
                int SW = karta.getCechyList().get(8).getRozw() + karta.getCechyList().get(8).getWartPo();

                int BS = getPierwszaCyfra(S);
                int BWt = getPierwszaCyfra(Wt) * 2;
                int BSW = getPierwszaCyfra(SW);

                binding.bsWart.setText(String.valueOf(BS));
                binding.bwtWart.setText(String.valueOf(BWt));
                binding.bswWart.setText(String.valueOf(BSW));

                int twardziel = 0;
                int poziomTwardziel =0;

                int idTalentuTwardziel= 168;
                boolean containsId = karta.getTalentList().stream()
                        .anyMatch(obj -> obj.getId() == idTalentuTwardziel);

                if (containsId){
                    for (Talent talent : karta.getTalentList()) {
                        if (talent.getId() == idTalentuTwardziel) {
                            poziomTwardziel = talent.getPoziom();
                            break;
                        }
                    }
                    twardziel = BWt/2 * poziomTwardziel;
                }

                binding.twWartosc.setText(String.valueOf(poziomTwardziel));

                binding.sumaWar.setText(String.valueOf(BS + BWt + BSW + twardziel));

                binding.zywotnoscEdit.setText(String.valueOf(karta.getZywotnoscAktualna()));

                zamoznoscIZywotnoscFragmentViewModel.updateKarta(karta);
                zamoznoscIZywotnoscFragmentViewModel.getKarta(kartaId);
            }
        });

        binding.editKorona.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setZlotaKorona(wartosc);
                zamoznoscIZywotnoscFragmentViewModel.updateKarta(karta);
            }
        });
        binding.editSrebro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setSreblo(wartosc);
                zamoznoscIZywotnoscFragmentViewModel.updateKarta(karta);
            }
        });
        binding.editPensy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setPensy(wartosc);
                zamoznoscIZywotnoscFragmentViewModel.updateKarta(karta);
            }
        });

        binding.zywotnoscEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setZywotnoscAktualna(wartosc);
                zamoznoscIZywotnoscFragmentViewModel.updateKarta(karta);
            }
        });


        return binding.getRoot();
    }

    public static int getPierwszaCyfra(int liczba) {
        while (liczba >= 10) {
            liczba /= 10;
        }
        return liczba;
    }

}