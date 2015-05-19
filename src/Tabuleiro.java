package jogooitorainhas;

import java.util.ArrayList;

public class Tabuleiro {
	
	/* Atributos */
	private int[] coluna = {0,0,0,0,0,0,0,0};
	private ArrayList<Tabuleiro> filhos = new ArrayList<Tabuleiro>();
	private Tabuleiro pai;
	private int nivel = -1;
	
	/* Métodos construtores */
	public Tabuleiro(){}
	public Tabuleiro(Tabuleiro pai, int nivel, int[] coluna){
		this.pai = pai;
		this.nivel = nivel;
		this.coluna = coluna.clone();
	}
	
	/* Polimorfismo. */
	public void setFilhos(Tabuleiro filho){ this.filhos.add(filho); };
	public Tabuleiro getPai(){ return this.pai; }
	public int getNivel(){ return this.nivel; }
	public int[] getColuna(){ return this.coluna; }
	public ArrayList<Tabuleiro> getFilhos(){ return this.filhos; }
	
	/* Gera as ramificações da árvore */
	public void gerarRamificacoes(){
		int[] colunaAuxiliar = getColuna().clone();
		if (getNivel() == -1){
			for (int i=0; i<8; i++){
				colunaAuxiliar[i] = i+1;
				criarFilho(colunaAuxiliar);
			}
		} else if (getNivel() < 8){
			for (int i = 0; i<8; i++){
				colunaAuxiliar[getNivel()] = i+1;
				criarFilho(colunaAuxiliar);
			}
		}
	}
	
	/* Cria filhos na árvore se eles forem soluções. */
	private void criarFilho(int[] coluna){
		if (possivelSolucao(coluna)){
			Tabuleiro filho = new Tabuleiro(this, getNivel()+1, coluna);
			setFilhos(filho);
			filho.gerarRamificacoes();
		}
	}
        
    /* Verifica se o array pode SER ou CHEGAR a uma solução de 8 rainhas. */
    private boolean possivelSolucao(int[] coluna){
    	for (int i=0; i<coluna.length-1; i++)
    		for (int j=i+1; j<coluna.length; j++)
    			if ((coluna[j] == coluna[i]) && (coluna[j] != 0))
    				return false;
    			else if ((coluna[j] > coluna[i]) && (j-i == coluna[j]-coluna[i]) && (coluna[j] != 0))
    				return false;
    			else if ((coluna[i] > coluna[j]) && (j-i == coluna[i]-coluna[j]) && (coluna[j] != 0))
    				return false;
    	return true;
    }
}