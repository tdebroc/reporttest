package com.grooptown.reportanalysis;

import lombok.Data;

import java.util.*;

/**
 * Created by thibautdebroca on 20/04/2019.
 */
@Data
public class SimilarReportsManager {
    private HashMap<String, SimilarReports> similarReports = new HashMap<>();

    /**
     *
     * @param report1
     * @param report2
     * @param pattern
     */
    public void addSimilarReports(Report report1, Report report2, String pattern) {
        String key = getKey(report1, report2);
        if (!similarReports.containsKey(key)) {
            SimilarReports similarReports = new SimilarReports();
            similarReports.getReports().add(report1);
            similarReports.getReports().add(report2);
            this.similarReports.put(key, similarReports);
        }
        similarReports.get(key).getSimilarPatterns().add(pattern);
    }


    /**
     *
     * @return
     */
    public List<SimilarReports> getSortedSimilarReports() {
        ArrayList<SimilarReports> similarReportses = new ArrayList<>(similarReports.values());
        Collections.sort(similarReportses);
        return similarReportses;
    }

    /**
     *
     * @param report1
     * @param report2
     * @return
     */
    private String getKey(Report report1, Report report2) {
        if (report1.fileName.compareTo(report2.fileName) > 0) {
            return report1.fileName + "#" + report2.fileName;
        } else {
            return report2.fileName + "#" + report1.fileName;
        }
    }
    
}
