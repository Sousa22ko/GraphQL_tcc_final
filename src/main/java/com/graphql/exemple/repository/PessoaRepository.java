package com.graphql.exemple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.model.Setor;

/**
 * 
 * @author Reinaldo Sousa
 *
 */
public interface PessoaRepository extends GenericRepository<Pessoa> {

	@Query("select t from Colaborador t where t.cpf = ?1 and t.ativo = true")
	Pessoa findByCPF(String value);

	@Query("select t from Colaborador t where t.email = ?1 and t.ativo = true")
	Pessoa findByEmail(String value);

	@Query("select c from Colaborador c join c.listVinculo v where v.setor.id = ?1 and v.statusVinculo = true ORDER BY c.nome")
	List<Pessoa> findBySetor(Integer idSetor);

	@Query("select c from Colaborador c join c.listVinculo v where v.pessoaJuridica.id = ?1 and v.statusVinculo = true")
	List<Pessoa> findByPessoaJuridica(Integer idPessoaJuridica);

	@Query("select c from Colaborador c join c.listVinculo v where v.denominacao = ?1 and v.statusVinculo = true")
	List<Pessoa> findByTipoVinculo(String tipoVinculo);

	@Query("select c from Colaborador c join c.listVinculo v where v.cargo.id = ?1 and v.statusVinculo = true")
	List<Pessoa> findByCargo(Integer idCargo);

	@Query("select c from Colaborador c join c.listVinculo v where v.statusVinculo = true")
	List<Pessoa> findAllVinculoValido();

	// Funciona apenas se um vínculo está ativo
	@Query(value = "SELECT s FROM Colaborador c " + "JOIN c.listVinculo v " + "JOIN v.setor s " + "WHERE c.id = :id "
			+ "AND v.statusVinculo = true")
	Setor findSetor(Integer id);

	@Query("SELECT p FROM Pessoa p WHERE p.nome = ?1 AND p.ativo = true")
	public Pessoa findByNome(String nome);

	@Query("SELECT p FROM Pessoa p LEFT JOIN Usuario u ON p.id = u.pessoa.id WHERE u.pessoa.id IS NULL AND p.ativo = true ORDER BY p.id")
	public List<Pessoa> findByNullUser();

	@Query(value = "select s.id_setor from pessoa p join rh.colaborador c on c.id_pessoa = p.id_pessoa join rh.vinculo v on c.id_pessoa = v.id_pessoa_colaborador join rh.setor s on s.id_setor = v.id_setor where v.status_vinculo = true and p.ativo = true and p.id_pessoa = ?1", nativeQuery = true)
	public Integer findSetorIdByPessoaId(Integer idPessoa);

}
