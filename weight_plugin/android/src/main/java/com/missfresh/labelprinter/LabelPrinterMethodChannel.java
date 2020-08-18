package com.missfresh.labelprinter;

import android.content.Context;
import android.util.Log;

import com.missfresh.weight.WeightConstants;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import static com.missfresh.weight.WeightConstants.LABEL_CHANNEL_GET_INT_PRINT_STATUS;
import static com.missfresh.weight.WeightConstants.LABEL_CHANNEL_GET_STRING_PRINT_STATUS;
import static com.missfresh.weight.WeightConstants.LABEL_PRINTER_CHANNEL_INIT;
import static com.missfresh.weight.WeightConstants.LABEL_PRINTER_CHANNEL_PRINT_BITMAP;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_MAP;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_MATERIAL_CODE;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_NET_WEIGHT;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_PACKAGE_NUM;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_SKU_CODE;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_SPEC;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_STORE_CONDITION;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_TIME;
import static com.missfresh.weight.WeightConstants.PRINT_PARAM_TITLE;

/**
 * 称重通信渠道
 */
public class LabelPrinterMethodChannel implements MethodChannel.MethodCallHandler {
    private Context mContext;
    private BinaryMessenger messenger;
    private  MethodChannel sLabelPrinterMethodChannel;

    public LabelPrinterMethodChannel(Context context, BinaryMessenger messenger) {
        Log.i("nb","LabelPrinterMethodChannel");
        mContext = context;
        Log.i("nb","1");
        this.messenger = messenger;
        Log.i("nb","2");
        Log.i("nb","3");

        sLabelPrinterMethodChannel = new MethodChannel(messenger, WeightConstants.LABEL_PRINTER_METHOD_CHANNEL);
        Log.i("nb","MethodChannel");

        sLabelPrinterMethodChannel.setMethodCallHandler(this);
        Log.i("nb","setMethodCallHandler");

    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        Log.i("nb","onMethodCall");

        if (methodCall.method.equals(LABEL_PRINTER_CHANNEL_PRINT_BITMAP)) {

            if(methodCall.hasArgument(PRINT_PARAM_MAP)){
               HashMap<String,String> arguments = methodCall.argument(PRINT_PARAM_MAP);
                MFLabelPrinter.getInstance().printBitmap(arguments.get(PRINT_PARAM_TITLE),arguments.get(PRINT_PARAM_SPEC),arguments.get(PRINT_PARAM_NET_WEIGHT),arguments.get(PRINT_PARAM_TIME),arguments.get(PRINT_PARAM_STORE_CONDITION),arguments.get(PRINT_PARAM_MATERIAL_CODE),arguments.get(PRINT_PARAM_SKU_CODE),arguments.get(PRINT_PARAM_PACKAGE_NUM));
            }
        } else if (methodCall.method.equals(LABEL_CHANNEL_GET_INT_PRINT_STATUS)) {
            int intPrintStatus = MFLabelPrinter.getInstance().getIntPrintStatus();
            result.success(intPrintStatus);

        } else if (methodCall.method.equals(LABEL_CHANNEL_GET_STRING_PRINT_STATUS)) {
            Log.i("nb","LABEL_CHANNEL_GET_STRING_PRINT_STATUS");

            String stringPrintStatus = MFLabelPrinter.getInstance().getStringPrintStatus();
            result.success(stringPrintStatus);
        }
        else if (methodCall.method.equals(LABEL_PRINTER_CHANNEL_INIT)) {
            Log.i("nb","LABEL_PRINTER_CHANNEL_INIT");

           MFLabelPrinter.init(mContext);

        }
    }

    public MethodChannel getsLabelPrinterMethodChannel() {
        return sLabelPrinterMethodChannel;
    }

    public void setsLabelPrinterMethodChannel(MethodChannel sLabelPrinterMethodChannel) {
        this.sLabelPrinterMethodChannel = sLabelPrinterMethodChannel;
    }
}
