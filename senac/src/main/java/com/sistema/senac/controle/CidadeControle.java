package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Cidade;
import com.sistema.senac.repositorio.CidadeRepositorio;
import com.sistema.senac.repositorio.EstadoRepositorio;

@Controller
public class CidadeControle {
	
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	@GetMapping("/cadastroCidade")
	public ModelAndView cadastrar (Cidade cidade) {
		ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro");
		mv.addObject("listaEstado", estadoRepositorio.findAll());
		mv.addObject("cidade",cidade);
		return mv;
	}
	
	@PostMapping("/salvarCidade")
	public ModelAndView salvar(Cidade cidade, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrar(cidade);
		}
		cidadeRepositorio.saveAndFlush(cidade);
		return cadastrar(new Cidade());
	}
	
	@GetMapping("/listarCidade")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarCidade/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepositorio.findById(id);  //busca cidade por id e armazena
		return cadastrar(cidade.get());		   //chama função cadastrar e carrega valores nela
	}
	
	@GetMapping("/removerCidade/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepositorio.findById(id);
		cidadeRepositorio.delete(cidade.get());
		return listar();
	}

}
