package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        int randomNumber = (int) (Math.random() * 1000000);
        String url = "http://numbersapi.com/" + randomNumber + "/trivia";
        System.out.println(url);

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String fact = response.toString();
        System.out.println(fact);

        Map<Character, Integer> frequency = new HashMap<Character, Integer>();
        ArrayList<Character> charsCloseAverage = new ArrayList<Character>();
        float numOfUniqueSymbols = 0, numOfAllSymbols = 0;
        int averageFrequency = 0;

        fact = fact.replaceAll("[^\\da-zA-Z]", "");

        for (int i = 0; i < fact.length(); i++) {
            if (frequency.putIfAbsent(fact.charAt(i), 1) != null) {
                frequency.replace(fact.charAt(i), frequency.get(fact.charAt(i)) + 1);
            } else {
                ++numOfUniqueSymbols;
            }
            ++numOfAllSymbols;
        }

        averageFrequency = Math.round(numOfAllSymbols / numOfUniqueSymbols);

        System.out.println("Частоты:");
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            if (value == averageFrequency) {
                charsCloseAverage.add(key);
            }
            System.out.print(key);              // Сделано через Print
            System.out.print(" - ");            // для экономии памяти без
            System.out.print(value);            // использования StringBuilder.
            System.out.println(" раза");
        }

        System.out.print("\nСреднее значение частоты ");
        System.out.printf("%.0f",numOfAllSymbols);
        System.out.print('/');
        System.out.printf("%.0f", numOfUniqueSymbols);
        System.out.print(" = ");
        System.out.println(numOfAllSymbols / numOfUniqueSymbols);
        System.out.print("Символы, которые соответствуют условию наиболее близкого значения частоты к среднему значанию: ");

        for (int i = 0; i < charsCloseAverage.size() - 1; i++) {
            System.out.print(charsCloseAverage.get(i));
            System.out.print('(');
            System.out.print((int) charsCloseAverage.get(i));
            System.out.print("), ");
        }
        System.out.print(charsCloseAverage.get(charsCloseAverage.size() - 1));
        System.out.print('(');
        System.out.print((int) charsCloseAverage.get(charsCloseAverage.size() - 1));
        System.out.print(')');
    }
}
