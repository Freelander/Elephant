package org.gemini.markdown.model.type;

/**
 * Created by geminiwen on 15/3/13.
 */
public class Range {
    public int lower;
    public int upper;

    public Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }
}
