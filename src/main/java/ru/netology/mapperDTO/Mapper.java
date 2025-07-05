package ru.netology.mapperDTO;

import org.springframework.stereotype.Component;
import ru.netology.model.CardEntity;
import ru.netology.model.CardEntityBuildImpl;
import ru.netology.model.TransferMoneyDTO;

@Component
public class Mapper {
    public CardEntity toEntity(TransferMoneyDTO transferMoneyDTO){
        return new CardEntityBuildImpl().setCurrency(transferMoneyDTO.getAmount().getCurrency())
                .setCardCVV(transferMoneyDTO.getCardFromCVV())
                .setCardNumber(transferMoneyDTO.getCardFromNumber())
                .setCardValidTill(transferMoneyDTO.getCardFromValidTill())
                .build();
    }
}
