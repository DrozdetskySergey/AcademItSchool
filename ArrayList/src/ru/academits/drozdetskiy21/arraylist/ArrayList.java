package ru.academits.drozdetskiy21.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    T[] array = (T[]) new Object[10];
    int size = 0;
    long modificationCount = 0;

    public ArrayList() {
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new RuntimeException("Задана вместимость: " + capacity);
        }

        array = Arrays.copyOf(array, capacity);
    }

    public ArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Передана Collection = NULL");
        }

        size = c.size();
        array = Arrays.copyOf(array, size);

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
        ++modificationCount;
        setMinCapacity(size + 1);
        array[size] = t;
        ++size;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int removeIndex = -1;

        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    removeIndex = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (array[i] != null && array[i].equals(o)) {
                    removeIndex = i;
                    break;
                }
            }
        }

        if (removeIndex >= 0) {
            ++modificationCount;
            System.arraycopy(array, removeIndex + 1, array, removeIndex, (size - 1) - removeIndex);
            --size;
            array[size] = null;

            return true;
        }

        return false;
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
        if (c == null) {
            return false;
        }

        ++modificationCount;
        addCollection(size, c);

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            return false;
        }

        chekIndex(index);
        ++modificationCount;
        addCollection(index, c);

        return true;
    }

    private void addCollection(int index, Collection<? extends T> c) {
        int collectionSize = c.size();
        setMinCapacity(size + collectionSize);
        System.arraycopy(array, index, array, index + collectionSize, (size - 1) - index);
        size += collectionSize;

        int i = 0;

        for (T t : c) {
            array[index + i] = t;
            ++i;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, false);
    }

    private boolean batchRemove(Collection<?> c, boolean isRemoved) {
        if (c == null) {
            return false;
        }

        boolean[] removeFlags = new boolean[size];
        Arrays.fill(removeFlags, !isRemoved);
        int removeCount = isRemoved ? 0 : size;

        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                removeFlags[i] = isRemoved;
                removeCount = isRemoved ? removeCount + 1 : removeCount - 1;
            }
        }

        if (removeCount == 0) {
            return false;
        }

        ++modificationCount;
        T[] newArray = (T[]) new Object[size - removeCount];
        int i = 0;

        for (int j = 0; j < size; j++) {
            if (!removeFlags[j]) {
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
        chekIndex(index);

        return array[index];
    }

    @Override
    public T set(int index, T element) {
        chekIndex(index);
        ++modificationCount;
        T oldElement = array[index];
        array[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        chekIndex(index);
        ++modificationCount;
        setMinCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, (size - 1) - index);
        array[index] = element;
        ++size;
    }

    @Override
    public T remove(int index) {
        chekIndex(index);
        ++modificationCount;
        T removeElement = array[index];
        System.arraycopy(array, index + 1, array, index, (size - 1) - index);
        --size;
        array[size] = null;

        return removeElement;
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
        for (int i = size - 1; i >= 0; i--) {
            if ((o == null && array[i] == null) || (array[i] != null && array[i].equals(o))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new RuntimeException("Этот метод не реализован.");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException("Этот метод не реализован.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Этот метод не реализован.");
    }

    public void ensureCapacity(int minCapacity) {
        ++modificationCount;
        setMinCapacity(minCapacity);
    }

    public void trimToSize() {
        if (array.length > size) {
            ++modificationCount;
            T[] newArray = Arrays.copyOf(array, size);
            Arrays.fill(array, null);
            array = newArray;
        }
    }

    private void chekIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Index = " + index + ". Список пустой!");
        }

        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index = " + index + ". Допустимые границы: [0, " + (size - 1) + "].");
        }
    }

    private void setMinCapacity(int minCapacity) {
        if (minCapacity <= array.length) {
            return;
        }

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

        StringBuilder string = new StringBuilder();

        for (int i = 0; i < size; i++) {
            string.append("[").append(array[i]).append("],");
        }

        return string.substring(0, string.length() - 1);
    }
}
