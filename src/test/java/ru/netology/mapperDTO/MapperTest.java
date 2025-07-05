package ru.netology.mapperDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.model.Amount;
import ru.netology.model.CardEntity;
import ru.netology.model.Currency;
import ru.netology.model.TransferMoneyDTO;

public class MapperTest {
    TransferMoneyDTO transferMoneyDTO;
    @BeforeEach
    public void init(){
        transferMoneyDTO = new TransferMoneyDTO(new Amount(Currency.RUB, 1000),
                "1234567812345678","12/30", "235", "2341567812345678");
    }

    @Test
    public void testToEntity(){
        CardEntity expected = new CardEntity(Currency.RUB, "1234567812345678", "12/30", "235", 0);
        Mapper mapper = new Mapper();
        CardEntity result = mapper.toEntity(transferMoneyDTO);
        Assertions.assertEquals(expected, result);
    }
}
