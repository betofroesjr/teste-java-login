package br.com.convergencia.testejava.login.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.convergencia.testejava.login.dto.LogarDto;
import br.com.convergencia.testejava.login.entity.Login;
import br.com.convergencia.testejava.login.service.LoginService;
import br.com.convergencia.testejava.login.service.exception.ConsistenciaEntidadeException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class LoginResourceTest {

	private static ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

	@Autowired
	MockMvc mockMvc;

	@MockBean
	LoginService service;

	@Test
	void deveRealizarLogin() throws JsonProcessingException, Exception {

		LogarDto dto = new LogarDto();
		dto.setCpf("98754");
		dto.setSenha("123456");
		
		String encodeToString = Base64.getEncoder().encodeToString("98754".getBytes());

		Mockito.when(service.realizarLogin("98754","123456")).thenReturn(encodeToString);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/login/logar").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsBytes(dto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		assertEquals(encodeToString, result.getResponse().getContentAsString());
	}
	
	@Test
	void deveLancarForbinddenCpfESenha() throws JsonProcessingException, Exception {

		LogarDto dto = new LogarDto();
		dto.setCpf("98754");
		dto.setSenha("123456");
		
		Mockito.when(service.realizarLogin("98754","123456")).thenThrow(ConsistenciaEntidadeException.class);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/login/logar").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsBytes(dto)))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	void deveSalvarLogin() throws JsonProcessingException, Exception {

		Login entidade = criarLogin();

		Login resultMock = criarLogin();
		resultMock.setId(1L);

		Mockito.when(service.insert(entidade)).thenReturn(resultMock);

		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsBytes(entidade)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	private Login criarLogin() {
		Login login = new Login();
		login.setSenha("123456");
		login.setCpf("987654");
		login.setDataCadastro(LocalDate.now());
		return login;
	}

}
