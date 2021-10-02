package ru.academits.drozdetskiy21.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class List<E> {
    private Node<E> head;
    private int size;

    public List() {
    }

    public List(java.util.List<E> list) {
        Node<E> lastNode = null;
        boolean isFirstElement = true;

        for (E e : list) {
            if (isFirstElement) {
                add(e);
                lastNode = head;
                isFirstElement = false;
            } else {
                Node<E> newNode = new Node<>(e);
                lastNode.setNext(newNode);
                lastNode = newNode;
            }
        }

        size = list.size();
    }

    public int getSize() {
        return size;
    }

    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The first element is missing. Size: 0");
        }

        return getNode(0).getData();
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
        add(0, data);
    }

    public void add(int index, E data) {
        if (index == size) {
            add(data);
        }

        checkIndex(index);
        Node<E> newNode = new Node<>(data);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<E> previousNode = getNode(index - 1);
            newNode.setNext(previousNode.getNext());
            previousNode.setNext(newNode);
        }

        ++size;
    }

    public void add(E data) {
        Node<E> newNode = new Node<>(data);

        if (size == 0) {
            head = newNode;
        } else {
            Node<E> lastNode = getNode(size - 1);
            lastNode.setNext(newNode);
        }

        ++size;
    }


    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The first element is missing. Size: 0");
        }

        return removeNode(0);
    }

    public E remove(int index) {
        checkIndex(index);
        return removeNode(index);
    }

    private E removeNode(int index) {
        E removedData;

        if (index == 0) {
            removedData = head.getData();
            head = head.getNext();
        } else {
            Node<E> previousNode = getNode(index - 1);
            Node<E> removedNode = previousNode.getNext();
            removedData = removedNode.getData();
            previousNode.setNext(removedNode.getNext());
        }

        --size;

        return removedData;
    }

    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        E data = head.getData();

        if (Objects.equals(o, data)) {
            head = head.getNext();
            --size;

            return true;
        }

        if (size == 1) {
            return false;
        }

        Node<E> node = head.getNext();
        Node<E> previousNode = head;

        for (int i = 1; i < size; i++) {
            data = node.getData();

            if (Objects.equals(o, data)) {
                previousNode.setNext(node.getNext());
                --size;

                return true;
            } else {
                previousNode = node;
                node = node.getNext();
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

        Node<E> node = head;
        listClone.head = new Node<>(node.getData());
        listClone.size = 1;
        Node<E> lastNodeClone = listClone.head;

        for (int i = 1; i < size; i++) {
            node = node.getNext();
            Node<E> nodeClone = new Node<>(node.getData());
            lastNodeClone.setNext(nodeClone);
            lastNodeClone = nodeClone;
        }

        listClone.size = size;

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
        Node<E> node = head;

        for (int i = 0; i < size; i++) {
            stringBuilder.append(node.getData()).append(", ");
            node = node.getNext();
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]").toString();
    }
}
