package service;

public interface RatingStore {
    Rating add(String laptopId, double score);
}
