package com.example.contentprovidergerador.ui.viewmodel;

import android.app.Application;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contentprovidergerador.data.db.MensagemContract;
import com.example.contentprovidergerador.data.model.Mensagem;

import java.util.ArrayList;

/**
 * ViewModel para a UI do gerador de mensagens.
 * Esta classe é responsável por buscar e manter a lista de mensagens,
 * expondo-a através de um {@link LiveData}. Ele também usa um {@link ContentObserver}
 * para recarregar os dados automaticamente sempre que o ContentProvider for modificado.
 * Estende {@link AndroidViewModel} para obter acesso seguro ao Context da aplicação.
 */
public class MensagemViewModel extends AndroidViewModel {
    /** LiveData que armazena e expõe a lista atual de mensagens para a UI. */
    private final MutableLiveData<ArrayList<Mensagem>> mensagens;
    /** Observador que escuta por mudanças nos dados do ContentProvider. */
    private final ContentObserver contentObserver;

    /**
     * Construtor do ViewModel.
     * Inicializa o LiveData, cria e registra o ContentObserver, e carrega os dados iniciais.
     * @param application A instância da aplicação, fornecida pelo framework.
     */
    // Construtor do ViewModel.
    public MensagemViewModel(@NonNull Application application) {
        super(application);
        mensagens = new MutableLiveData<>();

        // Instancia um ContentObserver que será acionado sempre que os dados na URI monitorada mudarem.
        contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // Quando uma mudança é detectada, a lista de mensagens é recarregada.
                loadMensagens();
            }
        };

        // Registra o observador para ser notificado sobre mudanças na URI de mensagens.
        getApplication().getContentResolver().registerContentObserver(
                MensagemContract.MensagemEntry.CONTENT_URI,
                true, // Notifica também os descendentes da URI
                contentObserver
        );

        loadMensagens(); // Carrega os dados pela primeira vez.
    }

    /**
     * Retorna o LiveData que contém a lista de mensagens.
     * A UI (Fragment) pode observar este LiveData para se atualizar automaticamente.
     * @return um {@link LiveData} contendo a lista de {@link Mensagem}.
     */
    // Expõe o LiveData para a UI.
    public LiveData<ArrayList<Mensagem>> getMensagens() {
        return mensagens;
    }

    /**
     * Carrega todas as mensagens do ContentProvider.
     * Este método consulta o ContentResolver, itera sobre o Cursor resultante,
     * converte cada linha em um objeto Mensagem e atualiza o LiveData.
     */
    // Busca os dados do ContentProvider e atualiza o LiveData.
    private void loadMensagens() {
        // Em um app de produção, esta operação de I/O deveria ser movida para uma thread de fundo.
        ArrayList<Mensagem> lista = new ArrayList<>();
        Cursor cursor = getApplication().getContentResolver().query(
                MensagemContract.MensagemEntry.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry._ID));
                    String texto = cursor.getString(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry.COLUMN_TEXTO));
                    String autor = cursor.getString(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry.COLUMN_AUTOR));
                    int favoritaInt = cursor.getInt(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry.COLUMN_FAVORITA));
                    boolean favoritaBool = favoritaInt == 1;

                    lista.add(new Mensagem(id, texto, autor, favoritaBool));
                }
            } finally {
                cursor.close(); // Garante que o cursor seja sempre fechado.
            }
        }
        // Define o novo valor no LiveData, notificando todos os seus observadores.
        mensagens.setValue(lista);
    }

    /**
     * Chamado quando o ViewModel está prestes a ser destruído.
     * É crucial cancelar o registro do ContentObserver aqui para evitar vazamentos de memória (memory leaks).
     */
    // Limpa os recursos quando o ViewModel é destruído.
    @Override
    protected void onCleared() {
        super.onCleared();
        // Remove o registro do observador para que ele não continue ativo desnecessariamente.
        getApplication().getContentResolver().unregisterContentObserver(contentObserver);
    }
}