# Complexidade de Algoritmo

Em suma, o algoritmo possui uma complexidade simples de entender por não possuir recursividade ou casos não-abordados. A seguir, a complexidade de cada etapa e no fim a complexidade total do algoritmo:

- for (String line : normalizedLines) O(N)
- Arrays.stream(sentences).forEach() O(N)
- for (String sentenceWord : sentenceWords) O(N)
- for (String sw : stopWords) O(N)

* As funçoes baseadas em Stream<T> (reduce, filter...) podem variar a sua complexidade.

#### Complexidade Geral

- **Big O Notation**: O(N4);
- **Coverage**: Class transformation time: **0.0106637s** for 173 classes.
