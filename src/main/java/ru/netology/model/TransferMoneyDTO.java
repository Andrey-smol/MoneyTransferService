package ru.netology.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferMoneyDTO {
    private Amount amount;
    @NotBlank(message = "Номер карты не может быть пустым")
    @Pattern(regexp = "^(\\d{16}|\\d{19})$", message = "Номер карты должен содержать 16 или 19 цифр")
    private String cardFromNumber;

    @NotBlank(message = "Дата истечения не может быть пустой")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Дата истечения должна быть в формате MM/YY")
    private String cardFromValidTill;

    @NotBlank(message = "CVV не может быть пустым")
    @Size(min = 3, max = 4, message = "CVV должен содержать 3 или 4 цифры")
    private String cardFromCVV;

    @NotBlank(message = "Номер карты не может быть пустым")
    @Pattern(regexp = "^(\\d{16}|\\d{19})$", message = "Номер карты должен содержать 16 или 19 цифр")
    private String cardToNumber;

}
