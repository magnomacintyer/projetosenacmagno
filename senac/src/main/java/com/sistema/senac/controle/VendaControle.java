package com.sistema.senac.controle;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.ItemVenda;
import com.sistema.senac.modelo.Produto;
import com.sistema.senac.modelo.Venda;
import com.sistema.senac.repositorio.VendaRepositorio;
import com.sistema.senac.repositorio.ClienteRepositorio;

import com.sistema.senac.repositorio.FuncionarioRepositorio;
import com.sistema.senac.repositorio.ItemVendaRepositorio;
import com.sistema.senac.repositorio.ProdutoRepositorio;

@Controller
public class VendaControle {
	
	@Autowired
	private VendaRepositorio vendaRepositorio;
	@Autowired
	private ItemVendaRepositorio itemVendaRepositorio;
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	private List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();
	
	@GetMapping("/cadastroVenda")
	public ModelAndView cadastrar(Venda venda, ItemVenda itemVenda) { 
		ModelAndView mv = new ModelAndView("administrativo/vendas/cadastro");
		mv.addObject("venda",venda);
		mv.addObject("itemVenda", itemVenda);
		mv.addObject("listaItemVenda", itemVendaRepositorio.buscarPorVenda(venda.getId()));
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaClientes", clienteRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;

	}
	
	@PostMapping("/salvarVenda")
	public ModelAndView salvar(String acao, Venda venda, ItemVenda itemVenda, BindingResult result) { 
		if(result.hasErrors()) {
			return cadastrar(venda, itemVenda);
		}
		
		if(acao.equals("itens")) {
			//aqui vai adicionar itens na lista de venda
			itemVenda.setValor( itemVenda.getProduto().getPrecoVenda() );
			itemVenda.setSubtotal( itemVenda.getProduto().getPrecoVenda() * itemVenda.getQuantidade() );
			venda.setValorTotal(venda.getValorTotal() + (itemVenda.getValor() * itemVenda.getQuantidade() ));
			venda.setQuantidadeTotal( venda.getQuantidadeTotal() + itemVenda.getQuantidade() );
			vendaRepositorio.saveAndFlush(venda);
			
			itemVenda.setVenda(venda);
			Optional<Produto> prod = produtoRepositorio.findById(itemVenda.getProduto().getId());
			Produto produto = prod.get();
			produto.setEstoque(produto.getEstoque() - itemVenda.getQuantidade());
			produtoRepositorio.saveAndFlush(produto);
			
			itemVendaRepositorio.saveAndFlush(itemVenda);
			
			
			
		}else if(acao.equals("salvar")) {	
			venda.setTipo("venda");
			vendaRepositorio.saveAndFlush(venda);
			return cadastrar(new Venda(), new ItemVenda());
		
		}
		return cadastrar(venda, new ItemVenda());
	}
	
	@GetMapping("/listarVenda")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/vendas/lista");
		mv.addObject("listaVendas", vendaRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarVenda/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Venda> venda = vendaRepositorio.findById(id);  //busca venda por id e armazena
		this.listaItemVenda = itemVendaRepositorio.buscarPorVenda(venda.get().getId()); //carregar os itens da venda
		return cadastrar(venda.get(), new ItemVenda());		   //chama função cadastrar e carrega valores nela
		
		
	}
	
	@GetMapping("/removerVenda/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Venda> venda = vendaRepositorio.findById(id);
		
		this.listaItemVenda = itemVendaRepositorio.buscarPorVenda(venda.get().getId());
		for(ItemVenda i : listaItemVenda){
			Optional<Produto> prod = produtoRepositorio.findById(i.getProduto().getId());
			Produto produto = prod.get();
			produto.setEstoque(produto.getEstoque() + i.getQuantidade());
			produtoRepositorio.saveAndFlush(produto);
			itemVendaRepositorio.delete(i);
			this.listaItemVenda = new ArrayList<>();
		}
		
		
		vendaRepositorio.delete(venda.get());
		return listar();
	}
	@GetMapping("/removerItemVenda/{id}")
	public ModelAndView removerItemVenda(@PathVariable("id") Long id) {
		Optional<ItemVenda> itemVenda = itemVendaRepositorio.findById(id);
		
		ItemVenda i = itemVenda.get();
		
		i.getVenda().setValorTotal(i.getVenda().getValorTotal() - (i.getValor() * i.getQuantidade() ));
		i.getVenda().setQuantidadeTotal( i.getVenda().getQuantidadeTotal() - i.getQuantidade() );
		
		Optional<Produto> prod = produtoRepositorio.findById(itemVenda.get().getProduto().getId());
		Produto produto = prod.get();
		produto.setEstoque(produto.getEstoque() - itemVenda.get().getQuantidade());
		produtoRepositorio.saveAndFlush(produto);
		
		itemVendaRepositorio.delete(itemVenda.get());
		return cadastrar(i.getVenda(), new ItemVenda());	
	}
	
	@GetMapping("/editarItemVenda/{id}")
	public ModelAndView editarItemVenda(@PathVariable("id") Long id) {
		Optional<ItemVenda> itemVenda = itemVendaRepositorio.findById(id);

		ItemVenda i = itemVenda.get();
		
		i.getVenda().setValorTotal(i.getVenda().getValorTotal() - (i.getValor() * i.getQuantidade() ));
		i.getVenda().setQuantidadeTotal( i.getVenda().getQuantidadeTotal() - i.getQuantidade() );
		
		Optional<Produto> prod = produtoRepositorio.findById(itemVenda.get().getProduto().getId());
		Produto produto = prod.get();
		produto.setEstoque(produto.getEstoque() - itemVenda.get().getQuantidade());
		produtoRepositorio.saveAndFlush(produto);
		
		itemVendaRepositorio.delete(itemVenda.get());
		
		return cadastrar(i.getVenda(), i);	
	}
	
	
	
	
}
