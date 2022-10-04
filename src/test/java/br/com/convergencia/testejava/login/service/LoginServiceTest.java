package br.com.convergencia.testejava.login.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.convergencia.testejava.login.entity.Login;
import br.com.convergencia.testejava.login.repository.LoginRepository;
import br.com.convergencia.testejava.login.rest.CpfValidacaoRest;
import br.com.convergencia.testejava.login.rest.exception.ValidacaoRestException;
import br.com.convergencia.testejava.login.service.exception.ConsistenciaEntidadeException;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
	
	@InjectMocks LoginService service;
	
	@Mock CpfValidacaoRest cpfValidacaoRest;
	@Mock LoginRepository repository;

	@Test
	void deveRealizarLogin() throws ConsistenciaEntidadeException, ValidacaoRestException {
		
		String cpf = "987654";
		
		String encodedString = Base64.getEncoder().encodeToString(cpf.getBytes());
		
		Login login = criarLogin();
		
		Mockito.when(cpfValidacaoRest.validaCpf(cpf)).thenReturn(true);
		Mockito.when(repository.getByCpfAndSenha(cpf, "123456")).thenReturn(login);
		
		String token = service.realizarLogin(cpf, "123456");
		
		assertNotNull(token);
		assertEquals(encodedString, token);
	}
	
	@Test
	void salvarLogin() throws ConsistenciaEntidadeException, ValidacaoRestException {
		
		Login criandoLogin = criarLogin();
		
		Login resultMock = criarLogin();
		resultMock.setId(1L);
		
		Mockito.when(cpfValidacaoRest.validaCpf("987654")).thenReturn(true);
		Mockito.when(repository.save(criandoLogin)).thenReturn(resultMock);
		
		Login result = service.insert(criandoLogin);
		
		assertNotNull(result.getId());
		assertEquals(resultMock.getId(), result.getId());
	}
	
	@Test
	void deveLancarExcecaoQuandoSalvarLoginComCpfInvalido() throws ConsistenciaEntidadeException, ValidacaoRestException {
		
		Login criandoLogin = criarLogin();
		
		assertThrows(ConsistenciaEntidadeException.class, () -> {
			service.insert(criandoLogin);
		});
	}

	private Login criarLogin() {
		Login login = new Login();
		login.setSenha("123456");
		login.setCpf("987654");
		login.setDataCadastro(LocalDate.now());
		return login;
	}
}
