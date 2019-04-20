package com.grooptown.reportanalysis;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.grooptown.reportanalysis.Main.NUMBER_OF_WORD_PER_PATTERN;

/**
 * Created by thibautdebroca on 19/04/2019.
 */
@Data
public class Report {

    String fileName;

    String content;

    public Report(File file) throws IOException {
        this.fileName = file.getName();
        readContent(file);
        sanitizeContent();
    }


    public void readContent(File file) throws IOException {
        content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }

    public void sanitizeContent() throws IOException {
        content = content.replaceAll("\r", " ");
        content = content.replaceAll("\n", " ");
        content = content.replaceAll("\t", " ");
        content = content.replaceAll("[^\\x00-\\x7F]", "");
        content = content.replaceAll(" ", " ");
        while (content.contains("  ")) {
            content = content.replaceAll("  ", " ");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return fileName != null ? fileName.equals(report.fileName) : report.fileName == null;

    }

    @Override
    public int hashCode() {
        return fileName != null ? fileName.hashCode() : 0;
    }

    public List<String> getPatternsFromReport() {
        List<String> patterns = new ArrayList<>();
        String[] reportWords = content.split(" ");
        for (int i = 0; i < reportWords.length - NUMBER_OF_WORD_PER_PATTERN; i++) {
            String nNextWords = getNNextWords(reportWords, i, NUMBER_OF_WORD_PER_PATTERN);
            patterns.add(nNextWords);
        }
        return patterns;
    }

    /**
     *
     * @param reportWords
     * @param start
     * @param countWords
     * @return
     */
    public String getNNextWords(String[] reportWords, int start, int countWords) {
        StringBuilder nNextWords = new StringBuilder();
        for (int i = start; i < start + countWords; i ++) {
            nNextWords.append(reportWords[i]);
            nNextWords.append(" ");
        }
        return nNextWords.toString();
    }
}
