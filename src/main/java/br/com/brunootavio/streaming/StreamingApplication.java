package br.com.brunootavio.streaming;

import br.com.brunootavio.streaming.main.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class StreamingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StreamingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		var consumoApi = new ConsumoApi();
//		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=96159931");
//
//		var conversor = new ConverteDados();
//		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
////		System.out.println(dadosSerie);
//
//		json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=96159931");
//		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
////		System.out.println(dadosEpisodio);

//		🔹 Jeito 1 – imprimir a lista inteira
//		System.out.println(listaTemporadas);

//		🔹 Jeito 2 – imprimir item por item (melhor)
//		listaTemporadas.forEach(System.out::println);

		Principal principal = new Principal();
		principal.exibeMenu();

//		List<String> nomes = List.of("coco","bruno", "julia","daiane","lourdes");
//		nomes.stream()
//				.sorted() // ORDENA LISTA ORDEM ALFABETICA
//				.limit(3) //LIMITA 3 PRIMEIROS
//				.filter(n -> n.startsWith("b")) // FILTRA SÓ PARA A PALAVRA COM B PRINTAR
//				.map(n -> n.toUpperCase()) // MAP = TRANFORMAÇÃO
//				.forEach(System.out::println);
	}
}
