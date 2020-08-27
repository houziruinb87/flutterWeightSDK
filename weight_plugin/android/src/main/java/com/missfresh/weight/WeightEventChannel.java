package com.missfresh.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;

import static com.missfresh.weight.WeightConstants.WEIGHT_EVENT_CHANNEL;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_GROSS_WEIGHT;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_IS_STABLE;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_IS_ZERO;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_MODEL;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_NET_WEIGHT;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_STATUS;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_TARE_WEIGHT;
import static com.missfresh.weight.WeightConstants.WEIGHT_PARAM_UNIT;

public class WeightEventChannel implements EventChannel.StreamHandler,OnWeightChangeListener {
    private Context mContext;
    private BinaryMessenger mBinaryMessenger;
    private EventChannel sWeightEventChannel;
    private static EventChannel.EventSink mEventSink;
    private static   HashMap<String, Object> objectObjectHashMap =new HashMap<>();
    private WeightEventChannelHandler mHandler;
    public WeightEventChannel(Context context, BinaryMessenger messenger) {
        mHandler = new WeightEventChannelHandler();
        if(MFWeigh.getInstance()!=null){
            MFWeigh.getInstance().setOnWeightChangeListener(this);
        }
        mContext = context;
        this.mBinaryMessenger = messenger;
        sWeightEventChannel = new EventChannel(messenger,WEIGHT_EVENT_CHANNEL);
        sWeightEventChannel.setStreamHandler(this);

    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        mEventSink = eventSink;
    }

    @Override
    public void onCancel(Object o) {

    }

    @Override
    public void onWeightChanged(WeightData weightData) {
//        HashMap<String, Object> objectObjectHashMap = ;
        objectObjectHashMap.put(WEIGHT_PARAM_MODEL,weightData.getModel());
        objectObjectHashMap.put(WEIGHT_PARAM_STATUS,weightData.getStatus());
        objectObjectHashMap.put(WEIGHT_PARAM_IS_ZERO,weightData.isZero());
        objectObjectHashMap.put(WEIGHT_PARAM_UNIT,weightData.getUnit());
        objectObjectHashMap.put(WEIGHT_PARAM_NET_WEIGHT,weightData.getNetWeight());
        objectObjectHashMap.put(WEIGHT_PARAM_TARE_WEIGHT,weightData.getTareWeight());
        objectObjectHashMap.put(WEIGHT_PARAM_GROSS_WEIGHT,weightData.getGrossWeight());
        objectObjectHashMap.put(WEIGHT_PARAM_IS_STABLE,weightData.isStable());
        mHandler.sendEmptyMessage(0);
    }

    public EventChannel getsWeightEventChannel() {
        return sWeightEventChannel;
    }

    public void setsWeightEventChannel(EventChannel sWeightEventChannel) {
        this.sWeightEventChannel = sWeightEventChannel;
    }

    public static class WeightEventChannelHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mEventSink!=null){
//                mEventSink.success("NBNBNBNBNBNBNBNBNBNBNBNNBNBNBNBNBNB");
                mEventSink.success(objectObjectHashMap);

            }
        }
    }
}

