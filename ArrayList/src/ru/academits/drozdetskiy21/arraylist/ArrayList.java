package ru.academits.drozdetskiy21.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private T[] elements;
    private int size;
    private int modificationsCount;

    public ArrayList() {
        //noinspection unchecked
        elements = (T[]) new Object[10];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity: " + capacity + ", must be >= 0");
        }

        //noinspection unchecked
        elements = (T[]) new Object[capacity];
    }

    public ArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        size = c.size();
        //noinspection unchecked
        elements = (T[]) new Object[size];

        int i = 0;

        for (T element : c) {
            elements[i] = element;
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
        return new Iterator<>() {
            private final int initialModificationsCount = modificationsCount;
            private int index = -1;
            private boolean canBeRemoved;

            @Override
            public boolean hasNext() {
                return size - index > 1;
            }

            @Override
            public T next() {
                checkModificationCount();

                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements.");
                }

                ++index;
                canBeRemoved = true;
                return elements[index];
            }

            @Override
            public void remove() {
                if (!canBeRemoved) {
                    throw new IllegalStateException("Illegal position for removing.");
                }

                checkModificationCount();

                System.arraycopy(elements, index + 1, elements, index, (size - 1) - index);
                elements[size - 1] = null;
                canBeRemoved = false;
                --index;
                --size;
            }

            private void checkModificationCount() {
                if (initialModificationsCount != modificationsCount) {
                    throw new ConcurrentModificationException("List is modified.");
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new NullPointerException("Specified array = NULL");
        }

        if (a.length < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, a, 0, size);
        Arrays.fill(a, size, a.length, null);

        return a;
    }

    @Override
    public boolean add(T t) {
        add(size, t);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index < 0) {
            return false;
        }

        remove(index);

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        if (c.size() == 0) {
            return false;
        }

        if (index != size) {
            checkIndex(index);
        }

        if (c == this) {
            //noinspection unchecked
            c = Arrays.asList((T[]) toArray());
        }

        int collectionSize = c.size();
        ensureMinCapacity(size + collectionSize);
        System.arraycopy(elements, index, elements, index + collectionSize, size - index);

        int i = index;

        for (T element : c) {
            elements[i] = element;
            ++i;
        }

        size += collectionSize;
        ++modificationsCount;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeOrRetainElements(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return removeOrRetainElements(c, false);
    }

    private boolean removeOrRetainElements(Collection<?> c, boolean isRemove) {
        if (c == null) {
            throw new NullPointerException("Collection = NULL");
        }

        int i = 0;

        for (int j = 0; j < size; j++) {
            if (c.contains(elements[j]) == !isRemove) {
                elements[i] = elements[j];
                ++i;
            }
        }

        if (i == size) {
            return false;
        }

        Arrays.fill(elements, i, size, null);
        size = i;
        ++modificationsCount;

        return true;
    }

    @Override
    public void clear() {
        if (size > 0) {
            Arrays.fill(elements, 0, size, null);
            size = 0;
            ++modificationsCount;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return elements[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);

        T oldElement = elements[index];
        elements[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index != size) {
            checkIndex(index);
        }

        ensureMinCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        ++size;
        ++modificationsCount;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T removedElement = elements[index];
        System.arraycopy(elements, index + 1, elements, index, (size - 1) - index);
        elements[size - 1] = null;
        --size;
        ++modificationsCount;

        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, elements[i])) {
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
        if (minCapacity > elements.length) {
            elements = Arrays.copyOf(elements, minCapacity);
            ++modificationsCount;
        }
    }

    public void trimToSize() {
        if (elements.length > size) {
            elements = Arrays.copyOf(elements, size);
            ++modificationsCount;
        }
    }

    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = 0");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Bounds: [0, " + (size - 1) + "]");
        }
    }

    private void ensureMinCapacity(int minCapacity) {
        if (minCapacity <= elements.length) {
            return;
        }

        int arrayMagnificationCoefficient = 2;
        int newArrayLength = elements.length <= 5 ? 10 : elements.length * arrayMagnificationCoefficient;

        while (minCapacity > newArrayLength) {
            newArrayLength *= arrayMagnificationCoefficient;
        }

        elements = Arrays.copyOf(elements, newArrayLength);
        ++modificationsCount;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            stringBuilder.append(elements[i]).append(", ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]").toString();
    }
}
