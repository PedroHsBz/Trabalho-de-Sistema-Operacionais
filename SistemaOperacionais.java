package com.mycompany.sistemaoperacionais;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Processo {
    String nome;          // Identificador do processo (ex: A)
    int tempoChegada;     // Instante em que o processo entra na fila de prontos
    int tempoCPU;         // Tempo total que o processo precisa ficar na CPU para finalizar
    int tempoRestante;    // Tempo restante que falta para terminar o processo por completo. 
    int prioridade;  
    int prioridadeRestante; 

    // Construtor para os processo com os dados informados pelo usuário
    public Processo(String nome, int tempoChegada, int tempoCPU) {
        this.nome = nome;
        this.tempoChegada = tempoChegada;
        this.tempoCPU = tempoCPU;
        this.tempoRestante = tempoCPU;
        this.prioridade = prioridade;
        this.prioridadeRestante = prioridadeRestante;
    }
}

public class SistemaOperacionais {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SIMULADOR DE ESCALONAMENTO ===");
        System.out.println("1 - SRT (Shortest Remaining Time)");
        System.out.println("2 - Sair");
        System.out.print("Escolha: ");
        int escolha = scanner.nextInt();

        System.out.print("Quantos processos deseja adicionar? ");
        int numProcessos = scanner.nextInt();

        List<Processo> processos = new ArrayList<>();

        for (int i = 0; i < numProcessos; i++) {
            System.out.println("\n--- Processo " + (i + 1) + " ---");
            System.out.print("Nome do processo (Ex: A, B, P1): ");
            String nome = scanner.next();
            System.out.print("Tempo de chegada: ");
            int chegada = scanner.nextInt();
            System.out.print("Tempo de uso da CPU: ");
            int cpu = scanner.nextInt();
            int prioridade;
            processos.add(new Processo(nome, chegada, cpu));
        }
        System.out.println("\n=========================================");
        System.out.println("INICIANDO SIMULAÇÃO...");
        System.out.println("=========================================\n");
        
        switch (escolha) {
            case 1:
                executarSRT(processos);
                break;
            case 2:
                System.out.println("Encerrando programa!");
                return;
            default:
            System.out.println("Opção inválida ou encerramento solicitado.");
        }

        scanner.close();
    }

    public static void executarSRT(List<Processo> processos) {
        int tempoAtual = 0;             // Representa o tempo de cpu (0..5)
        int processosConcluidos = 0;    // Contador para sabermos quando a simulação deve acabar
        int n = processos.size();       // Total de processos cadastrados
        
        List<String> historicoExecucao = new ArrayList<>(); // Vai guardar quem ocupou a CPU a cada ciclo (segundo)

        while (processosConcluidos < n) {
            List<Processo> processo = new ArrayList<>(); // Fila de Prontos (quem já chegou e pode rodar)
            Processo atual = null;                       // Ponteiro para o processo que vai ganhar a CPU agora

            // 1. FILTRAGEM (Quem está na fila?)
            // Varre a lista original para achar quem já chegou (<= tempoAtual) e ainda não terminou (> 0)
            for (Processo p : processos) {
                if (p.tempoChegada <= tempoAtual && p.tempoRestante > 0) {
                    processo.add(p); 
                }
            }

            // 2. Escolher o menor tempo restante
            if (!processo.isEmpty()) {
                atual = processo.get(0); 
                
                // Compara o candidato atual com os outros da fila para achar o verdadeiro "menor tempo"
                for (Processo p : processo) {
                    if (p.tempoRestante < atual.tempoRestante) {
                        atual = p; 
                    }
                }

                // 3. EXECUÇÃO:
                atual.tempoRestante--; // Desconta 1 unidade de tempo da tarefa do processo
                historicoExecucao.add(atual.nome); // Registra no histórico que ele rodou neste segundo

                // 4. VERIFICAÇÃO DE FIM DE PROCESSO:
                if (atual.tempoRestante == 0) {
                    processosConcluidos++; // Se zerou, o processo acabou! Aumenta o contador.
                }
            } else {
                // Se a fila de prontos estiver vazia, ninguém quer a CPU. O sistema fica ocioso.
                historicoExecucao.add("-"); // "-" vai representar ociosidade na timeline final
            }

            // Mostra que acabou de acontecer neste ciclo exato
            imprimirTimeline(tempoAtual, processo, atual);
            tempoAtual++; 
        }

        System.out.println("\nTodos os processos foram concluídos no tempo " + tempoAtual + "!");
        imprimirVetorFinal(historicoExecucao);
    }

    // Imprime o status momentâneo (o que acontece em 1 ciclo de tempo)
    public static void imprimirTimeline(int tempoAtual, List<Processo> processo, Processo atual) {
        System.out.println("Tempo: " + tempoAtual);
        if (atual != null) {
            System.out.println("  [CPU] -> " + atual.nome + " (Restante após este ciclo: " + atual.tempoRestante + ")");
            System.out.print("  [Processos em espera] -> ");
            boolean temPronto = false;
            
            // Exibe quem ficou na vontade, esperando na fila enquanto outro rodava
            for (Processo p : processo) {
                if (p != atual) {
                    System.out.print(p.nome + "(Restante: " + p.tempoRestante + ")  ");
                    temPronto = true;
                }
            }
            
            if (!temPronto) {
                System.out.print("Nenhum outro processo na fila.");
            }
            
            System.out.println("\n  ---------------------------------------");
        }
    }

    // Desenha uma linha do tempo horizontal (como um Gráfico de Gantt simplificado) no final
    public static void imprimirVetorFinal(List<String> historico) {
        System.out.println("\n=== VETOR FINAL DE EXECUÇÃO (TIMELINE) ===");

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