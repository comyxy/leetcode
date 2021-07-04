package concurrent;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;

/**
 * @author comyxy
 */
public class TaggedArray<T> {
    private final Object[] elements;

    public TaggedArray(T[] data, Object[] tags) {
        int size = data.length;
        if (size != tags.length) {
            throw new IllegalArgumentException();
        }
        this.elements = new Object[2 * size];
        for (int i = 0, j = 0; i < size; i++) {
            elements[j++] = data[i];
            elements[j++] = tags[i];
        }
    }

    public Spliterator<T> spliterator() {
        return new TaggedSpliterator<>(elements, 0, elements.length);
    }

    private static class TaggedSpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int origin;
        private int fence;

        public TaggedSpliterator(Object[] array, int origin, int fence) {
            this.array = array;
            this.origin = origin;
            this.fence = fence;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            for (; origin < fence; origin += 2) {
                action.accept((T) array[origin]);
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (origin < fence) {
                action.accept((T) array[origin]);
                origin += 2;
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            int lo = origin;
            int mid = ((lo + fence) >>> 1) & ~1;
            if (lo < mid) {
                origin = mid;
                return new TaggedSpliterator<>(array, lo, mid);
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return (long) (fence - origin) / 2;
        }

        @Override
        public int characteristics() {
            return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
        }
    }


    private static <T> void parEach(TaggedArray<T> a, Consumer<T> action) {
        Spliterator<T> spliterator = a.spliterator();
        long batchSize = spliterator.estimateSize() / 2;
        new ParEach<>(null, spliterator, action, batchSize).invoke();
    }

    private static class ParEach<T> extends CountedCompleter<Void> {
        final Spliterator<T> spliterator;
        final Consumer<T> action;
        final long batchSize;

        public ParEach(ParEach<T> parent, Spliterator<T> spliterator, Consumer<T> action, long batchSize) {
            super(parent);
            this.spliterator = spliterator;
            this.action = action;
            this.batchSize = batchSize;
        }

        @Override
        public void compute() {
            Spliterator<T> sub;
            while (spliterator.estimateSize() > batchSize && (sub = spliterator.trySplit()) != null) {
                addToPendingCount(1);
                new ParEach<>(this, sub, action, batchSize).fork();
            }
            spliterator.forEachRemaining(action);
            propagateCompletion();
        }
    }

    public static void main(String[] args) {
        Integer[] data = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        Object[] tags = new Object[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        TaggedArray<Integer> array = new TaggedArray<>(data, tags);
        Consumer<Integer> action = val -> System.out.println("consume value: " + val);
        parEach(array, action);
    }
}
