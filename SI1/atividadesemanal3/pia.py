'''
Prática 3 

Felipe Ferrer Sorrilha
João Antônio Sitta Martins
Millena Sartori de Oliveira
'''

INICIAL = [
    [2, 8, 3],
    [1, 6, 4],
    [7, 0, 5]
]

OBJETIVO = [
    [1, 2, 3],
    [8, 0, 4],
    [7, 6, 5]
]

def copiar_matriz(matriz):
    nova = []
    for linha in matriz:
        nova_linha = []
        for valor in linha:
            nova_linha.append(valor)
        nova.append(nova_linha)
    return nova

def imprimir_estado(estado):
    for i in range(3):
        for j in range(3):
            if estado[i][j] == 0:
                print(" ", end=" ")
            else:
                print(estado[i][j], end=" ")
        print()
    print()

def encontrar_zero(estado):
    for i in range(3):
        for j in range(3):
            if estado[i][j] == 0:
                return i, j

def contar_acertos(estado):
    acertos = 0
    for i in range(3):
        for j in range(3):
            if estado[i][j] == OBJETIVO[i][j]:
                acertos = acertos + 1
    return acertos

def pegar_melhor_filho(lista_filhos):
    if not lista_filhos: # se a lista estiver vazia
        return None
    
    # começa com o primeiro filho da lista
    melhor = lista_filhos[0]
    
    # compara com cada um dos outros
    for filho in lista_filhos:
        if filho['acertos'] > melhor['acertos']:
            melhor = filho
    
    return melhor

# gera todos os filhos possíveis
def gerar_todos_filhos(estado, ultimo_mov_i, ultimo_mov_j):
    i, j = encontrar_zero(estado)
    
    movimentos = [
        (-1, 0, "cima"),
        (1, 0, "baixo"),
        (0, -1, "esquerda"),
        (0, 1, "direita")
    ]
    
    todos_filhos = []
    
    for di, dj, direcao in movimentos:
        ni = i + di
        nj = j + dj
        
        # verifica se não sai do tabuleiro
        if ni >= 0 and ni < 3 and nj >= 0 and nj < 3:
            # vê se não volta pra onde tava antes
            if (ni == ultimo_mov_i and nj == ultimo_mov_j):
                continue
            
            # novo estado
            novo = copiar_matriz(estado)
            novo[i][j] = novo[ni][nj]
            novo[ni][nj] = 0
            
            # guarda o filho com suas informações
            todos_filhos.append({
                'estado': novo,
                'acertos': contar_acertos(novo),
                'pos_zero_i': i,
                'pos_zero_j': j,
                'direcao': direcao
            })
    
    return todos_filhos


def MonterLaMontagne(inicial, objetivo):
    atual = copiar_matriz(inicial)
    ultimo_mov_i = -1
    ultimo_mov_j = -1
    iteracoes = 0
    
    while True:
        iteracoes += 1
        acertos_atual = contar_acertos(atual)
        
        print("=" * 40)
        print("Iteração:", iteracoes)
        print("Estado atual (acertos:", acertos_atual, "/9):")
        imprimir_estado(atual)
        
        if atual == objetivo:
            print("Solução encontrada em", iteracoes, "passos")
            return True
        
        filhos = gerar_todos_filhos(atual, ultimo_mov_i, ultimo_mov_j)
        
        # separa os filhos em filhos q podem melhorar e q podem piorar
        filhos_que_melhoram = []
        filhos_que_pioram = []
        
        for filho in filhos:
            if filho['acertos'] > acertos_atual:
                filhos_que_melhoram.append(filho)
            elif filho['acertos'] < acertos_atual:
                filhos_que_pioram.append(filho)
        
        melhor_filho = None
        
        if filhos_que_melhoram:
            # pega o que melhora mais (maior acertos)
            melhor_filho = pegar_melhor_filho(filhos_que_melhoram)
            print(f"Indo para {melhor_filho['direcao']} (acertos: {melhor_filho['acertos']})")

        elif filhos_que_pioram:
            # pega o que piora menos (maior acertos entre os que pioram)
            melhor_filho = pegar_melhor_filho(filhos_que_pioram)
            print(f"Indo para {melhor_filho['direcao']}")
        
        # atualiza o estado
        ultimo_mov_i = melhor_filho['pos_zero_i']
        ultimo_mov_j = melhor_filho['pos_zero_j']
        atual = melhor_filho['estado']


# ================= MAIN =================
if __name__ == "__main__":
    print("Estado inicial:")
    imprimir_estado(INICIAL)
    
    print("Estado objetivo:")
    imprimir_estado(OBJETIVO)

    MonterLaMontagne(INICIAL, OBJETIVO)