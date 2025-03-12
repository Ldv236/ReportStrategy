package ru.furyjava.reportstrategy.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.furyjava.reportstrategy.model.Report;
import ru.furyjava.reportstrategy.model.ReportType;
import ru.furyjava.reportstrategy.service.docProcessor.DocStrategy;

import java.util.Arrays;
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

        // Проверка на покрытие всех типов
        List<ReportType> missingTypes = Arrays.stream(ReportType.values())
                .filter(type -> !docStrategies.containsKey(type))
                .toList();
        if (!missingTypes.isEmpty()) {
            throw new IllegalStateException("Отсутствуют стратегии для типов: " + missingTypes);
        }
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
        // либо выбрасывать исключение при отсутствии подходящей стратегии
//        if (docStrategy == null) {
//            throw new IllegalArgumentException("Нет подходящей стратегии для типа отчета: " + report);
//        }
        // либо заранее создать и применять здесь дефолтную стратегию
        // но лучше при старте сверять энам со стратегиями (сделано выше в методе initStrategy)
        docStrategy.generateDoc(report);
    }
}
