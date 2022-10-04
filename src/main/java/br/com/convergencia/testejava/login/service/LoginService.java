package br.com.convergencia.testejava.login.service;

import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.convergencia.testejava.login.base.service.BaseService;
import br.com.convergencia.testejava.login.entity.Login;
import br.com.convergencia.testejava.login.repository.LoginRepository;
import br.com.convergencia.testejava.login.rest.CpfValidacaoRest;
import br.com.convergencia.testejava.login.rest.exception.ValidacaoRestException;
import br.com.convergencia.testejava.login.service.exception.ConsistenciaEntidadeException;

@Service
public class LoginService extends BaseService<Login, LoginRepository>{
	
	private static final String FALHA_NA_VALIDAÇÃO_DO_CPF = "Falha na validação do cpf";
	private static final String DADOS_INVALIDOS = "Dados invalidos";
	private static final String CPF_NÃO_ESTÁ_ATIVO = "CPF não está ativo";
	
	@Autowired private CpfValidacaoRest cpfValidacaoRest;
	
	@Override
	public void consistPersistence(Login entidade) throws ConsistenciaEntidadeException {
		
		try {
			if(entidade == null || entidade.getCpf() == null) {
				throw new ConsistenciaEntidadeException(DADOS_INVALIDOS);
			}
			
			if(!cpfValidacaoRest.validaCpf(entidade.getCpf())) {
				throw new ConsistenciaEntidadeException(CPF_NÃO_ESTÁ_ATIVO);
			}
		} catch (ValidacaoRestException e) {
			throw new ConsistenciaEntidadeException(FALHA_NA_VALIDAÇÃO_DO_CPF);
		}
		
		super.consistPersistence(entidade);
	}

	public String realizarLogin(String cpf, String senha) throws ConsistenciaEntidadeException {
		
		Login login = repository.getByCpfAndSenha(cpf, senha);
		
		consistPersistence(login);
		
		return Base64.getEncoder().encodeToString(cpf.getBytes());
	}
}
