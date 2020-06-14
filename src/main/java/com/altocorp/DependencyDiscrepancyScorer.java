package com.altocorp;

import java.util.List;

public class DependencyDiscrepancyScorer {

    public int score(List<Discrepancy> discrepancies) {
        int score = 0;
        for (Discrepancy discrepancy : discrepancies) {
            if (discrepancy.getType().equals(DiscrepancyType.MAJOR)) {
                score += discrepancy.getMagnitude() * 10;
            }
            else if (discrepancy.getType().equals(DiscrepancyType.MINOR)) {
                score += discrepancy.getMagnitude() * 3;
            }
            else {
                score += discrepancy.getMagnitude();
            }
        }
        return score;
    }
}
