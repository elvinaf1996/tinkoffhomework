package tbank.kudago.controller;

import org.assertj.core.api.Assertions;

import java.util.List;

public class AssertionsUtils {

    public static void assertListNotEmpty(List<?> list, String error) {
        Assertions.assertThat(list)
                .as(error)
                .isNotEmpty();
    }

    public static void assertIsTrue(boolean value, String error) {
        Assertions.assertThat(value)
                .as(error)
                .isTrue();
    }

    public static void assertIsFalse(boolean value, String error) {
        Assertions.assertThat(value)
                .as(error)
                .isFalse();
    }

    public static void assertIsEquals(Object actualValue, Object expectedValue, String error) {
        Assertions.assertThat(actualValue)
                .as(error)
                .isEqualTo(expectedValue);
    }
}
