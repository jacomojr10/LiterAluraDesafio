package com.alura.LiterAluraDesafio.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private int anoNascimento;
    private int anoMorte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){};

    public Autor(DadosAutor dadosAutor){
        this.nome = dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoMorte = dadosAutor.anoMorte();
    }

    public void salvarLivro(Livro livroAdicionado){
        livros.add(livroAdicionado);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public int getAnoNascimento() {return anoNascimento; }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(int anoMorte) {
        this.anoMorte = anoMorte;
    }

    @Override
    public String toString() {
        return  "Nome do autor= " + nome +
                ", Ano de Nascimento= " + anoNascimento +
                ", Ano da Morte= " + anoMorte +
                "\n Obras Registradas no Banco de Dados: \n" + livros ;
    }
}
