package com.alura.LiterAluraDesafio.repository;

import com.alura.LiterAluraDesafio.model.Autor;
import com.alura.LiterAluraDesafio.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT l FROM Autor a JOIN a.livros l WHERE l.titulo ILIKE :titulo1")
    List<Livro> livrosJaAdicionados(String titulo1);

    @Query(value = "INSERT INTO livros (idioma, num_download, titulo, autor_id) VALUES (:idioma, :numDownload, :titulo, :id) RETURNING autor_id", nativeQuery = true)
    void adicionarLivroAutorExistente(String titulo, String idioma, int numDownload, Long id);

    @Query("SELECT l FROM Autor a JOIN a.livros l")
    List<Livro> listaLivro();

    @Query("""
            SELECT l FROM Livro l
            WHERE
            idioma = :idiomaPesquisado
            """)
    List<Livro> findByIdioma(String idiomaPesquisado);

    @Query("""
            SELECT a FROM Autor a
            JOIN a.livros l
            """)
    List<Autor> listarAutoresRegistrados(String autores);

    boolean existsByNomeContainingIgnoreCase(String nome);
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("""
            SELECT a FROM Autor a 
            WHERE :anoPesquisado
            BETWEEN
            a.anoNascimento 
            AND
            a.anoMorte 
            """)
    List<Autor> findByAnoEntre(Integer anoPesquisado);

}
