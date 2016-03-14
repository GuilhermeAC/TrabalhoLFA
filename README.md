# Algoritmo CYK

O objetivo do trabalho prático é desenvolver um aplicativo que, usando o algoritmo CYK, verifique se uma GLC (Gramatica Livre de Contexto) na FNC (Forma Normal de Chomsky é capaz de derivar uma palavra p.
O aplicativo deve ser desenvolvido em C ANSI ou Java.
Deve receber como entrada (i) um arquivo texto com a GLC G na FNC e (ii) a palavra p que tentará ser derivada a partir de S.
Deve prover como saída um arquivo texto com a matriz triangular produzida pelo algoritmo CYK.


Requisitos obrigatórios:

• Chamada deve ser por linha de comando:

	> ./Main glc.txt aaabbb saida.txt
    
• As produções devem considerar os seguintes padrões:

			– Variáveis: [A-Z], sendo S a variável inicial
			
			– Terminais: [a-z]
			
			– Operador de definição: ->
			
			– Separador de regras: | [as regras também podem ser escritas linha a linha (veja variável X)]
			
			– Lambda: .
			
			
• Exemplo:

Definição da GLC na FNC (glc.txt):
```
S −> AT | AB

T −> XB

X −> AT

X −> AB

A −> a

B −> b
```

Saída (saida.txt) para aaabbb
```
{S,X}

{ }   {T}

{ }  {S,X} { }

{ }   { }  {T}   { }

{ }   { }  {S,X} { } { }

{A}  {A} {A}  {B} {B} {B}

a	   a	  a		 b 	 b 	 b

```
