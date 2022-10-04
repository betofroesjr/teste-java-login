package br.com.convergencia.testejava.login.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.convergencia.testejava.login.entity.Login;

@SpringBootTest
class LoginRepositoryTest {
	
	@Autowired LoginRepository repository;

	@Test
	void deveRetornarLoginQuandoCpfESenhaCorretos() {

		Login login = criarLogin();
		
		repository.save(login);
	
		Login loginResult = repository.getByCpfAndSenha("987654", "123456");
		
		assertNotNull(loginResult);
	}

	private Login criarLogin() {
		Login login = new Login();
		login.setSenha("123456");
		login.setCpf("987654");
		login.setDataCadastro(LocalDate.now());
		return login;
	}
}
