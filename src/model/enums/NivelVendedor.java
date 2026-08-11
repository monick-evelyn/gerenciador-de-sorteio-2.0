package model.enums;

public enum NivelVendedor {
	 	BRONZE(0),
	    PRATA(15),
	    OURO(50),
	    DIAMANTE(100);

	    private final int vendasMinimas;

	    private NivelVendedor(int vendasMinimas) {
	        this.vendasMinimas = vendasMinimas;
	    }
	    

	    public int getVendasMinimas() {
	        return vendasMinimas;
	    }

	    public static NivelVendedor calcularNivel(int totalVendas) {
	        NivelVendedor nivelCalculado = BRONZE;
	        for (NivelVendedor nivel : values()) {
	            if (totalVendas >= nivel.getVendasMinimas()) {
	                nivelCalculado = nivel;
	            }
	        }
	        return nivelCalculado;
	    }
}

