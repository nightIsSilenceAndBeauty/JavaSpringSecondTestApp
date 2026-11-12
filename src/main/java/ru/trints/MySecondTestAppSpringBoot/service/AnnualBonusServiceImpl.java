package ru.trints.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.trints.MySecondTestAppSpringBoot.model.Positions;
import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        return salary * bonus * Year.now().length() * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions position, double salary, double bonus, int workDays) {
        if (!position.isManager()) {
            throw new IllegalArgumentException("Квартальная премия рассчитывается только для руководителей!!!");
        }
        return calculate(position, salary, bonus, workDays) / 4;
    }
}
