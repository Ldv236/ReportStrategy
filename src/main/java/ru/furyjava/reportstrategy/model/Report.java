package ru.furyjava.reportstrategy.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Report {

    private String data;

    private ReportType reportType;
}
