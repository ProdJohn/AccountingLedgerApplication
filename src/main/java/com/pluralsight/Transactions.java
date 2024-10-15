package com.pluralsight;

public class Transactions {
    private String date;
    private String time;
    private String vendor;
    private String description;
    private double amount;

    public Transactions(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() { return description;}

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", vendor='" + vendor + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}

