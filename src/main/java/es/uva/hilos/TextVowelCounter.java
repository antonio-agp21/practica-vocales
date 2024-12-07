package es.uva.hilos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TextVowelCounter {

    // Method that takes a String input and returns an ArrayList of words
    public static ArrayList<String> getWords(String input) {
        // TODO
        // Creamos un ArrayList a partir del array de Strings devuelto por el método
        // split, que toma como delimitadores uno o más caracteres no alfanuméricos

        ArrayList<String> palabras = new ArrayList<>(Arrays.asList(input.split("[^a-zA-Z0-9]+")));
        return palabras;
    }

    // Method that counts vowels in a string using parallelism
    public static int getVowels(String input, int parallelism) throws InterruptedException {

        // Create queues
        BlockingQueue<String> wordQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Result> resultQueue = new LinkedBlockingQueue<>();

        // Create and start the worker threads based on the parallelism parameter
        List<Thread> workers = new ArrayList<>();
        wordQueue.addAll(getWords(input));
        for (int i = 0; i < parallelism; i++) {
            WordVowelCounter worker = new WordVowelCounter(wordQueue, resultQueue);
            Thread workerThread = new Thread(worker);
            workers.add(workerThread);
            workerThread.start(); // Start each worker thread
            System.out.println(i + " -----------------------");
        }

        // Wait for all worker threads to finish
        for (Thread worker : workers) {
            worker.join();
        }
        // Gather results from resultQueue
        // TODO
        int sum = 0;
        try {
            while (!resultQueue.isEmpty()) {
                sum += resultQueue.take().getVowelCount();
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Hay " + sum + " vocales en: " + input);
        return sum;
    }
}
