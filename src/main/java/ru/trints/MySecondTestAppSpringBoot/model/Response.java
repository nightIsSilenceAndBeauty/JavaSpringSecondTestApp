package ru.trints.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    /** Уникальный идентификатор исходного сообщения. */
    private String uid;

    /** Уникальный идентификатор операции. */
    private String operationUid;

    /** Время формирования ответа. */
    private String systemTime;

    /** Результат обработки: success или failed. */
    private Codes code;

    /** Рассчитанный размер годовой премии. */
    private Double annualBonus;

    /** Рассчитанный размер квартальной премии для руководящей должности. */
    private Double quarterlyBonus;

    /** Код ошибки, если обработка завершилась неуспешно. */
    private ErrorCodes errorCode;

    /** Описание ошибки, если обработка завершилась неуспешно. */
    private ErrorMessages errorMessage;
}
