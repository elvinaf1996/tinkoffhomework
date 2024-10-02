package utils;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

import java.util.List;

import static java.lang.String.format;

public class AssertionsUtils {

    public static void assertListIsEquals(List<?> actualValues, List<?> expectedValues) {
        String error = format("Полученный список: '%s' не соответсвует ожидаемому: '%s'", actualValues, expectedValues);
        assertEquals(actualValues, expectedValues, error);
    }

    public static void assertEquals(Object actual, Object expected, String error) {
        Assertions.assertThat(actual)
                .as(error)
                .isEqualTo(expected);
    }

    public static void assertTrue(boolean actual, String error) {
        Assertions.assertThat(actual)
                .as(error)
                .isEqualTo(true);
    }

    public static void assertFalse(boolean actual, String error) {
        Assertions.assertThat(actual)
                .as(error)
                .isEqualTo(false);
    }

    public static void asserStringIsEmpty(String actual, String error) {
        Assertions.assertThat(actual)
                .as(error)
                .isEmpty();
    }

    public static <T> void assertThatThrownByWithErrorWithDescription(ThrowableAssert.ThrowingCallable shouldRaiseThrowable,
                                                                      Class<T> clazz, String errorMessage, String errorDescription) {
        Assertions.assertThatThrownBy(shouldRaiseThrowable)
                .as(errorDescription)
                .isInstanceOf(clazz)
                .hasMessageContaining(errorMessage);
    }

    public static <T> void assertThatThrownByWithErrorWith(ThrowableAssert.ThrowingCallable shouldRaiseThrowable,
                                                                      Class<T> clazz, String errorMessage) {
        Assertions.assertThatThrownBy(shouldRaiseThrowable)
                .isInstanceOf(clazz)
                .hasMessageContaining(errorMessage);
    }

    public static <T> void assertThatThrownBy(ThrowableAssert.ThrowingCallable shouldRaiseThrowable, Class<T> clazz,
                                              String errorDescription) {
        Assertions.assertThatThrownBy(shouldRaiseThrowable)
                .as(errorDescription)
                .isInstanceOf(clazz);
    }
}
