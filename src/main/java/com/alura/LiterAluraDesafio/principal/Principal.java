package com.alura.LiterAluraDesafio.principal;

import com.alura.LiterAluraDesafio.model.*;
import com.alura.LiterAluraDesafio.repository.AutorRepository;
import com.alura.LiterAluraDesafio.service.ConsumoAPI;
import com.alura.LiterAluraDesafio.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private DadosLivro dadosLivro;
    private DadosAutor dadosAutor;
    private List<Livro> listaLivros;
    private List<Autor> listaAutores;

    @Autowired
    private AutorRepository repositorio;

    public Principal(AutorRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibeMenu() {
        listaAutores = repositorio.findAll();
        listaLivros = repositorio.listaLivro();

        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    ******************************** 
                    Bem-vindes à Pesquisa de Livros 
                    ********************************
                    
                     Escolha a opção de pesquisa: 
                    
                     1- Buscar livro pelo Título
                     2- Listar livros registrados
                     3- Listar autores registrados
                     4- Listar autores vivos em um determinado ano
                     5- Listar livros em um determinado idioma
                    
                     0 - Sair
                    """;
            System.out.println(menu);

            try {
                opcao = leitura.nextInt();
                leitura.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um válido.");
                leitura.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listaLivro();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresPorAnoDeNascimento();
                    break;
                case 5:
                    buscarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private void buscarLivro() {
        ConverteDados conversor = new ConverteDados();
        ConsumoAPI consumo = new ConsumoAPI();
        System.out.println("Digite o nome do livro para busca");
        String livroBuscado = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + livroBuscado.replace(" ", "+"));
        try {
            RespostaLivros resposta = conversor.obterDados(json, RespostaLivros.class);
            if (resposta.results().isEmpty()) {
                System.out.println("Nenhum livro encontrado");
                return;
            }
            dadosLivro = resposta.results().get(0);
            dadosAutor = dadosLivro.autores().get(0);
            Livro livroEncontrado = new Livro(dadosLivro);
            Autor autorEncontrado = new Autor(dadosAutor);
            autorEncontrado.salvarLivro(livroEncontrado);
            livroEncontrado.salvarAutor(autorEncontrado);
            Optional<Autor> autorJaRegistrado = repositorio.findByNomeContainingIgnoreCase(autorEncontrado.getNome());
            if (autorJaRegistrado.isPresent()) {
                List<Livro> livrosBanco = repositorio.livrosJaAdicionados(livroEncontrado.getTitulo());
                if (livrosBanco.isEmpty()) {
                    repositorio.adicionarLivroAutorExistente(livroEncontrado.getTitulo(), livroEncontrado.getIdioma().toString(), livroEncontrado.getNumDownload(), autorJaRegistrado.get().getId());
                    System.out.println("Livro adicionado");

                } else {
                    System.out.println("Livro Já existe no banco de dados");
                }
            } else {
                repositorio.save(autorEncontrado);
                System.out.println("Autor e livro adicionado");
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Nenhum livro encontrado");
        }
    }

    private void listaLivro() {
        listaLivros = repositorio.listaLivro();
        System.out.println(listaLivros);
        ;
    }

    private void listarAutoresRegistrados() {
        listaAutores = repositorio.findAll();
        listaAutores.stream()
                .forEach(a -> System.out.println(
                        "-------------------------------\n" + "Nome: " + a.getNome() +
                                "\nAno de Nascimento: " + a.getAnoNascimento() + "\nAno de falecimento: " + a.getAnoMorte()
                ));
    }

    private void buscarAutoresPorAnoDeNascimento() {
        System.out.println("Digite o ano que gostaria de pesquisar:");
        var anoPesquisado = leitura.nextInt();

        List<Autor> autorPesquisado = repositorio.findByAnoEntre(anoPesquisado);
        autorPesquisado.stream()
                .forEach(
                        ap -> System.out.println(
                                "-------------------------------\n" + "Nome: " + ap.getNome() +
                                        "\nAno de Nascimento: " + ap.getAnoNascimento() +
                                        "\nAno de falecimento: " + ap.getAnoMorte()
                        )
                );

    }

    private void buscarLivrosPorIdioma() {
        System.out.println("Escolha as opções de idioma que deseja consultar:\nIngles: en\nPortuguês:pt, \nEspanhol: es\nFrances; fr");
        try {
            String idiomaSelecionado = leitura.nextLine();
            Categoria categoriaSelecionado = Categoria.fromString(idiomaSelecionado);
            List<Livro> listaLivrosIdiomaSelecionado = listaLivros.stream()
                    .filter(l -> l.getIdioma().equals(categoriaSelecionado))
                    .toList();
            if (listaLivrosIdiomaSelecionado.isEmpty()) {
                System.out.println("Por favor, digite a opção de idioma a ser consultada");
            } else {
                System.out.println("Livros em '" + categoriaSelecionado + "': ");
                listaLivrosIdiomaSelecionado.forEach(System.out::println);
                System.out.println("Quantidade de livros em " + categoriaSelecionado + ": " + listaLivrosIdiomaSelecionado.stream().count());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Só vale as opções listadas no menu, tente novamente");
        }
    }
}









