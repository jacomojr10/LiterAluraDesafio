package com.alura.LiterAluraDesafio.model;

import jakarta.persistence.*;

@Entity
@Table(name= "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Categoria idioma;

    private int numDownload;

    public Livro(){}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = Categoria.fromString(dadosLivro.idiomas().get(0));
        this.numDownload = dadosLivro.numDownload();
    }

    public void salvarAutor(Autor autorAdicionado){
        this.autor = autorAdicionado;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getNumDownload() {
        return numDownload;
    }

    public void setNumDownload(int numDownload) {
        this.numDownload = numDownload;
    }

    public Categoria getIdioma() {
        return idioma;
    }

    public void setIdioma(Categoria idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "\n--------------------- LIVRO ---------------------\n" +
                "Título              : " + titulo +  "\n" +
                "Autor               : " + (autor != null ? autor.getNome() : "N/A") +  "\n"  +
                "idioma              : " + idioma +  "\n" +
                "Número de downloads : " + numDownload +"\n"+
                "--------------------------------------------------\n";
    }
}


