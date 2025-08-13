// package com.example.contentprovidergerador.ui.fragments;
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
import androidx.navigation.Navigation;

//...
import com.example.contentprovidergerador.data.db.MensagemContract;
import com.example.contentprovidergerador.databinding.FragmentoCadastrarBinding;

public class FragmentoCadastrar extends Fragment {
    private FragmentoCadastrarBinding binding;
    // ... (onCreateView e onViewCreated permanecem os mesmos) ...
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentoCadastrarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSalvar.setOnClickListener(v -> salvarMensagem());
    }


    private void salvarMensagem() {
        String texto = binding.etMensagem.getText().toString().trim();
        String autor = binding.etAutor.getText().toString().trim();

        if (texto.isEmpty() || autor.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lendo o estado do Switch
        boolean isFavorita = binding.switchFavorita.isChecked();

        ContentValues values = new ContentValues();
        values.put(MensagemContract.MensagemEntry.COLUMN_TEXTO, texto);
        values.put(MensagemContract.MensagemEntry.COLUMN_AUTOR, autor);
        // Convertendo o boolean para int para salvar no banco de dados
        values.put(MensagemContract.MensagemEntry.COLUMN_FAVORITA, isFavorita ? 1 : 0);

        getContext().getContentResolver().insert(MensagemContract.MensagemEntry.CONTENT_URI, values);

        Toast.makeText(getContext(), "Mensagem salva!", Toast.LENGTH_SHORT).show();

        // Fecha o fragmento após salvar
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}