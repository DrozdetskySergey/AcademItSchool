package ru.academits.drozdetskiy21.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private T[] array;
    private int size;
    private long modificationCount;

    public ArrayList() {
        array = (T[]) new Object[10];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity: " + capacity);
        }

        array = (T[]) new Object[capacity];
    }

    public ArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Collection = NULL");
        }

        T[] collectionArray = (T[]) c.toArray();
        size = collectionArray.length;
        array = (T[]) new Object[size];
        System.arraycopy(collectionArray, 0, array, 0, size);
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
        ++modificationCount;

        return new Iterator<>() {
            private final long modificationCountValue = modificationCount;
            private int iteratorIndex = -1;
            private boolean isCanBeRemoved;

            @Override
            public boolean hasNext() {
                checkModificationCount();

                return size - 1 > iteratorIndex;
            }

            @Override
            public T next() {
                checkModificationCount();
                ++iteratorIndex;
                isCanBeRemoved = true;

                if (iteratorIndex >= size) {
                    throw new NoSuchElementException("No more elements.");
                }

                return array[iteratorIndex];
            }

            @Override
            public void remove() {
                if (!isCanBeRemoved) {
                    throw new IllegalStateException("Illegal position for removing.");
                }

                checkModificationCount();
                System.arraycopy(array, iteratorIndex + 1, array, iteratorIndex, (size - 1) - iteratorIndex);
                --size;
                array[size] = null;
                isCanBeRemoved = false;
                --iteratorIndex;
            }

            private void checkModificationCount() {
                if (modificationCountValue != modificationCount) {
                    throw new ConcurrentModificationException("List is modified.");
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new IllegalArgumentException("Array = null");
        }

        if (a.length < size) {
            return (T1[]) Arrays.copyOf(array, size);
        }

        System.arraycopy(array, 0, a, 0, size);
        Arrays.fill(a, size, a.length, null);

        return a;
    }

    @Override
    public boolean add(T t) {
        ++modificationCount;
        setMinCapacity(size + 1);
        array[size] = t;
        ++size;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int removedIndex = indexOf(o);

        if (removedIndex < 0) {
            return false;
        }

        ++modificationCount;
        System.arraycopy(array, removedIndex + 1, array, removedIndex, (size - 1) - removedIndex);
        --size;
        array[size] = null;

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            return false;
        }

        for (Object o : c) {
            if (indexOf(o) < 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addCollection(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index == size) {
            return addCollection(size, c);
        } else {
            checkIndex(index);
        }

        return addCollection(index, c);
    }

    private boolean addCollection(int index, Collection<? extends T> c) {
        if (c == null || c.size() == 0) {
            return false;
        }

        ++modificationCount;
        int collectionSize = c.size();
        T[] collectionArray = (T[]) c.toArray();
        setMinCapacity(size + collectionSize);
        System.arraycopy(array, index, array, index + collectionSize, size - index);
        System.arraycopy(collectionArray, 0, array, index, collectionSize);
        size += collectionSize;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, false);
    }

    private boolean batchRemove(Collection<?> c, boolean isRemove) {
        if (c == null || c.size() == 0) {
            return false;
        }

        boolean[] booleansArray = new boolean[size]; // equivalent array element be deleted or not
        Arrays.fill(booleansArray, !isRemove);
        int removedCount = isRemove ? 0 : size;

        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                booleansArray[i] = isRemove;
                removedCount = isRemove ? removedCount + 1 : removedCount - 1;
            }
        }

        if (removedCount == 0) {
            return false;
        }

        ++modificationCount;
        T[] newArray = (T[]) new Object[size - removedCount];
        int i = 0;

        for (int j = 0; j < size; j++) {
            if (!booleansArray[j]) {
                newArray[i] = array[j];
                ++i;
            }
        }

        size = i;
        Arrays.fill(array, null);
        array = newArray;

        return true;
    }

    @Override
    public void clear() {
        ++modificationCount;
        size = 0;
        Arrays.fill(array, null);
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return array[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        ++modificationCount;
        T oldElement = array[index];
        array[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index == size) {
            add(element);

            return;
        }

        checkIndex(index);
        ++modificationCount;
        setMinCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        ++size;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        ++modificationCount;
        T removedElement = array[index];
        System.arraycopy(array, index + 1, array, index, (size - 1) - index);
        --size;
        array[size] = null;

        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public void ensureCapacity(int minCapacity) {
        ++modificationCount;
        setMinCapacity(minCapacity);
    }

    public void trimToSize() {
        if (array.length == size) {
            return;
        }

        ++modificationCount;
        T[] newArray = Arrays.copyOf(array, size);
        Arrays.fill(array, null);
        array = newArray;
    }

    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = 0");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Bounds: [0, " + (size - 1) + "]");
        }
    }

    private void setMinCapacity(int minCapacity) {
        if (minCapacity <= array.length) {
            return;
        }

        ++modificationCount;
        int arrayMagnificationCoefficient = 2;
        int newArrayLength = array.length <= 5 ? 10 : array.length * arrayMagnificationCoefficient;

        while (minCapacity > newArrayLength) {
            newArrayLength *= arrayMagnificationCoefficient;
        }

        T[] newArray = Arrays.copyOf(array, newArrayLength);
        Arrays.fill(array, null);
        array = newArray;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            stringBuilder.append(array[i]).append(", ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]").toString();
    }
}
