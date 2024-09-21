package tbank.homework3;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Stream<Integer> numberStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        CustomLinkedList<Integer> list = CustomLinkedList.fromStream(numberStream);
        System.out.println(list);
    }
}
