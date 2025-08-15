package com.example.contentprovidergerador.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.contentprovidergerador.databinding.FragmentoListarBinding;
import com.example.contentprovidergerador.ui.adapter.MensagemAdapter;
import com.example.contentprovidergerador.ui.viewmodel.MensagemViewModel;

/**
 * Fragmento principal que exibe a lista de todas as mensagens cadastradas.
 * Ele utiliza um RecyclerView para exibir os dados de forma eficiente e observa
 * um {@link MensagemViewModel} para receber atualizações de dados de forma reativa.
 */
public class FragmentoListar extends Fragment {

    /** Instância do View Binding para acesso seguro às views do layout. */
    private FragmentoListarBinding binding;
    /** Instância do ViewModel, que serve como fonte de dados para este fragmento. */
    private MensagemViewModel viewModel;
    /** Adapter que conecta os dados da lista de mensagens ao RecyclerView. */
    private MensagemAdapter adapter;

    /**
     * Chamado para o fragmento instanciar sua hierarquia de views.
     *
     * @param inflater O LayoutInflater usado para inflar as views no fragmento.
     * @param container O contêiner pai ao qual a view do fragmento será anexada.
     * @param savedInstanceState Se não nulo, o fragmento está sendo reconstruído a partir de um estado salvo.
     * @return Retorna a View raiz para a UI do fragmento.
     */
    // Cria e retorna a view do fragmento.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentoListarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Chamado após a view do fragmento ter sido criada. É o local ideal para
     * inicializar componentes da UI, configurar listeners e observers.
     *
     * @param view A View retornada por onCreateView().
     * @param savedInstanceState Se não nulo, o fragmento está sendo reconstruído.
     */
    // Configura as views e os observers após a criação da view.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa o ViewModel com escopo da Activity, permitindo que os dados
        // sejam compartilhados entre fragments e sobrevivam a mudanças de configuração.
        viewModel = new ViewModelProvider(requireActivity()).get(MensagemViewModel.class);

        setupRecyclerView();
        observeViewModel();
    }

    /**
     * Configura o RecyclerView, incluindo seu LayoutManager e Adapter.
     * Prepara a lista para receber e exibir os dados.
     */
    // Inicializa e configura o RecyclerView.
    private void setupRecyclerView() {
        adapter = new MensagemAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    /**
     * Configura o observador no LiveData de mensagens do ViewModel.
     * A UI reagirá automaticamente a quaisquer alterações na lista de mensagens,
     * atualizando o adapter sempre que novos dados forem emitidos.
     */
    // Observa o LiveData do ViewModel para atualizar a UI.
    private void observeViewModel() {
        viewModel.getMensagens().observe(getViewLifecycleOwner(), mensagens -> {
            if (mensagens != null) {
                adapter.setMensagens(mensagens);
            }
        });
    }

    /**
     * Chamado quando a hierarquia de views do fragmento está sendo destruída.
     * É crucial limpar a referência ao binding para prevenir vazamentos de memória (memory leaks),
     * pois a view não existe mais, mas o objeto do fragmento pode continuar em memória.
     */
    // Limpa a referência do binding para evitar memory leaks.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}