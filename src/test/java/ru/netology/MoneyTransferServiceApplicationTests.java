package ru.netology;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.model.*;

import java.util.stream.Stream;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyTransferServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Container
    private static final GenericContainer<?> myContainer = new GenericContainer<>("myapp")
            .withExposedPorts(5500);

    private Integer port;

    public static Stream<Arguments> testDoTransfer() {
        return Stream.of(
                Arguments.of(new TransferMoneyDTO(new Amount(Currency.RUB, 100),
                        "1234567812345678", "12/30", "235", "2341567812345678"),
                        new OperationId("1"),
                        HttpStatus.OK),
                Arguments.of(new TransferMoneyDTO(new Amount(Currency.RUB, 100),
                        "3241567812345678", "12/30", "236", "4321567812345678"),
                        new OperationId("2"),
                        HttpStatus.OK),
                Arguments.of(new TransferMoneyDTO(new Amount(Currency.RUB, 100),
                        "1234567812345678", "12/30", "235", "2341567812345678"),
                        new OperationId("3"),
                        HttpStatus.INTERNAL_SERVER_ERROR),
                Arguments.of(new TransferMoneyDTO(new Amount(Currency.RUB, 100),
                        "1234567812345670", "12/30", "235", "2341567812345678"),
                        null,
                        HttpStatus.BAD_REQUEST),
                Arguments.of(new TransferMoneyDTO(new Amount(Currency.RUB, 100),
                        "2143567812345678", "12/30", "235", "2341567812345670"),
                        null,
                        HttpStatus.BAD_REQUEST),
                Arguments.of(new TransferMoneyDTO(new Amount(Currency.RUB, 100),
                        "2143567812345678", "12/30", "239", "2341567812345678"),
                        null,
                        HttpStatus.BAD_REQUEST)

        );
    }

    @Test
    @Order(1)
    void contextLoads() {
        port = myContainer.getMappedPort(5500);
        System.out.println("Port connect to container = " + port);
    }

    @ParameterizedTest
    @MethodSource
    @Order(2)
    public void testDoTransfer(TransferMoneyDTO transferMoneyDTO, OperationId expected, HttpStatusCode statusCode){
        ResponseEntity<OperationId> response = restTemplate.postForEntity("http://localhost:" + port + "/transfer", transferMoneyDTO, OperationId.class);

        if(response.getBody().getOperationId() == null){
            ResponseEntity<ResponseError> responseError = restTemplate.postForEntity("http://localhost:" + port + "/transfer", transferMoneyDTO, ResponseError.class);
            ResponseError result = responseError.getBody();

            System.out.println("[testDoTransfer] Response ERROR: id = " + result.getId() + ", message = " + result.getMessage());
            Assertions.assertEquals(statusCode, responseError.getStatusCode());
        }else {
            OperationId result = new OperationId(response.getBody().getOperationId());

            Assertions.assertEquals(expected, result);
            Assertions.assertEquals(statusCode, response.getStatusCode());
        }
    }

    @ParameterizedTest
    @MethodSource
    @Order(3)
    public void testConfirmOperation(ConfirmOperation confirmOperation, OperationId expected, HttpStatusCode statusCode){

        ResponseEntity<OperationId> response = restTemplate.postForEntity("http://localhost:" + port + "/confirmOperation", confirmOperation, OperationId.class);
        if(response.getBody().getOperationId() == null){
            ResponseEntity<ResponseError> response1 = restTemplate.postForEntity("http://localhost:" + port + "/confirmOperation", confirmOperation, ResponseError.class);
            ResponseError result = response1.getBody();

            System.out.println("[testConfirmOperation] Response ERROR: id = " + result.getId() + ", message = " + result.getMessage());
        }else {
            Assertions.assertEquals(expected, response.getBody());
        }
        Assertions.assertEquals(statusCode, response.getStatusCode());
    }


    public Stream<Arguments> testConfirmOperation() {
        return Stream.of(
                Arguments.of(new ConfirmOperation("1", "0000"),
                        new OperationId("1"),
                        HttpStatus.OK),
                Arguments.of(new ConfirmOperation("2", "0000"),
                        new OperationId("2"),
                        HttpStatus.OK),
                Arguments.of(new ConfirmOperation("3", "000"),
                        null,
                        HttpStatus.INTERNAL_SERVER_ERROR),
                Arguments.of(new ConfirmOperation("1", "0000"),
                        null,
                        HttpStatus.INTERNAL_SERVER_ERROR),
                Arguments.of(new ConfirmOperation("10", "0000"),
                        null,
                        HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }
}


//curl -X POST 'http://localhost:8080/books' \

//curl --location --request POST 'http://localhost:8080/books' \
//        --header 'Content-Type: application/json' \
//        --data-raw '{
//        "id": 4,
//        "title": "Fundamentals of Software Architecture: An Engineering Approach",
//        "author": "Mark Richards, Neal Ford",
//        "publisher": "Upfront Books",
//        "releaseDate": "Feb 2021",
//        "isbn": "B08X8H15BW",
//        "topic": "Architecture"
//        }'
