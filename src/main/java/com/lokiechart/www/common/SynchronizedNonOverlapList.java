package com.lokiechart.www.common;

import lombok.ToString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
@ToString
public class SynchronizedNonOverlapList<E> implements Iterable<E> {

    private final List<E> list;

    public SynchronizedNonOverlapList(List<E> list) {
        this.list = list.stream().distinct().collect(Collectors.toCollection(LinkedList::new));
    }

    public SynchronizedNonOverlapList(E[] array) {
        this.list = Arrays.stream(array).distinct().collect(Collectors.toCollection(LinkedList::new));
    }

    public void add(E e) {
        synchronized (list) {
            list.remove(e);
            list.add(e);
        }
    }

    public void addAll(SynchronizedNonOverlapList<E> c) {
        synchronized (list) {
            for (E e : c.list) {
                add(e);
            }
        }
    }

    public E get(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    public int size() {
        return list.size();
    }

    public void removeOldest() {
        synchronized (list) {
            list.remove(0);
        }
    }

    public SynchronizedNonOverlapList<E> copy(int startIndex, int endIndex) {
        return new SynchronizedNonOverlapList<>(list.subList(startIndex, endIndex));
    }

    public int indexOf(E e) {
        return list.indexOf(e);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    public Stream<E> stream() {
        return list.stream();
    }

}
