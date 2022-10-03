import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        PrintStream o = new PrintStream(new File("D:\\dev\\facul\\ta1\\src\\results\\teste3.txt"));
        System.setOut(o);
        */

        String stopWordsPath = "D:\\dev\\facul\\ta1\\src\\stopwords.txt";
        File fileSW = new File(stopWordsPath);
        List<String> stopWords = new ArrayList<>(Files.lines(fileSW.toPath()).toList());

        String fileName = "D:\\dev\\facul\\ta1\\src\\teste3.txt";

        Scanner lines = new Scanner(new File(fileName));
        lines.useDelimiter("\r\n");

        StringBuilder auxArr = new StringBuilder();
        ArrayList<String> normalizedLines = new ArrayList<>();
        while (lines.hasNext()) {
            String line = lines.next();

            if(!line.isEmpty()) {
                auxArr.append(line).append(" ");

                normalizedLines.add("");
            } else if(!auxArr.isEmpty()) {
                normalizedLines.add(auxArr.toString());

                auxArr.setLength(0);
            } else {
                normalizedLines.add("");
            }
        }
        normalizedLines.remove(0);

        // List<String> linesWithNoEmptyString = lines.filter(i -> !i.isEmpty()).toList();

        AtomicInteger linesIndex = new AtomicInteger(0);
        AtomicBoolean isParagraphOrTitle = new AtomicBoolean(true);

        for (String line : normalizedLines) {
            if (line.isEmpty() && !isParagraphOrTitle.get()) {
                System.out.println();
                System.out.println("[PARÁGRAFO]");
                isParagraphOrTitle.set(true);
            } else if (!line.isEmpty()) {
                isParagraphOrTitle.set(false);
            }

            String[] sentences = line.split("[.;?!:]");

            int index = linesIndex.incrementAndGet();

            if (!line.isEmpty()) {
                System.out.println("| LINHA: " + index);
                System.out.println("|  NÚMERO DE SENTENÇAS -> " + (sentences.length - 1));
                Arrays.stream(sentences).forEach(sentence -> {
                    String[] sentenceWords = sentence.split(" ");
                    ArrayList<String> sentenceWordsWithoutStopWords = new ArrayList<>();

                    AtomicBoolean isStopWordContain = new AtomicBoolean(false);

                    HashMap<String, Integer> occurrences = new HashMap<>();

                    for (String sentenceWord : sentenceWords) {
                        // por algum motivo o java não possui .find() nativo
                        for (String sw : stopWords) {
                            if (sentenceWord.equals(sw.trim().toLowerCase())) isStopWordContain.set(true);
                        }

                        if (!isStopWordContain.get()) {
                            sentenceWordsWithoutStopWords.add(sentenceWord);

                            // sem um observer ou watcher não tem como escutar qualquer mudanca no hashmap, então da forma que implementei não tem como calcular o posicionamento de não stopwords.
                            occurrences.put(sentenceWord, (occurrences.get(sentenceWord) != null ? occurrences.get(sentenceWord) : 0) + 1);
                        }

                        isStopWordContain.set(false);
                    }

                    occurrences.remove("");

                    if (sentenceWordsWithoutStopWords.size() - 1 <= 0) return;

                  System.out.println("| [SENTENÇA] -> [" + sentenceWordsWithoutStopWords.stream().reduce("", (acc, el) -> acc + (acc.length() == 0 ? "" : " ") + el) + "]");
                    System.out.println("|  NÚMERO DE PALAVRAS NA SENTENÇA -> " + (sentenceWordsWithoutStopWords.size()));

                    System.out.print("|  OCORRENCIAS NA SENTENÇA -> [");
                    SortedSet<String> keySet = new TreeSet<>(occurrences.keySet());
                    for(String key : keySet) {
                      System.out.print(key + " -> " + occurrences.get(key) + " | ");
                    }
                    System.out.println("]");
                    System.out.println(" ");
                });
            }
        }
    }
}
