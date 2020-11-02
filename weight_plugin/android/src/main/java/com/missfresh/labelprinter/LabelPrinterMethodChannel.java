package com.missfresh.labelprinter;

import android.content.Context;

import com.missfresh.weight.WeightConstants;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import static com.missfresh.weight.WeightConstants.LABEL_CHANNEL_GET_INT_PRINT_STATUS;
import static com.missfresh.weight.WeightConstants.LABEL_CHANNEL_GET_STRING_PRINT_STATUS;
import static com.missfresh.weight.WeightConstants.LABEL_PRINTER_CHANNEL_PRINT_BITMAP;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_BATCH_CODE;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_MAP;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_MATERIAL_CODE;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_NET_WEIGHT;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_PACKAGE_NUM;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_PACKAGE_TIME;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_SKU_CODE;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_SPEC;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_STORE_CONDITION;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_TIME;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_TITLE;

/**
 * 打印通信渠道
 */
public class LabelPrinterMethodChannel implements MethodChannel.MethodCallHandler {
    private Context mContext;
    private BinaryMessenger messenger;
    private MethodChannel sLabelPrinterMethodChannel;

    public LabelPrinterMethodChannel(Context context, BinaryMessenger messenger) {
        mContext = context;
        this.messenger = messenger;
        sLabelPrinterMethodChannel = new MethodChannel(messenger, WeightConstants.LABEL_PRINTER_METHOD_CHANNEL);
        sLabelPrinterMethodChannel.setMethodCallHandler(this);

    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        if (MFLabelPrinter.getInstance() != null) {
            //打印
            if (methodCall.method.equals(LABEL_PRINTER_CHANNEL_PRINT_BITMAP)) {
                //是否有需要打印的内容
                if (methodCall.hasArgument(PRINT_PARAM_MAP)) {
                    //当前打印状态正常
                    if(MFLabelPrinter.getInstance().getIntPrintStatus()==0){
                        HashMap<String, String> arguments = methodCall.argument(PRINT_PARAM_MAP);
//                        boolean isSuccess = MFLabelPrinter.getInstance().printBitmap(arguments.get(PRINT_PARAM_TITLE), arguments.get(PRINT_PARAM_SPEC), arguments.get(PRINT_PARAM_NET_WEIGHT), arguments.get(PRINT_PARAM_TIME), arguments.get(PRINT_PARAM_STORE_CONDITION), arguments.get(PRINT_PARAM_MATERIAL_CODE), arguments.get(PRINT_PARAM_SKU_CODE), arguments.get(PRINT_PARAM_PACKAGE_NUM));
                        boolean isSuccess = MFLabelPrinter.getInstance().printBitmapNew(arguments.get(PRINT_PARAM_TITLE), arguments.get(PRINT_PARAM_SPEC), arguments.get(PRINT_PARAM_NET_WEIGHT), arguments.get(PRINT_PARAM_TIME), arguments.get(PRINT_PARAM_STORE_CONDITION), arguments.get(PRINT_PARAM_MATERIAL_CODE), arguments.get(PRINT_PARAM_SKU_CODE), arguments.get(PRINT_PARAM_PACKAGE_NUM),arguments.get(PRINT_PARAM_BATCH_CODE),arguments.get(PRINT_PARAM_PACKAGE_TIME));
                           result.success(isSuccess);

                    }else {
                        //打印状态不正常,拿到异常信息
                        String stringPrintStatus = MFLabelPrinter.getInstance().getStringPrintStatus();
                       if(stringPrintStatus!=null){
                           result.error(stringPrintStatus,stringPrintStatus,stringPrintStatus);
                       }else {
                           result.error("打印异常,未获取到异常信息","打印异常,未获取到异常信息","打印异常,未获取到异常信息");
                       }
                    }
                }else {
                    result.error("未找到需要打印的内容","未找到需要打印的内容","未找到需要打印的内容");
                }
            }
            //获取打印的int状态
            else if (methodCall.method.equals(LABEL_CHANNEL_GET_INT_PRINT_STATUS)) {
                int intPrintStatus = MFLabelPrinter.getInstance().getIntPrintStatus();
                if (intPrintStatus == 0) {
                    result.success(intPrintStatus);
                } else {
                    //打印状态不正常,拿到异常信息
                    String stringPrintStatus = MFLabelPrinter.getInstance().getStringPrintStatus();
                    if(stringPrintStatus!=null){
                        result.error(stringPrintStatus,stringPrintStatus,stringPrintStatus);
                    }else {
                        result.error("打印异常,未获取到异常信息","打印异常,未获取到异常信息","打印异常,未获取到异常信息");
                    }
                }
            }
            //获取打印的string状态
            else if (methodCall.method.equals(LABEL_CHANNEL_GET_STRING_PRINT_STATUS)) {
                String stringPrintStatus = MFLabelPrinter.getInstance().getStringPrintStatus();
                if(stringPrintStatus!=null){
                    if(stringPrintStatus.equals("打印机状态正常")){
                        result.success(stringPrintStatus);
                    }else {
                        result.error(stringPrintStatus,stringPrintStatus,stringPrintStatus);
                    }
                }else {
                    result.error("打印异常,未获取到异常信息","打印异常,未获取到异常信息","打印异常,未获取到异常信息");
                }


            }
        }else {
            result.error("打印未初始化", "打印未初始化", "打印未初始化");

        }


    }

    public MethodChannel getsLabelPrinterMethodChannel() {
        return sLabelPrinterMethodChannel;
    }

    public void setsLabelPrinterMethodChannel(MethodChannel sLabelPrinterMethodChannel) {
        this.sLabelPrinterMethodChannel = sLabelPrinterMethodChannel;
    }
}
