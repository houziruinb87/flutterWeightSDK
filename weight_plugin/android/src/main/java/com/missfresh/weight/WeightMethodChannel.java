package com.missfresh.weight;

import android.content.Context;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import static com.missfresh.weight.WeightConstants.WEIGHT_CHANNEL_CLOSE;
import static com.missfresh.weight.WeightConstants.WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE;
import static com.missfresh.weight.WeightConstants.WEIGHT_CHANNEL_OPEN;
import static com.missfresh.weight.WeightConstants.WEIGHT_CHANNEL_SET_ZERO;

/**
 * 称重通信渠道
 */
public class WeightMethodChannel implements MethodChannel.MethodCallHandler {
    private Context mContext;
    private BinaryMessenger messenger;
    private MethodChannel sWeightMethodChannel;

    public WeightMethodChannel(Context context, BinaryMessenger messenger) {
        mContext = context;
        this.messenger = messenger;
        sWeightMethodChannel = new MethodChannel(messenger, WeightConstants.WEIGHT_METHOD_CHANNEL);
        sWeightMethodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        //开启重量硬件端口
        if (methodCall.method.equals(WEIGHT_CHANNEL_OPEN)) {

                boolean isSuccess = MFWeigh.getInstance().open();
                result.success(isSuccess);
        }else if (methodCall.method.equals(WEIGHT_CHANNEL_CLOSE)) {
           // 1, 关闭成功, 其他返回关闭失败
            boolean isSuccess = MFWeigh.getInstance().close();
            result.success(isSuccess);
        }
        else  if (methodCall.method.equals(WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE)) {
            // 1, 关闭成功, 其他返回关闭失败
            String weightResult = MFWeigh.getInstance().getWeightResult();
            result.success(weightResult);
        }
        else  if (methodCall.method.equals(WEIGHT_CHANNEL_SET_ZERO)) {
            //返回:归零成功   或者其他异常状态
            String weightResult = MFWeigh.getInstance().setZero();
            result.success(weightResult);
        }

    }

    public MethodChannel getWeightMethodChannel() {
        return sWeightMethodChannel;
    }

    public void setWeightMethodChannel(MethodChannel weightMethodChannel) {
        sWeightMethodChannel = weightMethodChannel;
    }
}
