package ru.academits.drozdetskiy21.list;

public class List<E> {
    private Element<E> head;
    private int size;

    public List() {
        head = null;
        size = 0;
    }

    public List(java.util.List<E> baseList) {
        for (E e : baseList) {
            add(e);
        }
    }

    public int getSize() {
        return size;
    }

    public E getFirstOne() {
        return getElement(0).getData();
    }

    public E get(int index) {
        return getElement(index).getData();
    }

    public E set(int index, E newDate) {
        Element<E> element = getElement(index);
        E oldData = element.getData();
        element.setData(newDate);

        return oldData;
    }

    public void addFirstOne(E newData) {
        add(0, newData);
    }

    public void add(int index, E newData) {
        chekIndex(index);
        Element<E> newElement = new Element<>(newData);

        if (index == 0) {
            newElement.setNext(head);
            head = newElement;
        } else {
            Element<E> element = getElement(index - 1);
            newElement.setNext(element.getNext());
            element.setNext(newElement);
        }

        ++size;
    }

    public void add(E newData) {
        Element<E> newElement = new Element<>(newData);

        if (size == 0) {
            head = newElement;
        } else {
            Element<E> element = getElement(size - 1);
            element.setNext(newElement);
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
            Element<E> element = getElement(index - 1);
            removeData = element.getNext().getData();
            element.setNext(element.getNext().getNext());
        }

        --size;

        return removeData;
    }

    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        if (head.getData().equals(o)) {
            head = head.getNext();
            --size;

            return true;
        }

        Element<E> element = head.getNext();
        Element<E> prevElement = head;

        if (size > 1 && element == null) {
            throw new RuntimeException("Size = " + size + ", index = 0, nextElement -> NULL");
        }

        int elementMaxIndexWithoutTail = size - 2;

        for (int i = 1; i < size; i++) {
            if (element.getData().equals(o)) {
                prevElement.setNext(element.getNext());
                --size;

                return true;
            } else {
                prevElement = element;
                element = element.getNext();

                if (i <= elementMaxIndexWithoutTail && element == null) {
                    throw new RuntimeException("Size = " + size + ", index = " + i + ", nextElement -> NULL");
                }
            }
        }

        return false;
    }

    public void reverse() {

    }

    public List<E> getClone() {
        return this;
    }

    private void chekIndex(int index) {
        if (size == 0) {
            throw new NullPointerException("Список пустой!");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Допустимые границы: [0, " + (size - 1) + "].");
        }
    }

    private Element<E> getElement(int index) {
        chekIndex(index);

        Element<E> element = head;

        if (index > 0) {
            for (int i = 0; i < index; i++) {
                element = element.getNext();

                if (element == null) {
                    throw new RuntimeException("Size = " + size + ", index = " + i + ", nextElement -> NULL");
                }
            }
        }

        return element;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder();
        Element<E> element = head;

        for (int i = 0; i < size; i++) {
            result.append("[").append(element.getData()).append("],");
            element = element.getNext();
        }

        return result.substring(0, result.length() - 1);
    }
}
