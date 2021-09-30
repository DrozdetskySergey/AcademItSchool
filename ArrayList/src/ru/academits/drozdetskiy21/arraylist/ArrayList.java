package ru.academits.drozdetskiy21.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    T[] array;
    int size = 0;

    public ArrayList() {
        array = (T[]) new Object[10];
    }

    public ArrayList(int capacity) {
        if (capacity >= 0) {
            array = (T[]) new Object[capacity];
        } else {
            throw new IllegalArgumentException("Задана вместимость: " + capacity);
        }
    }

    public ArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Передана Collection: NULL");
        }

        size = c.size();
        array = (T[]) new Object[size];

        int i = 0;

        for (T t : c) {
            array[i] = t;
            ++i;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(array, size);
        }

        for (int i = 0; i < size; i++) {
            a[i] = (T1) array[i];
        }

        for (int i = size; i < a.length; i++) {
            a[i] = null;
        }

        return a;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (array[i] != null && array[i].equals(o)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0 ; i--) {
            if ((o == null && array[i] == null) || (array[i] != null && array[i].equals(o))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        java.util.ArrayList<String> arrayList = new java.util.ArrayList<>();
        arrayList.add("");

        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private void chekIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Список пустой!");
        }

        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index = " + index + ", Допустимые границы: [0, " + (size - 1) + "].");
        }
    }
}
