package ru.academits.drozdetskiy21.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final LinkedList<T>[] listsArray;
    private int size;
    private int modificationsCount;

    public HashTable() {
        //noinspection unchecked
        listsArray = new LinkedList[71];
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity: " + capacity + ", must be > 0");
        }

        //noinspection unchecked
        listsArray = new LinkedList[capacity];
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
        int objectHashCode = Objects.hashCode(o);

        for (LinkedList<T> list : listsArray) {
            if (list != null) {
                for (T element : list) {
                    if ((objectHashCode == Objects.hashCode(element)) && Objects.equals(o, element)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int initialModificationsCount = modificationsCount;
            private final T[] elements = getElements();
            private int index = -1;

            @Override
            public boolean hasNext() {
                return elements.length - index > 1;
            }

            @Override
            public T next() {
                if (initialModificationsCount != modificationsCount) {
                    throw new ConcurrentModificationException("List is modified.");
                }

                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements.");
                }

                ++index;
                return elements[index];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(getElements(), size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new NullPointerException("Specified array = NULL");
        }

        T[] elements = getElements();

        if (a.length < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, a, 0, size);
        Arrays.fill(a, size, a.length, null);

        return a;
    }

    private T[] getElements() {
        //noinspection unchecked
        T[] elements = (T[]) new Object[size];
        int i = 0;

        for (LinkedList<T> list : listsArray) {
            if (list != null) {
                for (T element : list) {
                    elements[i] = element;
                    ++i;
                }
            }
        }

        return elements;
    }

    @Override
    public boolean add(T t) {
        int addedElementHashCode = Objects.hashCode(t);
        int index = Math.abs(addedElementHashCode % listsArray.length);

        if (listsArray[index] == null) {
            listsArray[index] = new LinkedList<>();
        } else {
            for (T element : listsArray[index]) {
                if ((addedElementHashCode == Objects.hashCode(element)) && Objects.equals(t, element)) {
                    return false;
                }
            }
        }

        listsArray[index].add(t);
        ++size;
        ++modificationsCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int objectHashCode = Objects.hashCode(o);
        int index = Math.abs(objectHashCode % listsArray.length);

        if (listsArray[index] == null) {
            return false;
        }

        for (Iterator<T> iterator = listsArray[index].iterator(); iterator.hasNext(); ) {
            T element = iterator.next();

            if ((objectHashCode == Objects.hashCode(element)) && Objects.equals(o, element)) {
                iterator.remove();
                --size;
                ++modificationsCount;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        for (T element : c) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        int initialSizeValue = size;

        for (Object o : c) {
            remove(o);
        }

        return size - initialSizeValue > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        int initialSizeValue = size;

        for (LinkedList<T> list : listsArray) {
            if (list != null) {
                for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
                    if (!c.contains(iterator.next())) {
                        iterator.remove();
                        --size;
                        ++modificationsCount;
                    }
                }
            }
        }

        return size - initialSizeValue > 0;
    }

    @Override
    public void clear() {
        if (size > 0) {
            Arrays.fill(listsArray, null);
            size = 0;
            ++modificationsCount;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (LinkedList<T> list : listsArray) {
            if (list != null) {
                for (T element : list) {
                    stringBuilder.append(element).append(", ");
                }
            }
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]").toString();
    }
}
