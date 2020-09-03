package com.missfresh.weight;

import android.content.Context;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import static com.missfresh.weight.WeightConstants.IS_WEIGHT_PLATFORM;
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

        if (MFWeigh.getInstance() != null) {
            //判断是否是称重加工设备
            if (methodCall.method.equals(IS_WEIGHT_PLATFORM)) {
                result.success(MFWeighUtil.isWeighDevice());
            }
            //开启重量硬件端口
            else if (methodCall.method.equals(WEIGHT_CHANNEL_OPEN)) {
                boolean isSuccess = MFWeigh.getInstance().open();
                result.success(isSuccess);
                //关闭端口
            } else if (methodCall.method.equals(WEIGHT_CHANNEL_CLOSE)) {
                boolean isSuccess = MFWeigh.getInstance().close();
                result.success(isSuccess);
                //获取未处理的称重信息
            } else if (methodCall.method.equals(WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE)) {
                String weightResult = MFWeigh.getInstance().getWeightResult();
                result.success(weightResult);
                //归零
            } else if (methodCall.method.equals(WEIGHT_CHANNEL_SET_ZERO)) {
                //返回:归零成功   或者其他异常状态
                String weightResult = MFWeigh.getInstance().setZero();
                if (weightResult.equals("归零成功")) {
                    result.success(weightResult);
                } else {
                    if(weightResult.equals("AD值溢出")){
                        weightResult = "重量超过300g无法手动归零,请清空托盘后重启设备,系统进行自动归零";
                    }
                    result.error(weightResult + "", weightResult, weightResult);
                }
            }
        } else {
            result.error("称重未初始化", "称重未初始化", "称重未初始化");
        }

    }

    public MethodChannel getWeightMethodChannel() {
        return sWeightMethodChannel;
    }

    public void setWeightMethodChannel(MethodChannel weightMethodChannel) {
        sWeightMethodChannel = weightMethodChannel;
    }
}
