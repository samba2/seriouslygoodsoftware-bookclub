package org.samba.seriouslygoodsw.chapter4;

public interface MultiSet<T> {
    public void add(T elem);
    public long count(T elem);
}
