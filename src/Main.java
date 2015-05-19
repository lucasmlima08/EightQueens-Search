package jogooitorainhas;

public class Main {
	
    public Tabuleiro tabuleiro = new Tabuleiro();;
    
	public Main(){
		
		// Gera as ramificações.
		tabuleiro.gerarRamificacoes();
		
		Busca busca = new Busca();
		
		busca.rainhasEscolhidas[0][0] = 7;
		busca.rainhasEscolhidas[0][1] = 3;
		busca.rainhasEscolhidas[1][0] = 1;
		busca.rainhasEscolhidas[1][1] = 4;
		
		busca.profundidade(tabuleiro);
		busca.limitada(tabuleiro,3);
		busca.largura(tabuleiro);
		busca.iterativa(tabuleiro);
		
		System.out.println(" - - Busca em Profundidade - - ");
		for (int i=0; i<busca.profundidade.size(); i++){
			imprimeTabuleiro(busca.profundidade.get(i));
			System.out.print("   Posição = "+busca.profundidadePosicao.get(i));
			System.out.println();
		}
		System.out.println(" - - Busca Limitada - - ");
		for (int i=0; i<busca.limitada.size(); i++){
			imprimeTabuleiro(busca.limitada.get(i));
			System.out.print("   Posição = "+busca.limitadaPosicao.get(i));
			System.out.println();
		}
		System.out.println(" - - Busca em Largura - - ");
		for (int i=0; i<busca.largura.size(); i++){
			imprimeTabuleiro(busca.largura.get(i));
			System.out.print("   Posição = "+busca.larguraPosicao.get(i));
			System.out.println();
		}
		System.out.println(" - - Busca Iterativa - - ");
		for (int i=0; i<busca.iterativa.size(); i++){
			imprimeTabuleiro(busca.iterativa.get(i));
			System.out.print("   Posição = "+busca.iterativaPosicao.get(i));
			System.out.println();
		}
	}
	
	/* Exibe o tabuleiro na tela. */
	public void imprimeTabuleiro(int[] coluna){
		for(int i = 0; i < coluna.length; i++)
			System.out.print(" "+coluna[i]);
		//System.out.println();
	}

	public static void main(String[] args){ new Main(); }
}
