package ru.furyjava.reportstrategy.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.furyjava.reportstrategy.model.Report;
import ru.furyjava.reportstrategy.model.ReportType;
import ru.furyjava.reportstrategy.service.docProcessor.DocStrategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final List<DocStrategy> docStrategyList;
    private Map<ReportType, DocStrategy> docStrategies;

    @PostConstruct
    private void initStrategy() {
        docStrategies = docStrategyList.stream()
                .collect(Collectors.toMap(DocStrategy::getReportType, Function.identity()));
    }

    // метод выполняется при старте приложения
    // (имитируем создание объекта и выполнение операции с ним)
    @EventListener(ApplicationReadyEvent.class)
    private void prepairReport() {
        Report report = Report.builder().data("Данные для отчета")
                .reportType(ReportType.PDF) // !!! здесь задаётся тип
                .build();

        generateDoc(report);
    }

    public void generateDoc(Report report) {

        DocStrategy docStrategy = docStrategies.get(report.getReportType());
        docStrategy.generateDoc(report);
    }
}
