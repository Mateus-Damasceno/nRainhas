package nrainhas;


import java.util.Scanner;
import java.util.Random;

public class SubidaDaEncosta {
  private static int n ;
  private static int proximoNo = 0;
  private static int no =0;
  private static int heuristica = 0;
  private static int restartsAleatorios = 0;


  public static Nrainhas[] Tabuleiro() {
      Nrainhas[] tabuleiro = new Nrainhas[n];
      Random rndm = new Random();
      for(int i=0; i<n; i++){
          tabuleiro[i] = new Nrainhas(rndm.nextInt(n), i);
      }
      return tabuleiro;
  }


  public static void printTabuleiro (Nrainhas[] estado) {
     
      int[][] tabTemp = new int[n][n];
      for (int i=0; i<n; i++) {
        
          tabTemp[estado[i].getLinha()][estado[i].getColuna()]=1;
      }
      System.out.println();
      for (int i=0; i<n; i++) {
          for (int j= 0; j < n; j++) {
              System.out.print(tabTemp[i][j] + " ");
          }
          System.out.println();
      }
  }


  public static int encontraHeuristica (Nrainhas[] estado) {
      int heuristica = 0;
  
      for (int i = 0; i< estado.length; i++) {
          for (int j=i+1; j<estado.length; j++ ) {
              if (estado[i].seConflita(estado[j])) {
                 heuristica++;
                  
              }
             
          }
      }
    
      return heuristica/2;
  }

 
  public static Nrainhas[] No (Nrainhas[] tabuleiroAtual) {
      Nrainhas[] proximoTabuleiro = new Nrainhas[n];
      Nrainhas[] tempTabuleiro = new Nrainhas[n];
      int heuristicaAtual = encontraHeuristica(tabuleiroAtual);
      int melhorHeuristica = heuristicaAtual;
      int tempH;

      for (int i=0; i<n; i++) {
       
          proximoTabuleiro[i] = new Nrainhas(tabuleiroAtual[i].getLinha(), tabuleiroAtual[i].getColuna());
          tempTabuleiro[i] = proximoTabuleiro[i];
      }
      //  iteracao de cada linha
      for (int i=0; i<n; i++) {
          if (i>0)
              tempTabuleiro[i-1] = new Nrainhas (tabuleiroAtual[i-1].getLinha(), tabuleiroAtual[i-1].getColuna());
          tempTabuleiro[i] = new Nrainhas (0, tempTabuleiro[i].getColuna());
          //  iteracao em cada coluna
          for (int j=0; j<n; j++) {
              //pega a heuristica
              tempH = encontraHeuristica(tempTabuleiro);
              //checa se o tabuleiro temporario é melhor
              if (tempH < melhorHeuristica) {
                  melhorHeuristica = tempH;
                  //  copia o tabuleiro temporario para o melhor tabuleiro
                  for (int k=0; k<n; k++) {
                      proximoTabuleiro[k] = new Nrainhas(tempTabuleiro[k].getLinha(), tempTabuleiro[k].getColuna());
                  }
              }
              //move a rainha
              if (tempTabuleiro[i].getLinha()!=n-1)
                  tempTabuleiro[i].mover();
          }
      }
    //Verifique se o quadro atual e o melhor quadro encontrado têm a mesma heurística
      //entao gera um novo tabuleiro aleatorio e marca como melhor
      if (melhorHeuristica == heuristicaAtual) {
          restartsAleatorios++;
          proximoNo = 0;
          proximoTabuleiro = Tabuleiro();
          heuristica = encontraHeuristica(proximoTabuleiro);
      } else
          heuristica = melhorHeuristica;
      no++;
      proximoNo++;
      return proximoTabuleiro;
  }

  public static void main(String[] args) {
      int heuristicaPresente;
      Scanner s=new Scanner(System.in);
      while (true){
          System.out.println("numero de rainhas :");
          n = s.nextInt();
          if ( n == 2 || n ==3) {
              System.out.println("sem solucao para "+n+" rainhas. por favor entre com outro numero");
          }
          else
              break;
      }
      System.out.println("Solucao de "+n+" rainhas usando subida da encosta:");
      //cria o tabuleiro
      Nrainhas[] tabuleiroAtual = Tabuleiro();
      heuristicaPresente = encontraHeuristica(tabuleiroAtual);
      // testa se o tabuleiro é solucao
      while (heuristicaPresente != 0) {
          // pega o proximo tabuleiro
        
          tabuleiroAtual = No(tabuleiroAtual);
          heuristicaPresente  = heuristica;
      }
    
      printTabuleiro(tabuleiroAtual);
      System.out.println("\nnumero total de passo de subida da encosta: " + no);
      System.out.println("numero de resets: " + restartsAleatorios);
      System.out.println("passos subido depois do ultimo reset: " + proximoNo);
  }
}