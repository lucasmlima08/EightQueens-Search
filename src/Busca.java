package jogooitorainhas;

import java.util.ArrayList;

public class Busca {
	
	public int[][] rainhasEscolhidas = new int[2][2];
	
	public ArrayList<int[]> profundidade = new ArrayList<int[]>();
	public ArrayList<int[]> limitada = new ArrayList<int[]>();
	public ArrayList<int[]> largura = new ArrayList<int[]>();
	public ArrayList<int[]> iterativa = new ArrayList<int[]>();
	
	public ArrayList<Integer> profundidadePosicao = new ArrayList<Integer>();
	public ArrayList<Integer> limitadaPosicao = new ArrayList<Integer>();
	public ArrayList<Integer> larguraPosicao = new ArrayList<Integer>();
	public ArrayList<Integer> iterativaPosicao = new ArrayList<Integer>();
	
	private int acumulador = 0;
	
	/** - - - - - - - - - BUSCA EM PROFUNDIDADE - - - - - - - - - - - - - */
	
	int profundidadeInteracoesAux = 0;
	
	public void profundidade(Tabuleiro tabuleiro){
		if (tabuleiro.getNivel() == -1){
			profundidade.clear();
			profundidadePosicao.clear();
			acumulador = 0;
		}
		acumulador++;
		// Aqui ele vai incluir só os estados com nível 8 e que tenham as rainhas incluidas.
		for (int i=0; i<tabuleiro.getFilhos().size(); i++){
			if ((tabuleiro.getFilhos().get(i).getNivel() == 8) && rainhasIncluidas(tabuleiro.getFilhos().get(i).getColuna())){
				profundidade.add(tabuleiro.getFilhos().get(i).getColuna());
				profundidadePosicao.add(acumulador);
			}
			profundidade(tabuleiro.getFilhos().get(i));
		}
	}
	
	/** - - - - - - - - - BUSCA LIMITADA - - - - - - - - - - - - - */
	
	ArrayList<Tabuleiro> limitadaAux = new ArrayList<Tabuleiro>();
	int limiteAux = 0;
	
	public void limitada(Tabuleiro tabuleiro, int limite){
		if (tabuleiro.getNivel() == -1){
			limitada.clear();
			limitadaPosicao.clear();
			limitadaAux.clear();
			limiteAux = limite;
			acumulador = 0;
		}
		acumulador++;
		if (tabuleiro.getNivel() == 8){ // Se for nivel 8
			if (rainhasIncluidas(tabuleiro.getColuna())){ // Se for uma solução esperada.
				limitada.add(tabuleiro.getColuna());
				limitadaPosicao.add(acumulador);
			}
		} else if (limite == 0){ // Se chegou ao limite e não é nível 8.
			limitadaAux.add(tabuleiro);
		} else {
			for (int i=0; i<tabuleiro.getFilhos().size(); i++) // Percorre filhos.
				limitada(tabuleiro.getFilhos().get(i), limite-1);
			if (limite == limiteAux && tabuleiro.getNivel() != -1) // Se está no topo do limite e não é o início da árvore.
				limitadaAux.remove(0); // Remove para não repetir.
			if (limite == limiteAux) // Se percorreu a árvore no nível esperado e não terminou, continua percorrendo.
				while (!limitadaAux.isEmpty())
					limitada(limitadaAux.get(0),limiteAux);
		}
	}
	
	/** - - - - - - - - - BUSCA EM LARGURA - - - - - - - - - - - - - */
	
	ArrayList<Tabuleiro> larguraAux = new ArrayList<Tabuleiro>();
	
	public void largura(Tabuleiro tabuleiro){
		if (tabuleiro.getNivel() == -1){
			largura.clear();
			larguraPosicao.clear();
			larguraAux.clear();
			acumulador = 0;
		}
		acumulador++;
		// Aceita se for nível 8.
		if (tabuleiro.getNivel() == 8)
			if (rainhasIncluidas(tabuleiro.getColuna())){
				largura.add(tabuleiro.getColuna());
				larguraPosicao.add(acumulador);
			}
		// Remove o primeiro elemento se a fila não for vazia.
		if (!larguraAux.isEmpty())
			larguraAux.remove(0);
		// Adiciona todos os filhos à fila.
		for (int i=0; i<tabuleiro.getFilhos().size(); i++)
			larguraAux.add(tabuleiro.getFilhos().get(i));
		// Começa a percorrer a fila.
		if (!larguraAux.isEmpty())
			largura(larguraAux.get(0));
	}
	
	/** - - - - - - - - - BUSCA ITERATIVA - - - - - - - - - - - - - */
	
	ArrayList<Tabuleiro> iterativaAux = new ArrayList<Tabuleiro>();
	int limiteIterativa = 0;
	
	public void iterativa(Tabuleiro tabuleiro){
		if (tabuleiro.getNivel() == -1){
			iterativa.clear();
			iterativaPosicao.clear();
			limiteIterativa = 0;
			acumulador = 0;
		}
		// Chama largura no inicio.
		larguraIterativa(tabuleiro);
		// começa a interativa.
		while (!iterativaAux.isEmpty()){
			limitadaIterativa(iterativaAux.get(0), limiteIterativa);
			iterativaAux.remove(0);
		}
	}
	
	public void larguraIterativa(Tabuleiro tabuleiro){
		for (int i=0; i<tabuleiro.getFilhos().size(); i++)
			iterativaAux.add(tabuleiro.getFilhos().get(i));
		limiteIterativa++;
	}
	
	public void limitadaIterativa(Tabuleiro tabuleiro, int limite){
		acumulador++;
		if (tabuleiro.getNivel() == 8){ // Se for nivel 8
			if (rainhasIncluidas(tabuleiro.getColuna())){ // Se for uma solução esperada.
				iterativa.add(tabuleiro.getColuna());
				iterativaPosicao.add(acumulador);
			}
		} else if (limite == 0){ // Se chegou ao limite e não é nível 8, joga para a largura.
			larguraIterativa(tabuleiro);
		} else {
			for (int i=0; i<tabuleiro.getFilhos().size(); i++) // Percorre filhos enquanto nivel != 0.
				limitadaIterativa(tabuleiro.getFilhos().get(i), limite-1);
		}
	}
	
	/** - - - - - - - - - MÉTODOS UTILIZADOS - - - - - - - - - - - - - */
	
	private boolean rainhasIncluidas(int[] coluna){
		boolean[] rainhasIncluidas = {false, false};
		// Verifica se não é igual.
		if (rainhasEscolhidas[0][0] == rainhasEscolhidas[1][0] && rainhasEscolhidas[0][1] == rainhasEscolhidas[1][1])
			return false;
		// Verifica se as rainhas estão na coluna solução.
		for (int i=0; i<coluna.length; i++){
			if ((i == rainhasEscolhidas[0][0]) && (coluna[i]-1 == rainhasEscolhidas[0][1]))
				rainhasIncluidas[0] = true;
			if ((i == rainhasEscolhidas[1][0]) && (coluna[i]-1 == rainhasEscolhidas[1][1]))
				rainhasIncluidas[1] = true;
		}
		if (rainhasIncluidas[0] && rainhasIncluidas[1])
			return true;
		return false;
	}
}
