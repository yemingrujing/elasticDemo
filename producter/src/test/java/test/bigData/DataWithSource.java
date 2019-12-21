package test.bigData;

import lombok.Data;

/**
 * @ProjectName: elasticsearch
 * @Package: test.bigData
 * @ClassName: DataWithSource
 * @Author: guang
 * @Description: 堆排序：如何找出排名前 500 的数
 * https://juejin.im/post/5ddb2476f265da7e0a3e2c85?utm_source=gold_browser_extension#heading-43
 * @Date: 2019/11/26 10:33
 * @Version: 1.0
 */
@Data
public class DataWithSource implements Comparable<DataWithSource> {

    /**
     * 数值
     */
    private int value;

    /**
     * 记录数值来源的数组
     */
    private int source;

    /**
     * 记录数值在数组中的索引
     */
    private int index;

    public DataWithSource(int value, int source, int index) {
        this.value = value;
        this.source = source;
        this.index = index;
    }

    /**
     * 由于 PriorityQueue 使用小顶堆来实现，这里通过修改两个整数的比较逻辑来让 PriorityQueue 变成大顶堆
     * @param o
     * @return
     */
    @Override
    public int compareTo(DataWithSource o) {
        return Integer.compare(o.getValue(), this.value);
    }


}
