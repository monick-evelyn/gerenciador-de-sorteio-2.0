package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

import java.lang.invoke.LambdaConversionException;

import org.junit.Rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import controller.ControladorSorteio;
import exception.BilheteInvalidoException;
import exception.CompradorInvalidoException;
import exception.CompradorNaoEncontradoException;
import exception.DadosInvalidosException;
import exception.SorteioInvalidoException;
import exception.SorteioNaoEncontradoException;
import exception.VendedorInvalidoException;
import exception.VendedorNaoEncontradoException;
import model.Comprador;
import model.PixPremiado;
import model.Rifa;
import model.Vendedor;
import model.enums.FormaDePagamento;
import model.interfaces.Sorteavel;

public class ControladorTest {

	private ControladorSorteio controlador = new ControladorSorteio();
	
	// ============================================================
	// ============================================================
	
	
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
		
		/*assertThrows(SorteioInvalidoException.class, () -> {
			controlador.cadastrarPixPremiado("R001", "R$100 no Pix", 5.0, 100);
	    });*/
	}
	
	@Test
	public void naoDeveCadastrarRifaComMetaInvalida() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, -100.0);
	}

	@Test
	public void naoDeveCadastrarPixPremiadoComLimiteInvalido() {
		controlador.cadastrarPixPremiado("P001", "R$100 no Pix", 5.0, 0);
	}
	
	// ============================================================
	// ============================================================
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
	    
	    //assertThrows(VendedorInvalidoException.class, () -> {controlador.cadastrarVendedor("11122233344", "Anderson Soares", "83988887777");});
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
	// ============================================================
	
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

		controlador.venderBilhete("P001", 11, "11122233344", "55566677788", FormaDePagamento.PIX);
		controlador.venderBilhete("P001", -1, "11122233344", "55566677788", FormaDePagamento.PIX);
	}
	
	@Test
	public void rifaNaoDeveSortearAntesDeAtingirAMeta() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 100.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");
		
		controlador.venderBilhete("R001", 1, "11122233344", "55566677788", FormaDePagamento.PIX);
		controlador.realizarSorteio("R001");
	}

	// ============================================================
	// ============================================================

	@Test
	public void vendedorDeveIniciarNoNivelBronzeENaoMudarAteAtingirAQuantidader() {
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");

		assertEquals("BRONZE", vendedor.getNivel().name());
	}

	@Test
	public void vendedorDeveSubirParaPrataAoCompletar15Vendas() {
		controlador.cadastrarRifa("R001", "Moto 0km", 10.0, 1500.0);
		controlador.cadastrarVendedor("11122233344", "Carlos Souza", "83999990000");
		controlador.cadastrarComprador("55566677788", "Ana Lima", "83988880000");

		for (int i = 1; i <= 15; i++) {
			controlador.venderBilhete("R001", i, "11122233344", "55566677788", FormaDePagamento.PIX);
		}

		Vendedor vendedor = controlador.buscarVendedorPorCPF("11122233344");
		assertEquals(15, vendedor.getQuantidadeVendas());
		assertEquals("PRATA", vendedor.getNivel().name());
	}
}
