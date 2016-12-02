package br.com.oficial.web.caninofeliz.dao;

import java.util.List;

public interface Persistivel<Entity> {

	public void save(Entity Entity);

	public List<Entity> listarTudo();
	
	public Entity buscarComCodigo(Long codigo);

	public void excluir(Entity Entity);

	public void update(Entity Entity);

	// Metodo merge, ele é um save e update... Caso o objeto não exista ele
	// salva, caso exista ele faz um update.
	public Entity merge(Entity Entity);

}
