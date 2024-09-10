package homework3;

import org.junit.Test;
import tbank.homework3.CustomLinkedList;

import java.util.stream.Stream;

import static java.lang.String.format;
import static utils.AssertionsUtils.*;

public class CustomLinkedListTest {

    private String firstElement = "Item 1";
    private String secondElement = "Item 2";

    @Test
    public void testGetSize() {
        CustomLinkedList<String> list = new CustomLinkedList<>();

        list.add(firstElement);
        list.add(secondElement);

        int expectedSize = 2;
        int actualSize = list.getSize();
        String sizeError = format("Ожидал, что количество элементов будет равно '%d', по факту получил: '%d'", expectedSize, actualSize);
        assertEquals(actualSize, expectedSize, sizeError);
    }

    @Test
    public void testGetValidIndex() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        list.add(secondElement);

        String actualFirstElement = list.get(0);
        String actualSecondElement = list.get(1);
        String firstElementError = format("Ожидал первым элементом '%s', по факту получил '%s'", firstElement, actualFirstElement);
        String secondElementError = format("Ожидал вторым элементом '%s', по факту получил '%s'", secondElement, actualSecondElement);

        assertEquals(actualFirstElement, firstElement, firstElementError);
        assertEquals(actualSecondElement, secondElement, secondElementError);
    }

    @Test
    public void testGetInvalidIndex() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        String error = "Ожидал, что при вызове get с некорректным индексом будет выкидываться IndexOutOfBoundsException";

        assertThatThrownBy(() -> list.get(1), IndexOutOfBoundsException.class, error);
        assertThatThrownBy(() -> list.get(-1), IndexOutOfBoundsException.class, error);
    }

    @Test
    public void testRemoveExistingElement() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        list.add(secondElement);
        list.remove(firstElement);
        String sizeAfterRemoveError = "Ожидал, что размер списка уменьшится на после удаления элемента";
        String elementShiftedError = "Ожидал, что элемент сместится на 1 ячейку после удаления элемента";
        String containsError = "Ожидал, что при вызове contains удаленного элемента вернется false";

        assertEquals(list.getSize(), 1, sizeAfterRemoveError);
        assertEquals(list.get(0), secondElement, elementShiftedError);
        assertFalse(list.contains(firstElement), containsError);
    }

    @Test
    public void testRemoveNonExistingElement() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        int expectedSize = list.getSize();
        list.remove("Non-existing Item");
        int actualSize = list.getSize();
        String sizeError = format("Ожидал, что после удаления несуществующего элемента, размер списка останется " +
                "неизменным ('%s'), по факту получил размер списка: '%s'", expectedSize, actualSize);

        assertEquals(actualSize, expectedSize, sizeError);
    }

    @Test
    public void testContainsExistingElement() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        assertTrue(list.contains(firstElement), "Ожидал, что вызов метода contains вернет true");
    }

    @Test
    public void testContains_NonExistingElement() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        assertFalse(list.contains(secondElement), "Ожидал, что метод contains вернет false для элемента, которого нет в списке");
    }

    @Test
    public void testAddAll() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        for (int i = 1; i <= 3; i++) {
            list.add("Item " + i);
        }
        int firstListSize = list.getSize();

        CustomLinkedList<String> otherList = new CustomLinkedList<>();
        for (int i = 4; i <= 6; i++) {
            otherList.add("Item " + i);
        }
        int secondListSize = otherList.getSize();

        list.addAll(otherList);
        assertEquals(list.getSize(), firstListSize + secondListSize, "Ожидал, что размер списка увеличится на количество элементов второго списка");
        for (int i = 1; i <= 6; i++) {
            String item = "Item " + i;
            assertTrue(list.contains(item), format("Ожидал, что элемент '%s' будет в списке, по факту его нет", item));
        }
    }

    @Test
    public void testAddAllIfOtherListIsEmpty() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        for (int i = 1; i <= 3; i++) {
            list.add("Item " + i);
        }
        int firstListSize = list.getSize();

        CustomLinkedList<String> otherList = new CustomLinkedList<>();

        list.addAll(otherList);
        assertEquals(list.getSize(), firstListSize, "Ожидал, что размер списка останется неизменным, тк второй список не содержит элементы");
        for (int i = 1; i <= 3; i++) {
            String item = "Item " + i;
            assertTrue(list.contains(item), format("Ожидал, что элемент '%s' будет в списке, по факту его нет", item));
        }
    }

    @Test
    public void testAddAllIfThisListIsEmpty() {
        CustomLinkedList<String> list = new CustomLinkedList<>();

        CustomLinkedList<String> otherList = new CustomLinkedList<>();
        for (int i = 5; i <= 10; i++) {
            otherList.add("Item " + i);
        }
        int secondListSize = otherList.getSize();

        list.addAll(otherList);
        assertEquals(list.getSize(), secondListSize, "Ожидал, что размер списка будет равен размеру второго списка," +
                " тк первый список не содержит элементы");

        for (int i = 5; i <= 10; i++) {
            String item = "Item " + i;
            assertTrue(list.contains(item), format("Ожидал, что элемент '%s' будет в списке, по факту его нет", item));
        }
    }

    @Test
    public void testFromStream() {
        Stream<String> stream = Stream.of("A", "B", "C");
        CustomLinkedList<String> resultList = CustomLinkedList.fromStream(stream);

        assertEquals(resultList.getSize(), 3, "");
        assertEquals("A", resultList.get(0), "");
        assertEquals("B", resultList.get(1), "");
        assertEquals("C", resultList.get(2), "");
    }

    @Test
    public void testToString() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add(firstElement);
        list.add(secondElement);
        String expectedString = format("[%s, %s]", firstElement, secondElement);
        String actualString = list.toString();
        String error = format("Ожидал, что метод toString вернет строку '%s', по факту получил '%s'", expectedString, actualString);
        assertEquals(list.toString(), expectedString, error);
    }
}
