package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class AccountingLedgerApp {
    private static final String fileLocation = "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        ArrayList<Transactions> transactions = loadTransactions();
        Scanner Keyboard = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Prodigy Financials, please select an option: \n" +
                    "D- Add Deposit \n" +
                    "P- Make Payment \n" +
                    "L- Ledger \n" +
                    "X- Exit");
            String Selection = Keyboard.nextLine().trim().toUpperCase();
            switch (Selection) {
                case "D":
                    addTransaction(transactions, Keyboard, "deposit");
                    break;
                case "P":
                    addTransaction(transactions, Keyboard, "payment");
                    break;
                case "L":
                    addTransaction(transactions, Keyboard);
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

        }

    }
}
