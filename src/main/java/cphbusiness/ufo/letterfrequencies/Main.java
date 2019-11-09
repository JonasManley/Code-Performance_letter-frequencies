package cphbusiness.ufo.letterfrequencies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */

public class Main {
    
private static String fileName = "C:\\Users\\Jonas\\Documents\\GitHub\\Code-Performance_letter-frequencies\\src\\main\\resources\\FoundationSeries.txt";
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Instant starts = Instant.now();
         long startTime = System.currentTimeMillis();
        Reader reader = new FileReader(fileName);
        Scanner sc = new Scanner(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        Map<Integer, Long> freq = new HashMap<>();
        //tallyChars(reader, freq);
        //tallyCharsOptimizedWithScanner(sc, freq);
        tallyCharsOptimizedWithBufferedReader(bufferedReader, freq);
        //print_tally(freq);
         Instant ends = Instant.now();
            System.out.println("Duration for main method: "+Duration.between(starts, ends));
    }
    
   
    private static void tallyCharsOptimizedWithScanner(Scanner sc, Map<Integer, Long> freq) throws IOException {
        Instant starts = Instant.now();
        int t = 0;
        try {
        while (sc.hasNextLine()) {
            freq.put(t, freq.get(t) + 1);
        }
        sc.close();
    } 
    catch (NullPointerException np) {
                freq.put(t, 1L);
            };
            
            Instant ends = Instant.now();
            System.out.println("Duration: "+Duration.between(starts, ends));
    }
    
    private static void tallyCharsOptimizedWithBufferedReader(BufferedReader bufferedReader, Map<Integer, Long> freq) throws IOException {
        String strCurrentLine;   
        int t = 0;
        try {
        while ((strCurrentLine = bufferedReader.readLine()) != null) {

         freq.put(t, freq.get(t) + 1);
        }

       } catch (NullPointerException np) {
                freq.put(t, 1L);
            };
    }

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        Instant starts = Instant.now();
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
        }
        Instant ends = Instant.now();
            System.out.println("Duration: "+Duration.between(starts, ends));
    }

    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));;
        }
    }
}
