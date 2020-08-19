import 'dart:async';
import 'dart:collection';

import 'package:flutter/services.dart';
import 'package:weight/weight_constants.dart';

class Weight {
  static const MethodChannel _sWeightMethodChannel = const MethodChannel(WeightConstants.WEIGHT_METHOD_CHANNEL) ;
  static const MethodChannel _sLabelPrinterMethodChannel =  const MethodChannel(WeightConstants.LABEL_PRINTER_METHOD_CHANNEL);
  static const EventChannel _sWeightEventChannel  =  const EventChannel(WeightConstants.WEIGHT_EVENT_CHANNEL);
 ///打印相关
  /*初始打印*/
  static Future<String> get weightChannelInitPrint async {
    final String stringPrintStatus = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_PRINTER_CHANNEL_INIT);
    return stringPrintStatus;
  }
/*打印bitmap*/
  static Future<bool>  weightChannelPrintBitmap(HashMap map) async{
    final bool isSuccess = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_PRINTER_CHANNEL_PRINT_BITMAP,{WeightConstants.PRINT_PARAM_MAP: map});
    return isSuccess;
  }
  /*获取打印状态int*/
  //  * 查询打印机状态,0是正常,不剥纸时可 返回2（未取纸）时也进行打印
  //     * @return
  //     * case 1:
  //     *     showmsg("打印机缺纸");
  //     *     break;
  //     * case 2:
  //     *     showmsg("打印机未取纸");
  //     *     break;
  //     * case 3:
  //     *     showmsg("打印机开盖");
  //     *     break;
  //     * case 4:
  //     *     showmsg("打印机高温");
  //     *     break;
  //     * case 5:
  //     *     showmsg("打印机定位异常");
  //     *     break;
  //     * case 6:
  //     *     showmsg("打印机忙");
  //     *     break;
  //     * case 7:
  //     *     showmsg("打印机未知异常");
  //     *     break;
  static Future<int> get weightChannelGetIntPrintStatus async {
    final int intPrintStatus = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_CHANNEL_GET_INT_PRINT_STATUS);
    return intPrintStatus;
  }
  /*获取打印状态String*/
  //  * 查询打印机状态,0是正常,不剥纸时可 返回2（未取纸）时也进行打印
  //     * @return
  //     * case 1:
  //     *     showmsg("打印机缺纸");
  //     *     break;
  //     * case 2:
  //     *     showmsg("打印机未取纸");
  //     *     break;
  //     * case 3:
  //     *     showmsg("打印机开盖");
  //     *     break;
  //     * case 4:
  //     *     showmsg("打印机高温");
  //     *     break;
  //     * case 5:
  //     *     showmsg("打印机定位异常");
  //     *     break;
  //     * case 6:
  //     *     showmsg("打印机忙");
  //     *     break;
  //     * case 7:
  //     *     showmsg("打印机未知异常");
  //     *     break;
  static Future<String> get weightChannelGetStringPrintStats async {
    final String stringPrintStatus = await _sLabelPrinterMethodChannel.invokeMethod(WeightConstants.LABEL_CHANNEL_GET_STRING_PRINT_STATUS);
    return stringPrintStatus;
  }


///称重相关
  /*开启称重端口*/
  static Future<bool> get weightChannelOpen async {
    final bool isOpen = await _sWeightMethodChannel.invokeMethod(WeightConstants.WEIGHT_CHANNEL_OPEN);
    return isOpen;
  }
  /*关闭称重端口*/
  static Future<bool> get weightChannelClose async {
    final bool isClose = await _sWeightMethodChannel.invokeMethod(WeightConstants.WEIGHT_CHANNEL_CLOSE);
    return isClose;
  }
  /*获取称重信息*/
  static Future<String> get weightChannelGetWeightMessage async {
    final String weightMessage = await _sWeightMethodChannel.invokeMethod(WeightConstants.WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE);
    return weightMessage;
  }
  /*归零*/
  static Future<String> get weightChannelSetZero async {
    final String setResult = await _sWeightMethodChannel.invokeMethod(WeightConstants.WEIGHT_CHANNEL_SET_ZERO);
    return setResult;
  }
/*监听重量变化*/
  static Stream<dynamic> get onWeightChange {
    Stream<dynamic>  stream=  _sWeightEventChannel.receiveBroadcastStream();

  return _sWeightEventChannel.receiveBroadcastStream();
 }
}
