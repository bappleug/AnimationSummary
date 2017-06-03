package io.amosbake.animationsummary.twitterslike;

/**
 * Created by Ray on 2017/5/24.
 */

public class Utils {
    /**
     * 将值从一个范围映射到另一个范围
     */
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    /**
     * 取符合low到high的value值，超出范围时取边界值
     */
    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }
}
