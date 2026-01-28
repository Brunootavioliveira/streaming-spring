package br.com.brunootavio.streaming;

import br.com.brunootavio.streaming.model.DadosSerie;
import br.com.brunootavio.streaming.service.ConsumoApi;
import br.com.brunootavio.streaming.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StreamingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=96159931");

		var conversor = new ConverteDados();
		var dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
