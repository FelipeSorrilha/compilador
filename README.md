# Compilador GYH
 
Produção de um compilador para a gramática GYH.
 
---
 
## Estrutura do projeto
 
A pasta `analisadorsintatico` contém o **Analisador Sintático LL(1)** já integrado com o **Analisador Léxico**.
 
---
 
## Como usar
 
1. Coloque o arquivo `.gyh` na **raiz do projeto**, fora da pasta `src/`.
2. No arquivo principal `ProjetoCompiladores.java`, altere o nome do arquivo `.gyh` na **linha 18**:
```java
private static final String ARQUIVO_PADRAO = "seu_arquivo.gyh";
```
 
3. Compile e execute o projeto normalmente.
