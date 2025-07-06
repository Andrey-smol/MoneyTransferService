package ru.netology.common;

/**
 * Этот класс хранения верификационного кода подтверждения операции.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public class VerificationCode {

    /**
     * Верификационный код.
     */
    private static final String VERIFICATION_CODE = "0000";

    /**
     * Метод getter - возвращает верификационный код
     *
     * @return верификационный код в виде строки
     */
    public static String getVerificationCode(){
        return VERIFICATION_CODE;
    }
}
