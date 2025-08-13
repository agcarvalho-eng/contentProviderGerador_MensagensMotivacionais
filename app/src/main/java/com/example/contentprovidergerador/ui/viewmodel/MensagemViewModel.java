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

public class MensagemViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Mensagem>> mensagens;
    private final ContentObserver contentObserver; // Novo: O observador

    public MensagemViewModel(@NonNull Application application) {
        super(application);
        mensagens = new MutableLiveData<>();

        // Novo: Cria uma instância do ContentObserver
        contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // Quando uma mudança é detectada, recarrega as mensagens
                loadMensagens();
            }
        };

        // Novo: Registra o observador para escutar mudanças na URI das mensagens
        getApplication().getContentResolver().registerContentObserver(
                MensagemContract.MensagemEntry.CONTENT_URI,
                true,
                contentObserver
        );

        loadMensagens(); // Carrega os dados iniciais
    }

    public LiveData<ArrayList<Mensagem>> getMensagens() {
        return mensagens;
    }

    private void loadMensagens() {
        // ... (o resto deste método permanece exatamente o mesmo)
        ArrayList<Mensagem> lista = new ArrayList<>();
        Cursor cursor = getApplication().getContentResolver().query(
                MensagemContract.MensagemEntry.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry._ID));
                String texto = cursor.getString(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry.COLUMN_TEXTO));
                String autor = cursor.getString(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry.COLUMN_AUTOR));
                int favoritaInt = cursor.getInt(cursor.getColumnIndexOrThrow(MensagemContract.MensagemEntry.COLUMN_FAVORITA));
                boolean favoritaBool = favoritaInt == 1;

                lista.add(new Mensagem(id, texto, autor, favoritaBool));
            }
            cursor.close();
        }
        mensagens.setValue(lista);
    }

    // Novo: Garante que o observador seja removido para evitar vazamentos de memória
    @Override
    protected void onCleared() {
        super.onCleared();
        getApplication().getContentResolver().unregisterContentObserver(contentObserver);
    }
}