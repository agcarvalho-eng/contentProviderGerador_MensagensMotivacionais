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

/**
 * Adapter para o RecyclerView que exibe a lista de mensagens.
 * Esta classe é responsável por pegar uma lista de objetos {@link Mensagem} e
 * mapear seus dados para as views individuais de cada item da lista.
 */
public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.MensagemViewHolder> {

    /** A lista de mensagens que serve como fonte de dados para o adapter. */
    private List<Mensagem> mensagens = new ArrayList<>();

    /**
     * Chamado quando o RecyclerView precisa de um novo {@link MensagemViewHolder} para representar um item.
     * Este método infla o layout do item a partir do XML e retorna uma nova instância do ViewHolder.
     *
     * @param parent O ViewGroup no qual a nova View será adicionada.
     * @param viewType O tipo da view do novo item.
     * @return Uma nova instância de MensagemViewHolder que contém a View para o item.
     */
    // Cria novas views (invocado pelo layout manager).
    @NonNull
    @Override
    public MensagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem, parent, false);
        return new MensagemViewHolder(view);
    }

    /**
     * Chamado pelo RecyclerView para exibir os dados na posição especificada.
     * Este método obtém os dados do modelo com base na posição e preenche as views do ViewHolder.
     *
     * @param holder O ViewHolder que deve ser atualizado.
     * @param position A posição do item no conjunto de dados.
     */
    // Substitui o conteúdo de uma view (invocado pelo layout manager).
    @Override
    public void onBindViewHolder(@NonNull MensagemViewHolder holder, int position) {
        Mensagem msg = mensagens.get(position);
        holder.tvTexto.setText("\"" + msg.getTexto() + "\"");
        holder.tvAutor.setText("- " + msg.getAutor());

        // Controla a visibilidade do indicador "Favorita" com base no valor booleano.
        if (msg.isFavorita()) {
            holder.tvFavorita.setText("Favorita");
            holder.tvFavorita.setVisibility(View.VISIBLE);
        } else {
            holder.tvFavorita.setVisibility(View.GONE);
        }
    }

    /**
     * Retorna o número total de itens no conjunto de dados.
     * @return O tamanho da lista de mensagens.
     */
    // Retorna o tamanho do seu conjunto de dados (invocado pelo layout manager).
    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    /**
     * Atualiza a lista de mensagens do adapter e notifica o RecyclerView para redesenhar a lista.
     * @param mensagens A nova lista de mensagens a ser exibida.
     */
    // Método auxiliar para atualizar os dados do adapter.
    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
        notifyDataSetChanged(); // Notifica o RecyclerView que os dados mudaram.
    }

    /**
     * ViewHolder que descreve a view de um item e armazena referências às suas sub-views.
     * Isso evita chamadas repetidas e custosas de `findViewById()`, otimizando a performance.
     */
    static class MensagemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTexto, tvAutor, tvFavorita;

        /**
         * Construtor do ViewHolder.
         * @param itemView A view raiz do layout do item.
         */
        public MensagemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Mapeia as views do layout para as variáveis locais.
            tvTexto = itemView.findViewById(R.id.tvTexto);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvFavorita = itemView.findViewById(R.id.tvFavorita);
        }
    }
}
