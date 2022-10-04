package br.com.convergencia.testejava.login.resource;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.convergencia.testejava.login.base.resource.BaseResource;
import br.com.convergencia.testejava.login.dto.LogarDto;
import br.com.convergencia.testejava.login.entity.Login;
import br.com.convergencia.testejava.login.service.LoginService;
import br.com.convergencia.testejava.login.service.exception.ConsistenciaEntidadeException;

@RestController
@RequestMapping("/login")
public class LoginResource extends BaseResource<Login, LoginService>{

	@GetMapping("/logar")
	public ResponseEntity<String> findByCpf(@RequestBody @Valid LogarDto dto){
		try {
			return ResponseEntity.ok(service.realizarLogin(dto.getCpf(), dto.getSenha()));
		} catch (ConsistenciaEntidadeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}