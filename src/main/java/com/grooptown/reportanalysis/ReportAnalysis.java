package com.grooptown.reportanalysis;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.grooptown.reportanalysis.Main.MAX_NUMBER_OF_SIMILAR_REPORT_FOR_ONE_PATTERN;
import static com.grooptown.reportanalysis.Main.NUMBER_OF_WORD_PER_PATTERN;

/**
 * Created by thibautdebroca on 19/04/2019.
 */
public class ReportAnalysis {

    Set<Report> reports = new HashSet();

    HashMap<String, Set<Report>> patternsToReport = new HashMap();

    List<SimilarReports> similarReportses;

    Set<String> patternToExclude = new HashSet<>();

    /**
     * @param folder
     * @param suffixFile
     * @throws IOException
     */
    public void launchAnalysis(String folder, String suffixFile) throws IOException {
        buildReports(folder, suffixFile);
        analyzeSimilarPattern();
        analyzeSimilarReport();
        printAnalysis();
    }


    /**
     *
     */
    private void analyzeSimilarReport() {
        SimilarReportsManager similarReportsManager = new SimilarReportsManager();
        for (Map.Entry<String, Set<Report>> entry : patternsToReport.entrySet()) {
            String pattern = entry.getKey();
            List<Report> reports = new ArrayList(entry.getValue());
            if (reports.size() > MAX_NUMBER_OF_SIMILAR_REPORT_FOR_ONE_PATTERN || reports.size() == 1) {
                continue;
            }
            for (int i = 0; i < reports.size(); i++) {
                for (int j = i + 1; j < reports.size(); j++) {
                    similarReportsManager.addSimilarReports(
                            reports.get(i), reports.get(j), pattern
                    );
                }
            }
        }
        this.similarReportses = similarReportsManager.getSortedSimilarReports();
    }


    /**
     * @param folder
     * @param suffixFile
     * @throws IOException
     */
    public void buildReports(String folder, String suffixFile) throws IOException {
        File dir = new File(folder);
        File[] files = dir.listFiles((d, name) -> name.endsWith(suffixFile));
        for (File file : files) {
            Report report = new Report(file);
            reports.add(report);
        }
    }


    /**
     *
     */
    private void analyzeSimilarPattern() {
        for (Report report : reports) {
            List<String> patterns = report.getPatternsFromReport();
            for (String pattern : patterns) {
                if (!patternToExclude.contains(pattern)) {
                    addToPatternsToReport(pattern, report);
                }
            }
        }
    }

    /**
     * @param nNextWords
     */
    private void addToPatternsToReport(String nNextWords, Report report) {
        if (!patternsToReport.containsKey(nNextWords)) {
            patternsToReport.put(nNextWords, new HashSet());
        }
        patternsToReport.get(nNextWords).add(report);
    }


    /**
     *
     */
    private void printAnalysis() {
        System.out.println("### Scanned " + reports.size() + " reports ###");

        for (Report report : reports) {
            System.out.println(report.fileName.replace(".pdf.txt", ""));
        }

        System.out.println("\n\n\n### Most famous patterns are : ###\n");

        List<NWordsPattern> allPatterns = patternsToReport
                .entrySet()
                .stream()
                .map(
                        entry -> new NWordsPattern(entry.getKey(), entry.getValue().size())
                ).collect(Collectors.toList());
        Collections.sort(allPatterns);
        for (int i = 0; i < allPatterns.size(); i++) {
            if (allPatterns.get(i).count > 5) {
                System.out.println(allPatterns.get(i).words + " => " + allPatterns.get(i).count);
            }
        }

        System.out.println("\n\n\n### Similar Reports Summary: ###\n");
        for (SimilarReports similarReports : similarReportses) {
            System.out.print(" => " + similarReports.similarPatterns.size() + " same patterns");
            System.out.print(" for Reports : ");
            for (Report report : similarReports.getReports()) {
                System.out.print(report.fileName + " and ");
            }
            System.out.println();
        }


        System.out.println("\n\n\n### Similar Reports Details: ###\n");
        List<SimilarReports> similarReportses = this.similarReportses;
        for (SimilarReports similarReports : similarReportses) {
            System.out.print("Report: ");
            for (Report report : similarReports.getReports()) {
                System.out.print(report.fileName + " and ");
            }
            System.out.print("\n There are " + similarReports.similarPatterns.size() + " similar patterns\n");
            for (String pattern : similarReports.similarPatterns) {
                System.out.println(" - " + pattern);
            }
            System.out.print("\n\n");
        }

    }


    /***
     *
     * @param folder
     */
    public void addPatternToExclude(String folder, String suffixFile) throws IOException {
        File dir = new File(folder);
        File[] files = dir.listFiles((d, name) -> name.endsWith(suffixFile));
        for (File file : files) {
            Report reportWithPatternToExclude = new Report(file);
            List<String> patternsFromReport = reportWithPatternToExclude.getPatternsFromReport();
            patternToExclude.addAll(patternsFromReport);
        }
    }
}
