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

public class MensagemProvider extends ContentProvider {

    private MensagemDbHelper mDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MENSAGENS = 100;
    private static final int MENSAGEM_COM_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MensagemContract.AUTHORITY;
        matcher.addURI(authority, MensagemContract.PATH_MENSAGENS, MENSAGENS);
        matcher.addURI(authority, MensagemContract.PATH_MENSAGENS + "/#", MENSAGEM_COM_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MensagemDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case MENSAGENS:
                cursor = database.query(MensagemContract.MensagemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("URI desconhecida: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        if (match == MENSAGENS) {
            long id = db.insert(MensagemContract.MensagemEntry.TABLE_NAME, null, values);
            if (id > 0) {
                returnUri = ContentUris.withAppendedId(MensagemContract.MensagemEntry.CONTENT_URI, id);
            } else {
                throw new android.database.SQLException("Falha ao inserir linha em " + uri);
            }
        } else {
            throw new UnsupportedOperationException("URI desconhecida: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        if (match == MENSAGEM_COM_ID) {
            String id = uri.getLastPathSegment();
            rowsUpdated = db.update(MensagemContract.MensagemEntry.TABLE_NAME, values, MensagemContract.MensagemEntry._ID + "=?", new String[]{id});
        } else {
            throw new UnsupportedOperationException("URI desconhecida para atualização: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Não implementado, mas poderia ser
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Não é estritamente necessário para este exemplo
        return null;
    }
}