package io.github.henriquempereira.spring_jpa_exerc_aula1;

import io.github.henriquempereira.spring_jpa_exerc_aula1.model.CodigoErro;
import io.github.henriquempereira.spring_jpa_exerc_aula1.model.Mes;
import io.github.henriquempereira.spring_jpa_exerc_aula1.model.Moeda;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringJpaExercAula1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaExercAula1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Exercício 1
		List<String> input = Arrays.asList("10", "abc", "20", "30x");
		List<Integer> result = input.stream()
				.map(s -> {
					try {
						return Optional.of(Integer.parseInt(s));
					} catch (NumberFormatException e) {
						return Optional.<Integer>empty();
					}
				})
				.flatMap(Optional::stream)
				.collect(Collectors.toList());

		result.stream()
				.forEach(System.out::println);

		// Exercício 2
		System.out.println(processaNumero(Optional.of(5)));
		System.out.println(processaNumero(Optional.of(-3)));
		System.out.println(processaNumero(Optional.empty()));

		// Exercício 3
		System.out.println(obterPrimeiroEUltimoNome("  João Carlos Silva   "));
		System.out.println(obterPrimeiroEUltimoNome("Maria   "));

		// Exercício 4
		System.out.println(ehPalindromo("socorram me subi no onibus em marrocos")); // Saída: true
		System.out.println(ehPalindromo("Java")); // Saída: false

		// Exercício 5
		List<String> emails = Arrays.asList("TESTE@EXEMPLO.COM", "exemplo@Java.com ", "Usuario@teste.Com");
		System.out.println(converterEmails(emails));
		// Saída: ["teste@exemplo.com", "exemplo@java.com", "usuario@teste.com"]

		// Exercício 6
		System.out.println(Mes.FEVEREIRO.getNumeroDeDias()); // 28
		System.out.println(Mes.JULHO.getNumeroDeDias()); // 31

		// Exercício 7
		System.out.println(Moeda.DOLAR.converterPara(100)); // 19.60 (aproximado)
		System.out.println(Moeda.EURO.converterPara(100)); // 18.18 (aproximado)

		// Exercício 8
		System.out.println(CodigoErro.NOT_FOUND.getCodigo()); // 404
		System.out.println(CodigoErro.BAD_REQUEST.getDescricao()); // Requisição inválida
	}

	public static Optional<Integer> processaNumero(Optional<Integer> numero) {
		if(numero.isPresent()){
			if(numero.get() >= 0){
				return Optional.of(numero.get()*numero.get());
			}
		}
		return Optional.empty();
	}

	public static String obterPrimeiroEUltimoNome(String nomeCompleto) {
		if(nomeCompleto.isBlank() || nomeCompleto == null) {
			return "Nome inválido";
		}
		List<String> list = Arrays.stream(nomeCompleto.trim().split("\\s+")).toList();
		if(list.size() == 1){
			return list.getFirst();
		} else {
			return (list.getFirst() + " " + list.getLast());
		}
	}

	public static boolean ehPalindromo(String palavra) {
		if(palavra == null){
			return false;
		}

		String word = palavra.replaceAll("\\s+", "").toLowerCase();
		return IntStream.range(0, word.length()/2)
				.allMatch(i -> word.charAt(i) == word.charAt(word.length()-1-i));
	}

	public List<String> converterEmails(List<String> emails) {
		if(emails == null || emails.isEmpty()){
			return null;
		}

		List<String> resultado = emails.stream()
				.map(e -> e.toLowerCase())
				.collect(Collectors.toList());
		return resultado;
	}
}
