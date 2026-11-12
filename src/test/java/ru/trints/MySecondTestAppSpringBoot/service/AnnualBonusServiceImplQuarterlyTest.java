package ru.trints.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.trints.MySecondTestAppSpringBoot.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AnnualBonusServiceImplQuarterlyTest {

    private final AnnualBonusServiceImpl annualBonusService = new AnnualBonusServiceImpl();

    @Test
    void calculatesQuarterlyBonusForManager() {
        double result = annualBonusService.calculateQuarterlyBonus(Positions.TL, 100000.0, 2.0, 243);

        double expected = 100000.0 * 2.0 * java.time.Year.now().length() * 2.6 / 243 / 4;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void rejectsQuarterlyBonusForNonManager() {
        assertThatThrownBy(() -> annualBonusService.calculateQuarterlyBonus(
                Positions.DEV, 100000.0, 2.0, 243
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
