package com.j2ns.backend.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilsCaixeiro {//Aplicação de um projeto de PCV em Codigo Guloso com Metrica principal baseada em Distancia

    private static final int CAPACIDADE_MAX = 12; // Capacidade máxima de mercadorias

    /**
     * Método para calcular a melhor rota usando uma abordagem gulosa.
     *
     * @param todasEntregas Lista de todas as entregas disponíveis.
     * @return RotaDTO com as entregas selecionadas.
     */
    public RotaDTO calcularRota(List<EntregaDTO> todasEntregas) {
        List<EntregaDTO> rota = new ArrayList<>();
        boolean[] visitado = new boolean[todasEntregas.size()];
        int totalDistancia = 0;
        int totalMercadorias = 0;

        // Começa a partir da primeira entrega
        EntregaDTO entregaAtual = todasEntregas.get(0);
        rota.add(entregaAtual);
        visitado[0] = true;

        while (rota.size() < todasEntregas.size()) {
            int menorDistancia = Integer.MAX_VALUE;
            EntregaDTO proximaEntrega = null;
            int indiceProximaEntrega = -1;

            // Busca a próxima entrega mais próxima
            for (int i = 0; i < todasEntregas.size(); i++) {
                if (!visitado[i]) { // Verifica se a entrega já foi visitada
                    int distancia = calcularDistanciaEntreDuasEntregas(entregaAtual, todasEntregas.get(i));
                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        proximaEntrega = todasEntregas.get(i);
                        indiceProximaEntrega = i;
                    }
                }
            }

            // Adiciona a entrega mais próxima à rota
            if (proximaEntrega != null) {
                rota.add(proximaEntrega);
                visitado[indiceProximaEntrega] = true;
                totalDistancia += menorDistancia;
                entregaAtual = proximaEntrega;
                totalMercadorias += proximaEntrega.getQuantidadeMercadoria();
            }
        }

        // Retorna a rota calculada como um RotaDTO
        return new RotaDTO(rota);
    }

    /**
     * Método auxiliar para calcular a distância entre duas entregas.
     *
     * @param e1 Entrega 1.
     * @param e2 Entrega 2.
     * @return Distância simulada entre as entregas.
     */
    private int calcularDistanciaEntreDuasEntregas(EntregaDTO e1, EntregaDTO e2) {
        return Math.abs(e1.getDistancia() - e2.getDistancia()); // Simulação de distância
    }
}//Aplicação de um projeto de PCV em Codigo Guloso







/*Caixeiro viajante Dianmico Caso nao seja no padrao Guloso.*/

//package com.fachter.backend.utils;
//
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UtilsCaixeiro {
//
//    private static final int CAPACIDADE_MAX = 12; // Capacidade máxima de mercadorias
//
//    /**
//     * Método para calcular a melhor rota utilizando programação dinâmica.
//     *
//     * @param todasEntregas Lista de todas as entregas disponíveis.
//     * @return RotaDTO com as entregas selecionadas.
//     */
//
//    public RotaDTO calcularRota(List<EntregaDTO> todasEntregas) {
//        int n = todasEntregas.size();// Criar uma matriz de distâncias entre todas as entregas
//        int[][] distancias = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (i != j) {
//                    distancias[i][j] = calcularDistanciaEntreDuasEntregas(todasEntregas.get(i), todasEntregas.get(j));
//                }
//            }
//        }
//
//        int[][] memo = new int[1 << n][n];// Matriz de memoização (armazena os resultados das sub-rotas)
//        for (int[] row : memo) {
//            java.util.Arrays.fill(row, -1);
//        }
//
//        List<EntregaDTO> melhorRota = new ArrayList<>();// Chamar a função recursiva do caixeiro viajante
//        int distanciaMinima = pcv(0, 1, distancias, memo, melhorRota, todasEntregas);
//
//        return new RotaDTO(melhorRota);// Retorna a rota final com a menor distância
//    }
//
//    /*Uma Função recursiva de Caixeiro viajante/pcv com memoização, explicação do que é memoização no final.*/
//    private int pcv(int pos, int mask, int[][] distancias, int[][] memo, List<EntregaDTO> melhorRota, List<EntregaDTO> entregas) {
//        if (mask == (1 << entregas.size()) - 1) {
//            return distancias[pos][0];  // Retorna a distância para o ponto inicial
//        }
//
//        if (memo[mask][pos] != -1) {
//            return memo[mask][pos];  // Retorna o valor memoizado
//        }
//
//        int distanciaMinima = Integer.MAX_VALUE;
//        int proximaEntrega = -1;
//
//        // Passa sobre todas as entregas para buscar a próxima entrega
//        for (int i = 0; i < entregas.size(); i++) {
//            if ((mask & (1 << i)) == 0) {  // Se a entrega não foi visitada
//                int novaDistancia = distancias[pos][i] + pcv(i, mask | (1 << i), distancias, memo, melhorRota, entregas);
//                if (novaDistancia < distanciaMinima) {
//                    distanciaMinima = novaDistancia;
//                    proximaEntrega = i;
//                }
//            }
//        }
//
//        // Armazena a resposta na tabela de memoização
//        memo[mask][pos] = distanciaMinima;
//
//        // Adiciona a entrega à rota no caso prioritario
//        if (proximaEntrega != -1) {
//            melhorRota.add(entregas.get(proximaEntrega));
//        }
//
//        return distanciaMinima; //retorna a prioridade
//    }
//
//    /* Um metodo auxiliar que calcula a distância entre duas entregas.*/
//    private int calcularDistanciaEntreDuasEntregas(EntregaDTO e1, EntregaDTO e2) {
//        return Math.abs(e1.getDistancia() - e2.getDistancia());  // Simulação de distância
//    }
//    private int calcularPrioridadePorHorario(EntregaDTO e1, EntregaDTO e2) {
//        return Math.abs(e1.getHorarioPrioridade() - e2.getHorarioPrioridade());
//    }// Simula a diferença de prioridade no horário
//}
//    /*
//    Resumindo Memoização é usada aqui para otimizar o cálculo, evitando a repetição desnecessária de subproblemas já resolvidos.
//    */
//}
