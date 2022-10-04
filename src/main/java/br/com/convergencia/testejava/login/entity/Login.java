package br.com.convergencia.testejava.login.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.convergencia.testejava.login.base.entity.BasicEntity;

@Entity
@Table(name = "login")
public class Login extends BasicEntity<Login>{

	@NotNull(message = "Informe um cpf")
	@Column(unique = true)	
	private String cpf;

	@NotNull(message = "Infome uma senha")
	private String senha;
	
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
