package com.grooptown.reportanalysis;

import java.io.IOException;

/**
 * Created by thibautdebroca on 19/04/2019.
 */
public class Main {

    public static int NUMBER_OF_WORD_PER_PATTERN = 4;

    public static int MAX_NUMBER_OF_SIMILAR_REPORT_FOR_ONE_PATTERN = 10;


    /**
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        ReportAnalysis reportAnalysis = new ReportAnalysis();
        reportAnalysis.addPatternToExclude("/Users/thibautdebroca/Documents/ISEP/PatternToExclude", ".txt");
        reportAnalysis.launchAnalysis("/Users/thibautdebroca/Documents/ISEP/BDD June 2019", ".txt");
    }

}
