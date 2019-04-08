package nrainhas;

public class Nrainhas {
    private int linha;
    private int coluna;


    public Nrainhas(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void mover () {
        linha++;
    }

    public boolean seConflita(Nrainhas q){
   
        if(linha == q.getLinha() || coluna == q.getColuna()) {
        	
        	return true;
        }
                
        else if(Math.abs(coluna-q.getColuna()) == Math.abs(linha-q.getLinha())) {
        	
        	return true;
        }
            
        return false;
    }
    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}