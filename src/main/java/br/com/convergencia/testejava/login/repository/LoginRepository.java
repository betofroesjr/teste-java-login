package br.com.convergencia.testejava.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.convergencia.testejava.login.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

	Login getByCpfAndSenha(String cpf, String senha); 
}
