package service;

public class Rating {
    private int count;
    private double sum;

    public Rating(int count, double sum) {
        this.count = count;
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public static Rating add(Rating rating1, Rating rating2) {
        return new Rating(rating1.count+rating2.count, rating1.sum+rating2.sum);
    }
}
