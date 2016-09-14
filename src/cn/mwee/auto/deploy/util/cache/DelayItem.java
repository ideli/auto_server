package cn.mwee.auto.deploy.util.cache;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/9/13.
 */
public class DelayItem<T> implements Delayed {
    public static final long NANO_ORIGIN = System.nanoTime();
    /**
     * 辅助比较
     **/
    private static final AtomicLong sequencer = new AtomicLong(0);
    private long sequenceNumber;

    private long liveTime;
    /**
     * 数据
     **/
    private T item;

    private static long now() {
        return System.nanoTime() - NANO_ORIGIN;
    }

    public DelayItem(T item, long timeout, TimeUnit unit) {
        this.item = item;
        this.liveTime = now() + TimeUnit.NANOSECONDS.convert(timeout, unit);
        this.sequenceNumber = sequencer.getAndIncrement();
    }


    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        if (o instanceof DelayItem) {
            DelayItem other = (DelayItem) o;
            long diff = liveTime - other.liveTime;
            if (diff < 0) {
                return -1;
            } else if (diff > 0) {
                return 1;
            } else if (sequenceNumber < other.sequenceNumber) {
                return -1;
            } else {
                return 1;
            }
        }
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return d == 0 ? 0 : (d < 0 ? -1 : 1);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(liveTime - now(), TimeUnit.NANOSECONDS);
        return d;
    }

    public T getItem() {
        return item;
    }
}
