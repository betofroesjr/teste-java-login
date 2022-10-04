package br.com.convergencia.testejava.login.base.service;

import java.util.List;

import br.com.convergencia.testejava.login.base.entity.BasicEntity;
import br.com.convergencia.testejava.login.service.exception.ConsistenciaEntidadeException;

public interface BaseServiceInterface<E extends BasicEntity<E>> {

	public E insert(E entidade) throws ConsistenciaEntidadeException;
	public E change(E entidade, Long id) throws ConsistenciaEntidadeException;
	public void deleteById(Long id);
	public List<E> findAll();
	public E findById(Long id);
}