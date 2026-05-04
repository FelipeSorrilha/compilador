##############################
### CODIGO FODA JOAZ WOW ###
##############################

#Estado inicial do problema 
INICIAL = [
    [2, 8, 3],
    [1, 6, 4],
    [7, 0, 5]
]

#Estado que eu quero chegar
OBJETIVO = [
    [1, 2, 3],
    [8, 0, 4],
    [7, 6, 5]
]


#Essa função copia a matriz pra não dar problema de alterar o original
#(se não copiar, mexer em um estado mexe em todos)
def copiar_matriz(matriz):
    nova = []
    for linha in matriz:  #pega cada linha da matriz
        nova_linha = []
        for valor in linha:  #ega cada número da linha
            nova_linha.append(valor)
        nova.append(nova_linha)
    return nova


#Só imprime o tabuleiro bonitinho
def imprimir_estado(estado):
    for i in range(3):
        for j in range(3):
            if estado[i][j] == 0:
                print(" ", end=" ")  #0 é o espaço vazio
            else:
                print(estado[i][j], end=" ")
        print()
    print()


#Procura onde o 0 
def encontrar_zero(estado):
    for i in range(3):
        for j in range(3):
            if estado[i][j] == 0:
                return i, j  #retorna linha e coluna


#Aqui é onde gera os próximos estados (movimentos possíveis)
def gerar_filhos(estado):
    i, j = encontrar_zero(estado)  #pega posição do 0
    filhos = []

    #movimentos possíveis
    movimentos = [
            (0, -1),  #esquerda
            (-1, 0),  #cima
            (0, 1),  #direita
            (1, 0)  #baixo

        ]

    for di, dj in movimentos:
        ni = i + di
        nj = j + dj

        #verifica se o movimento não sai da matriz
        if ni >= 0 and ni < 3 and nj >= 0 and nj < 3:
            novo = copiar_matriz(estado)  # cria um novo estado
            #troca o 0 com o número da nova posição
            novo[i][j] = novo[ni][nj]
            novo[ni][nj] = 0
            filhos.append(novo)  #adiciona na lista de filhos

    return filhos


#Verifica se um estado já está na lista (pra não repetir)
def esta_na_lista(lista, estado):
    for e in lista:
        if e == estado:
            return True
    return False


#BFS = busca em largura /usa fila
def bfs(inicial, objetivo):
    abertos = [inicial]   #primeiro que entra  primeiro que sai
    fechados = []         #já visitados
    iteracoes = 0

    #enquanto ainda tiver estados pra explorar
    while len(abertos) > 0:
        atual = abertos.pop(0)   #pega o primeiro da fila
        iteracoes += 1

        print("=================================")
        print("Iteração:", iteracoes)
        print("Estado atual:")
        imprimir_estado(atual)

        #verifica se chegou no objetivo
        if atual == objetivo:
            print("BFS encontrou a solução!")
            print("Iterações:", iteracoes)
            return True

        fechados.append(atual)  #marca como visitado

        filhos = gerar_filhos(atual)  #gera próximos estados

        print("Filhos gerados:")
        for filho in filhos:
            imprimir_estado(filho)

        #adiciona filhos na fila (se não estiver repetido)
        for filho in filhos:
            if not esta_na_lista(abertos, filho) and not esta_na_lista(fechados, filho):
                abertos.append(filho)

        print("Tamanho de ABERTOS:", len(abertos))
        print("Tamanho de FECHADOS:", len(fechados))

    print("BFS não encontrou solução.")
    print("Iterações:", iteracoes)
    return False


def dfs(inicial, objetivo, limite = 5):
    abertos = [(inicial, 0)]  #estado e profundidade
    fechados = []
    iteracoes = 0

    while len(abertos) > 0:
        atual, prof = abertos.pop() 
        iteracoes += 1

        print("=================================")
        print("Iteração:", iteracoes)
        print("Estado atual:")
        imprimir_estado(atual)

        #verifica se chegou no objetivo
        if atual == objetivo:
            print("DFS encontrou a solução!")
            print("Iterações:", iteracoes)
            return True

        fechados.append(atual)  #marca como visitado

        if prof < limite:
            filhos = gerar_filhos(atual) #gera próximos estados

            print("Filhos gerados:")
            for filho in filhos:
                imprimir_estado(filho)

            #adiciona filhos na pilha (se não estiver repetido)
            for filho in filhos:
                if not esta_na_lista(abertos, (filho, prof+1)) and not esta_na_lista(fechados, filho):
                    abertos.append((filho, prof + 1))

        print("Tamanho de ABERTOS:", len(abertos))
        print("Tamanho de FECHADOS:", len(fechados))

    print("DFS não encontrou solução.")
    print("Iterações:", iteracoes)
    return False

# ================= MAIN POGGERS =================

if __name__ == "__main__":
    print("Estado inicial:")
    imprimir_estado(INICIAL)

    print("Estado objetivo:")
    imprimir_estado(OBJETIVO)

    print("=== BFS ===")
    bfs(INICIAL, OBJETIVO)
    
    print("=== DFS ===")
    dfs(INICIAL, OBJETIVO)