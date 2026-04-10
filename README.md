# Trabalho de Sistema Operacionais
# Simulador de Escalonamento de Processos ⏱️

[cite_start]**Disciplina:** Sistemas Operacionais [cite: 3, 4]  
[cite_start]**Instituição:** Instituto Federal Triângulo Mineiro - Campus Patrocínio [cite: 1, 2]  
[cite_start]**Professor:** Gilberto Oliveira 

---

## 📌 Sobre o Projeto
[cite_start]Este projeto é um trabalho avaliativo focado em aplicar algoritmos vistos em sala através de uma simulação prática[cite: 7, 9]. [cite_start]O programa foi desenvolvido em **Java** (escolha livre do grupo) [cite: 14] e é executado via console.

### Status de Implementação dos Algoritmos
* [cite_start]✅ **SRT (Shortest Remaining Time):** Implementado e funcional[cite: 26].
* [cite_start]⏳ **Escalonamento por Prioridades:** Pendente/Em desenvolvimento[cite: 27].

---

## ⚙️ Guia de Uso (Apoio para a Apresentação)

[cite_start]O programa foi construído para facilitar a avaliação via entrevista, permitindo a configuração de todo o cenário de simulação antes da execução[cite: 37, 40]. 

### 1. Entradas Manuais
[cite_start]O sistema solicitará a quantidade de processos e, em seguida, pedirá iterativamente os dados obrigatórios[cite: 20]. [cite_start]Caso o professor solicite um teste específico, como *"Entre com um P1 que chega no tempo 4 e possui 5 unidades de tempo"* [cite: 39][cite_start], os dados devem ser inseridos na seguinte ordem[cite: 21]:
1.  **Nome do processo:** `P1`
2.  **Tempo de chegada:** `4`
3.  **Tempo de uso da CPU:** `5`

### 2. Saída e Monitoramento (Timeline)
[cite_start]Assim que os processos são "setados" e o algoritmo iniciado[cite: 40], o sistema imprime o acompanhamento exato de cada unidade de tempo passada. [cite_start]O log do console exibirá detalhadamente[cite: 32]:
* **Tempo atual** da simulação.
* **Processo na CPU** e seu tempo de processamento restante.
* **Fila de Prontos:** Quais processos já chegaram, estão em espera e qual o tempo restante de cada um.

Ao término da simulação do SRT, o programa gera um **Vetor Final de Execução** (semelhante a um Gráfico de Gantt) que resume visualmente qual processo ocupou a CPU (ou se o sistema ficou ocioso) a cada instante de tempo.

---

## 💻 Pré-requisitos
[cite_start]Para garantir o funcionamento no computador durante a entrevista, é necessário possuir:
* Java Development Kit (JDK) 8 ou superior.
* Terminal ou IDE (VSCode, IntelliJ, Eclipse, NetBeans) capaz de compilar e rodar o pacote `com.mycompany.sistemaoperacionais`.

---

## 👥 Membros do Grupo
[cite_start]*(Preencha com o nome da equipe - organização de até 4 pessoas)* [cite: 8]
* [Seu Nome Aqui]
* [Nome do Integrante 2]
