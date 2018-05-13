package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraExcpetion;
import br.ce.wcaquino.utils.DataUtils;
import java.util.Calendar;
import java.util.Date;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraExcpetion {

        if (filmes == null || filmes.isEmpty())
            throw new LocadoraExcpetion("Filme vazio!");

		for(Filme filme: filmes) {
			if (filme.getEstoque().equals(0))
				throw new FilmeSemEstoqueException("Filme sem estoque!");
		}

		if (usuario == null)
		    throw new LocadoraExcpetion("Usuário vazio!");


		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(getValorLocacao(filmes));

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);

		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);

		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	private Double getValorLocacao(List<Filme> filmes) {
		Double valorLocacao = 0.0;
		Double valorFilme;
		for (Filme filme: filmes) {
			valorFilme = filme.getPrecoLocacao();
			switch (filmes.indexOf(filme)) {
				case 2: valorFilme *= 0.75; break;
				case 3: valorFilme *= 0.50; break;
				case 4: valorFilme *= 0.25; break;
				case 5: valorFilme = 0.0; break;
			}
			valorLocacao += valorFilme;
		}
		return valorLocacao;
	}
}