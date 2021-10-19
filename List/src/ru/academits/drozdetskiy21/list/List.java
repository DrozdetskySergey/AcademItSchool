package ru.academits.drozdetskiy21.list;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

public class List<E> {
    private Node<E> head;
    private int size;

    public List() {
    }

    public List(Collection<? extends E> collection) {
        Node<E> lastNode = null;
        boolean isFirstElement = true;

        for (E e : collection) {
            if (isFirstElement) {
                addFirst(e);
                lastNode = head;
                isFirstElement = false;
            } else {
                lastNode.setNext(new Node<>(e));
                lastNode = lastNode.getNext();
            }
        }

        size = collection.size();
    }

    public int getSize() {
        return size;
    }

    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The first element is missing. Size: 0");
        }

        return head.getData();
    }

    public E get(int index) {
        checkIndex(index);

        return getNode(index).getData();
    }

    public E set(int index, E data) {
        checkIndex(index);
        Node<E> node = getNode(index);
        E oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    public void addFirst(E data) {
        head = new Node<>(data, head);
        ++size;
    }

    public void add(int index, E data) {
        if (index == 0) {
            addFirst(data);

            return;
        }

        if (index != size) {
            checkIndex(index);
        }

        Node<E> previousNode = getNode(index - 1);
        previousNode.setNext(new Node<>(data, previousNode.getNext()));
        ++size;
    }

    public void add(E data) {
        add(size, data);
    }


    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The first element is missing. Size: 0");
        }

        E removedData = head.getData();
        head = head.getNext();
        --size;

        return removedData;
    }

    public E remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        Node<E> previousNode = getNode(index - 1);
        Node<E> removedNode = previousNode.getNext();
        E removedData = removedNode.getData();
        previousNode.setNext(removedNode.getNext());
        --size;

        return removedData;
    }

    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        if (Objects.equals(o, head.getData())) {
            head = head.getNext();
            --size;

            return true;
        }

        if (size == 1) {
            return false;
        }

        Node<E> previousNode = head;

        for (Node<E> node = head.getNext(); node != null; previousNode = node, node = node.getNext()) {
            if (Objects.equals(o, node.getData())) {
                previousNode.setNext(node.getNext());
                --size;

                return true;
            }
        }

        return false;
    }

    public List<E> reverse() {
        if (size < 2) {
            return this;
        }

        Node<E> previousNode = head;
        Node<E> node = head.getNext();
        Node<E> nextNode = node.getNext();
        node.setNext(previousNode);

        for (int i = 2; i < size; i++) {
            previousNode = node;
            node = nextNode;
            nextNode = node.getNext();
            node.setNext(previousNode);
        }

        head.setNext(null);
        head = node;

        return this;
    }

    public List<E> getClone() {
        List<E> listClone = new List<>();

        if (size == 0) {
            return listClone;
        }

        listClone.size = size;
        listClone.head = new Node<>(head.getData());
        Node<E> nodeClone = listClone.head;

        for (Node<E> node = head.getNext(); node != null; node = node.getNext()) {
            nodeClone.setNext(new Node<>(node.getData()));
            nodeClone = nodeClone.getNext();
        }

        return listClone;
    }

    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size: 0");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Bounds: [0, " + (size - 1) + "]");
        }
    }

    private Node<E> getNode(int index) {
        Node<E> node = head;

        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        return node;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (Node<E> node = head; node != null; node = node.getNext()) {
            stringBuilder.append(node.getData()).append(", ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]").toString();
    }
}
