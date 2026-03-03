package com.aditya.diffcheckerbackend.service;

import com.aditya.diffcheckerbackend.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiffService {

    public DiffResponse compare(DiffRequest request) {

        String t1 = request.isCaseSensitive() ? request.getText1()
                : request.getText1().toLowerCase();

        String t2 = request.isCaseSensitive() ? request.getText2()
                : request.getText2().toLowerCase();

        List<String> list1 = tokenize(t1, request.getMode());
        List<String> list2 = tokenize(t2, request.getMode());

        int m = list1.size();
        int n = list2.size();

        int[][] dp = new int[m + 1][n + 1];

        // Build LCS table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (list1.get(i - 1).equals(list2.get(j - 1))) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        List<DiffChunk> result = new ArrayList<>();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (list1.get(i - 1).equals(list2.get(j - 1))) {
                result.add(new DiffChunk(list1.get(i - 1), "SAME"));
                i--; j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                result.add(new DiffChunk(list1.get(i - 1), "REMOVED"));
                i--;
            } else {
                result.add(new DiffChunk(list2.get(j - 1), "ADDED"));
                j--;
            }
        }

        while (i > 0) {
            result.add(new DiffChunk(list1.get(i - 1), "REMOVED"));
            i--;
        }

        while (j > 0) {
            result.add(new DiffChunk(list2.get(j - 1), "ADDED"));
            j--;
        }

        Collections.reverse(result);

        return new DiffResponse(result);
    }

    private List<String> tokenize(String text, String mode) {
        if ("WORD".equalsIgnoreCase(mode)) {
            return Arrays.asList(text.split("\\s+"));
        } else if ("LINE".equalsIgnoreCase(mode)) {
            return Arrays.asList(text.split("\\R"));
        } else {
            return Arrays.asList(text.split(""));
        }
    }
}
