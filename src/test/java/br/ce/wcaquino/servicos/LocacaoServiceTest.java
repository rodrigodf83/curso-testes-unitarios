package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraExcpetion;
import br.ce.wcaquino.utils.DataUtils;
import java.util.*;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void testeBefore() {
        service = new LocacaoService();
    }

    @Test
    public void deveALugarFilme() throws Exception {

        assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenário - inicialiçao das variáveis
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Filme 1", 2, 5.00));
        filmes.add(new Filme("Filme 2", 2, 5.00));

        //ação - invocar o método que desejo testar
        Locacao locacao = service.alugarFilme(usuario, filmes);
        //verificação - verificar se o método ser comportou como esperado
//        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
//        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
//        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

        // assertThat (com import statico)

//        assertThat(locacao.getValor(), is(5.0)); // Verifique que o valor da locação é 5.0
//        assertThat(locacao.getValor(), is(not(6.0))); // Verifique que o valor da lcoação não é 5.0

        error.checkThat(locacao.getValor(), is(equalTo(10.0))); // Verifique que o valore da locação é igual a 5.0
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception {
        Usuario usuario = new Usuario("Usuario  1");

        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Filme 1", 0, 5.00));
        filmes.add(new Filme("Filme 2", 1, 5.00));

        Locacao locacao = service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque2() {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Filme 1", 0, 5.00));
        filmes.add(new Filme("Filme 2", 1, 5.00));

        try {
            Locacao locacao = service.alugarFilme(usuario, filmes);
            fail("Deveria ser lançado uma exceção!");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque!"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque3() throws Exception {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Filme 1", 0, 5.00));
        filmes.add(new Filme("Filme 2", 1, 5.00));

        exception.expect(Exception.class);
        exception.expectMessage("Filme sem estoque!");

        Locacao locacao = service.alugarFilme(usuario, filmes);

    }
    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
        //Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Filme 1", 1, 5.00));
        filmes.add(new Filme("Filme 2", 1, 5.00));

        try {
            Locacao locacao = service.alugarFilme(null, filmes);
            fail();
        }  catch (LocadoraExcpetion locadoraExcpetion) {
           assertThat(locadoraExcpetion.getMessage(), is("Usuário vazio!"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraExcpetion {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = new ArrayList<>();
        //Filme filme = new Filme("Filme 1", 2, 5.00);

        exception.expect(LocadoraExcpetion.class);
        exception.expectMessage("Filme vazio!");
        Locacao locacao = service.alugarFilme(usuario, filmes);
    }

    @Test
    public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraExcpetion {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 4.00),
                new Filme("Filme 2", 1, 4.00),
                new Filme("Filme 3", 1, 4.00)
        );

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //4 + 4 + 3 = 11
        assertThat(locacao.getValor(), is(11.0));
    }

    @Test
    public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraExcpetion {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 4.00),
                new Filme("Filme 2", 1, 4.00),
                new Filme("Filme 3", 1, 4.00),
                new Filme("Filme 4", 1, 4.00)
        );

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //4 + 4 + 3 + 2 = 14
        assertThat(locacao.getValor(), is(13.0));
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraExcpetion {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 4.00),
                new Filme("Filme 2", 1, 4.00),
                new Filme("Filme 3", 1, 4.00),
                new Filme("Filme 4", 1, 4.00),
                new Filme("Filme 5", 1, 4.00)
        );

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //4 + 4 + 3 + 2 + 1 = 14
        assertThat(locacao.getValor(), is(14.0));
    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraExcpetion {
        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 4.00),
                new Filme("Filme 2", 1, 4.00),
                new Filme("Filme 3", 1, 4.00),
                new Filme("Filme 4", 1, 4.00),
                new Filme("Filme 5", 1, 4.00),
                new Filme("Filme 6", 1, 4.00)
        );

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //4 + 4 + 3 + 2 + 1 + 0 = 14
        assertThat(locacao.getValor(), is(14.0));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoDomingo() throws FilmeSemEstoqueException, LocadoraExcpetion {

        assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = new Usuario("Usuario  1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 4.00),
                new Filme("Filme 2", 1, 4.00)
        );

        Locacao locacao = service.alugarFilme(usuario, filmes);

        boolean ehSegunda = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }
}
