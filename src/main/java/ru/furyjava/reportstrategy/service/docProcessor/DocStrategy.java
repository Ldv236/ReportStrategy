package ru.furyjava.reportstrategy.service.docProcessor;

import ru.furyjava.reportstrategy.model.Report;
import ru.furyjava.reportstrategy.model.ReportType;

public interface DocStrategy {

    ReportType getReportType();

    void generateDoc(Report report);
}
