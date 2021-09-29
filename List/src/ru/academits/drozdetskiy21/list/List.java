package ru.academits.drozdetskiy21.list;

public class List<E> {
    private Node<E> head = null;
    private int size = 0;

    public List() {
    }

    public List(java.util.List<E> baseList) {
        Node<E> lastNode = null;
        boolean isFirstElement = true;

        for (E e : baseList) {
            if (isFirstElement) {
                add(e);
                lastNode = head;
                isFirstElement = false;
            } else {
                Node<E> newNode = new Node<>(e);
                lastNode.setNext(newNode);
                ++size;
                lastNode = newNode;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public E getFirstOne() {
        return getNode(0).getData();
    }

    public E get(int index) {
        return getNode(index).getData();
    }

    public E set(int index, E data) {
        Node<E> node = getNode(index);
        E oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    public void addFirstOne(E data) {
        add(0, data);
    }

    public void add(int index, E data) {
        chekIndex(index);
        Node<E> newNode = new Node<>(data);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<E> prevNode = getNode(index - 1);
            newNode.setNext(prevNode.getNext());
            prevNode.setNext(newNode);
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


    public E removeFirstOne() {
        return remove(0);
    }

    public E remove(int index) {
        chekIndex(index);
        E removeData;

        if (index == 0) {
            removeData = head.getData();
            head = head.getNext();
        } else {
            Node<E> prevNode = getNode(index - 1);
            Node<E> removeNode = getNextNode(prevNode, index - 1);
            removeData = removeNode.getData();
            prevNode.setNext(removeNode.getNext());
        }

        --size;

        return removeData;
    }

    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        chekHead();
        E data = head.getData();

        if ((o == null && data == null) || (data != null && data.equals(o))) {
            head = head.getNext();
            --size;

            return true;
        }

        if (size == 1) {
            return false;
        }

        Node<E> node = getNextNode(head, 0);
        Node<E> prevNode = head;

        for (int i = 1; i < size; i++) {
            data = node.getData();

            if ((o == null && data == null) || (data != null && data.equals(o))) {
                prevNode.setNext(node.getNext());
                --size;

                return true;
            } else {
                prevNode = node;
                node = getNextNode(node, i);
            }
        }

        return false;
    }

    public List<E> reverse() {
        if (size < 2) {
            return this;
        }

        chekHead();
        Node<E> prevNode = head;
        Node<E> node = getNextNode(head, 0);
        Node<E> nextNode = getNextNode(node, 1);
        node.setNext(prevNode);

        for (int i = 2; i < size; i++) {
            prevNode = node;
            node = nextNode;
            nextNode = getNextNode(node, i);
            node.setNext(prevNode);
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

        chekHead();
        Node<E> node = head;
        listClone.head = new Node<>(node.getData());
        listClone.size = 1;
        Node<E> lastNodeClone = listClone.head;

        for (int i = 1; i < size; i++) {
            node = getNextNode(node, i - 1);
            Node<E> nodeClone = new Node<>(node.getData());
            lastNodeClone.setNext(nodeClone);
            listClone.size++;
            lastNodeClone = nodeClone;
        }

        return listClone;
    }

    private void chekIndex(int index) {
        if (size == 0) {
            throw new NullPointerException("Список пустой!");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Допустимые границы: [0, " + (size - 1) + "].");
        }
    }

    private void chekHead() {
        if (head == null) {
            throw new RuntimeException("Size = " + size + ", head = NULL");
        }
    }

    private Node<E> getNode(int index) {
        chekIndex(index);
        chekHead();
        Node<E> node = head;

        for (int i = 0; i < index; i++) {
            node = getNextNode(node, i);
        }

        return node;
    }

    private Node<E> getNextNode(Node<E> node, int nodeIndex) {
        Node<E> nextNode = node.getNext();
        int nodeMaxIndex = size - 1;

        if (nextNode == null && nodeIndex < nodeMaxIndex) {
            throw new RuntimeException("Size = " + size + ", nodeIndex = " + (nodeIndex + 1) + ", node = NULL");
        }

        return nextNode;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        chekHead();
        StringBuilder result = new StringBuilder();
        Node<E> node = head;

        for (int i = 0; i < size; i++) {
            result.append("[").append(node.getData()).append("],");
            node = getNextNode(node, i);
        }

        return result.substring(0, result.length() - 1);
    }
}
