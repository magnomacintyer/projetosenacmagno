package com.sistema.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.senac.modelo.Cidade;

public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {
	

}
