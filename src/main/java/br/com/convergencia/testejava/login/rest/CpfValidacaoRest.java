package br.com.convergencia.testejava.login.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.convergencia.testejava.login.dto.CpfDto;
import br.com.convergencia.testejava.login.dto.CpfStatusDto;
import br.com.convergencia.testejava.login.rest.exception.ValidacaoRestException;

@Component
public class CpfValidacaoRest {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired RestTemplate restTemplate;
	
	@Value("${cpf.endeco}")
	private String URL;
	
	@Value("${cpf.consulta.status}")
	private String SERVICO;

	public String consultaStatusCpf(String cpf) throws ValidacaoRestException {
		
		try {
			CpfDto body = new CpfDto();
			body.setCpf(cpf);
			
			CpfStatusDto response = exec(objectMapper.writeValueAsString(body));
			
			if(response.getError() != null) {
				throw new ValidacaoRestException(response.getError());
			}
			
			return response.getStatus();
		} catch (JsonProcessingException e) {
			throw new ValidacaoRestException(e.getMessage());
		}
	}
	
	
	public CpfStatusDto exec(String body) throws ValidacaoRestException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		RequestEntity<?> requestEntity = new RequestEntity(body, headers, HttpMethod.GET, URI.create(URL + SERVICO)); 
		
		ResponseEntity<CpfStatusDto> response;
		try {
			response = restTemplate.exchange(requestEntity, CpfStatusDto.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new ValidacaoRestException(e.getMessage());
		}
	}
	
	public boolean validaCpf(String cpf) throws ValidacaoRestException {
		return consultaStatusCpf(cpf).equals("ATIVADO") ? true : false;
	}
}
