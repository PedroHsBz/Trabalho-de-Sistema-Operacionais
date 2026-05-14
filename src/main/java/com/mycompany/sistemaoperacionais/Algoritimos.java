package com.mycompany.sistemaoperacionais;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pedro, carlos, lucas
 */
class Processo {

    String nome;          // Identificador do processo (ex: A)
    int tempoChegada;     // Instante em que o processo entra na fila de prontos
    int tempoCPU;         // Tempo total que o processo precisa ficar na CPU para finalizar
    int tempoRestante;    // Tempo restante que falta para terminar o processo por completo. 
    int prioridade;

    public Processo(String nome, int tempoChegada, int tempoCPU, int prioridade) {
        this.nome = nome;
        this.tempoChegada = tempoChegada;
        this.tempoCPU = tempoCPU;
        this.tempoRestante = tempoCPU;
        this.prioridade = prioridade;
    }
}

public class Algoritimos {

    public static void executarSRT(List<Processo> processos) {
        int tempoAtual = 0;
        int processosConcluidos = 0;
        int totalProcessos = processos.size();

        List<String> historicoExecucao = new ArrayList<>(); // Vai guardar quem ocupou a CPU a cada ciclo

        while (processosConcluidos < totalProcessos) {
            List<Processo> filaProcesso = new ArrayList<>();
            Processo atual = null;

            // 1. Filtrar (Quais processos está na fila?)
            for (Processo p : processos) {
                if (p.tempoChegada <= tempoAtual && p.tempoRestante > 0) {
                    filaProcesso.add(p);
                }
            }

            // 2. Escolher o menor tempo restante
            if (!filaProcesso.isEmpty()) {
                atual = filaProcesso.get(0);

                for (Processo p : filaProcesso) {
                    if (p.tempoRestante < atual.tempoRestante) {
                        atual = p;
                    }
                }

                // 3. EXECUÇÃO:
                atual.tempoRestante--; // Desconta 1 unidade de tempo restante do processo
                historicoExecucao.add(atual.nome); // Registra no histórico que ele rodou neste ciclo

                // 4. VERIFICAÇÃO DE FIM DE PROCESSO:
                if (atual.tempoRestante == 0) {
                    processosConcluidos++; // Se tempo restante e 0, o processo acabou! Aumenta o contador.
                }
            } else {
                // Se a fila de processos estiver vazia. O sistema fica ocioso.
                historicoExecucao.add("-"); // "-" vai representar ociosidade na timeline final
            }


            // Mostra que acabou de acontecer neste ciclo exato
            System.out.println("Tempo: " + tempoAtual);
            if (atual != null) {
                System.out.println("  [CPU] -> " + atual.nome + " (Restante após este ciclo: " + atual.tempoRestante + ")");
                System.out.print("  [Processos em espera] -> ");
                boolean temProcesso = false;

                // Exibe quem ficou esperando na fila enquanto outro rodava
                for (Processo p : filaProcesso) {
                    if (p != atual) {
                        System.out.print(p.nome + "(Restante: " + p.tempoRestante + ")  ");
                        temProcesso = true;
                    }
                }

                if (!temProcesso) {
                    System.out.print("Nenhum outro processo na fila.");
                }

                System.out.println("\n  ---------------------------------------");
            } else {
                System.out.println("  [CPU] -> Ocioso");
                System.out.println("  ---------------------------------------");
            }
            tempoAtual++;
        }

        System.out.println("\nTodos os processos foram concluídos no tempo " + tempoAtual + "!");
        imprimirVetorFinal(historicoExecucao);
    }

    public static void executarPrioridades(List<Processo> processos) {
        int tempoAtual = 0;
        int processosConcluidos = 0;
        int n = processos.size();

        List<String> historicoExecucao = new ArrayList<>();

        // Começa na fila 5, com 5 de "energia" (quantum) proporcional à prioridade
        int filaAtual = 5;
        int ticksRestantes = 5;

        while (processosConcluidos < n) {
            List<Processo> disponiveis = new ArrayList<>();
            for (Processo p : processos) {
                if (p.tempoChegada <= tempoAtual && p.tempoRestante > 0) {
                    disponiveis.add(p);
                }
            }

            // Se o sistema inteiro estiver vazio, fica ocioso
            if (disponiveis.isEmpty()) {
                historicoExecucao.add("-");
                System.out.println("Tempo: " + tempoAtual);
                System.out.println("  [CPU] -> Ocioso");
                System.out.println("  ---------------------------------------");
                tempoAtual++;

                // Reinicia o ciclo para a maior prioridade se o sistema esvaziou
                filaAtual = 5;
                ticksRestantes = 5;
                continue;
            }

            boolean executouNesteCiclo = false;
            int verificacoes = 0;
            Processo atual = null;
            List<Processo> candidatosFilaAtual = new ArrayList<>();

            // Procura a próxima fila válida que tem processo para rodar
            while (verificacoes < 5) {
                // Se a fila atual gastou sua cota, desce pra próxima
                if (ticksRestantes == 0) {
                    filaAtual--;
                    if (filaAtual < 1) {
                        filaAtual = 5; // Volta pra 5 depois da 1
                    }
                    ticksRestantes = filaAtual; // Reseta as "moedas" da fila
                }

                candidatosFilaAtual.clear();
                for (Processo p : disponiveis) {
                    if (p.prioridade == filaAtual) {
                        candidatosFilaAtual.add(p);
                    }
                }

                if (!candidatosFilaAtual.isEmpty()) {
                    // Executa SRT dentro dos candidatos da fila atual
                    atual = candidatosFilaAtual.get(0);
                    for (Processo p : candidatosFilaAtual) {
                        if (p.tempoRestante < atual.tempoRestante) {
                            atual = p;
                        }
                    }

                    atual.tempoRestante--;
                    ticksRestantes--;
                    historicoExecucao.add(atual.nome);

                    if (atual.tempoRestante == 0) {
                        processosConcluidos++;
                    }
                    executouNesteCiclo = true;
                    break;
                } else {
                    // Fila vazia, zera o quantum para pular pra próxima na próxima iteração do while
                    ticksRestantes = 0;
                    verificacoes++;
                }
            }

            if (executouNesteCiclo) {
                System.out.println("Tempo: " + tempoAtual);
                System.out.println("  [Fila " + filaAtual + "] [CPU] -> " + atual.nome + " (Restante: " + atual.tempoRestante + ")");
                System.out.print("  [Espera na fila " + filaAtual + "] -> ");
                boolean temPronto = false;

                for (Processo p : candidatosFilaAtual) {
                    if (p != atual) {
                        System.out.print(p.nome + "(Rest: " + p.tempoRestante + ")  ");
                        temPronto = true;
                    }
                }

                if (!temPronto) {
                    System.out.print("Nenhum outro na fila atual.");
                }
                System.out.println("\n  ---------------------------------------");
            }

            tempoAtual++;
        }

        System.out.println("\nTodos os processos foram concluídos no tempo " + tempoAtual + "!");
        imprimirVetorFinal(historicoExecucao);
    }

    public static void imprimirVetorFinal(List<String> historico) {
        System.out.println("\n=== VETOR FINAL DE EXECUCAO (TIMELINE) ===");

        // Imprime a linha superior mostrando os instantes de tempo (0 | 1 | 2 | 3...)
        System.out.print("Tempo | ");
        for (int i = 0; i < historico.size(); i++) {
            System.out.printf("%2d | ", i);
        }
        System.out.println();

        // Imprime a linha inferior mostrando qual processo rodou naquele tempo (A | B | A | -...)
        System.out.print("Proc. | ");
        for (String proc : historico) {
            System.out.printf("%2s | ", proc);
        }
        System.out.println("\n==========================================");
    }
}
