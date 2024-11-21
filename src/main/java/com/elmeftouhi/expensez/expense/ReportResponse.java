package com.elmeftouhi.expensez.expense;

import java.util.Map;

public record ReportResponse(
        Map<Integer, Double> data
) {
}
