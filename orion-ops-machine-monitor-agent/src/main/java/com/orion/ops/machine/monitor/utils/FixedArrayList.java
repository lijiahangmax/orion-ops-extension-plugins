package com.orion.ops.machine.monitor.utils;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.utils.Valid;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 固长集合
 * <p>
 * 添加元素时超长则会删除头部元素
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 16:54
 */
public class FixedArrayList<E> extends ArrayList<E> {

    /**
     * 最大数量
     */
    private final int maxSize;

    public FixedArrayList(int maxSize) {
        super();
        this.maxSize = Valid.gt(maxSize, Const.N_0);
    }

    @Override
    public boolean add(E e) {
        this.removeIfFull(1);
        return super.add(e);
    }

    @Override
    public void add(int index, E element) {
        this.removeIfFull(1);
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Valid.lte(c.size(), maxSize);
        this.removeIfFull(c.size());
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Valid.lte(c.size(), maxSize);
        this.removeIfFull(c.size());
        return super.addAll(index, c);
    }

    /**
     * 如果集合已满则删除
     *
     * @param count 添加数量
     */
    private void removeIfFull(int count) {
        int newSize = this.size() + count;
        if (newSize > maxSize) {
            this.subList(0, newSize - maxSize).clear();
        }
    }

}
