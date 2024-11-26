package com.fachter.backend.utils;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilsCaixeiro {

    private static final int CAPACIDADE_MAX = 12; // Capacidade máxima de mercadorias

    /**
     * Método para calcular a melhor rota utilizando programação dinâmica.
     *
     * @param todasEntregas Lista de todas as entregas disponíveis.
     * @return RotaDTO com as entregas selecionadas.
     */

    public RotaDTO calcularRota(List<EntregaDTO> todasEntregas) {
        int n = todasEntregas.size();// Criar uma matriz de distâncias entre todas as entregas
        int[][] distancias = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    distancias[i][j] = calcularDistanciaEntreDuasEntregas(todasEntregas.get(i), todasEntregas.get(j));
                }
            }
        }

        int[][] memo = new int[1 << n][n];// Matriz de memoização (armazena os resultados das sub-rotas)
        for (int[] row : memo) {
            java.util.Arrays.fill(row, -1);
        }

        List<EntregaDTO> melhorRota = new ArrayList<>();// Chamar a função recursiva do caixeiro viajante
        int distanciaMinima = pcv(0, 1, distancias, memo, melhorRota, todasEntregas);

        return new RotaDTO(melhorRota);// Retorna a rota final com a menor distância
    }

    /*Uma Função recursiva de Caixeiro viajante/pcv com memoização, explicação do que é memoização no final.*/
    private int pcv(int pos, int mask, int[][] distancias, int[][] memo, List<EntregaDTO> melhorRota, List<EntregaDTO> entregas) {
        if (mask == (1 << entregas.size()) - 1) {
            return distancias[pos][0];  // Retorna a distância para o ponto inicial
        }

        if (memo[mask][pos] != -1) {
            return memo[mask][pos];  // Retorna o valor memoizado
        }

        int distanciaMinima = Integer.MAX_VALUE;
        int proximaEntrega = -1;

        // Passa sobre todas as entregas para buscar a próxima entrega
        for (int i = 0; i < entregas.size(); i++) {
            if ((mask & (1 << i)) == 0) {  // Se a entrega não foi visitada
                int novaDistancia = distancias[pos][i] + pcv(i, mask | (1 << i), distancias, memo, melhorRota, entregas);
                if (novaDistancia < distanciaMinima) {
                    distanciaMinima = novaDistancia;
                    proximaEntrega = i;
                }
            }
        }

        // Armazena a resposta na tabela de memoização
        memo[mask][pos] = distanciaMinima;

        // Adiciona a entrega à rota no caso prioritario
        if (proximaEntrega != -1) {
            melhorRota.add(entregas.get(proximaEntrega));
        }

        return distanciaMinima; //retorna a prioridade
    }

    /* Um metodo auxiliar que calcula a distância entre duas entregas.*/
    private int calcularDistanciaEntreDuasEntregas(EntregaDTO e1, EntregaDTO e2) {
        return Math.abs(e1.getDistancia() - e2.getDistancia());  // Simulação de distância
    }

    /*Na computação, memoização ou MEMOIZAÇÃO é uma técnica de otimização usada principalmente para
    acelerar programas de computador, armazenando os resultados de chamadas de funções caras em funções
    puras e retornando o resultado armazenado em cache quando as mesmas entradas ocorrerem novamente.

    Resumindo ela armazena o valor em cache e as retorna caso seja solicitada novamente.
*/
}
