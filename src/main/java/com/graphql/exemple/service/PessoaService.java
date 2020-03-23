package com.graphql.exemple.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import com.graphql.exemple.APIQL.APIQueryParams;
import com.graphql.exemple.exception.NegocioException;
import com.graphql.exemple.model.Ferias;
import com.graphql.exemple.model.FeriasParcela;
import com.graphql.exemple.model.Pessoa;
import com.graphql.exemple.model.Setor;
import com.graphql.exemple.model.Vinculo;
import com.graphql.exemple.repository.PessoaRepository;

/**
 * 
 * @author Reinaldo Sousa
 *
 */
@Service
@Transactional
public class PessoaService extends GenericService<Pessoa, PessoaRepository> {

	@Override
	public boolean validate(Pessoa obj) throws NegocioException {
		NegocioException exception = new NegocioException("Erro");

		Pessoa c = this.repository.findByCPF(obj.getCpf());
		Pessoa c2 = this.repository.findByEmail(obj.getEmail());
		List<Vinculo> vinculos = new ArrayList<Vinculo>();

		if (obj.getListVinculo() != null) {
			for (Vinculo aux : obj.getListVinculo()) {
				if (aux.getStatusVinculo())
					vinculos.add(aux);
			}
		}

		if (obj.getNome().length() < 2)
			exception.addFieldError(new FieldError("Nome", "nome", "O nome não pode ser muito curto"));
		if (obj.getNome().startsWith(" ") || obj.getNome().endsWith(" "))
			exception.addFieldError(
					new FieldError("Nome", "nome", "O nome não pode conter espaços no inicio ou no fim"));

		if (c != null && obj.getId() == null)
			exception.addFieldError(new FieldError("Cpf", "cpf", "CPF ja cadastrado no sistema"));
		if (obj.getId() != null && c != null && !c.getId().equals(obj.getId()))
			exception.addFieldError(new FieldError("Cpf", "cpf", "CPF ja cadastrado no sistema"));

		if (c2 != null && obj.getId() == null)
			exception.addFieldError(new FieldError("Email", "email", "E-mail ja cadastrado no sistema"));
		if (obj.getId() != null && c2 != null && !c2.getId().equals(obj.getId()))
			exception.addFieldError(new FieldError("Email", "email", "E-mail ja cadastrado no sistema"));

		if (vinculos.size() > 1)
			exception.addFieldError(
					new FieldError("listVinculo", "vinculo", "O funcionário ja possui um vinculo ativo"));

		if (obj.getStatusCadastro() == null)
			exception.addFieldError(new FieldError("StatusCadastro", "statusCadastro", "Informe o status do cadastro"));

		if (exception.getErros().size() > 0)
			throw exception;

		return true;
	}

	@Override
	public Pessoa update(Pessoa obj) throws NegocioException {
		Date dataAtual = new Date();

		for (Ferias ferias : obj.getVinculoAtivo().getListFerias()) {
			if (dataAtual.before(ferias.getListParcelas().get(0).getInicio())) {
				ferias.setStatus("Marcadas");
			} else if (dataAtual.after(ferias.getListParcelas().get(ferias.getListParcelas().size() - 1).getFim())) {
				ferias.setStatus("Paga");
			} else {
				for (FeriasParcela parcela : ferias.getListParcelas()) {
					if (dataAtual.after(parcela.getInicio()) && dataAtual.before(parcela.getFim())) {
						ferias.setStatus("De Férias");
					} else {
						ferias.setStatus("Marcadas");
					}
				}
			}
		}
		return super.update(obj);
	}

	public Setor findSetor(Integer id) {
		return this.repository.findSetor(id);
	}

	public Pessoa findByCPF(String cpf) {
		return this.repository.findByCPF(cpf);
	}

	public List<Pessoa> findBySetor(Integer id) {
		return this.repository.findBySetor(id);
	}

//	public APIQueryParams preFilter(APIQueryParams params) {
//
//		Usuario usuario = helper.getUsuarioLogado();
//		Colaborador colaborador = null;
//		if (!usuario.getLogin().equals("admin") && !usuario.getLogin().equals("job")
//				&& !usuario.getLogin().equals("frequencia")) {
//			colaborador = this.repository.findById(Constants.ADMIN_ID).get();
//		}
//
//		Integer prioridade = 0;
//		for (Papel p : usuario.getPermissoes()) {
//			if (p.getCodigo().equals("ADMIN") || p.getCodigo().equals("CHEFE_SGP")) {
//				prioridade = 1;
//			}
//			if (p.getCodigo().contains("CHEFE") && !p.getCodigo().equals("CHEFE_SGP") && prioridade != 1) {
//				prioridade = 2;
//			}
//		}
//
//		if (prioridade == 1) {
//			// admin chefe rh
//		}
//
//		if (prioridade == 2) {
//			LinkedTreeMap<String, Object> filter = new LinkedTreeMap<String, Object>();
//			LinkedTreeMap<String, Object> setor = new LinkedTreeMap<String, Object>();
//			LinkedTreeMap<String, Object> vinculo = new LinkedTreeMap<String, Object>();
//
//			setor.put("id", Double.parseDouble(colaborador.getVinculoAtivo().getSetor().getId().toString()));
//
//			vinculo.put("ativo", true);
//			vinculo.put("setor", setor);
//
//			List<Object> listVinculo = new ArrayList<Object>();
//			listVinculo.add(vinculo);
//
//			filter.put("listVinculo", listVinculo);
//			params.setFilter(filter);
//
//		} 
//		
//		if (prioridade == 0) {
//
//			LinkedTreeMap<String, Object> filter = new LinkedTreeMap<String, Object>();
//			filter.put("id", Double.parseDouble(colaborador.getId().toString()));
//			params.setFilter(filter);
//		}
//		
//		return params;
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> findAll(APIQueryParams params, Boolean to, Boolean papelFilter) {

		List response;

//		if(papelFilter) {
//			params = this.preFilter(params);
//		}

//		if (to) {
//			response = new ArrayList<ColaboradorTO>();
//			
//			for(Colaborador aux: findAll(params)) {
//				Vinculo v = aux.getVinculoAtivo();
//				VinculoTO vinculoTO = null;
//				if(v != null) {
//					String denominacaoSetor = null;
//					Integer idSetor = null;
//					String denominacaoCargo = null;
//					Integer idCargo = null;
//					
//					if(v.getSetor() != null) {
//						denominacaoSetor = v.getSetor().getDenominacao();
//						idSetor = v.getSetor().getId();
//					}
//					
//					if(v.getCargo() != null) {
//						denominacaoCargo = v.getCargo().getDenominacao();
//						idCargo = v.getCargo().getId();
//					}
//					
//					vinculoTO = new VinculoTO(v.getDenominacao(), v.getDataAdmissao(), denominacaoSetor, idSetor, denominacaoCargo, idCargo);
//				}
//				response.add(new ColaboradorTO(aux.getId(), vinculoTO, aux.getNome(), aux.getCpf(), aux.getListFerias(), aux.getStatusCadastro()));
//			}
//		}

//		else {
		response = new ArrayList<Pessoa>();
		response.addAll(findAll(params));
//		}

		return response;
	}

	public Pessoa findByNome(String nome) {
		return this.repository.findByNome(nome);
	}

	/**
	 * Retorna todos os usuários que não possuem login.
	 */
	public List<Pessoa> findByNullUser() {
		return this.repository.findByNullUser();
	}
}
