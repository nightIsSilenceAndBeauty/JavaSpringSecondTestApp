package ru.trints.MySecondTestAppSpringBoot.service;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.trints.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.trints.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.trints.MySecondTestAppSpringBoot.model.Codes;
import ru.trints.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.trints.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.trints.MySecondTestAppSpringBoot.model.Request;
import ru.trints.MySecondTestAppSpringBoot.model.Response;
import ru.trints.MySecondTestAppSpringBoot.util.DateTimeUtil;

@Service
@Slf4j
public class FeedbackService {

    private final ValidationService validationService;
    private final AnnualBonusService annualBonusService;
    private final ModifyRequestService modifyRequestService;
    private final ModifyResponseService modifySystemTimeService;
    private final ModifyResponseService modifyOperationUidService;

    @Autowired
    public FeedbackService(
            ValidationService validationService,
            AnnualBonusService annualBonusService,
            ModifyRequestService modifyRequestService,
            @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifySystemTimeService,
            @Qualifier("ModifyOperationUidResponseService") ModifyResponseService modifyOperationUidService
    ) {
        this.validationService = validationService;
        this.annualBonusService = annualBonusService;
        this.modifyRequestService = modifyRequestService;
        this.modifySystemTimeService = modifySystemTimeService;
        this.modifyOperationUidService = modifyOperationUidService;
    }

    public ResponseEntity<Response> process(Request request, BindingResult bindingResult, long requestReceivedAtMillis) {
        log.info("request: {}", request);
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            validationService.isValid(bindingResult);
            validationService.checkSupportedUid(request.getUid());

            modifyOperationUidService.modify(response);
            modifySystemTimeService.modify(response);
            response.setAnnualBonus(annualBonusService.calculate(
                    request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays()
            ));
            if (request.getPosition().isManager()) {
                response.setQuarterlyBonus(annualBonusService.calculateQuarterlyBonus(
                        request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays()
                ));
            }
            modifyRequestService.modify(request, requestReceivedAtMillis);
            log.info("response: {}", response);
            return ResponseEntity.ok(response);
        } catch (ValidationFailedException e) {
            log.error("Не удалось проверить запрос: {}", e.getMessage());
            return errorResponse(response, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION,
                    HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Запрос содержит неподдерживаемый uid: {}", e.getMessage());
            return errorResponse(response, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED,
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Ошибка при обработке запроса", e);
            return errorResponse(response, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Response> errorResponse(
            Response response, ErrorCodes code, ErrorMessages message, HttpStatus status
    ) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(code);
        response.setErrorMessage(message);
        log.info("Response с ошибкой: {}", response);
        return new ResponseEntity<>(response, status);
    }
}
