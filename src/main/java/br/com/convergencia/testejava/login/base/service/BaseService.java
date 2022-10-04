package br.com.convergencia.testejava.login.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.convergencia.testejava.login.base.entity.BasicEntity;
import br.com.convergencia.testejava.login.service.exception.ConsistenciaEntidadeException;

public abstract class BaseService<E extends BasicEntity<E>, R extends JpaRepository<E, Long>> implements BaseServiceInterface<E> {

	@Autowired
	protected R repository;
	
	public void consistPersistence(E entidade) throws ConsistenciaEntidadeException{
	};
	
	@Override
	@Transactional
	public E insert(E entidade) throws ConsistenciaEntidadeException {
		consistPersistence(entidade);
		entidade.setId(null);
		E entidadeCadastrado = repository.save(entidade);
		return entidadeCadastrado;
	}

	@Override
	@Transactional
	public E change(E entidade, Long id) throws ConsistenciaEntidadeException {
		findById(id);
		consistPersistence(entidade);
		entidade.setId(id);
		return repository.save(entidade);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<E> findAll() {
		return repository.findAll();
	}

	@Override
	public E findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(getMessageError(),1));
	}

	protected String getMessageError() {
		return "Recurso não encontrado!";
	}
}
