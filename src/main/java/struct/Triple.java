package struct;

import java.util.Objects;

/**
 * @since 2021/10/4 16:02
 */
public class Triple<T, U, V> {
    T first;
    U second;
    V third;

    public static <T, U, V> Triple<T, U, V> of(T first, U second, V third) {
        return new Triple<>(first, second, third);
    }

    public Triple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(first, triple.first) && Objects.equals(second, triple.second) && Objects.equals(third, triple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
