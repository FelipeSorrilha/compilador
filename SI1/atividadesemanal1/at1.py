'''
Alunos:
Felipe Ferrer Sorrilha
João Antônio Sitta Martins
Millena Sartori de Oliveira
'''

def busca_retrocesso(grafo, inicial, quantidadevertices):
    LE = []      
    LNE = [inicial]         
    contador = 0
    
    print(f"Inicial: {inicial}")
    print(f"LE: {LE}")
    print(f"LNE: {LNE}")
    
    while LNE != []: # a LNE fica vazia quando vc visita todos os filhos
        
        atual = LNE.pop()
        LE.append(atual)
        contador += 1
        
        print(f"Atual: {atual}")
        print(f"LE: {LE}")
        print(f"LNE: {LNE}")
        
        # nesse caso, tem 5 vertices, quando o vetor LE for igual a 5, a gnt já vai ter visitado todos os vertices
        # dai, só voltar pro inicial
        if len(LE) == quantidadedevertices:  
            LE.append(inicial)
            print(f"Caminho encontrado: {LE}")
            return LE
        
        filhos = grafo[atual]
        
        for filho in filhos:
            if filho not in LE:
                if filho not in LNE:
                    LNE.append(filho)
                    print(f"  Adicionou {filho} no LNE")
        
        print(f"LNE atual: {LNE}")
    
    return None


# grafo do exercício
grafo = {
    'A': ['B', 'C', 'D', 'E'],
    'B': ['A', 'C', 'D', 'E'],
    'C': ['A', 'B', 'D', 'E'],
    'D': ['A', 'B', 'C', 'E'],
    'E': ['A', 'B', 'C', 'D']
}

quantidadedevertices = 5

busca_retrocesso(grafo, 'A', quantidadedevertices)