package dto;

import java.time.LocalDate;
import java.util.Date;

public class IncomeDTO {
    private LocalDate date;
    private double dailyIncome;
    private double monthlyIncome;
    private double annuallyIncome;
    private double totalIncome;

    public IncomeDTO() {
    }

    public IncomeDTO(LocalDate date, double dailyIncome, double monthlyIncome, double annuallyIncome, double totalIncome) {
        this.date = date;
        this.dailyIncome = dailyIncome;
        this.monthlyIncome = monthlyIncome;
        this.annuallyIncome = annuallyIncome;
        this.totalIncome = totalIncome;
    }

    public IncomeDTO(LocalDate dateNow, double income) {
        this.date=dateNow;
        this.totalIncome = income;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getAnnuallyIncome() {
        return annuallyIncome;
    }

    public void setAnnuallyIncome(double annuallyIncome) {
        this.annuallyIncome = annuallyIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "IncomeDTO{" +
                "date=" + date +
                ", dailyIncome=" + dailyIncome +
                ", monthlyIncome=" + monthlyIncome +
                ", annuallyIncome=" + annuallyIncome +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
