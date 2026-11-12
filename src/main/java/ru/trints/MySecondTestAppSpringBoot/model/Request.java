package ru.trints.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /** Уникальный идентификатор сообщения. */
    @NotBlank(message = "UID не может быть пустым")
    @Size(max = 32)
    private String uid;

    /** Уникальный идентификатор операции. */
    @NotBlank
    @Size(max = 32)
    private String operationUid;

    /** Имя системы-отправителя. */
    private Systems systemName;

    /** Время создания сообщения. */
    @NotBlank
    private String systemTime;

    /** Наименование ресурса. */
    private String source;

    /** Уникальный идентификатор коммуникации. */
    @Min(1)
    @Max(100000)
    private Integer communicationId;

    /** Уникальный идентификатор шаблона. */
    private Integer templateId;

    /** Код продукта. */
    private Integer productCode;

    /** SMS-код. */
    private Integer smsCode;

    /** Должность сотрудника. */
    private Positions position;

    /** Размер заработной платы. */
    private Double salary;

    /** Коэффициент премирования. */
    private Double bonus;

    /** Количество отработанных дней в году. */
    private Integer workDays;

    @Override
    public String toString() {
        return "Request{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                ", position=" + position +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", workDays=" + workDays +
                '}';
    }
}
