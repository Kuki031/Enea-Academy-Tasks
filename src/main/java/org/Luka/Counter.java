package org.Luka;

public class Counter {
    private int count;
    private String label;

    public Counter(int initialCount, String label) {
        this.count = initialCount;
        this.label = label;
    }

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public String getLabel() {
        return label;
    }
}
