package tbank.homework3;

import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class CustomLinkedList<E> {

    @Getter
    private int size;
    private Node<E> head;
    private Node<E> tail;

    public CustomLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(E e) {
        Node<E> newNode = new Node<>(tail, e, null);

        if (size == 0) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }

        tail = newNode;
        size++;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index > size / 2) {
            return getByIndexInSecondHalf(index);
        } else {
            return getByIndexInFirstHalf(index);
        }
    }

    private E getByIndexInFirstHalf(int index) {
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        return node.getItem();
    }

    private E getByIndexInSecondHalf(int index) {
        Node<E> node = tail;
        int tailIndex = size - 1;
        for (int i = 0; i < tailIndex - index; i++) {
            node = node.getPrev();
        }

        return node.getItem();
    }

    public void remove(E e) {
        if (isNull(head)) {
            return;
        } else if (head.getItem().equals(e)) {
            removeHead();
            size--;
        } else if (size == 1) {
            return;
        } else if (tail.getItem().equals(e)) {
            removeTail();
            size--;
        } else {
            removeIntermediate(e);
        }
    }

    private void removeIntermediate(E e) {
        Node<E> node = head.getNext();
        while (nonNull(node.getNext())) {
            if (node.getItem().equals(e)) {
                Node<E> prev = node.getPrev();
                Node<E> next = node.getNext();
                if (prev != null) {
                    prev.setNext(next);
                }
                size--;
                return;
            }
            node = node.getNext();
        }
    }

    private void removeTail() {
        Node<E> prevTailNode = tail.getPrev();
        prevTailNode.setNext(null);
        tail = prevTailNode;
    }

    private void removeHead() {
        head = head.getNext();
        if (head != null) {
            head.setPrev(null);
        }
    }

    public boolean contains(E e) {
        if (isNull(head)) {
            return false;
        }

        Node<E> node = head;
        while (nonNull(node)) {
            if (node.getItem().equals(e)) {
                return true;
            }
            node = node.getNext();
        }

        return false;
    }

    public void addAll(CustomLinkedList<E> list) {
        if (size == 0) {
            head = list.getHead();
            tail = list.getTail();
        } else if (list.size != 0) {
            Node<E> listHead = list.getHead();
            tail.setNext(listHead);
            listHead.setPrev(tail);
        }

        size = size + list.size;
    }

    private Node<E> getHead() {
        return head;
    }

    private Node<E> getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return getMassiveAsString();
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private E[] getMassiveValue() {
        E[] massive = (E[]) new Object[size];
        Node<E> node = head;
        int index = 0;

        while (node != null) {
            massive[index] = node.getItem();
            node = node.getNext();
            index++;
        }

        return massive;
    }

    public static <T> CustomLinkedList<T> fromStream(Stream<T> stream) {
        return stream.reduce(new CustomLinkedList<T>(), (list, element) -> {
            list.add(element);
            return list;
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

    private String getMassiveAsString() {
        if (size == 0) {
            return "[]";
        }

        E[] massive = getMassiveValue();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (E elem : massive) {
            if (isNull(elem)) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(elem);
            }

            stringBuilder.append(", ");
        }

        int size = stringBuilder.length();

        stringBuilder.delete(size - 2, size);
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    @Getter
    @Setter
    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
