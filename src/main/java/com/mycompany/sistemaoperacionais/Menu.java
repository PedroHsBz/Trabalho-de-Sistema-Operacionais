package com.mycompany.sistemaoperacionais;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//'Entrada'
public class Menu {

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        //enquanto for verdadeira irá executar
        while (true) {
            System.out.println("\n=== SIMULADOR DE ESCALONAMENTO ===");
            System.out.println("1 - SRT Classico (Shortest Remaining Time)");
            System.out.println("2 - Escalonamento por Prioridades");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int escolha = scanner.nextInt();

            //se o usuário digitar 0, o programa encerra
            if (escolha == 0) {
                System.out.println("Encerrando programa!");
                break;
            }

            //com base no que o Usuário digitar, irá executar a opção desejada
            switch (escolha) {

                //se for o 1: SRT Classico
                case 1 -> {
                    System.out.print("Quantos processos deseja adicionar? ");
                    //verifica se o número digitado é inteiro
                    while (!scanner.hasNextInt()) {
                        System.out.println("ERRO! Digite um numero inteiro!");
                        scanner.next();
                    }
                    int numProcessos = scanner.nextInt();

                    //criação de um array Processos, composto por processo
                    //ex: Processos é a fila, processo é a pessoa na fila
                    //new ArrayList<>(): cria a lista na memória
                    List<Processo> processos = new ArrayList<>();

                    //recebe os dados do processo com base na quantidade de processos que o usuario quer
                    for (int i = 0; i < numProcessos; i++) {
                        System.out.println("\n--- Elemento " + (i + 1) + " ---");

                        System.out.print("Nome: ");
                        String nome = scanner.next();

                        //verifica se o número digitado da chegada é >= 0 e é inteiro
                        int chegada;
                        while (true) {
                            System.out.print("Chegada: ");
                            if (scanner.hasNextInt()) {
                                chegada = scanner.nextInt();
                                if (chegada >= 0) {
                                    break;
                                }
                            } else {
                                scanner.next();
                            }
                            System.out.println("Invalido, digite um numero maior ou igual a 0");
                        }

                        int cpu;
                        while (true) {
                            System.out.print("Tempo CPU: ");
                            if (scanner.hasNextInt()) {
                                cpu = scanner.nextInt();
                                if (cpu > 0) {
                                    break;
                                }
                                System.out.println("Erro, o tempo de cpu deve ser pelo menos 1 uai");
                            } else {
                                System.out.println("Digite numeros, Apenas");
                                scanner.next();
                            }
                        }

                        //cria um novo 'processo' no ArrayList 'Processos'
                        processos.add(new Processo(nome, chegada, cpu, 0));
                    }

                    //Realiza a Simulação do SRT e chama a função que a executa
                    System.out.println("\n=========================================");
                    System.out.println("    INICIANDO SIMULACAO SRT...");
                    System.out.println("=========================================\n");
                    Algoritimos.executarSRT(processos);
                }

                //se o usuario digitar o 2: executará Escalonamento por Prioridades
                case 2 -> {
                    //recebe a quantidade de processos que o usuário deseja adicionar
                    System.out.print("Quantos processos deseja adicionar? ");
                    //verificação se o número digitado é do tipo inteiro
                    while (!scanner.hasNextInt()) {
                        System.out.println("ERRO! Digite um numero inteiro!");
                        scanner.next();
                    }
                    int numProcessos = scanner.nextInt();

                    //criação de um array Processos, composto por processo
                    //ex: Processos é a fila, processo é a pessoa na fila
                    //new ArrayList<>(): cria a lista na memória
                    List<Processo> processos = new ArrayList<>();

                    System.out.println("OBS: As prioridades são estaticas ele não iram alterar ao decorrer do escalonamento.\n"
                            + "A prioridade de 5 possui um quantum de 5 ciclos de executação.\n"
                            + "A prioridade de 4 possui um quantum de 4 ciclos de executação.\n"
                            + "A prioridade de 3 possui um quantum de 3 ciclos de executação.\n"
                            + "A prioridade de 2 possui um quantum de 2 ciclos de executação.\n"
                            + "A prioridade de 1 possui um quantum de 1 ciclos de executação.\n");
                   

                    for (int i = 0; i < numProcessos; i++) {
                        System.out.println("\n--- Elemento " + (i + 1) + " ---");
                        System.out.print("Nome: ");
                        String nome = scanner.next();

                        int chegada;
                        while (true) {
                            System.out.print("Chegada: ");
                            if (scanner.hasNextInt()) {
                                chegada = scanner.nextInt();
                                if (chegada >= 0) {
                                    break;
                                }
                            } else {
                                scanner.next();
                            }
                            System.out.println("Invalido, digite um numero maior ou igual a 0");
                        }

                        int cpu;
                        while (true) {
                            System.out.print("Tempo CPU: ");
                            if (scanner.hasNextInt()) {
                                cpu = scanner.nextInt();
                                if (cpu > 0) {
                                    break;
                                }
                                System.out.println("Erro, o tempo de cpu deve ser pelo menos 1 uai");
                            } else {
                                System.out.println("Digite numeros, Apenas");
                                scanner.next();
                            }
                        }

                        int prioridade;
                        while (true) {
                            System.out.print("Prioridade (1 a 5): ");

                            if (scanner.hasNextInt()) {
                                prioridade = scanner.nextInt();
                                if (prioridade >= 1 && prioridade <= 5) {
                                    break;

                                } else {
                                    System.out.println("Erro, a prioridade deve ser entre 1 a 5");

                                }

                            } else {
                                System.out.println("ERRO: digite numeros apenas");
                                scanner.next();
                            }
                        }

                        //cria um novo 'processo' no ArrayList 'Processos'
                        processos.add(new Processo(nome, chegada, cpu, prioridade));
                    }

                    //Realiza a Simulação Prioridades e chama a função que a executa
                    System.out.println("\n=========================================");
                    System.out.println("INICIANDO SIMULAÇÃO PRIORIDADES...");
                    System.out.println("=========================================\n");
                    Algoritimos.executarPrioridades(processos);
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}
