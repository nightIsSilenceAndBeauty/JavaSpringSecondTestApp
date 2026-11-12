package ru.trints.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.trints.MySecondTestAppSpringBoot.model.Request;
import ru.trints.MySecondTestAppSpringBoot.model.Response;
import ru.trints.MySecondTestAppSpringBoot.service.FeedbackService;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final FeedbackService feedbackService;

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(
            @Valid @RequestBody Request request,
            BindingResult bindingResult
    ) {
        return feedbackService.process(request, bindingResult, System.currentTimeMillis());
    }
}
