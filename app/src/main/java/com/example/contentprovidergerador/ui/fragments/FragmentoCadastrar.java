package com.example.contentprovidergerador.ui.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//...
import com.example.contentprovidergerador.data.db.MensagemContract;
import com.example.contentprovidergerador.databinding.FragmentoCadastrarBinding;

/**
 * Fragmento responsável por fornecer uma interface para o usuário cadastrar uma nova mensagem.
 * Ele coleta os dados dos campos de texto, os valida e utiliza o ContentResolver
 * para inserir a nova mensagem no ContentProvider.
 */
public class FragmentoCadastrar extends Fragment {

    /** Instância do View Binding para este fragmento, permitindo acesso seguro às views do layout. */
    private FragmentoCadastrarBinding binding;

    /**
     * Chamado para que o fragmento instancie sua hierarquia de views.
     *
     * @param inflater O LayoutInflater usado para inflar as views no fragmento.
     * @param container O contêiner pai ao qual a view do fragmento será anexada.
     * @param savedInstanceState Se não nulo, o fragmento está sendo reconstruído a partir de um estado salvo.
     * @return Retorna a View raiz para a UI do fragmento.
     */
    // Cria e retorna a hierarquia de view associada ao fragmento.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentoCadastrarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Chamado imediatamente após onCreateView() ter retornado. É o local ideal
     * para configurar os listeners de UI e outras inicializações de view.
     *
     * @param view A View retornada por onCreateView().
     * @param savedInstanceState Se não nulo, o fragmento está sendo reconstruído.
     */
    // Configura os listeners de eventos após a criação da view.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSalvar.setOnClickListener(v -> salvarMensagem());
    }


    /**
     * Coleta os dados dos campos de entrada, valida-os e os insere no banco de dados
     * através do ContentProvider.
     */
    // Valida e salva os dados da nova mensagem.
    private void salvarMensagem() {
        String texto = binding.etMensagem.getText().toString().trim();
        String autor = binding.etAutor.getText().toString().trim();

        // Validação simples para garantir que os campos não estão vazios.
        if (texto.isEmpty() || autor.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtém o estado do switch de 'favorita'.
        boolean isFavorita = binding.switchFavorita.isChecked();

        // Cria um objeto ContentValues para armazenar os dados a serem inseridos.
        ContentValues values = new ContentValues();
        values.put(MensagemContract.MensagemEntry.COLUMN_TEXTO, texto);
        values.put(MensagemContract.MensagemEntry.COLUMN_AUTOR, autor);
        // Converte o valor booleano para um inteiro (1 para true, 0 para false) para compatibilidade com o SQLite.
        values.put(MensagemContract.MensagemEntry.COLUMN_FAVORITA, isFavorita ? 1 : 0);

        // Usa o ContentResolver para chamar o método insert() do ContentProvider.
        getContext().getContentResolver().insert(MensagemContract.MensagemEntry.CONTENT_URI, values);

        // Exibe uma mensagem de confirmação para o usuário.
        Toast.makeText(getContext(), "Mensagem salva!", Toast.LENGTH_SHORT).show();

        // Remove o fragmento atual da pilha de navegação, retornando à tela anterior.
        getParentFragmentManager().popBackStack();
    }

    /**
     * Chamado quando a hierarquia de views associada ao fragmento está sendo removida.
     * É crucial limpar as referências às views aqui para evitar vazamentos de memória (memory leaks).
     */
    // Limpa a referência do binding para evitar memory leaks.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}