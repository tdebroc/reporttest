package com.grooptown.reportanalysis;

/**
 * Created by thibautdebroca on 19/04/2019.
 */
public class NWordsPattern implements Comparable {
    public String words;
    public int count;

    public NWordsPattern(String words, int count) {
        this.words = words;
        this.count = count;
    }


    @Override
    public int compareTo(Object o) {
        NWordsPattern nWordsPattern = (NWordsPattern) o;
        if (nWordsPattern.count == count) return 0;
        return nWordsPattern.count > count ? 1 : -1;
    }
}
