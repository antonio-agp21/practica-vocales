package es.uva.hilos;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class WordVowelCounter implements Runnable {
    private BlockingQueue<String> wordQueue;
    private BlockingQueue<Result> resultQueue;

    // Constructor
    public WordVowelCounter(BlockingQueue<String> wordQueue, BlockingQueue<Result> resultQueue) {
        this.wordQueue = wordQueue;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {

        // TODO
        try {
            while (!wordQueue.isEmpty()) { // Mirar si lo estoy haciendo bien con el número de hilos que se pueden
                                           // generar y tal*****************
                String palabra = wordQueue.take();
                resultQueue.add(new Result(palabra, countVowels(palabra)));
            }
        } catch (InterruptedException e) {
        }
    }

    private int countVowels(String word) {
        String vocales = "aeiouAEIOU";
        int contador = 0;
        for (int i = 0; i < word.length(); i++) {
            if (vocales.indexOf(word.charAt(i)) != -1)
                contador++;
        }
        return contador;
    }
}
