package com.j2ns.backend.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UtilsCaixeiro {
    private static final int CAPACIDADE_MAX = 12; // Capacidade máxima de mercadorias
    private static final int TEMPO_TOTAL = 240;  // Tempo total disponível (minutos entre 10h e 14h)

    public List<RotaDTO> calcularRotas(List<EntregaDTO> todasEntregas) {
        List<RotaDTO> rotas = new ArrayList<>();
        boolean[] visitado = new boolean[todasEntregas.size()];

        int tempoAtual = 0;
        while (tempoAtual < TEMPO_TOTAL && !todasEntregas.isEmpty()) {
            List<EntregaDTO> rotaAtual = new ArrayList<>();
            int mercadoriasCarregadas = 0;
            EntregaDTO entregaAtual = encontrarEntregaMaisProxima(todasEntregas, null, visitado);

            while (entregaAtual != null && mercadoriasCarregadas < CAPACIDADE_MAX && tempoAtual < TEMPO_TOTAL) {
                // Adiciona a entrega à rota atual
                rotaAtual.add(entregaAtual);
                visitado[todasEntregas.indexOf(entregaAtual)] = true;
                mercadoriasCarregadas += entregaAtual.getQuantidadeMercadoria();

                // Calcula o tempo gasto até a entrega
                EntregaDTO proximaEntrega = encontrarEntregaMaisProxima(todasEntregas, entregaAtual, visitado);
                if (proximaEntrega != null) {
                    tempoAtual += calcularTempoEntreEntregas(entregaAtual, proximaEntrega);
                }

                entregaAtual = proximaEntrega;
            }

            // Adiciona a rota atual à lista de rotas
            rotas.add(new RotaDTO(rotaAtual));
        }
        return rotas;
    }

    private EntregaDTO encontrarEntregaMaisProxima(List<EntregaDTO> entregas, EntregaDTO atual, boolean[] visitado) {
        int menorDistancia = Integer.MAX_VALUE;
        EntregaDTO entregaMaisProxima = null;

        for (int i = 0; i < entregas.size(); i++) {
            if (!visitado[i]) {
                EntregaDTO entrega = entregas.get(i);
                int distancia = (atual == null)
                        ? entrega.getDistancia() // Distância do restaurante
                        : calcularDistanciaEntreDuasEntregas(atual, entrega);

                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    entregaMaisProxima = entrega;
                }
            }
        }
        return entregaMaisProxima;
    }

    private int calcularDistanciaEntreDuasEntregas(EntregaDTO e1, EntregaDTO e2) {
        return (int) Math.sqrt(
                Math.pow(e1.getLongitude() - e2.getLongitude(), 2) +
                        Math.pow(e1.getLatitude() - e2.getLatitude(), 2)
        );
    }

    private int calcularTempoEntreEntregas(EntregaDTO e1, EntregaDTO e2) {
        // Suponha uma velocidade fixa para simplificar
        return calcularDistanciaEntreDuasEntregas(e1, e2) / 10; // Velocidade fictícia
    }
}


/*						Temos aqui, um fóssil do q um dia foi um código dinamico							*/

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
