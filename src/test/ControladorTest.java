package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import controller.ControladorSorteio;
import exception.BilheteInvalidoException;
import exception.SorteioInvalidoException;
import model.Vendedor;
import model.enums.FormaDePagamento;

public class ControladorTest {

	private ControladorSorteio controlador = new ControladorSorteio();
	
	// ============================================================
	// TESTES COM SORTEAVEL
	// ============================================================
	
	@Test
	public void deveIniciarSorteiosCadastrados() {
		assertEquals(0, controlador.contarSorteios());
		assertEquals("Nenhum sorteio cadastrado.", controlador.exibirTodosOsSorteios());
	}
	
	@Test
	public void deveCadastrarUmaRifaEUmPixPremiado() {
		assertTrue(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0));
		assertTrue(controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 500));

		assertEquals(2, controlador.contarSorteios());
	}
	
	@Test
	public void naoDeveCadastrarDoisSorteiosComMesmoCodigo() {
		assertTrue(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0));
		assertFalse(controlador.cadastrarPixPremiado("R001", "R$100 no Pix", 5.0, 10));
		
		assertEquals(1, controlador.contarSorteios());
	}
	
	@Test
	public void naoDeveCadastrarRifaComMetaInvalida() {
		assertFalse(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, -100.0));
		
		assertEquals(0, controlador.contarSorteios());
	}
	
	@Test
	public void naoDeveCadastrarPixPremiadoComLimiteInvalido() {
		assertFalse(controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 0));
		
		assertEquals(0, controlador.contarSorteios());
	}
	
	// ============================================================
	// TESTES DE CADASTRO DE ENTENDADE
	// ============================================================
	@Test
	public void deveCadastrarPessoa() {
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));
		assertTrue(controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000"));
		
		assertEquals(1, controlador.contarVendedores());
		assertEquals(1, controlador.contarCompradores());
	}
	
	public void naoDeveCadastrarDoisVendedoresComMesmoCpf() {
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));
		assertFalse(controlador.cadastrarVendedor("11122233344", "Anderson Soares", "83988887777"));
		
		assertEquals(1, controlador.contarVendedores());
	}
	
	public void naoDeveCadastrarDoisCompradoresComMesmoCpf() {
		assertTrue(controlador.cadastrarComprador("55566677788", "Ana Lima", "83999990000"));
		assertFalse(controlador.cadastrarComprador("55566677788", "Maria Margarida", "8399997777"));
		
		assertEquals(1, controlador.contarCompradores());
	}
	
	public void naoDeveCadastrarPessoaComDadosVazios() {
		assertFalse(controlador.cadastrarVendedor("", "Maria Margarida", "8399997777"));
		assertFalse(controlador.cadastrarVendedor("55566677788", "", "8399997777"));
		assertFalse(controlador.cadastrarVendedor("55566677788", "Maria Margarida", ""));
		assertFalse(controlador.cadastrarVendedor("", "", ""));
		
		assertFalse(controlador.cadastrarComprador("", "Ana Lima", "83999990000"));
		assertFalse(controlador.cadastrarComprador("55566677788", "", "8399997777"));
		assertFalse(controlador.cadastrarComprador("55566677788", "Maria Margarida", ""));
		assertFalse(controlador.cadastrarComprador("", "", ""));
	}
	
	public void naoDeveCadastrarPessoaComCPFInvalido() {
		assertFalse(controlador.cadastrarVendedor("-1", "Maria Margarida", "8399997777"));
		assertFalse(controlador.cadastrarVendedor("55566", "Maria", "8399997777"));
		
		assertFalse(controlador.cadastrarComprador("-1", "Maria Margarida", "8399997777"));
		assertFalse(controlador.cadastrarComprador("55566", "Maria", "8399997777"));
		
		assertEquals(0, controlador.contarCompradores());
		assertEquals(0, controlador.contarVendedores());
	}
	
	public void naoDeveCadastrarPessoaTelefoneInvalido() {
		assertFalse(controlador.cadastrarVendedor("55566677788", "Maria", "-1"));
		assertFalse(controlador.cadastrarVendedor("55566677788", "Maria", "6435"));
		assertFalse(controlador.cadastrarVendedor("55566677788", "Maria", "90990909090909090909090"));
		
		assertFalse(controlador.cadastrarComprador("55566677788", "Maria", "-1"));
		assertFalse(controlador.cadastrarComprador("55566677788", "Maria", "6435"));
		assertFalse(controlador.cadastrarComprador("55566677788", "Maria", "90990909090909090909090"));
		
		assertEquals(0, controlador.contarCompradores());
		assertEquals(0, controlador.contarVendedores());
	}
	
	// ============================================================
	// TESTES DE BUSCA
	// ============================================================
	
	@Test
	public void deveBuscarSorteioPeloCodigo() {
		assertTrue(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0));

		Sorteavel sorteio = controlador.buscarSorteioPorCodigo("R001");

		assertNotNull(sorteio);
		assertEquals("R001", sorteio.getCodigo());
		assertEquals("Moto 0km", sorteio.getPremio());
		assertEquals(10.0, sorteio.getValorBilhete());
		assertEquals(100.0, sorteio.getMeta());
	}
	
	@Test
	public void deveBuscarVendedorPeloCPF() {
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));

		Vendedor vendedorEsperado = controlador.buscarVendedorPorCPF("11122233344");

		assertNotNull(vendedorEsperado);
		assertEquals("11122233344", vendedorEsperado.getCPF());
		assertEquals("Carlos Souza", vendedorEsperado.getNome());
		assertEquals("83999990000", vendedorEsperado.getTelefone());
	}
	
	
	@Test
	public void deveBuscarCompradorPeloCPF() {
		assertTrue(controlador.cadastrarComprador("11122233344", "Carlos Souza", "83999990000"));

		Comprador compradorEsperado = controlador.buscarCompradorPorCPF("11122233344");

		assertNotNull(compradorEsperado);
		assertEquals("11122233344", compradorEsperado.getCPF());
		assertEquals("Carlos Souza", compradorEsperado.getNome());
		assertEquals("83999990000", compradorEsperado.getTelefone());
	}
	
	@Test
	public void deveRetornarNullAoBuscarCodigoInexistente() {
		assertNull(controlador.buscarSorteioPorCodigo("999"));
	}
	
	@Test(expected = SorteioInvalidoException.class)
	public void deveLancarExcecaoAoBuscarCodigoInexistente() {
		controlador.buscarSorteioPorCodigo("R999");
	}

	@Test(expected = VendedorInvalidoException.class)
	public void deveLancarExcecaoAoBuscarVendedorInexistente() {
		controlador.buscarVendedorPorCPF("00000000000");
	}

	@Test(expected = CompradorInvalidoException.class)
	public void deveLancarExcecaoAoBuscarCompradorInexistente() {
		controlador.buscarCompradorPorCPF("00000000000");
	}
	
	// ============================================================
	// TESTES DE VENDA DE BILHETE E POLIMORFISMO DE INTERFACE
	// ============================================================
	
	@Test
	public void deveVenderBilheteTantoParaRifaQuantoParaPixPremiado() {
		assertTrue(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0));
		assertTrue(controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 500));
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));
		assertTrue(controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000"));

		assertTrue(controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX));
		assertTrue(controlador.venderBilhete("P001", 1, "11122233344", "55566677788", FormaDePagamento.CARTAO));
	}
	
	@Test(expected = BilheteInvalidoException.class)
	public void naoDeveVenderBilheteComNumeroJaVendido() {
		assertTrue(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0));
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));
		assertTrue(controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000"));

		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
	}
	
	// ============================================================
	// TESTES DE REGRAS ESPECÍFICAS DE CADA TIPO DE SORTEIO
	// ============================================================
	
	@Test
	public void rifaDeveFicarProntaParaSorteioApenasAoAtingirAMeta() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		assertFalse(controlador.buscarRifaPorCodigo("R001").prontoParaSorteio());

		controlador.venderBilhete("R001", 2, "11122233344", "55566677788", FormaDePagamento.PIX);
		assertTrue(controlador.buscarRifaPorCodigo("R001").prontoParaSorteio());
	}
	
	@Test(expected = BilheteInvalidoException.class)
	public void pixPremiadoNaoDeveVenderBilheteForaDoIntervalo() {
		controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 10);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		controlador.venderBilhete("P001", 11, "11122233344", "55566677788", FormaDePagamento.PIX);
		controlador.venderBilhete("P001", -1, "11122233344", "55566677788", FormaDePagamento.PIX);
	}
	
	@Test(expected = SorteioInvalidoException.class)
	public void rifaNaoDeveSortearAntesDeAtingirAMeta() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		controlador.realizarSorteio("R001");
	}

	// ============================================================
	// TESTES DE NÍVEL DE VENDEDOR
	// ============================================================

	@Test
	public void vendedorDeveIniciarNoNivelBronzeENaoMudarAteAtingirAQuantidader() {
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");

		assertEquals("BRONZE", vendedor.getNivel().name());
	}

	@Test
	public void vendedorDeveSubirParaPrataAoCompletar15Vendas() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		for (int i = 0; i < 15; i++) {
			controlador.venderBilhete("R001", i, "11122233344", "55566677788", FormaDePagamento.PIX);
		}

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");
		assertEquals("PRATA", vendedor.getNivel().name());
	}
}
