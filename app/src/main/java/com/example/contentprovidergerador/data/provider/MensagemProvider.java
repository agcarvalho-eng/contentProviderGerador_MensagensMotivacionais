package com.example.contentprovidergerador.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contentprovidergerador.data.db.MensagemContract;
import com.example.contentprovidergerador.data.db.MensagemDbHelper;

/**
 * O Content Provider para a base de dados de mensagens.
 * Esta classe é a camada de abstração que permite que outras aplicações (Consumidores)
 * acessem e modifiquem os dados deste aplicativo de forma segura e padronizada,
 * sem expor os detalhes da implementação do banco de dados.
 */
public class MensagemProvider extends ContentProvider {

    /** Instância do helper do banco de dados para acessar o SQLite. */
    private MensagemDbHelper mDbHelper;

    /** Objeto UriMatcher para corresponder as URIs de conteúdo com os códigos de operação. */
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Códigos de correspondência para o UriMatcher
    private static final int MENSAGENS = 100; // Para acessar a tabela inteira de mensagens
    private static final int MENSAGEM_COM_ID = 101; // Para acessar uma única mensagem por seu ID

    /**
     * Constrói e retorna um UriMatcher que reconhece as URIs que este provedor pode manipular.
     * @return Um UriMatcher configurado.
     */
    // Constrói o UriMatcher para mapear as URIs para os códigos correspondentes.
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MensagemContract.AUTHORITY;

        // URI para a tabela de mensagens (ex: content://.../mensagens)
        matcher.addURI(authority, MensagemContract.PATH_MENSAGENS, MENSAGENS);
        // URI para uma única mensagem (ex: content://.../mensagens/3)
        matcher.addURI(authority, MensagemContract.PATH_MENSAGENS + "/#", MENSAGEM_COM_ID);

        return matcher;
    }

    /**
     * Inicializa o provedor. É chamado quando o provedor é iniciado.
     * @return true se o provedor foi carregado com sucesso.
     */
    // Inicializa o provedor e o helper do banco de dados.
    @Override
    public boolean onCreate() {
        mDbHelper = new MensagemDbHelper(getContext());
        return true;
    }

    /**
     * Realiza uma consulta ao banco de dados.
     * @param uri A URI a ser consultada.
     * @param projection A lista de colunas a serem retornadas.
     * @param selection Um filtro para as linhas a serem retornadas.
     * @param selectionArgs Os valores para o filtro de seleção.
     * @param sortOrder A ordem de classificação dos resultados.
     * @return Um Cursor contendo o resultado da consulta.
     */
    // Manipula as requisições de consulta (query) dos clientes.
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case MENSAGENS:
                // Se a URI corresponde a todas as mensagens, faz a query na tabela.
                cursor = database.query(MensagemContract.MensagemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("URI desconhecida: " + uri);
        }

        // Configura uma notificação na URI para que o cursor seja atualizado se os dados mudarem.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insere uma nova linha no provedor.
     * @param uri A URI do conteúdo para a inserção.
     * @param values Os valores a serem inseridos.
     * @return A URI da nova linha inserida.
     */
    // Manipula as requisições de inserção de novos dados.
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        if (match == MENSAGENS) {
            long id = db.insert(MensagemContract.MensagemEntry.TABLE_NAME, null, values);
            if (id > 0) {
                // Se a inserção for bem-sucedida, cria a URI específica para o novo registro.
                returnUri = ContentUris.withAppendedId(MensagemContract.MensagemEntry.CONTENT_URI, id);
            } else {
                throw new android.database.SQLException("Falha ao inserir linha em " + uri);
            }
        } else {
            throw new UnsupportedOperationException("URI desconhecida: " + uri);
        }

        // Notifica todos os 'listeners' que os dados nesta URI mudaram.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    /**
     * Atualiza dados existentes no provedor.
     * @param uri A URI a ser atualizada.
     * @param values Um conjunto de pares coluna/valor para atualizar.
     * @param selection Um filtro opcional para aplicar à atualização.
     * @param selectionArgs Os valores para o filtro de seleção.
     * @return O número de linhas atualizadas.
     */
    // Manipula as requisições de atualização de dados existentes.
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        if (match == MENSAGEM_COM_ID) {
            // Extrai o ID da URI para atualizar a linha correta.
            String id = uri.getLastPathSegment();
            rowsUpdated = db.update(MensagemContract.MensagemEntry.TABLE_NAME, values, MensagemContract.MensagemEntry._ID + "=?", new String[]{id});
        } else {
            throw new UnsupportedOperationException("URI desconhecida para atualização: " + uri);
        }

        // Se alguma linha foi atualizada, notifica os 'listeners'.
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * Exclui dados do provedor.
     * (Não implementado neste exemplo)
     * @param uri A URI a ser excluída.
     * @param selection Um filtro opcional.
     * @param selectionArgs Os valores para o filtro.
     * @return O número de linhas excluídas.
     */
    // Manipula as requisições de exclusão.
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Lógica de exclusão vai ser implementada aqui. Não necessário neste exemplo.
        return 0;
    }

    /**
     * Retorna o tipo MIME dos dados na URI fornecida.
     * (Não implementado neste exemplo)
     * @param uri A URI para a qual consultar o tipo.
     * @return Uma String com o tipo MIME.
     */
    // Retorna o tipo MIME da URI.
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Não é necessário para este exemplo.
        return null;
    }
}