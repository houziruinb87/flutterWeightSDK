package com.missfresh.weight;

import android.content.Context;
import android.util.Log;

import java.io.File;

import OnePlusOneAndroidSDK.ScalesOS.ScalesSDK;
import OnePlusOneAndroidSDK.ScalesOS.WeightInfo;

/**
 * 称重工具单例
 */
public class MFWeigh {
    private final String TAG = this.getClass().getSimpleName();
    private static MFWeigh INSTANCE;
    private static final Object mLock = new Object();
    private Context mContext;
    // 秤重
    private ScalesSDK mScalesSDK;
    private OnWeightChangeListener mOnWeightChangeListener;

    public static MFWeigh init(Context context, OnWeightChangeListener onWeightChangeListener) {
        synchronized (mLock) {
            if (null == INSTANCE) {
                INSTANCE = new MFWeigh(context, onWeightChangeListener);
            }
        }
        return INSTANCE;
    }

    public static MFWeigh init(Context context) {
        synchronized (mLock) {
            if (null == INSTANCE) {
                INSTANCE = new MFWeigh(context);
            }
        }
        return INSTANCE;
    }

    public static MFWeigh getInstance() {
        return INSTANCE;
    }

    /**
     * 构造函数
     * @param context context引用
     * @param onWeightChangeListener 重量变化监听者
     */
    private MFWeigh(Context context, final OnWeightChangeListener onWeightChangeListener) {
        mContext = context;
        mOnWeightChangeListener = onWeightChangeListener;
        //  称重
        mScalesSDK = ScalesSDK.getInstance(context, new ScalesSDK.WeightChangedListener() {
            @Override
            public void onWeightChanged(WeightInfo weightInfo) {
                WeightData weightData = new WeightData();
                weightData.setGrossWeight(weightInfo.getGrossWeight());
                weightData.setTareWeight(weightInfo.getTareWeight());
                weightData.setModel(weightInfo.getMode());
                weightData.setNetWeight(weightInfo.getNetWeight());
                weightData.setStatus(weightInfo.getStatus());
                weightData.setUnit(weightInfo.getUnit());
                weightData.setZero(weightInfo.getZero());
                if (weightInfo.getStatus() != null && weightInfo.getStatus().equals("Stable")) {
                    weightData.setStable(true);
                } else {
                    weightData.setStable(false);
                }
                if (mOnWeightChangeListener != null) {
                    mOnWeightChangeListener.onWeightChanged(weightData);

                } else {
                    Log.i(TAG, "重量监听者没有初始化");
                }
            }
        });

        //初始化成功
        if (mScalesSDK != null) {
            if (mScalesSDK.Open(new File("/dev/ttyS0"))) {

            } else {

            }
        }

    }

    /**
     * 构造函数
     * @param context context引用
     */
    private MFWeigh(Context context) {
        mContext = context;
        //  称重
        mScalesSDK = ScalesSDK.getInstance(context, new ScalesSDK.WeightChangedListener() {
            @Override
            public void onWeightChanged(WeightInfo weightInfo) {
                WeightData weightData = new WeightData();
                weightData.setGrossWeight(weightInfo.getGrossWeight());
                weightData.setTareWeight(weightInfo.getTareWeight());
                weightData.setModel(weightInfo.getMode());
                weightData.setNetWeight(weightInfo.getNetWeight());
                weightData.setStatus(weightInfo.getStatus());
                weightData.setUnit(weightInfo.getUnit());
                weightData.setZero(weightInfo.getZero());
                if (weightInfo.getStatus() != null && weightInfo.getStatus().equals("Stable")) {
                    weightData.setStable(true);
                } else {
                    weightData.setStable(false);
                }
                if (mOnWeightChangeListener != null) {
                    mOnWeightChangeListener.onWeightChanged(weightData);

                } else {
                    Log.i(TAG, "重量监听者没有初始化");
                }
            }
        });


    }

    /**
     * 打开串口连接
     *
     * @return 返回是否打开成功
     */
    public boolean open() {

        if (mScalesSDK != null) {
            if (mScalesSDK.Open(new File("/dev/ttyS0"))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 关闭串口连接
     *
     * @return 1, 关闭成功, 其他返回关闭失败
     */
    public int close() {
        if (mScalesSDK != null) {
            return mScalesSDK.Close();
        } else {
            return -1;
        }
    }

    /**
     * 获取重量
     *
     * @return 数据格式: sMode,sStatus,sZero,sUnit,sNetWeight,sTareWeight,sGrossWeight
     * sMode:
     * sMode = 'N'	"净重称量"
     * sMode = 'T'	"去皮称量"
     * sMode = 'P'	"预制去皮称量"
     * sStatus：
     * sStatus = 'F'  	"重量溢出或没有开机归零"
     * sStatus = 'S'  	"重量稳定"
     * sStatus = 'U'	"重量不稳定"
     * sZero:		零点('->0<-')  非零点（''）
     * sUnit:		单位
     * sNetWeight：	净重
     * sTareWeight：	皮重
     * sGrossWeight：	毛重
     */
    public String getWeightResult() {
        if (mScalesSDK != null) {
            return mScalesSDK.GetResult();
        } else {
            return "";

        }
    }

    /**
     * 归零操作
     *
     * @return 1归零成功
     */
    public String setZero() {
        if (mScalesSDK != null) {
            int status = mScalesSDK.Zero();
            if (status == 1) {
                return "归零成功";
            } else {
                return getErrorInfo(status);
            }

        } else {
            return "";
        }
    }

    /**
     * 获取重量监听者
     * @return 返回重量监听者
     */
    public OnWeightChangeListener getOnWeightChangeListener() {
        return mOnWeightChangeListener;
    }

    /**
     * 设置重量监听者
     * @param onWeightChangeListener 重量监听者
     */
    public void setOnWeightChangeListener(OnWeightChangeListener onWeightChangeListener) {
        mOnWeightChangeListener = onWeightChangeListener;
    }
    /*************************内部方法**********************/

    /**
     * 判断称重机状态类型
     * @param i 类型code
     * @return 具体的类型名字
     */
    private String getErrorInfo(int i) {
        switch (i) {
            case 0x8000:
                return "命令数据不在合法范围内";
            case 0x8100:
                return "重量不稳定";
            case 0x8200:
                return "AD值溢出";
            case 0x8300:
                return "当前在去皮模式";
            case 0x8400:
                return "没有开机归零";
            case 0x8500:
                return "当前在预置去皮模式";
            case 0x8600:
                return "防作弊没有打开，不能设置";
            case 0xFE00:
                return "错误的命令";
        }
        return "";
    }

}
