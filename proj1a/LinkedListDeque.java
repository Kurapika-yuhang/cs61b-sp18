import jdk.dynalink.NamedOperation;

public class LinkedListDeque<T> {
    /**
     * double ended queue
     * */

    private Node sentinel;
    private int size;

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        /* Create an empty list deque */
        sentinel = new Node(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        Node oldLast = sentinel.prev;
        Node newNode = new Node(item,sentinel.prev,sentinel);
        oldLast.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) return null;
        T result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return result;
    }

    public T removeLast() {
        if (size == 0) return null;
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return result;
    }

    public T get(int index) {
        if (index >= size) return null;

        Node p = sentinel.next; // 不可能是null
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursive(Node node, int index) {
        /* return index th item start from node */
        if (index == 0) return node.item;
        return getRecursive(node.next,index-1);
    }
    public T getRecursive(int index) {
        if (index >= size) return null;
        return getRecursive(sentinel.next,index);
    }

    public void printDeque() {
        Node p = sentinel.next;
        for(int i = 0; i < size; i++) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }


    public static void main(String[] args) {
        LinkedListDeque<Integer> l = new LinkedListDeque<>();
        l.addFirst(1);
        l.addFirst(0);
        l.addLast(2);
        l.printDeque();

        int a = l.getRecursive(0);
        int a1 = l.get(0);


        int b = l.getRecursive(1);
        int b1 = l.get(1);

    }
}
