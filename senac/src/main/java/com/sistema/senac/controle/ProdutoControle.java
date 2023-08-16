package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Produto;
import com.sistema.senac.repositorio.ProdutoRepositorio;

@Controller
public class ProdutoControle {
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@GetMapping("/cadastroProduto")
	public ModelAndView cadastrar (Produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto",produto);
		return mv;
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvar(Produto produto, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrar(produto);
		}
		produtoRepositorio.saveAndFlush(produto);
		return cadastrar(new Produto());
	}
	
	@GetMapping("/listarProduto")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarProduto/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);  //busca produto por id e armazena
		return cadastrar(produto.get());		   //chama função cadastrar e carrega valores nela
	}
	
	@GetMapping("/removerProduto/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		produtoRepositorio.delete(produto.get());
		return listar();
	}

}
