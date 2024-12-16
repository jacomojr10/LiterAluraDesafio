package com.alura.LiterAluraDesafio;

import com.alura.LiterAluraDesafio.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alura.LiterAluraDesafio.principal.Principal;

@SpringBootApplication
public class LiterAluraDesafioApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository repositorio;


	public static void main(String[] args) {

		SpringApplication.run(LiterAluraDesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}

}
