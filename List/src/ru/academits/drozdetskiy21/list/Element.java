package ru.academits.drozdetskiy21.list;

class Element<E> {
    private E data;
    private Element<E> next;

    public Element(E data) {
        this.data = data;
        next = null;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Element<E> getNext() {
        return next;
    }

    public void setNext(Element<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
