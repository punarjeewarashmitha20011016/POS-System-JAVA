package entity;

import java.time.LocalDate;
import java.util.Date;

public class Income {
    private LocalDate date;
    private double totalIncome;
    private String dateAsAsString;

    public Income(LocalDate date, double totalIncome) {
        this.date = date;
        this.totalIncome = totalIncome;
    }
    public Income(String date, double totalIncome) {
        this.dateAsAsString = date;
        this.totalIncome = totalIncome;
    }

    public String getDateAsAsString() {
        return dateAsAsString;
    }

    public void setDateAsAsString(String dateAsAsString) {
        this.dateAsAsString = dateAsAsString;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "Income{" +
                "date=" + date +
                ", totalIncome=" + totalIncome +
                ", dateAsAsString='" + dateAsAsString + '\'' +
                '}';
    }
}
