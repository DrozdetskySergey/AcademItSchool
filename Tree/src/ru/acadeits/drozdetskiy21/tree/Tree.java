package ru.acadeits.drozdetskiy21.tree;

import java.util.Comparator;
import java.util.Objects;

public class Tree<E> {
    private Node<E> root;
    private int size;
    private final Comparator<E> comparator;

    public Tree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int getSize() {
        return size;
    }

    public void add(E data) {
        if (data == null) {
            throw new NullPointerException("Data = Null");
        }

        if (root == null) {
            root = new Node<>(data);

            return;
        }

        for(Node<E> currentNode = root; ; ) {
            if (Objects.compare(data, currentNode.getData(), comparator) < 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(new Node<>(data));
                    break;
                }

                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(new Node<>(data));
                    break;
                }

                currentNode = currentNode.getRight();
            }
        }

        ++size;
    }
}
