package ru.netology.mapperDTO;

import org.springframework.stereotype.Component;
import ru.netology.model.CardEntity;
import ru.netology.model.CardEntityBuildImpl;
import ru.netology.model.TransferMoneyDTO;
import ru.netology.repository.CardEntityStorage;
import ru.netology.repository.StatusCardStory;

/**
 * Этот класс mapperDTO.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Component
public class Mapper {
    /**
     * Метод преобразует объект TransferMoneyDTO в CardEntity
     *
     * @param transferMoneyDTO входные данные которые преобразуются в CardEntity
     * @return CardEntity
     */
    public CardEntity toEntity(TransferMoneyDTO transferMoneyDTO) {
        return new CardEntityBuildImpl().setCurrency(transferMoneyDTO.getAmount().getCurrency())
                .setCardCVV(transferMoneyDTO.getCardFromCVV())
                .setCardNumber(transferMoneyDTO.getCardFromNumber())
                .setCardValidTill(transferMoneyDTO.getCardFromValidTill())
                .build();
    }

    public CardEntityStorage toEntityStorage(CardEntity card) {
        return new CardEntityStorage(card.getCurrency(),
                card.getCardNumber(),
                card.getCardValidTill(),
                card.getCardCVV(),
                card.getBalance(),
                StatusCardStory.ACTIVE);
    }
    public CardEntity toEntityFromStorage(CardEntityStorage cardEntityStorage){
        return new CardEntityBuildImpl().setCurrency(cardEntityStorage.getCurrency().getCode())
                .setCardCVV(cardEntityStorage.getCardCVV())
                .setCardNumber(cardEntityStorage.getCardNumber())
                .setBalance(cardEntityStorage.getBalance())
                .setCardValidTill(cardEntityStorage.getCardValidTill())
                .build();
    }
}
