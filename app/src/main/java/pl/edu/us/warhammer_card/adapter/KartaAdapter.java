package pl.edu.us.warhammer_card.adapter;


import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import java.util.List;


import pl.edu.us.warhammer_card.DbHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaAdapter extends RecyclerView.Adapter<KartaAdapter.ViewHolder> {

    private List<Karta> kartaList = new ArrayList<>();
    private SQLiteDatabase db;
    private OnPdfClickListener onPdfClickListener;

    public <E> KartaAdapter(ArrayList<E> es, SQLiteDatabase db) {
        this.db = db;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView imie;
        TextView rasa;
        TextView profesja;
        Button pdf;

        public ViewHolder(View itemView) {
            super(itemView);
            imie = itemView.findViewById(R.id.imie);
            rasa = itemView.findViewById(R.id.rasa);
            profesja = itemView.findViewById(R.id.profesja);
            pdf = itemView.findViewById(R.id.pdf);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(kartaList.get(position));
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(kartaList.get(position));
                        return true;
                    }
                }
                return false;
            });
            pdf.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Karta karta = kartaList.get(position);
                     if (onPdfClickListener != null) {
                        onPdfClickListener.onPdfClick(karta);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.karta_item, parent, false);

        return new KartaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KartaAdapter.ViewHolder holder, int position) {

        Karta karta = kartaList.get(position);
        DbHelper helper = new DbHelper();

        holder.imie.setText(kartaList.get(position).getImie());

        kartaList.get(position).getRasaId();
        String rasaName = helper.getRasaNameById(db, kartaList.get(position).getRasaId());
        String profesjaName = helper.getProfesjaNameById(db, kartaList.get(position).getProfesjaId());

        holder.rasa.setText(rasaName);
        holder.profesja.setText(profesjaName);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(karta);
            }
        });

        holder.pdf.setOnClickListener(v -> {
            if (onPdfClickListener != null) {
                onPdfClickListener.onPdfClick(karta);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kartaList.size();
    }

    public void updateList(List<Karta> newList) {
        kartaList = newList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Karta karta);
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Karta karta);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(KartaAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<Karta> getKartaList() {
        return kartaList;
    }

    public interface OnPdfClickListener {
        void onPdfClick(Karta karta);
    }

    public void setOnPdfClickListener(OnPdfClickListener listener) {
        this.onPdfClickListener = listener;
    }
}
