package com.missfresh.weight;

import android.os.Build;

public class MFWeighUtil {
    /**
     * 判断是否是打印设备
     * @return 返回是否是
     */
    public static boolean isWeighDevice() {
        if (Build.MODEL.contains("C-L8A")
                || Build.MODEL.contains("C-P8")
                || Build.MODEL.contains("HSP500A")
                || Build.MODEL.contains("C-F1A")
        ) {
            return true;
        } else {
            return false;
        }
    }
}
