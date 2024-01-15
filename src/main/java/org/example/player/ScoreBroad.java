package org.example.player;

public class ScoreBroad {
    int score;
    int remainingTime;

    int goal;

    public ScoreBroad(int score, int remainingTime, int goal) {
        this.score = score;
        this.remainingTime = remainingTime;
        this.goal = goal;
    }

    public int getScore() {
        return score;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getGoal() {
        return goal;
    }
}
