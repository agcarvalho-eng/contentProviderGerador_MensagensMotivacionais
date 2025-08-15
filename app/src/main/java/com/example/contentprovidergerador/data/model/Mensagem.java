package com.example.contentprovidergerador.data.model;

/**
 * Representa o objeto de modelo (Model) para uma única mensagem.
 * Esta classe é um POJO (Plain Old Java Object) que encapsula os dados de uma mensagem,
 * facilitando a manipulação e o transporte de dados através do aplicativo.
 */
public class Mensagem {

    /** O identificador único da mensagem no banco de dados. */
    private long id;

    /** O conteúdo textual da mensagem. */
    private String texto;

    /** O nome do autor da mensagem. */
    private String autor;

    /** Um valor booleano que indica se a mensagem foi marcada como favorita. */
    private boolean favorita;

    /**
     * Construtor padrão (vazio).
     * Necessário para certas bibliotecas e frameworks que podem instanciar
     * o objeto via reflexão (reflection).
     */
    public Mensagem() {}

    /**
     * Construtor completo para criar uma instância de Mensagem com todos os seus dados.
     *
     * @param id       O ID único da mensagem.
     * @param texto    O conteúdo da mensagem.
     * @param autor    O autor da mensagem.
     * @param favorita O status de favorita (true ou false).
     */
    public Mensagem(long id, String texto, String autor, boolean favorita) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.favorita = favorita;
    }

    // --- Getters e Setters ---

    /**
     * Retorna o ID da mensagem.
     * @return O ID único do tipo long.
     */
    public long getId() { return id; }

    /**
     * Define o ID da mensagem.
     * @param id O novo ID único para a mensagem.
     */
    public void setId(long id) { this.id = id; }

    /**
     * Retorna o texto da mensagem.
     * @return A String contendo o texto da mensagem.
     */
    public String getTexto() { return texto; }

    /**
     * Define o texto da mensagem.
     * @param texto O novo conteúdo textual para a mensagem.
     */
    public void setTexto(String texto) { this.texto = texto; }

    /**
     * Retorna o autor da mensagem.
     * @return A String contendo o nome do autor.
     */
    public String getAutor() { return autor; }

    /**
     * Define o autor da mensagem.
     * @param autor O novo nome do autor para a mensagem.
     */
    public void setAutor(String autor) { this.autor = autor; }

    /**
     * Verifica se a mensagem é favorita. (Convenção de nomenclatura "is" para getters booleanos).
     * @return true se a mensagem for favorita, false caso contrário.
     */
    public boolean isFavorita() { return favorita; }

    /**
     * Define o status de favorita da mensagem.
     * @param favorita O novo status de favorita (true ou false).
     */
    public void setFavorita(boolean favorita) { this.favorita = favorita; }
}