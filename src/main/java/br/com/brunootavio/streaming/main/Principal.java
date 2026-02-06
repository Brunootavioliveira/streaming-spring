package br.com.brunootavio.streaming.main;

import br.com.brunootavio.streaming.model.DadosEpisodio;
import br.com.brunootavio.streaming.model.DadosSerie;
import br.com.brunootavio.streaming.model.DadosTemporada;
import br.com.brunootavio.streaming.model.Episodio;
import br.com.brunootavio.streaming.service.ConsumoApi;
import br.com.brunootavio.streaming.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {

    }
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=96159931";

    public void exibeMenu(){
        System.out.println("Digite o nome da série: ");
        var nomeSerie = scanner.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace( " ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> listaTemporadas = new ArrayList<>();
//
		for (int i = 1; i <= dadosSerie.temporadasTotais(); i++) {
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace( " ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			listaTemporadas.add(dadosTemporada);
		}
		listaTemporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSerie.temporadasTotais(); i++) {
//            List<DadosEpisodio> episodios = listaTemporadas.get(i).episodios();
//            for (int j = 0; j < episodios.size(); j++) {
//                System.out.println(episodios.get(j).titulo());
//            }
//        }

        //ou


        listaTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); //Para cada temporada, percorre seus episódios e imprime o título.

        List<DadosEpisodio> dadosEpisodios = listaTemporadas.stream()
                .flatMap(t -> t.episodios().stream()) //gera um fluxo de dados com os episodios de todas as temporadas
                .collect(Collectors.toList()); //coletar dados para outra lista | Collectors.toList = para conseguir add mais coisa depois, apenas toList é imutavel, nao da pra add depois

        System.out.println("Top 5 episodio");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro(N/A)" + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .map(e -> e.titulo().toUpperCase())
                .forEach(System.out::println);

        List<Episodio> episodios = listaTemporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d)) //traformar cada um desse dados episodios em novos episodio "para cada dadoEpisodio, quero criar um episodio com numero e outras infos"
                ).collect(Collectors.toList());


        episodios.forEach(System.out::println);

        System.out.println("Digite o nome episodio: ");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream() //Optional: conteiner que pode ou nao conter valor nao nulo
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if (episodioBuscado.isPresent()){
            System.out.println("Episodio encontrado! ");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada()); // conteiner que guarda a informacao, get para pegar
        } else {
            System.out.println("Não encontrado");
        }

        System.out.println("Qual ano vocẽ deseja ver o episodio: ");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca)) //data tem que ser depois(isAfter) da data colocada pelo cliente
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                "Episodio: " + e.getTitulo() +
                                "Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avaliacoesPorTemporadas = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporadas);

        DoubleSummaryStatistics estatistca = episodios.stream() //classe utilitária que permite calcular estatísticas como soma, média, valor mínimo e máximo...
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Pior Episodio: " + estatistca.getMin());
        System.out.println("Melhor Episodio: " + estatistca.getMax());
        System.out.println("Quantidade Episodio: " + estatistca.getCount());
        System.out.println("MEDIA: " + estatistca.getAverage());

    }
}

//findFirst() não retorna o objeto direto, ele retorna um:
//
//        👉 Optional<T>
//
//Porque pode acontecer de:
//
//a lista estar vazia
//
//o filtro não encontrar nada

//Quando usar findFirst()?
//✔ Quando você só precisa de um resultado
//✔ Quando a ordem importa
//✔ Quando quer performance (para no primeiro)