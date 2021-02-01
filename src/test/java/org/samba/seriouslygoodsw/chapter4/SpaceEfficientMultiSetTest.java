package org.samba.seriouslygoodsw.chapter4;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class SpaceEfficientMultiSetTest {

    @Nested
    class SimpleArrayMultiSet {
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

        class ArrayMultiSet<T> implements MultiSet<T> {
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

    @Nested
    class DuplicateFreeArrayTest {

        @Test
        public void notFound() {
            var uut = new DuplicateFreeArrayMultiSet<>(String.class);
            assertThat(uut.count("a")).isEqualTo(0);
        }

        @Test
        public void addingOneElement() {
            var uut = new DuplicateFreeArrayMultiSet<>(String.class);
            uut.add("a");
            assertThat(uut.count("a")).isEqualTo(1);
        }

        @Test
        public void addingMultipleElements() {
            var uut = new DuplicateFreeArrayMultiSet<>(String.class);
            uut.add("a");
            uut.add("b");
            uut.add("a");
            uut.add("a");
            assertThat(uut.count("a")).isEqualTo(3);
            assertThat(uut.count("b")).isEqualTo(1);
        }

        /**
         * Multiset which exactly uses the space required for the elements to carry.
         *
         * count(...) is O(n)  (has to lookup the index)
         * add(...) is O(n) ?  or O(n^2) ?
         *
         * Shout outs to Horst.
         *
         * @param <T>
         */
        class DuplicateFreeArrayMultiSet<T> implements MultiSet<T> {
            public static final int ELEMENT_NOT_FOUND = -1;
            private T[] uniqueElements;
            private int[] elementCounts;

            public DuplicateFreeArrayMultiSet(Class<T> clazz) {
                uniqueElements = (T[]) Array.newInstance(clazz, 0);
                elementCounts = new int[0];
            }

            @Override
            public void add(T elem) {
                int index = getIndex(elem);
                if (index == ELEMENT_NOT_FOUND) {
                    addElement(elem);
                } else {
                    elementCounts[index]++;
                }
            }

            private void addElement(T elem) {
                // increase
                uniqueElements = Arrays.copyOf(uniqueElements, uniqueElements.length + 1);
                elementCounts = Arrays.copyOf(elementCounts, elementCounts.length + 1);
                uniqueElements[uniqueElements.length - 1] = elem;
                elementCounts[elementCounts.length - 1] = 1;
            }

            private int getIndex(T elem) {
                for (int i = 0; i < uniqueElements.length; i++) {
                    if (uniqueElements[i].equals(elem)) return i;
                }
                return ELEMENT_NOT_FOUND;
            }

            @Override
            public long count(T elem) {
                int index = getIndex(elem);
                return index == ELEMENT_NOT_FOUND ? 0 : elementCounts[index];
            }
        }
    }

}
