package cn.javgo.dynamic_array;

import java.util.NoSuchElementException;

/**
 * Desc: 动态数组实现
 *
 * @author javgo
 * @create 2024-08-06 18:33
 */
public class MyArrayList<E> {

    // 真正存储数据的底层数组
    private E[] data;

    // 数组中实际存储的元素个数
    private int size;

    // 默认数组容量
    private static final int INIT_CAPACITY = 10;

    /**
     * 默认构造方法
     */
    public MyArrayList() {
        this(INIT_CAPACITY);
    }

    /**
     * 可以自定义数组容量的构造方法
     *
     * @param initCapacity 初始容量
     */
    public MyArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity];
        size = 0;
    }

    /**
     * 添加最后一个元素
     *
     * @param e 元素
     */
    public void addLast(E e) {
        // 获取数组容量
        int capacity = data.length;

        // 检查数组容量是否足够, 不够则扩容至实际存储数组的2倍
        if (size == capacity) {
            resize(capacity * 2);
        }

        // 添加元素
        data[size] = e;
        // 元素个数加1
        size++;
    }

    /**
     * 添加指定索引位置的元素
     *
     * @param index 索引
     * @param e     元素
     */
    public void add(int index, E e) {
        // 检查索引是否合法
        checkPositionIndex(index);

        // 获取数组容量
        int capacity = data.length;
        // 检查数组容量是否足够, 不够则扩容至实际存储数组的2倍
        if (capacity == size) {
            resize(capacity * 2);
        }

        // 移动 index 和后面的元素，为 index 的位置腾出空间
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }

        // 添加元素
        data[index] = e;

        // 元素个数加1
        size++;
    }

    /**
     * 添加第一个元素
     *
     * @param e 元素
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 删除最后一个元素
     *
     * @return 被删除的元素
     */
    public E removeLast() {
        // 如果时空数组，则抛出异常
        if (size == 0) {
            throw new NoSuchElementException();
        }

        // 获取数组容量
        int capacity = data.length;

        // 如果可以释放空间，则释放空间
        if (size == capacity / 4) {
            resize(capacity / 2);
        }

        // 被删除的元素
        E deleteVal = data[size - 1];

        // 删除最后一个元素, 释放内存空间
        data[size - 1] = null;
        // 元素个数减1
        size--;

        return deleteVal;
    }

    /**
     * 删除指定索引位置的元素
     *
     * @param index 索引
     * @return 被删除的元素
     */
    public E remove(int index) {
        // 检查索引是否合法
        checkElementIndex(index);

        // 获取数组容量
        int capacity = data.length;

        // 如果可以释放空间，则释放空间
        if (size == capacity / 4) {
            resize(capacity / 2);
        }

        // 被删除的元素
        E deleteVal = data[index];

        // 移动 index 之后的元素，覆盖 index 位置
        for (int i = index + 1; i < size; i++) {
            data[i + 1] = data[i];
        }

        // 删除最后一个元素, 释放内存空间
        data[size - 1] = null;

        // 元素个数减1
        size--;

        return deleteVal;
    }

    /**
     * 删除第一个元素
     *
     * @return 被删除的元素
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 获取指定索引位置的元素
     *
     * @param index 索引
     * @return 元素
     */
    public E get(int index) {
        // 检查索引是否合法
        checkElementIndex(index);

        return data[index];
    }

    /**
     * 修改指定索引位置的元素
     *
     * @param index 索引
     * @param e     元素
     * @return 被修改的元素
     */
    public E set(int index, E e) {
        // 检查索引是否合法
        checkElementIndex(index);

        // 被修改的元素
        E oldVal = data[index];

        // 修改元素
        data[index] = e;

        return oldVal;
    }

    /**
     * 获取数组中实际存储的元素个数
     *
     * @return 元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 判断数组是否为空
     *
     * @return true/false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 扩容
     *
     * @param newCapacity 新容量
     */
    private void resize(int newCapacity) {
        // 创建临时数组
        E[] temp = (E[]) new Object[newCapacity];

        // 拷贝数组元素
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }

        // 释放旧数组
        data = temp;
    }

    /**
     * 检查 index 索引位置是否可以存在元素
     *
     * @param index 索引
     */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " , size: " + size);
        }
    }

    /**
     * 检查 index 索引位置是否可以插入元素
     *
     * @param index 索引
     */
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " , size: " + size);
        }
    }

    /**
     * 检查 index 索引位置是否可以存在元素
     *
     * @param index 索引
     * @return true/false
     */
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    /**
     * 检查 index 索引位置是否可以插入元素
     *
     * @param index 索引
     * @return true/false
     */
    private boolean isPositionIndex(int index) {
        // 由于可以在 size 的位置插入元素，所以 index 必须小于等于 size
        return index >= 0 && index <= size;
    }
}
