package com.example.contentprovidergerador.data.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Define o "contrato" para o Content Provider de mensagens.
 * Uma classe de contrato é um contêiner para constantes que definem nomes para URIs,
 * tabelas e colunas. Isso garante que o código que acessa o provedor use as mesmas
 * constantes, evitando erros de digitação.
 */
public final class MensagemContract {

    /**
     * O construtor é privado para impedir que a classe seja instanciada acidentalmente.
     * Esta classe deve ser usada apenas para acessar suas constantes estáticas.
     */
    private MensagemContract() {}

    /**
     * A autoridade do Content Provider, uma string única que o identifica no sistema Android.
     */
    public static final String AUTHORITY = "com.example.contentprovidergerador.provider";

    /**
     * A URI base (content://) para interagir com o Content Provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * O "caminho" (path) para a tabela de mensagens, que será anexado à URI base.
     */
    public static final String PATH_MENSAGENS = "mensagens";

    /**
     * Classe interna que define as constantes para a tabela de mensagens do banco de dados.
     * Implementar a interface {@link BaseColumns} adiciona automaticamente as colunas
     * padrão {@code _ID} e {@code _COUNT}.
     */
    public static final class MensagemEntry implements BaseColumns {

        /**
         * A URI de conteúdo completa para acessar os dados da tabela de mensagens.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MENSAGENS).build();

        /** O nome da tabela no banco de dados. */
        public static final String TABLE_NAME = "mensagens";

        /** O nome da coluna para o texto da mensagem. Tipo: TEXT */
        public static final String COLUMN_TEXTO = "texto";

        /** O nome da coluna para o autor da mensagem. Tipo: TEXT */
        public static final String COLUMN_AUTOR = "autor";

        /** O nome da coluna que indica se uma mensagem é favorita. Tipo: INTEGER (0 para falso, 1 para verdadeiro) */
        public static final String COLUMN_FAVORITA = "favorita";
    }
}