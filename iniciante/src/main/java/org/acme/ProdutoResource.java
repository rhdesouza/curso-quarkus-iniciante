package org.acme;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

	@GET()
	public List<Produto> buscarTodosProtudos() {
		return Produto.listAll();
	}

	@POST()
	@Transactional
	public void cadastrarProduto(CadastrarProdutoDTO dto) {
		Produto p = new Produto();
		p.nome = dto.getNome();
		p.valor = dto.getValor();
		p.persist();
	}

	@PUT()
	@Path("{id}")
	@Transactional
	public void AtualizaProduto(@PathParam("id") Long id, CadastrarProdutoDTO dto) {
		Optional<Produto> produto0p = Produto.findByIdOptional(id);
		if (produto0p.isPresent()) {
			Produto produto = produto0p.get();
			produto.nome = dto.getNome();
			produto.valor = dto.getValor();
			produto.persist();
		} else {
			throw new NotFoundException();
		}
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void ExcluirProduto(@PathParam("id") Long id) {
		Optional<Produto> produto0p = Produto.findByIdOptional(id);
		produto0p.ifPresentOrElse(Produto::delete, () -> {
			throw new NotFoundException();
		});

	}

}
