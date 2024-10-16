package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class AccountingLedgerApp {
    private static final String fileLocation = "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        ArrayList<Transactions> transactions = loadTransactions();
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Prodigy Financials, please select an option: \n" +
                    "D- Add Deposit \n" +
                    "P- Make Payment \n" +
                    "L- Ledger \n" +
                    "X- Exit");
            String Selection = keyboard.nextLine().trim().toUpperCase();
            switch (Selection) {
                case "D":
                    addTransaction(transactions, keyboard,"deposit");
                    break;
                case "P":
                    addTransaction(transactions, keyboard, "payment");
                    break;
                case "L":
                    displayLedger(transactions, keyboard);
                    break;
                case "X":
                    saveTransactions(transactions);
                    System.exit(0);
                default:
                    System.out.println("Error, invalid option entered. Please try again and choose from the options provided.");
            }
        }
    }

    private static ArrayList<Transactions> loadTransactions() {
        ArrayList<Transactions> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                Transactions Transactions = new Transactions(
                        parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
                transactions.add(Transactions);
            }
        } catch (IOException e) {
            System.out.println("Error during reading the transactions file.");
            e.printStackTrace();
        }
        return transactions;
    }

    private static void saveTransactions(ArrayList<Transactions> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv"))) {
            for (Transactions Transactions : transactions) {
                writer.write(transactions.toString());
                writer.newLine();
            }
            System.out.println("Transactions were updated successfully.");
        } catch (IOException e) {
            System.out.println("Error in updating the transactions.");
            e.printStackTrace();

        }

    }

    private static void addTransaction(ArrayList<Transactions> transactions, Scanner Keyboard, String type) {
        System.out.println("Please provide the item description: ");
        String description = Keyboard.nextLine().trim();
        System.out.print("Enter the vendor: ");
        String vendor = Keyboard.nextLine().trim();
        System.out.print("Enter the amount: ");
        double amount = Keyboard.nextDouble();
        Keyboard.nextLine();

        if (type.equalsIgnoreCase("payment")) {
            amount = -Math.abs(amount);
        }
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss"));
        String[] dateTimeParts = dateTime.split("\\|");

        Transactions transaction = new Transactions(dateTimeParts[0], dateTimeParts[1], description, vendor, amount);
        transactions.add(transaction);

        System.out.println("Transaction added successfully.");

    }

    private static void displayLedger(ArrayList<Transactions> transactions, Scanner Keyboard) {
        System.out.println("Select an option: \n" +
                "A - All transactions+\n" +
                "D - Deposits only \n" +
                "P - Payments only");
        String Selection = Keyboard.nextLine().trim().toUpperCase();
        switch (Selection) {
            case "A":
                System.out.println("Displaying all of the transactions: ");
                for (Transactions transaction : transactions) {
                    if (transaction.getAmount() > 0) {
                        System.out.println(transaction);
                    }
                }
        }
    }
}
