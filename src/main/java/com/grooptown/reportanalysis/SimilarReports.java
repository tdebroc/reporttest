package com.grooptown.reportanalysis;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by thibautdebroca on 20/04/2019.
 */
@Data
public class SimilarReports implements  Comparable {
    Set<Report> reports = new HashSet<>();
    List<String> similarPatterns = new ArrayList<>();


    @Override
    public int compareTo(Object o) {
        SimilarReports similarReports = (SimilarReports) o;
        if (similarReports.similarPatterns.size() == this.similarPatterns.size()) {
            return 0;
        }
        return similarReports.similarPatterns.size() > this.similarPatterns.size() ? 1 : -1;
    }
}
