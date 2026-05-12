package util;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

public class BinaryHeapTest {

    private static final Comparator<Integer> MIN_COMPARATOR = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };

    @Test
    public void addAndRemoveMaintainsOrder() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(5);
        heap.add(3);
        heap.add(7);
        heap.add(1);
        heap.add(4);

        assertEquals(5, heap.size());
        assertEquals(Integer.valueOf(1), heap.remove());
        assertEquals(Integer.valueOf(3), heap.remove());
        assertEquals(Integer.valueOf(4), heap.remove());
        assertEquals(Integer.valueOf(5), heap.remove());
        assertEquals(Integer.valueOf(7), heap.remove());
        assertTrue(heap.size() == 0);
    }

    @Test
    public void removeSpecificElementMaintainsOrder() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(10);
        heap.add(20);
        heap.add(30);
        heap.add(40);
        heap.add(50);

        heap.remove(30);

        assertEquals(4, heap.size());
        assertEquals(Integer.valueOf(10), heap.remove());
        assertEquals(Integer.valueOf(20), heap.remove());
        assertEquals(Integer.valueOf(40), heap.remove());
        assertEquals(Integer.valueOf(50), heap.remove());
    }

    @Test
    public void removeElementThatNeedsBubbleUp() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(1);
        heap.add(100);
        heap.add(2);
        heap.add(101);
        heap.add(102);
        heap.add(3);
        heap.add(4);

        heap.remove(101);

        assertTrue(isValidMinHeap(heap));
        assertEquals(6, heap.size());
    }

    @Test
    public void removeElementThatNeedsSiftDown() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(1);
        heap.add(3);
        heap.add(2);
        heap.add(10);
        heap.add(11);
        heap.add(4);
        heap.add(5);

        heap.remove(3);

        assertTrue(isValidMinHeap(heap));
        assertEquals(6, heap.size());
    }

    @Test
    public void removeLastElementIsNoOp() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(10);
        heap.add(20);
        heap.add(30);

        heap.remove(30);

        assertEquals(2, heap.size());
        assertTrue(isValidMinHeap(heap));
    }

    @Test
    public void removeNonExistentElementDoesNothing() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(1);
        heap.add(2);

        heap.remove(99);

        assertEquals(2, heap.size());
    }

    @Test
    public void containsReturnsCorrectly() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(42);
        heap.add(7);

        assertTrue(heap.contains(42));
        assertTrue(heap.contains(7));
        assertFalse(heap.contains(99));
    }

    @Test
    public void clearResetsHeap() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(1);
        heap.add(2);
        heap.add(3);

        heap.clear();

        assertEquals(0, heap.size());
        assertNull(heap.remove());
        assertFalse(heap.contains(1));
    }

    @Test
    public void removeFromEmptyHeapReturnsNull() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        assertNull(heap.remove());
    }

    @Test
    public void addRemoveManyElements() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        for (int i = 0; i < 1000; i++) {
            heap.add(i);
        }
        assertEquals(1000, heap.size());

        for (int i = 0; i < 1000; i++) {
            assertEquals(Integer.valueOf(i), heap.remove());
        }
        assertTrue(heap.size() == 0);
    }

    @Test
    public void interleavedAddRemove() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(MIN_COMPARATOR);
        heap.add(10);
        heap.add(5);
        assertEquals(Integer.valueOf(5), heap.remove());
        heap.add(7);
        heap.add(3);
        assertEquals(Integer.valueOf(3), heap.remove());
        heap.add(8);
        assertEquals(Integer.valueOf(7), heap.remove());
        assertEquals(Integer.valueOf(8), heap.remove());
        assertEquals(Integer.valueOf(10), heap.remove());
    }

    private <E> boolean isValidMinHeap(BinaryHeap<E> heap) {
        for (int i = 1; i <= heap.size / 2; i++) {
            int left = i * 2;
            int right = i * 2 + 1;
            if (left <= heap.size && heap.comparator.compare((E) heap.array[i], (E) heap.array[left]) > 0) {
                return false;
            }
            if (right <= heap.size && heap.comparator.compare((E) heap.array[i], (E) heap.array[right]) > 0) {
                return false;
            }
        }
        return true;
    }
}
