package br.com.convergencia.testejava.login.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.convergencia.testejava.login.base.rest.BaseRestService;
import br.com.convergencia.testejava.login.dto.CpfStatusDto;
import br.com.convergencia.testejava.login.rest.exception.ValidacaoRestException;

@Component
public class CpfValidacaoRest extends BaseRestService<CpfStatusDto> {

	@Value("${cpf.endeco}")
	private String URL;
	
	@Value("${cpf.consulta.status}")
	private String SERVICO;

	public String consultaStatusCpf(String cpf) throws ValidacaoRestException {
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("cpf", cpf);
		super.setBody(body);
		
		CpfStatusDto response = sendSemAutenticacao(HttpMethod.GET);
		
		if(response.getError() != null) {
			throw new ValidacaoRestException(response.getError());
		}
		
		return response.getStatus();
	}
	
	public boolean validaCpf(String cpf) throws ValidacaoRestException {
		return consultaStatusCpf(cpf).equals("ATIVADO") ? true : false;
	}

	@Override
	protected String getServico() {
		return SERVICO;
	}

	@Override
	protected String getUrl() {
		return URL;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getClazz() {
		return CpfStatusDto.class;
	}
}
