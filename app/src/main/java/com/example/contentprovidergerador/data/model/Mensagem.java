package com.example.contentprovidergerador.data.model;

public class Mensagem {
    private long id;
    private String texto;
    private String autor;
    private boolean favorita; // Alterado de int para boolean

    public Mensagem() {}

    // Construtor atualizado para aceitar boolean
    public Mensagem(long id, String texto, String autor, boolean favorita) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.favorita = favorita;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    // Getter e Setter atualizados para boolean
    public boolean isFavorita() { return favorita; }
    public void setFavorita(boolean favorita) { this.favorita = favorita; }
}
