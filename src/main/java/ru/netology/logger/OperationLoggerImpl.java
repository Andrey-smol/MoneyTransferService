package ru.netology.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.netology.model.OperationInfo;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Класс который реализует интерфейс OperationLogger,
 * для записи логов в файл
 *
 * @author Андрей Кузавов
 * @version 1.1
 */
@Slf4j
@Component
@ConfigurationProperties("log-operation")
public class OperationLoggerImpl implements OperationLogger {
    /**
     * Строка указатель на путь к файлу логов
     */
    private String patchFile;

    private String workDir;


    public void setPatchFile(String patchFile) {
        this.patchFile = patchFile;
    }

    public void setWorkDir(String workDir) {
        File file = new File(workDir);
        if (!file.exists()) {
            file.mkdir();
        }
        this.workDir = workDir;
    }

    /**
     * Метод получает объкт OperationInfo для сохранеия в файл.
     *
     * @param info входные данные которые надо сохранить
     * @return
     */
    @Override
    public void logWrite(OperationInfo info) {
        try (Writer bw = new BufferedWriter(getFileWriter(patchFile))) {
            bw.write("\n[" + getDate() + "] " + getMessage(info));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Метод преобразует время в удобный вид.
     *
     * @return время в виде строки "5-7-2025 11-33-32"
     */
    private String getDate() {
        LocalDateTime localDate = LocalDateTime.now();
        return new StringBuilder().append(localDate.getDayOfMonth()).append("-")
                .append(localDate.getMonth().getValue()).append("-")
                .append(localDate.getYear()).append(" ")
                .append(localDate.getHour()).append("-")
                .append(localDate.getMinute()).append("-")
                .append(localDate.getSecond()).append(" ")
                .toString();
    }

    /**
     * Преобразование входных данных в строку для записи в файл
     *
     * @param info входные данные в виде объекта OperationInfo
     * @return преобразованные данные объекта в строку вида
     * [1] [Status: START] - CardDebit Number: 1234567812345678, CardTransfer Number: 2341567812345678, Debit: 1000, Currency: RUB, Commission: 0
     */
    private String getMessage(OperationInfo info) {

        return new StringBuilder().append("[").append(info.getOperationId()).append("] ")
                .append("[Status: ").append(info.getStatus().getValue()).append("] - ")
                .append("CardDebit Number: ").append(info.getCardDebitNumber())
                .append(", CardTransfer Number: ").append(info.getCardTransferNumber())
                .append(", Debit: ").append(info.getAmount().getValue())
                .append(", Currency: ").append(info.getAmount().getCurrency())
                .append(", Commission: ").append(info.getCommission())
                .toString();
    }

    protected Writer getFileWriter(String pathFile) throws IOException {
        return new FileWriter(patchFile, true);
    }
}
