package io.github.architers.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luyi
 * 数据测试模型
 */
public class TestModel {
    private long startTime;
    private long endTime;
    private long count;
    private long useTime;
    /**
     * 额外信息
     */
    private Map<Object, Object> extra = new HashMap<>(4);
    /**
     * 备注
     */
    private String remark;

    public static TestModel build() {
        return new TestModel();
    }

    public TestModel addExtra(String key, String value) {
        this.extra.put(key, value);
        return this;
    }

    public long getUseTime() {
        if (this.useTime <= 0) {
            return endTime - startTime;
        }
        return this.useTime;

    }

    public TestModel setUseTime(long useTime) {
        this.useTime = useTime;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public TestModel setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getEndTime() {
        return endTime;
    }

    public TestModel setEndTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public long getCount() {
        return count;
    }

    public TestModel setCount(long count) {
        this.count = count;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public TestModel setRemark(String remark) {
        this.remark = remark;
        return this;
    }


    public Map<Object, Object> getExtra() {
        return extra;
    }

    public TestModel setExtra(Map<Object, Object> extra) {
        this.extra = extra;
        return this;
    }
}
