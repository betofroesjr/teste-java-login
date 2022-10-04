package br.com.convergencia.testejava.login.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.convergencia.testejava.login.dto.CpfStatusDto;
import br.com.convergencia.testejava.login.rest.exception.ValidacaoRestException;

@ExtendWith(MockitoExtension.class)
class CpfValidacaoRestTest {

	@InjectMocks CpfValidacaoRest rest;
	
	@Mock RestTemplate restTemplate;
	
	@Test
	void deveValidarStatusCpf() throws ValidacaoRestException {
		
		ReflectionTestUtils.setField(rest, "URL", "http://localhost:8080");
		ReflectionTestUtils.setField(rest, "SERVICO", "/pessoa/consultarStatus");
		
		CpfStatusDto dto = new CpfStatusDto();
		dto.setStatus("ATIVADO");
		
		ResponseEntity<CpfStatusDto> response = ResponseEntity.ok(dto);
		
		HttpHeaders headers = new HttpHeaders();
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("cpf", "1234");
		
		HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
		
		Mockito.when(restTemplate.exchange("http://localhost:8080/pessoa/consultarStatus", HttpMethod.GET, entity, CpfStatusDto.class)).thenReturn(response);
		
		String statusCpf = rest.consultaStatusCpf("1234");

		assertNotNull(statusCpf);
		
		assertEquals("ATIVADO", statusCpf);
	}

}
