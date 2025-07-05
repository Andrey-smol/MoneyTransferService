package ru.netology.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.netology.common.CommonPatch;
import ru.netology.model.OperationInfo;

import java.io.*;
import java.time.LocalDateTime;

@Slf4j
@Component
public class OperationLoggerImpl implements OperationLogger{
    private final String patchFile;

    public OperationLoggerImpl() {
        File file = new File(CommonPatch.WORK_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        patchFile = CommonPatch.WORK_DIR + CommonPatch.PATCH_FILE_LOG_OPERATION;
    }

    @Override
    public void logWrite(OperationInfo info) {
        try (Writer bw = new BufferedWriter(getFileWriter(patchFile))) {
            bw.write("\n[" + getDate() + "] " + getMessage(info));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

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
    private String getMessage(OperationInfo info){

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
