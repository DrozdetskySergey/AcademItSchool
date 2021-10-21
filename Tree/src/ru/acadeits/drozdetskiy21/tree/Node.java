package ru.acadeits.drozdetskiy21.tree;

class Node<E> {
    private E data;
    private Node<E> left;
    private Node<E> right;

    public Node(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }

    public void copy(Node<E> node) {
        data = node.data;
        left = node.left;
        right = node.right;
    }

    public void copyLeft() {
        copy(left);
    }

    public void copyRight() {
        copy(right);
    }
}
