import 'dart:async';
import 'dart:collection';

import 'package:flutter/services.dart';
import 'package:weight/weight_constants.dart';

class Weight {
  static const MethodChannel _sWeightMethodChannel = const MethodChannel(WeightConstants.WEIGHT_METHOD_CHANNEL) ;
  static const MethodChannel _sLabelPrinterMethodChannel =  const MethodChannel(WeightConstants.LABEL_PRINTER_METHOD_CHANNEL);
  static const EventChannel _sWeightEventChannel  =  const EventChannel(WeightConstants.WEIGHT_EVENT_CHANNEL);
  /*初始打印*/
  static Future<String> get weightChannelInitPrint async {
    final String stringPrintStatus = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_PRINTER_CHANNEL_INIT);
    return stringPrintStatus;
  }
/*打印bitmap*/
  static Future<String>  weightChannelPrintBitmap(HashMap map) async{
    final String version = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_PRINTER_CHANNEL_PRINT_BITMAP,{WeightConstants.PRINT_PARAM_MAP: map});
    return version;
  }
  /*获取打印状态int*/
  static Future<int> get weightChannelGetIntPrintStatus async {
    final int intPrintStatus = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_CHANNEL_GET_INT_PRINT_STATUS);
    return intPrintStatus;
  }
  /*获取打印状态String*/
  static Future<String> get weightChannelGetStringPrintStats async {
    final String stringPrintStatus = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_CHANNEL_GET_STRING_PRINT_STATUS);
    return stringPrintStatus;
  }
 static Stream<HashMap<String,dynamic>> get onWeightChange {
  return _sWeightEventChannel.receiveBroadcastStream();
 }
}
