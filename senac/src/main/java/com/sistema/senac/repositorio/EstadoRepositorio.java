package com.sistema.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.senac.modelo.Estado;

public interface EstadoRepositorio extends JpaRepository<Estado, Long> {
	

}
