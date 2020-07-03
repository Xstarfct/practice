package com.fct.daily.dailylearn.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 从 5 亿个数中找出中位数。数据排序后，位置在最中间的数就是中位数。当样本数为奇数时，中位数为 第 (N+1)/2 个数；当样本数为偶数时，中位数为 第 N/2 个数与第 1+N/2 个数的均值。
 *
 * @author xstarfct
 * @version 2020-06-23 3:59 下午
 */
public class MedianFinder {
    
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;
    
    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        minHeap = new PriorityQueue<>(Integer::compareTo);
    }
    
    public void addNum(int num) {
        if (maxHeap.isEmpty() || maxHeap.peek() > num) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        int size1 = maxHeap.size();
        int size2 = minHeap.size();
        if (size1 - size2 > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (size2 - size1 > 1) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        int size1 = maxHeap.size();
        int size2 = minHeap.size();
        
        return size1 == size2
                ? (maxHeap.peek() + minHeap.peek()) * 1.0 / 2
                : (size1 > size2 ? maxHeap.peek() : minHeap.peek());
    }
}
