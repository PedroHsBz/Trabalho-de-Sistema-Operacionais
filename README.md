# Trabalho de Sistema Operacionais
# Simulador de Escalonamento de Processos ⏱️

**Disciplina:** Sistemas Operacionais 

**Instituição:** Instituto Federal Triângulo Mineiro - Campus Patrocínio 

**Professor:** Gilberto Oliveira 

---

## 👥 Membros do Grupo

* Pedro Henrique da Silva Bazilio 
* Carlos Eduardo Ferreira Sales
* Lucas Daniel Cunha

Projeto realizado em conjunto e optamos por enviar o projeto ja pronto

---

## 📌 Sobre o Projeto
Este projeto é um trabalho avaliativo focado em aplicar algoritmos vistos em sala através de uma simulação prática. O programa foi desenvolvido em **Java** (escolha livre do grupo) e é executado via console.

### Algoritmos
* 1️⃣ **SRT (Shortest Remaining Time):** 
* 2️⃣ **Escalonamento por Prioridades:**

---

## A Estrutura Base: O Processo 🏗️

Para que o simulador funcione, a classe `Processo` armazena as características vitais de cada tarefa.

**O Método Construtor:**
O construtor inicializa o processo com os dados fornecidos. Notavelmente, o `tempoRestante` é igualado ao `tempoCPU` no início, sendo decrementado conforme o processo é executado.

```// Localizado em Algoritimos.java

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

```
---
## Intaface de Menu 

O que é e como funciona?

Ao término de toda a simulação (quando o laço de execução acaba), o sistema precisa fornecer um panorama geral de tudo o que aconteceu.
O método recebe a lista historico (que foi preenchida a cada segundo com o nome do processo executado ou - para ócio) e formata esses dados como uma tabela de dupla entrada, simulando um Gráfico de Gantt no próprio terminal. A linha superior marca o instante de tempo, e a linha inferior mostra exatamente quem estava ocupando o processador naquele segundo.

```Java

/// Localizado em Menu.java
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
                        while(true){
                            System.out.print("Tempo CPU: ");
                            if(scanner.hasNextInt()){
                                cpu = scanner.nextInt();
                                if(cpu > 0) break;
                                System.out.println("Erro, o tempo de cpu deve ser pelo menos 1 uai");
                            }else{
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
                            + "A prioridade de 1 possui um quantum de 1 ciclos de executação.");
                    
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
                        while(true){
                            System.out.print("Tempo CPU: ");
                            if(scanner.hasNextInt()){
                                cpu = scanner.nextInt();
                                if(cpu > 0) break;
                                System.out.println("Erro, o tempo de cpu deve ser pelo menos 1 uai");
                            }else{
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
```
---

## Algoritmo SRT (Shortest Remaining Time) ⏱️
   
O que é e como funciona?

O SRT é a versão preemptiva do SJF. A cada unidade de tempo, o sistema reavalia a fila e concede a CPU ao processo que possui o menor tempo restante de execução. Se um novo processo chegar com um tempo menor que o atual, ocorre a preempção.

```// Localizado em Algoritimos.java
\\ Implementação do algoritmo SRT

    public static void executarSRT(List<Processo> processos) {
        int tempoAtual = 0;
        int processosConcluidos = 0;
        int n = processos.size();       // Total de processos 

        List<String> historicoExecucao = new ArrayList<>(); // Vai guardar quem ocupou a CPU a cada ciclo 

        while (processosConcluidos < n) {
            List<Processo> processo = new ArrayList<>(); // Fila de processo (quem já chegou e pode rodar)
            Processo atual = null;                       // Ponteiro para o processo que vai ganhar a CPU agora

            // 1. FILTRAGEM (Quais processos está na fila?)
            for (Processo p : processos) {
                if (p.tempoChegada <= tempoAtual && p.tempoRestante > 0) {
                    processo.add(p);
                }
            }

            // 2. Escolher o menor tempo restante
            if (!processo.isEmpty()) {
                atual = processo.get(0);

                // Compara o candidato atual com os outros da fila para achar o processo com "menor tempo restante"
                for (Processo p : processo) {
                    if (p.tempoRestante < atual.tempoRestante) {
                        atual = p;
                    }
                }

                // 3. EXECUÇÃO:
                atual.tempoRestante--; // Desconta 1 unidade de tempo restante do processo
                historicoExecucao.add(atual.nome); // Registra no histórico que ele rodou neste segundo

                // 4. VERIFICAÇÃO DE FIM DE PROCESSO:
                if (atual.tempoRestante == 0) {
                    processosConcluidos++; // Se tempo restante e 0, o processo acabou! Aumenta o contador.
                }
            } else {
                // Se a fila de processos estiver vazia. O sistema fica ocioso.
                historicoExecucao.add("-"); // "-" vai representar ociosidade na timeline final
            }

            // Mostra que acabou de acontecer neste ciclo exato
            imprimirTimeline(tempoAtual, processo, atual);
            tempoAtual++;
        }

        System.out.println("\nTodos os processos foram concluídos no tempo " + tempoAtual + "!");
        imprimirVetorFinal(historicoExecucao);
    }

```
---

## Algoritmo de Escalonamento de Prioridade

O que é e como funciona?

O escalonamento por prioridade é um algoritmo que escalona processos com base em suas prioridades estáticas (1 a 5). Ele executa cada prioridade conforme uma cota de ciclos de execução (quantum) específica."

```// Localizado em Algoritimos.java

\\ Implementação do algoritmo Escalonamento de Prioridade
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
                    if (filaAtual < 1) filaAtual = 5; // Volta pra 5 depois da 1
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

```
---

## Saída e Monitoramento Visual 📊

## Monitoramento Ciclo a Ciclo (imprimirTimeline) 👁️
O que é e como funciona?

Esse metodo e responsavel por exibir os detalhes do o que esta acontecendo em cada ciclo do processo, onde temos o tempo do ciclo, processo na CPU (junto o tempo restante) e os processes que estão esperando.

```Java
// Localizado em Algoritimos.java
// Implementação do monitoramento de ciclo
public static void imprimirTimeline(int tempoAtual, List<Processo> processo, Processo atual) {
    System.out.println("Tempo: " + tempoAtual);
    if (atual != null) {
        System.out.println("  [CPU] -> " + atual.nome + " (Restante após este ciclo: " + atual.tempoRestante + ")");
        System.out.print("  [Processos em espera] -> ");
        boolean temPronto = false;
        
        // Exibe quem ficou aguardando na fila enquanto outro rodava
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
    } else {
            System.out.println("  [CPU] -> Ocioso");
            System.out.println("  ---------------------------------------");
        }
}
```
---

## O Gráfico de Gantt em Texto (imprimirVetorFinal) 📈
O que é e como funciona?

Ao término de toda a simulação (quando o laço de execução acaba), o sistema precisa fornecer um panorama geral de tudo o que aconteceu.

O método recebe a lista historico (que foi preenchida a cada segundo com o nome do processo executado ou - para ócio) e formata esses dados como uma tabela de dupla entrada, simulando um Gráfico de Gantt no próprio terminal. A linha superior marca o instante de tempo, e a linha inferior mostra exatamente quem estava ocupando o processador naquele segundo.

```Java
/// Localizado em Algoritimos.java
// Implementação do vetor de execução final
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
```    
---


## 💻 Pré-requisitos
Para garantir o funcionamento no computador durante a entrevista, é necessário possuir:
* Java Development Kit (JDK) 8 ou superior.
* Terminal ou IDE (VSCode, IntelliJ, Eclipse, NetBeans) capaz de compilar e rodar o pacote `com.mycompany.sistemaoperacionais`.

---
