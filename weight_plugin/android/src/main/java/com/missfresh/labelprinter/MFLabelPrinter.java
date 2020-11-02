package com.missfresh.labelprinter;

import android.content.Context;
import android.util.Log;

import OnePlusOneAndroidSDK.Printer.LabelPrinter;

/**
 * 打印机工具单例
 */
public class MFLabelPrinter {
    private final String TAG = this.getClass().getSimpleName();
    private static MFLabelPrinter INSTANCE;
    private static final Object mLock = new Object();
    private Context mContext;
    // 标签打印机
    private LabelPrinter mLabelPrinter;
    public static  MFLabelPrinter init(Context context) {
        synchronized (mLock) {
            if (null == INSTANCE) {
                INSTANCE = new MFLabelPrinter(context);
                if(INSTANCE!=null){
                    return INSTANCE;
                }else {
                    return null;
                }
            }else {
                return INSTANCE;
            }

        }
    }

//    public static  boolean init(Context context) {
//        synchronized (mLock) {
////            Build.BRAND
////            if(Build.CPU_ABI.contains("armeabi-v7a")){
//                if (null == INSTANCE) {
//                    INSTANCE = new MFLabelPrinter(context);
//                    if(INSTANCE!=null){
//                        return true;
//                    }else {
//                        return false;
//                    }
//                }else {
//                    return true;
//                }
////            }else {
////                return false;
////            }
//
//        }
////        return INSTANCE;
//    }

    public static MFLabelPrinter getInstance() {
        return INSTANCE;
    }

    private MFLabelPrinter(Context context) {
        Log.i("nb","MFLabelPrinter");

        mContext = context;
        Log.i("nb","MFLabelPrinter1");
        try {
            if(context!=null){
                Log.i("nb","Context!=null");

            }else {
                Log.i("nb","Context==null");

            }
            mLabelPrinter = LabelPrinter.getInstance(context);

        }catch (Exception e) {
            Log.i("nb","e"+e.getMessage());

        }
        Log.i("nb","MFLabelPrinter2");
        //  标签打印 不支持字符串打印，只支持图片打印 即 打印只支持 PrintLabelBitmap 方法。
        if (mLabelPrinter.Open()) {
            Log.d(TAG, "Label Printer Open OK");
        } else {
            Log.d(TAG, "Label Printer Open Fail");
        }
    }

    /**
     * 查询打印机状态,0是正常,不剥纸时可 返回2（未取纸）时也进行打印
     * @return
     * case 1:
     *     showmsg("打印机缺纸");
     *     break;
     * case 2:
     *     showmsg("打印机未取纸");
     *     break;
     * case 3:
     *     showmsg("打印机开盖");
     *     break;
     * case 4:
     *     showmsg("打印机高温");
     *     break;
     * case 5:
     *     showmsg("打印机定位异常");
     *     break;
     * case 6:
     *     showmsg("打印机忙");
     *     break;
     * case 7:
     *     showmsg("打印机未知异常");
     *     break;
     */
    public int getIntPrintStatus() {
        if (mLabelPrinter != null) {
            return mLabelPrinter.GetStatus();
        } else {
            return -1;
        }

    }
    /**
     * 查询打印机状态,0是正常,不剥纸时可 返回2（未取纸）时也进行打印
     * @return
     * case 1:
     *     showmsg("打印机缺纸");
     *     break;
     * case 2:
     *     showmsg("打印机未取纸");
     *     break;
     * case 3:
     *     showmsg("打印机开盖");
     *     break;
     * case 4:
     *     showmsg("打印机高温");
     *     break;
     * case 5:
     *     showmsg("打印机定位异常");
     *     break;
     * case 6:
     *     showmsg("打印机忙");
     *     break;
     * case 7:
     *     showmsg("打印机未知异常");
     *     break;
     */
    public String getStringPrintStatus() {
        if (mLabelPrinter != null) {
            if(mLabelPrinter.GetStatus() == 0){
                return "打印机状态正常";
            }else if(mLabelPrinter.GetStatus() == 1){
                return "打印机缺纸";
            }else if(mLabelPrinter.GetStatus() == 2){
                return "打印机未取纸";
            }else if(mLabelPrinter.GetStatus() == 3){
                return "打印机开盖";
            }else if(mLabelPrinter.GetStatus() == 4){
                return "打印机高温";
            }else if(mLabelPrinter.GetStatus() == 5){
                return "打印机定位异常";
            }else if(mLabelPrinter.GetStatus() == 6){
                return "打印机忙";
            }else if(mLabelPrinter.GetStatus() == 7){
                return "打印机未知异常";
            }else {
                return "打印机未知异常";
            }
        } else {
            return "打印机未初始化";
        }

    }

    /**
     *
     * @param title 打印标题
     * @param spec  打印规格
     * @param netWeight 净重
     * @param time  生产时间
     * @param storeCondition 存储条件
     * @param materialCode 原料编码
     * @param SKUCode sku编码
     * @param SNCode 包裹号
     * @return 打印是否成功
     */
    public boolean printBitmap(String title, String spec, String netWeight, String time, String storeCondition, String materialCode, String SKUCode, String SNCode){
       if(mContext!=null){
           int printerStatus = mLabelPrinter.GetStatus();
           if(printerStatus != 0){
               return false;
           }else {
               return   mLabelPrinter.PrintLabelBitmap( BitMapUtil.createBitmap(mContext,title,spec,netWeight,time,storeCondition,SNCode,materialCode,SKUCode));
           }
       }else {
           return false;
       }
    }

    /**
     *
     * @param title 打印标题
     * @param spec  打印规格
     * @param netWeight 净重
     * @param time  生产时间
     * @param storeCondition 存储条件
     * @param materialCode 原料编码
     * @param SKUCode sku编码
     * @param SNCode 包裹号
     * @return 打印是否成功
     */
    public boolean printBitmapNew(String title, String spec, String netWeight, String time, String storeCondition, String materialCode, String SKUCode, String SNCode,String batchCode,String packageTime){
        if(mContext!=null){
            int printerStatus = mLabelPrinter.GetStatus();
            if(printerStatus != 0){
                return false;
            }else {
                return   mLabelPrinter.PrintLabelBitmap( BitMapUtilNewTime.createBitmap(mContext,title,spec,netWeight,time,storeCondition,SNCode,materialCode,SKUCode,batchCode,packageTime));
            }
        }else {
            return false;
        }
    }
}
