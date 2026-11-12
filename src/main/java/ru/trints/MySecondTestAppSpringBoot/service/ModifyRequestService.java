package ru.trints.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.trints.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
