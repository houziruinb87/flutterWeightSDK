package com.missfresh.weight;

import android.content.Context;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

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

    }

    public MethodChannel getWeightMethodChannel() {
        return sWeightMethodChannel;
    }

    public void setWeightMethodChannel(MethodChannel weightMethodChannel) {
        sWeightMethodChannel = weightMethodChannel;
    }
}
