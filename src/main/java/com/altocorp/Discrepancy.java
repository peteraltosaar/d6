package com.altocorp;

class Discrepancy {
    private DiscrepancyType type;
    private int magnitude;

    public Discrepancy(DiscrepancyType type, int magnitude) {
        this.type = type;
        this.magnitude = magnitude;
    }

    public DiscrepancyType getType() {
        return type;
    }

    public int getMagnitude() {
        return magnitude;
    }

}
