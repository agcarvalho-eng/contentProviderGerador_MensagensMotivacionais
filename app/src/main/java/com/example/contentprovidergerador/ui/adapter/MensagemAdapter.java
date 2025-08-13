package com.example.contentprovidergerador.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contentprovidergerador.R;
import com.example.contentprovidergerador.data.model.Mensagem;
import java.util.ArrayList;
import java.util.List;

// ...
public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.MensagemViewHolder> {
    // ...
    private List<Mensagem> mensagens = new ArrayList<>();

    // ... (onCreateViewHolder permanece o mesmo) ...
    @NonNull
    @Override
    public MensagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem, parent, false);
        return new MensagemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MensagemViewHolder holder, int position) {
        Mensagem msg = mensagens.get(position);
        holder.tvTexto.setText("\"" + msg.getTexto() + "\"");
        holder.tvAutor.setText("- " + msg.getAutor());

        // Lógica atualizada para usar o boolean
        if (msg.isFavorita()) {
            holder.tvFavorita.setText("Favorita");
            holder.tvFavorita.setVisibility(View.VISIBLE);
        } else {
            holder.tvFavorita.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    // ... (setMensagens e ViewHolder permanecem os mesmos) ...
    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
        notifyDataSetChanged();
    }

    static class MensagemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTexto, tvAutor, tvFavorita;
        public MensagemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTexto = itemView.findViewById(R.id.tvTexto);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvFavorita = itemView.findViewById(R.id.tvFavorita);
        }
    }
}
