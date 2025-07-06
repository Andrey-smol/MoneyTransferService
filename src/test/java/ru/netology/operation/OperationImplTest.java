package ru.netology.operation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import ru.netology.logger.OperationLogger;
import ru.netology.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OperationImplTest {

    private OperationLogger logger;
    private OperationStorage operationStorage;
    private Operation operation;
    private final Map<Long, OperationEntity> operationEntityMap = new HashMap<>();
    private static final OperationEntity operationEntity = new OperationEntity(
            new CardEntity(Currency.RUB, "1234567812345678", "12/30", "235", 500000),
            new CardEntity(Currency.RUB, "2341567812345678", "12/30", "230", 500000),
            new Amount(Currency.RUB, 1000),
            10,
            StatusOperation.START
    );

    private static final OperationEntity operationEntity1 = new OperationEntity(
            new CardEntity(Currency.RUB, "5234567812345678", "12/30", "235", 500000),
            new CardEntity(Currency.RUB, "5341567812345678", "12/30", "230", 500000),
            new Amount(Currency.RUB, 1000),
            10,
            StatusOperation.START
    );

    public static Stream<Arguments> testGetOperationById() {
        return Stream.of(
                Arguments.of(new OperationId("1"), Optional.of(operationEntity)),
                Arguments.of(new OperationId("2"), Optional.empty())
        );
    }

    public static Stream<Arguments> testGetStatus() {
        return Stream.of(
                Arguments.of(new OperationId("1"), StatusOperation.START),
                Arguments.of(new OperationId("2"), null)
        );
    }

    public static Stream<Arguments> testGetStatus_() {
        return Stream.of(
                Arguments.of(1L, StatusOperation.START),
                Arguments.of(2L, null)
        );
    }

    public static Stream<Arguments> testSetStatus() {
        return Stream.of(
                Arguments.of(new OperationId("1"), StatusOperation.IN_PROGRESS, true),
                Arguments.of(new OperationId("1"), StatusOperation.DONE_SUCCESSFUL, true)
        );
    }

    public static Stream<Arguments> testUpdateById() {
        return Stream.of(
                Arguments.of(new OperationId("1"), operationEntity, Optional.of(operationEntity)),
                Arguments.of(new OperationId("1"), operationEntity1, Optional.of(operationEntity1))
        );
    }

    @BeforeEach
    public void init() {
        logger = Mockito.mock(OperationLogger.class);
        operationStorage = Mockito.mock(OperationStorage.class);
        Mockito.when(operationStorage.getId()).thenReturn(1L);
        Mockito.when(operationStorage.getMap()).thenReturn(operationEntityMap);
        Mockito.when(operationStorage.incrementId()).thenReturn(2L);

        operationEntityMap.put(1L, operationEntity);

        operation = new MyOperation(operationStorage, logger);
    }

    @Test
    @Order(1)
    public void testAdd() {
        OperationId expected = new OperationId("2");

        OperationId result = operation.add(operationEntity);

        ArgumentCaptor<OperationInfo> argumentCaptor = ArgumentCaptor.forClass(OperationInfo.class);
        Mockito.verify(logger).logWrite(argumentCaptor.capture());

        Assertions.assertEquals(StatusOperation.START, argumentCaptor.getValue().getStatus());

        Mockito.verify(logger, Mockito.times(1)).logWrite(Mockito.any());
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    @Order(2)
    public void testGetOperationById(OperationId id, Optional<OperationEntity> expected) {
        Optional<OperationEntity> result = operation.getOperationById(id);

        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    @Order(3)
    public void testGetStatus(OperationId id, StatusOperation expected) {
        StatusOperation result = operation.getStatus(id);

        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    @Order(4)
    public void testGetStatus_(Long id, StatusOperation expected) {
        StatusOperation result = operation.getStatus(id);

        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    public void testSetStatus(OperationId id, StatusOperation statusOperation, boolean expected) {
        boolean result = operation.setStatus(id, statusOperation);

        ArgumentCaptor<OperationInfo> argumentCaptor = ArgumentCaptor.forClass(OperationInfo.class);
        Mockito.verify(logger).logWrite(argumentCaptor.capture());

        Assertions.assertEquals(statusOperation, argumentCaptor.getValue().getStatus());
        Mockito.verify(logger, Mockito.times(1)).logWrite(Mockito.any());
        Assertions.assertEquals(expected, result);

    }

    @ParameterizedTest
    @MethodSource
    public void testUpdateById(OperationId id, OperationEntity entity, Optional<OperationEntity> expected) {

        Optional<OperationEntity> result = operation.updateById(id, entity);

        ArgumentCaptor<OperationInfo> argumentCaptor = ArgumentCaptor.forClass(OperationInfo.class);
        Mockito.verify(logger).logWrite(argumentCaptor.capture());

        Assertions.assertEquals(entity.getCardDebit().getCardNumber(), argumentCaptor.getValue().getCardDebitNumber());

        Assertions.assertEquals(expected, result);
        Mockito.verify(logger, Mockito.times(1)).logWrite(Mockito.any());
    }

}
