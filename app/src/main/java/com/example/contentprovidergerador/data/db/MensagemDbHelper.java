package com.example.contentprovidergerador.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe auxiliar para gerenciar a criação e atualização do banco de dados SQLite.
 * Esta classe estende {@link SQLiteOpenHelper}, que abstrai a lógica complexa
 * de criação e versionamento do banco de dados.
 */
public class MensagemDbHelper extends SQLiteOpenHelper {

    /** O nome do arquivo do banco de dados. */
    public static final String DATABASE_NAME = "mensagens.db";

    /**
     * A versão do banco de dados. Se este número for alterado, o método onUpgrade()
     * será chamado para atualizar o esquema do banco de dados.
     */
    public static final int DATABASE_VERSION = 2;

    /**
     * Construtor da classe.
     * @param context O contexto da aplicação.
     */
    public MensagemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Chamado quando o banco de dados é criado pela primeira vez.
     * Este método é responsável por criar as tabelas e popular os dados iniciais, se necessário.
     * @param db O banco de dados.
     */
    // Cria a tabela do banco de dados.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // String SQL para criar a tabela de mensagens.
        final String SQL_CREATE_MENSAGENS_TABLE = "CREATE TABLE " +
                MensagemContract.MensagemEntry.TABLE_NAME + " (" +
                MensagemContract.MensagemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MensagemContract.MensagemEntry.COLUMN_TEXTO + " TEXT NOT NULL, " +
                MensagemContract.MensagemEntry.COLUMN_AUTOR + " TEXT NOT NULL, " +
                // A coluna 'favorita' é definida como BOOLEAN com valor padrão 0 (falso).
                MensagemContract.MensagemEntry.COLUMN_FAVORITA + " BOOLEAN NOT NULL DEFAULT 0);";

        // Executa o comando SQL.
        db.execSQL(SQL_CREATE_MENSAGENS_TABLE);
    }

    /**
     * Chamado quando a versão do banco de dados (DATABASE_VERSION) é incrementada.
     * Este método define como o esquema do banco de dados deve ser atualizado.
     * @param db O banco de dados.
     * @param oldVersion A versão antiga do banco de dados.
     * @param newVersion A nova versão do banco de dados.
     */
    // Atualiza o banco de dados quando a versão muda.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // A estratégia de atualização aqui é simples: apagar a tabela existente e criá-la novamente.
        // ATENÇÃO: Em uma aplicação real, isso apagaria todos os dados do usuário.
        // O ideal seria implementar uma migração de dados (ex: usando ALTER TABLE).
        db.execSQL("DROP TABLE IF EXISTS " + MensagemContract.MensagemEntry.TABLE_NAME);
        onCreate(db);
    }
}
