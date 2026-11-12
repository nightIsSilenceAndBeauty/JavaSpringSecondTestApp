package ru.trints.MySecondTestAppSpringBoot.service;

import ru.trints.MySecondTestAppSpringBoot.model.Request;

public interface ModifyRequestService {
    void modify(Request request, long requestReceivedAtMillis);
}
