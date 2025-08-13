package com.example.contentprovidergerador.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MensagemDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mensagens.db";
    // Versão incrementada para forçar a atualização do banco
    public static final int DATABASE_VERSION = 2;

    public MensagemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Usando a palavra-chave BOOLEAN para maior clareza
        final String SQL_CREATE_MENSAGENS_TABLE = "CREATE TABLE " +
                MensagemContract.MensagemEntry.TABLE_NAME + " (" +
                MensagemContract.MensagemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MensagemContract.MensagemEntry.COLUMN_TEXTO + " TEXT NOT NULL, " +
                MensagemContract.MensagemEntry.COLUMN_AUTOR + " TEXT NOT NULL, " +
                // Alterado de INTEGER para BOOLEAN (funcionalmente equivalente, mas mais legível)
                MensagemContract.MensagemEntry.COLUMN_FAVORITA + " BOOLEAN NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_MENSAGENS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Esta implementação simples apaga a tabela antiga e cria uma nova.
        // Em um app de produção, você faria uma migração de dados aqui.
        db.execSQL("DROP TABLE IF EXISTS " + MensagemContract.MensagemEntry.TABLE_NAME);
        onCreate(db);
    }
}
