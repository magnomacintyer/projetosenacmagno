package com.sistema.senac.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sistema.senac.modelo.ItemVenda;

public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {
	
	@Query("SELECT item FROM ItemVenda item WHERE item.venda.id= ?1")
	List<ItemVenda>buscarPorVenda(long id);
	

}
