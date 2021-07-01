public class ArrayDeque<T> implements Deque<T> {
    /**
     *  Invariants:
     *  1. items[size-1] is the last item
     * */
    private static final int INITIAL_CAPACITY = 8;
    private static final int RESIZE_FACTOR = 2;
    private static final double USAGE_RATIO = 0.25;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int capacity;


    public ArrayDeque() {
        /*Create an empty deque*/
        items = (T[]) new Object[INITIAL_CAPACITY];
        capacity = INITIAL_CAPACITY;
        size = 0;
        nextFirst = 0;
        nextLast = 1;
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
    public T get(int idx) {
        if (idx >= size) { return null; }
        return items[(nextFirst + 1 + idx)%capacity];
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(capacity*RESIZE_FACTOR);
        }

        items[nextLast] = item;
        nextLast = idxAdd(nextLast,1);
        size += 1;
    }


    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(capacity*RESIZE_FACTOR);
        }

        items[nextFirst] = item;
        nextFirst = idxAdd(nextFirst,-1);
        size += 1;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {return null;}
        shrink();

        int last = idxAdd(nextLast,-1);
        T res = items[last];
        items[last] = null;
        nextLast = last;
        size -= 1;
        return res;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {return null;}
        shrink();

        int first = idxAdd(nextFirst,1);
        T res = items[first];
        items[first] = null;
        nextFirst = first;
        size -= 1;
        return res;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " " );
        }
    }

    private int idxAdd(int i, int j) {
        return (i + j + capacity) % capacity;
    }

    private boolean isFull() {
        return size == capacity;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }

        nextFirst = capacity - 1;
        nextLast = size;
        this.capacity = capacity;
        items = newItems;
    }

    private void shrink() {
        double usage_ratio = (double) size/capacity;
        if (usage_ratio < USAGE_RATIO && capacity > INITIAL_CAPACITY) {
            resize(capacity/2);
        }
    }

}
