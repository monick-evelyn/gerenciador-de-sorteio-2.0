package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import controller.ControladorSorteio;
import model.Bilhete;
import model.Comprador;
import model.Pessoa;
import model.PixPremiado;
import model.Rifa;
import model.Vendedor;
import model.enums.FormaDePagamento;
import model.enums.NivelVendedor;
import model.interfaces.Sorteavel;

public class ControladorTest {

	private ControladorSorteio controlador = new ControladorSorteio();
	
	// ========================================================================================================================
	
	@Test
	public void deveIniciarSorteiosCadastrados() {
		
		String esperado = "Nenhum sorteio encontrado.";
		assertEquals(esperado, controlador.exibirTodosOsSorteios());
		assertEquals(0, controlador.contarSorteios());
	}
	
	@Test
	public void deveCadastrarUmaRifaEUmPixPremiado() {
		assertTrue(controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0));
		assertTrue(controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 500));

		assertEquals(2, controlador.contarSorteios());
	}
	
	@Test
	public void naoDeveCadastrarDoisSorteiosComMesmoCodigo() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		assertEquals(1, controlador.contarSorteios());
		assertFalse(controlador.cadastrarPixPremiado("R001", "R$100 no Pix", 5.0, 100));
		
	}
	
	@Test
	public void naoDeveCadastrarRifaComMetaInvalida() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, -100.0);
	}

	@Test
	public void naoDeveCadastrarPixPremiadoComLimiteInvalido() {
		controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 0);
	}
	
	// ========================================================================================================================
	@Test
	public void deveCadastrarPessoa() {
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));
		assertTrue(controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000"));
		
		assertEquals(1, controlador.contarVendedores());
		assertEquals(1, controlador.contarCompradores());
	}
	
	@Test
	public void naoDeveCadastrarDoisVendedoresComMesmoCpf() {
	    controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
	    assertFalse(controlador.cadastrarVendedor("11122233344", "Anderson Soares", "83988887777"));
	    assertEquals(1, controlador.contarVendedores());
	    
	}
	
	@Test
	public void naoDeveCadastrarDoisCompradoresComMesmoCpf() {
	    controlador.cadastrarComprador("55566677788", "Ana Lima", "83999990000");
	    assertFalse(controlador.cadastrarComprador("55566677788", "Maria Margarida", "8399997777"));
	    assertEquals(1, controlador.contarCompradores());
	}
	
	@Test
	public void naoDeveCadastrarPessoaComDadosVazios() {
		controlador.cadastrarVendedor("", "Maria Margarida", "8399997777");
		assertFalse(controlador.cadastrarVendedor("55566677788", "", "8399997777"));
		assertFalse(controlador.cadastrarVendedor("55566677788", "Maria Margarida", ""));
		assertFalse(controlador.cadastrarVendedor("", "", ""));
		
		assertFalse(controlador.cadastrarComprador("", "Ana Lima", "83999990000"));
		assertFalse(controlador.cadastrarComprador("55566677788", "", "8399997777"));
		assertFalse(controlador.cadastrarComprador("55566677788", "Maria Margarida", ""));
		assertFalse(controlador.cadastrarComprador("", "", ""));
	}
	
	@Test
	public void naoDeveCadastrarPessoaComCPFInvalido() {
		
		assertFalse(controlador.cadastrarVendedor("-1", "Maria", "8399997777"));
		assertFalse(controlador.cadastrarVendedor("55566", "Maria", "8399997777"));
		
		assertFalse(controlador.cadastrarComprador("-1", "Maria Margarida", "8399997777"));
		assertFalse(controlador.cadastrarComprador("55566", "Maria", "8399997777"));
		
		assertEquals(0, controlador.contarCompradores());
		assertEquals(0, controlador.contarVendedores());
	}
	
	@Test
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
	
	// ========================================================================================================================
	
	@Test
	public void deveBuscarSorteioPeloCodigo() {
	    controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);

	    Sorteavel sorteio = controlador.buscarSorteioPorCodigo("R001");
	    assertNotNull(sorteio);
	    assertTrue(sorteio instanceof Rifa);

	    Rifa rifa = (Rifa) sorteio;
	    assertEquals("R001", rifa.getCodigo());
	    assertEquals("Moto 0km", rifa.getPremio());
	    assertEquals(10.0, rifa.getValorBilhete(), 0.001);
	    assertEquals(100.0, rifa.getMeta(), 0.001);
	}
	
	@Test
	public void deveBuscarSorteioPixPeloCodigo() {
	    controlador.cadastrarPixPremiado("P001", "Moto 0km", 10.0, 100);

	    Sorteavel sorteio = controlador.buscarSorteioPorCodigo("P001");
	    assertNotNull(sorteio);
	    assertTrue(sorteio instanceof PixPremiado);

	    PixPremiado pixPremiado = (PixPremiado) sorteio;
	    assertEquals("P001", pixPremiado.getCodigo());
	    assertEquals("Moto 0km", pixPremiado.getPremio());
	    assertEquals(10.0, pixPremiado.getValorBilhete(), 0.001);
	    assertEquals(100, pixPremiado.getMetaBilhetes());
	}
	
	@Test
	public void deveBuscarVendedorPeloCPF() {
		assertTrue(controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000"));

		Vendedor vendedorEsperado = controlador.buscarVendedorPorCPF("11122233344");

		assertNotNull(vendedorEsperado);
		assertEquals("11122233344", vendedorEsperado.getCpf());
		assertEquals("Carlos Souza", vendedorEsperado.getNome());
		assertEquals("83999990000", vendedorEsperado.getTelefone());
	}
	
	
	@Test
	public void deveBuscarCompradorPeloCPF() {
		assertTrue(controlador.cadastrarComprador("11122233344", "Carlos Souza", "83999990000"));

		Comprador compradorEsperado = controlador.buscarCompradorPorCPF("11122233344");

		assertNotNull(compradorEsperado);
		assertEquals("11122233344", compradorEsperado.getCpf());
		assertEquals("Carlos Souza", compradorEsperado.getNome());
		assertEquals("83999990000", compradorEsperado.getTelefone());
	}
	
	@Test
	public void deveRetornarNullAoBuscarCodigoInexistente() {
		assertNull(controlador.buscarSorteioPorCodigo("999"));
	}

	@Test
	public void deveRetornarNullAoBuscarVendedorInexistente() {
		assertNull(null, controlador.buscarVendedorPorCPF("00000000000"));
	}

	@Test
	public void deveRetornarNullAoBuscarCompradorInexistente() {
		assertNull(null, controlador.buscarCompradorPorCPF("00000000000"));
	}
	
	// ============================================================
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
	
	@Test
	public void naoDeveVenderBilheteComNumeroJaVendido() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
	    controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
	    controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
	    
	    assertTrue(controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX));
	    assertFalse(controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX));
	}
	
	// ============================================================
	// ============================================================
	
	@Test
	public void rifaDeveFicarProntaParaSorteioApenasAoAtingirAMeta() {
		controlador.cadastrarRifa("R001", "Moto 0km", 50.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		assertFalse(controlador.buscarSorteioPorCodigo("R001").prontoParaSorteio());

		controlador.venderBilhete("R001", 2, "11122233344", "55566677788", FormaDePagamento.PIX);
		assertTrue(controlador.buscarSorteioPorCodigo("R001").prontoParaSorteio());
	}
	
	@Test
	public void pixPremiadoNaoDeveVenderBilheteForaDoIntervalo() {
		controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 10);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		assertFalse(controlador.venderBilhete("P001", 11, "11122233344", "55566677788", FormaDePagamento.PIX));
		assertFalse(controlador.venderBilhete("P001", -1, "11122233344", "55566677788", FormaDePagamento.PIX));
	}
	
	@Test
	public void rifaNaoDeveSortearAntesDeAtingirAMeta() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		assertEquals("Não foi possível realizar o sorteio", controlador.realizarSorteio("R001"));
	}

	// ============================================================
	// ============================================================

		
	@Test
	public void vendedorDeveIniciarNoNivelBronzeENaoMudarAteAtingirAQuantidader() {
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");

		assertEquals(NivelVendedor.BRONZE.name(), vendedor.getNivel().name());
	}

	@Test
	public void vendedorDeveSubirParaPrataAoCompletar15Vendas() {
		controlador.cadastrarRifa("R001", "Moto 0km", 5.0, 15000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		for (int i = 1; i <= 15; i++) {
			controlador.venderBilhete("R001", i, "11122233344", "55566677788", FormaDePagamento.PIX);
		}

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");
		assertEquals(15, vendedor.getQuantidadeVendas());
		assertEquals(NivelVendedor.PRATA.name(), vendedor.getNivel().name());
		
	}
	
	@Test
	public void vendedorDeveSubirParaOuroAoCompletar50Vendas() {
		controlador.cadastrarRifa("R001", "Moto 0km", 5.0, 15000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		for (int i = 1; i <= 50; i++) {
			controlador.venderBilhete("R001", i, "11122233344", "55566677788", FormaDePagamento.PIX);
		}

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");
		assertEquals(50, vendedor.getQuantidadeVendas());
		assertEquals(NivelVendedor.OURO.name(), vendedor.getNivel().name());
	}
	
	@Test
	public void vendedorDeveSubirParaDiamanteAoCompletar100Vendas() {
		controlador.cadastrarRifa("R001", "Moto 0km", 5.0, 15000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		
		for (int i = 1; i <= 100; i++) {
			controlador.venderBilhete("R001", i, "11122233344", "55566677788", FormaDePagamento.PIX);
		}

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");
		assertEquals(100, vendedor.getQuantidadeVendas());
		assertEquals(NivelVendedor.DIAMANTE.name(), vendedor.getNivel().name());
	}
	
	//=================================================================================

	@Test
	public void naoDeveVenderBilheteParaVendedorInexistente() {
	    controlador.cadastrarRifa("R001", "Moto", 10.0, 100.0);
	    assertFalse(controlador.venderBilhete("R001", 1, "00000000000", "11111111111", FormaDePagamento.PIX));
	}

	@Test
	public void naoDeveVenderBilheteAposSorteioJaRealizado() {
	    controlador.cadastrarPixPremiado("P001", "Premio", 5.0, 1);
	    controlador.cadastrarVendedor("11122233344", "Carlos", "83999990000");
	    controlador.cadastrarComprador("55566677788", "Ana", "83988880000");
	    controlador.venderBilhete("P001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
	    
	    controlador.realizarSorteio("P001");
	    assertFalse(controlador.venderBilhete("P001", 1, "11122233344", "55566677788", FormaDePagamento.PIX));
	}
	
	@Test
	public void consultarSorteioDeveFuncionarPolimorficamenteParaRifaEPixPremiado() {
	    controlador.cadastrarRifa("R001", "Moto", 10.0, 100.0);
	    controlador.cadastrarPixPremiado("P001", "R$500", 5.0, 10);

	    assertTrue(controlador.consultarSorteioPorCodigo("R001").contains("R001"));
	    assertTrue(controlador.consultarSorteioPorCodigo("P001").contains("P001"));
	}
	
	@Test
	public void buscarPorCodigoDeveRetornarUmSorteavel() {
		controlador.cadastrarPixPremiado("P001", "Premio", 5.0, 1);
	    controlador.cadastrarVendedor("11122233344", "Carlos", "83999990000");
	    controlador.cadastrarComprador("55566677788", "Ana", "83988880000");
	    controlador.venderBilhete("P001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		
		Bilhete bilhete = controlador.buscarBilhetePorCodigo("P001", 1);
		System.out.println(bilhete);
		assertNotNull(bilhete);
	}
	
	
	@Test
	public void deveRetornarUmaStringFormatadaAoConsultarBilhete() {
		controlador.cadastrarPixPremiado("P001", "Premio", 5.0, 10);
	    controlador.cadastrarVendedor("11122233344", "Carlos", "83999990000");
	    controlador.cadastrarComprador("55566677788", "Ana", "83988880000");
	    controlador.venderBilhete("P001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		
		String esperado = "Numero: 1\n"
				+ "Vendedor: 11122233344\n"
				+ "Comprador: Ana\n"
				+ "Forma de pagamento: PIX";
		
		assertEquals(esperado, controlador.consularBilhetePorCodigo("P001", 1));
	}
	
	// ========================================================================================================================

	@Test
	public void deveRealizarSorteioQuandoMetaForAtingida() {
		controlador.cadastrarRifa("R001", "Moto 0km", 50.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		controlador.venderBilhete("R001", 2, "11122233344", "55566677788", FormaDePagamento.PIX);

		String resultado = controlador.realizarSorteio("R001");
		assertTrue(resultado.contains("SORTEADO"));
	}

	@Test
	public void naoDeveRealizarSorteioAntesDeAtingirAMeta() {
		controlador.cadastrarRifa("R001", "Moto 0km", 50.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		String resultado = controlador.realizarSorteio("R001");
		assertEquals("Não foi possível realizar o sorteio", resultado);
	}

	// ========================================================================================================================

	@Test
	public void deveRemoverVendaExistente() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		assertTrue(controlador.removerVenda("R001", 1));
		assertEquals(null, controlador.buscarBilhetePorCodigo("R001", 1));
	}

	// ========================================================================================================================

	@Test
	public void deveAtualizarMetaDaRifa() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		assertTrue(controlador.atualizarMetaRifa("R001", 2000.0));

		Rifa rifa = (Rifa) controlador.buscarSorteioPorCodigo("R001");
		assertEquals(2000.0, rifa.getMeta(), 0.001);
	}

	@Test
	public void deveAtualizarMetaDoPixPremiado() {
		controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 500);
		assertTrue(controlador.atualizarMetaPix("P001", 1000));

		PixPremiado pix = (PixPremiado) controlador.buscarSorteioPorCodigo("P001");
		assertEquals(1000, pix.getMetaBilhetes());
	}

	// ========================================================================================================================

	@Test
	public void deveSortearNumeroQuandoProntoParaSorteio() {
		controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 1);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("P001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		
		Sorteavel sorteio = controlador.buscarSorteioPorCodigo("P001");
		String resultado = controlador.sortearNumero("P001");
		
		String esperado = "Código: P001\n"
				+ "Prêmio: R$100 no Pix\n"
				+ "Valor por bilhete: 5.0\n"
				+ "Meta: 1 bilhetes\n"
				+ "Arrecadacao atual: 5.0\n"
				+ "Quantidade de bilhetes vendidos: 1\n"
				+ "Sorteado? true";
		
		assertTrue(resultado.contains("SORTEADO"));
		assertEquals(esperado, sorteio.toString());
	}

	
	// ========================================================================================================================

	@Test
	public void deveTransformarRifaEmPixPremiado() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		assertTrue(controlador.transformarRifaemPix("R001", 50));
		assertTrue(controlador.buscarSorteioPorCodigo("R001") instanceof PixPremiado);
	}

	// ========================================================================================================================

	
	@Test
	public void deveExibirRelatorioGeralDaRifa() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);

		String relatorio = controlador.exibirRelatorioGeralDoSorteio("R001");
		
		assertTrue(relatorio.contains("RELATÓRIO"));
	}

	// ========================================================================================================================

	
	@Test
	public void deveGerarRankingGeralDeVendedores() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		String ranking = controlador.gerarRankingVendedores();
		String esperado = "====================RANKING GERAL DE VENDEDORES====================\n"
				+ "1º Lugar: Carlos Souza (CPF: 11122233344) - Total Vendido: 1 bilhetes\n"
				+ "===============================================================";
		assertTrue(ranking.contains("Carlos Souza"));
		
		assertEquals(esperado, ranking);
	}

	
	@Test
	public void deveGerarRankingDeVendedoresPorSorteio() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		String ranking = controlador.gerarRankingVendedoresPorSorteio("R001");
		assertTrue(ranking.contains("Carlos Souza"));
	}

	@Test
	public void deveListarVendasPorVendedor() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		String vendas = controlador.listarVendasPorVendedor("11122233344");
		assertTrue(vendas.contains("Carlos Souza"));
	}

	
	// ========================================================================================================================

	@Test
	public void deveExibirTodosOsCompradores() {
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		String compradores = controlador.exibirTodosOsCompradores();
		assertTrue(compradores.contains("Ana Lima"));
	}

	
	@Test
	public void deveExibirHistoricoDoComprador() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1000.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);

		String historico = controlador.exibirHistoricoPorComprador("55566677788");
		assertTrue(historico.contains("Ana Lima"));
	}

	// ========================================================================================================================

	@Test
	public void deveBuscarVendedorComoPessoa() {
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");

		Pessoa pessoa = controlador.buscarPessoaPorCPF("11122233344");
		assertNotNull(pessoa);
		assertTrue(pessoa instanceof Vendedor);
	}

	@Test
	public void deveBuscarCompradorComoPessoa() {
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		Pessoa pessoa = controlador.buscarPessoaPorCPF("55566677788");
		assertNotNull(pessoa);
		assertTrue(pessoa instanceof Comprador);
	}

	@Test
	public void deveRetornarNullAoBuscarPessoaComCpfInexistente() {
		assertNull(controlador.buscarPessoaPorCPF("00000000000"));
	}
	
}
