package br.com.alura.screensound.principal;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Carreira;
import br.com.alura.screensound.model.Musica;
import br.com.alura.screensound.repository.ArtistaRepository;
import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ArtistaRepository artistaRepository;

    public Principal(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println("\nPressione Enter para continuar...");
            leitura.nextLine(); // Consome o Enter para continuar
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Digite o nome do artista para cadastro: ");
            var nomeArtista = leitura.nextLine();
            System.out.println("Digite o tipo de carreira do artista: (solo, dupla ou banda)");
            var carreiraArtista = leitura.nextLine();
            Artista artista = new Artista(nomeArtista,carreiraArtista);
            artistaRepository.save(artista);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }

    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar música de que artista? ");
        var nome = leitura.nextLine();
        Optional<Artista> artista = artistaRepository.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()) {
            System.out.println("Informe o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            artistaRepository.save(artista.get());
            System.out.println("Música cadastrada com sucesso!");
        } else {
            System.out.println("Artista não encontrado");
        }
    }

    private void listarMusicas() {
        List<Artista> artistas = artistaRepository.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Músicas de qual artista você quer buscar?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = artistaRepository.findByNomeContainingIgnoreCase(nome);
        if(artista.isPresent()){
            System.out.println("Músicas de " + artista.get().getNome() + ": ");
            artista.get().getMusicas().forEach(System.out::println);
        }else {
            System.out.println("Artista não encontrado");
        }

//        OUTRA FORMA DE BUSCAR AS MÚSICAS
//        List<Musica> musicas = artistaRepository.buscarMusicasPorArtista(nome);
//        musicas.forEach(System.out::println);
    }

    private void pesquisarDadosDoArtista() {
        //Falta implementar com alguma IA que não seja o CHATGPT.
    }
}
