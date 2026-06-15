# ⏱️ Simulador de Escalonamento de Processos

**Disciplina:** Sistemas Operacionais  
**Instituição:** Instituto Federal Triângulo Mineiro - Campus Patrocínio  
**Professor:** Gilberto Oliveira  

### 👥 Membros do Grupo
* Pedro Henrique da Silva Bazilio
* Carlos Eduardo Ferreira Sales
* Lucas Daniel Cunha
> *Nota: Projeto realizado em conjunto e optamos por enviar o projeto já pronto.*

---

## 📌 Sobre o Projeto

Este projeto é um trabalho avaliativo focado em aplicar algoritmos de escalonamento vistos em sala através de uma simulação prática. O programa foi desenvolvido em **Java** e possui um menu interativo via console para executar as simulações passo a passo.

### ⚙️ Algoritmos Implementados
1️⃣ **SRT (Shortest Remaining Time):** Um algoritmo preemptivo que seleciona o processo com o menor tempo de CPU restante.
2️⃣ **Escalonamento por Prioridades:** Uma abordagem baseada em prioridades onde os processos são executados com base em prioridades estáticas (1 a 5), cada uma com uma cota de execução (quantum) específica.

---

## 🏗️ Arquitetura do Sistema

O simulador é dividido em três componentes principais: a estrutura de dados base (`Processo`), a interface do usuário (`Menu`) e a lógica principal (`Algoritimos`).

### 1. A Estrutura Base: `Processo`
A classe `Processo` armazena as características vitais de cada tarefa. Notavelmente, o `tempoRestante` é inicializado com o valor exato do `tempoCPU` e é decrementado conforme o processo é executado pelo processador.

```java
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

### 2. Interface do Usuário: `Menu`

A classe `Menu` lida com as entradas do usuário via console. Ela permite selecionar o algoritmo desejado, definir a quantidade de processos e inserir os atributos específicos de cada um (Nome, Chegada, Tempo CPU e Prioridade). Inclui tratamento de erros para garantir que todos os dados digitados sejam válidos.

```java
// Trecho de Menu.java mostrando a inicialização e chamada da simulação
List<Processo> processos = new ArrayList<>();
// ... (Laço de repetição para entrada de dados dos processos) ...
System.out.println("\n=========================================");
System.out.println("    INICIANDO SIMULACAO SRT...");
System.out.println("=========================================\n");
Algoritimos.executarSRT(processos);

```

### 3. Lógica Principal: `Algoritimos`

#### ⏳ SRT (Shortest Remaining Time)

O método `executarSRT` implementa um ciclo contínuo que se repete até que todos os processos sejam concluídos:
**Filtra (Processos Prontos) ➔ Escolhe (Menor Tempo Restante) ➔ Executa (1 ciclo) ➔ Avança o Relógio**

```java
// Trecho da lógica principal em executarSRT
if (!filaProcesso.isEmpty()) {
    atual = filaProcesso.get(0);
    for (Processo p : filaProcesso) {
        if (p.tempoRestante < atual.tempoRestante) {
            atual = p;
        }
    }
    atual.tempoRestante--; // Executa 1 ciclo de CPU
    historicoExecucao.add(atual.nome); 

    if (atual.tempoRestante == 0) {
        processosConcluidos++; 
    }
} else {
    historicoExecucao.add("-"); // CPU Ociosa
}

```

#### ⚡ Escalonamento por Prioridade

O método `executarPrioridades` escalona processos com base em prioridades estáticas (de 1 a 5). Cada nível tem um quantum de execução correspondente (ex: Prioridade 5 roda 5 ciclos, Prioridade 4 roda 4 ciclos). Se a fila atual esgotar sua cota ou ficar vazia, o sistema desce para a próxima prioridade.

```java
// Trecho da lógica principal em executarPrioridades
if (ticksRestantes == 0) {
    filaAtual--;
    if (filaAtual < 1) {
        filaAtual = 5; // Reinicia para a prioridade mais alta
    }
    ticksRestantes = filaAtual; // Reseta as "moedas" da fila
}

```

---

## 📊 Saída e Monitoramento Visual

O simulador fornece um feedback visual detalhado diretamente no terminal.

### 👁️ Monitoramento Ciclo a Ciclo (`imprimirTimeline`)

Durante a execução, o programa exibe o tempo atual, o processo que está ocupando a CPU e os processos que estão aguardando na fila.

```text
Tempo: 2
  [CPU] -> A (Restante após este ciclo: 3)
  [Processos em espera] -> B(Restante: 2)  
  ---------------------------------------

```

### 📈 Gráfico de Gantt em Texto (`imprimirVetorFinal`)

Ao término da simulação, o sistema fornece um panorama geral formatado como uma tabela de dupla entrada, mostrando exatamente qual processo ocupou o processador em cada segundo da linha do tempo.

```java
public static void imprimirVetorFinal(List<String> historico) {
    System.out.println("\n=== VETOR FINAL DE EXECUCAO (TIMELINE) ===");

    System.out.print("Tempo | ");
    for (int i = 0; i < historico.size(); i++) {
        System.out.printf("%2d | ", i);
    }
    System.out.println();

    System.out.print("Proc. | ");
    for (String proc : historico) {
        System.out.printf("%2s | ", proc);
    }
    System.out.println("\n==========================================");
}

```

```

```
