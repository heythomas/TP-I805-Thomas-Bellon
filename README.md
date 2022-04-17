# TP Compilation : BELLON Thomas

## Exemples d'exécution :

### 1. Simple déclaration de variables (avec additions successives)
```
let variable = 123; let variable2 = 4+5+6.
```
#### Résultat assembleur
```
DATA SEGMENT
	variable DD
	variable2 DD
DATA ENDS
CODE SEGMENT
	mov eax, 123
	pop eax
	mov variable, eax
	push eax
	mov eax, 4
	mov eax, 5
	pop ebx
	pop eax
	add eax, ebx
	push eax
	mov eax, 6
	pop ebx
	pop eax
	add eax, ebx
	push eax
	pop eax
	mov variable2, eax
	push eax
CODE ENDS
```
#### Arbre Correspondant
```
(; (LET variable 123) (LET variable2 (+ (+ 4 5) 6)))
```

### 2. While true et input
```
let b = input; while 1 do 2.
```
#### Résultat assembleur
```
DATA SEGMENT
	b DD
DATA ENDS
CODE SEGMENT
	in eax
	pop eax
	mov b, eax
	push eax
debut_while_1:
	mov eax, 1
faux_gt_1:
	mov eax,0
sortie_gt_1:
	sortie_while_1
	mov eax, 2
	jmp debut_while_1
CODE ENDS


```
#### Arbre Correspondant
```
(; (LET b INPUT) (WHILE 1 2))
```

### 3. Notes
- Le programme ne peut faire tourner que 2 expressions successives (un point virgule)
- L'arbre fonctionne bien
- La génération de code assembleur fonctionne bien pour les opérateurs primaires, addition, division...
- Les opérateurs booléens ne sont pas implémentés
- Le while fonctionne partiellement, cf exemple ci dessus