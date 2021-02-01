package org.samba.seriouslygoodsw.chapter4;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class SpaceEfficientMultiSetTest {

    @Test
    public void notFound() {
        var uut = new ArrayMultiSet<>(String.class);
        assertThat(uut.count("a")).isEqualTo(0);
    }

    @Test
    public void addingOneElement() {
        var uut = new ArrayMultiSet<>(String.class);
        uut.add("a");
        assertThat(uut.count("a")).isEqualTo(1);
    }

    @Test
    public void addingMultipleElements() {
        var uut = new ArrayMultiSet<>(String.class);
        uut.add("a");
        uut.add("b");
        uut.add("a");
        uut.add("a");
        assertThat(uut.count("a")).isEqualTo(3);
        assertThat(uut.count("b")).isEqualTo(1);
    }

    static class ArrayMultiSet<T> implements MultiSet<T> {
        private T[] elements;

        public ArrayMultiSet(Class<T> clazz) {
            elements = (T[]) Array.newInstance(clazz, 0);
        }

        @Override
        public void add(T elem) {
            elements = Arrays.copyOf(elements, elements.length + 1);
            elements[elements.length - 1] = elem;
        }

        @Override
        public long count(T elem) {
            int i = 0;
            int foundCnt = 0;
            while (elements.length > 0 && i < elements.length) {
                if (elements[i].equals(elem)) {
                    foundCnt++;
                }
                i++;
            }
            return foundCnt;
        }
    }

}
