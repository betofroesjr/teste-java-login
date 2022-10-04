package br.com.convergencia.testejava.login.base.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class BaseRestService<R> {
	
	private MultiValueMap<String, String> body;
	
	private HttpHeaders headers;
	
	@Autowired RestTemplate restTemplate;
	
	public R sendSemAutenticacao(HttpMethod httMethod) {
		
		HttpHeaders headers = getHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<?> entity = new HttpEntity<Object>(getBody(), getHeaders());
		
		@SuppressWarnings("unchecked")
		ResponseEntity<R> response = restTemplate.exchange(getUrl()+getServico(), httMethod, entity, getClazz());
		
		return response.getBody();
	}

	protected MultiValueMap<String, String> getBody() {
		return body == null ? new LinkedMultiValueMap<String, String>() : body;
	}

	protected HttpHeaders getHeaders() {
		return headers == null ? new HttpHeaders() : headers;
	}
	
	protected void setBody(MultiValueMap<String, String> body) {
		this.body = body;
	}

	protected void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	protected abstract String getServico();

	protected abstract String getUrl();

	protected abstract Class getClazz();

}
