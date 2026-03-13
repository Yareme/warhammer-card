    package pl.edu.us.warhammer_card.adapter;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;
    import java.util.List;

    import pl.edu.us.warhammer_card.R;
    import pl.edu.us.warhammer_card.table.Umiejetnosci;

    public class UmiejetnoscAdapter extends RecyclerView.Adapter<UmiejetnoscAdapter.ViewHolder> {

        private List<Umiejetnosci> umiejetnosciList = new ArrayList<>();
        private OnMinusClickListener onMinusClickListener;
        private OnPlusClickListener onPlusClickListener;
        // private TextChangedListener textChangedListener;

        public UmiejetnoscAdapter(List<Umiejetnosci> umiejetnosciList) {
            this.umiejetnosciList = umiejetnosciList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nazwa;
            TextView cecha;
            TextView wartoscZcehy;
            ImageButton buttonMinus;
            EditText ilosc;
            ImageButton buttonPlus;
            TextView summa;

            public ViewHolder(View itemView) {
                super(itemView);

                nazwa = itemView.findViewById(R.id.nazwa);

                cecha = itemView.findViewById(R.id.cecha);
                wartoscZcehy = itemView.findViewById(R.id.wartoscZcehy);
                buttonMinus = itemView.findViewById(R.id.buttonMinus);
                ilosc = itemView.findViewById(R.id.ilosc);
                buttonPlus = itemView.findViewById(R.id.buttonPlus);
                summa = itemView.findViewById(R.id.summa);

                buttonMinus.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Umiejetnosci umiejetnosci = umiejetnosciList.get(position);
                        if (onMinusClickListener != null) {
                            onMinusClickListener.onMinusClick(umiejetnosci);
                        }
                    }
                });

                buttonPlus.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Umiejetnosci umiejetnosci = umiejetnosciList.get(position);
                        if (onPlusClickListener != null) {
                            onPlusClickListener.onPlusClick(umiejetnosci);
                        }
                    }
                });
            }

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umiejetnosci_item, parent, false);

            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Umiejetnosci umiejetnosci = umiejetnosciList.get(position);

            holder.nazwa.setText(umiejetnosciList.get(position).getNazwa());

            holder.cecha.setText(umiejetnosciList.get(position).getCechaNazwa());

            holder.ilosc.setText(String.valueOf(umiejetnosci.getRozwoj()));
            holder.summa.setText(String.valueOf(umiejetnosci.getSuma()));
            holder.wartoscZcehy.setText(String.valueOf(umiejetnosci.getWortascCecha()));

            holder.buttonMinus.setOnClickListener(v -> {
                if (onMinusClickListener != null) {
                    onMinusClickListener.onMinusClick(umiejetnosci);
                }
            });

            holder.buttonPlus.setOnClickListener(v -> {
                if (onPlusClickListener != null) {
                    onPlusClickListener.onPlusClick(umiejetnosci);
                }
            });

        }

        @Override
        public int getItemCount() {
            return umiejetnosciList.size();
        }

        public void updateList(List<Umiejetnosci> newList) {
            umiejetnosciList = newList;
            notifyDataSetChanged();
        }


        public interface OnMinusClickListener {
            void onMinusClick(Umiejetnosci umiejetnosci);
        }

        public interface OnPlusClickListener {
            void onPlusClick(Umiejetnosci umiejetnosci);
        }

        public void setOnMinusClickListener(OnMinusClickListener listener) {
            this.onMinusClickListener = listener;
        }

        public void setOnPlusClickListener(OnPlusClickListener listener) {
            this.onPlusClickListener = listener;
        }
    }
