package ru.furyjava.reportstrategy.service.docProcessor;

import org.springframework.stereotype.Component;
import ru.furyjava.reportstrategy.model.Report;
import ru.furyjava.reportstrategy.model.ReportType;

@Component
public class DocWordStrategy implements DocStrategy {

    private final ReportType reportType = ReportType.WORD;

    @Override
    public ReportType getReportType() {
        return reportType;
    }

    @Override
    public void generateDoc(Report report) {
        System.out.println("...логика генерации документа в ворде... - " + report.getData());
    }
}
