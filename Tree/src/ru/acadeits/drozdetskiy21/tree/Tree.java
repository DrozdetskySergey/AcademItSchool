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
        } else {
            for (Node<E> currentNode = root; ; ) {
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
        }

        ++size;
    }

    public boolean contains(E data) {
        if (data == null) {
            throw new NullPointerException("Data = Null");
        }

        for (Node<E> currentNode = root; currentNode != null; ) {
            int comparisonResult = Objects.compare(data, currentNode.getData(), comparator);

            if (comparisonResult == 0) {
                return true;
            }

            currentNode = comparisonResult < 0 ? currentNode.getLeft() : currentNode.getRight();
        }

        return false;
    }

    public boolean remove(E data) {
        if (data == null) {
            throw new NullPointerException("Data = Null");
        }

        Node<E> previousNode = null;
        boolean toLeft = false;

        for (Node<E> currentNode = root; currentNode != null; currentNode = toLeft ? currentNode.getLeft() : currentNode.getRight()) {
            int comparisonResult = Objects.compare(data, currentNode.getData(), comparator);

            if (comparisonResult == 0) {
                if (currentNode.getRight() != null || currentNode.getLeft() != null) {
                    removeNodeHasChildren(currentNode);
                } else {
                    if (previousNode == null) { // currentNode == root, root don't have children -> size == 1
                        root = null;
                    } else {
                        if (toLeft) {
                            previousNode.setLeft(null);
                        } else {
                            previousNode.setRight(null);
                        }
                    }
                }

                --size;

                return true;
            }

            previousNode = currentNode;
            toLeft = comparisonResult < 0;
        }

        return false;
    }

    private void removeNodeHasChildren(Node<E> removedNode) {
        if (removedNode.getLeft() == null) {
            removedNode.copyRight();

            return;
        }

        if (removedNode.getRight() == null) {
            removedNode.copyLeft();

            return;
        }

        Node<E> previousNode = removedNode;

        for (Node<E> currentNode = removedNode.getRight(); ; ) {
            if (currentNode.getLeft() != null) {
                previousNode = currentNode;
                currentNode = currentNode.getLeft();
            } else {
                removedNode.setData(currentNode.getData());

                if (currentNode.getRight() != null) {
                    currentNode.copyRight();
                } else {
                    if (previousNode == removedNode) {
                        previousNode.setRight(null);
                    } else {
                        previousNode.setLeft(null);
                    }
                }

                return;
            }
        }
    }
}
