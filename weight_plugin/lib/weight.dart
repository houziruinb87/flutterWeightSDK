import 'dart:async';
import 'dart:collection';

import 'package:flutter/services.dart';
import 'package:weight/weigh_detail_model.dart';
import 'package:weight/weigh_print_model.dart';
import 'package:weight/weight_constants.dart';

class Weight {
  static const MethodChannel _sWeightMethodChannel =
      const MethodChannel(WeightConstants.WEIGHT_METHOD_CHANNEL);

  static const MethodChannel _sLabelPrinterMethodChannel =
      const MethodChannel(WeightConstants.LABEL_PRINTER_METHOD_CHANNEL);
  static const EventChannel _sWeightEventChannel =
      const EventChannel(WeightConstants.WEIGHT_EVENT_CHANNEL);

  ///打印相关
  /*初始打印*/
  static Future<String> get weightChannelInitPrint async {
    final String stringPrintStatus = await _sLabelPrinterMethodChannel
        .invokeMethod(WeightConstants.LABEL_PRINTER_CHANNEL_INIT);
    return stringPrintStatus;
  }

/*打印bitmap*/
  static Future<bool> weightChannelPrintBitmap(HashMap map) async {
    return await _sLabelPrinterMethodChannel.invokeMethod(
        WeightConstants.LABEL_PRINTER_CHANNEL_PRINT_BITMAP,
        {WeightConstants.PRINT_PARAM_MAP: map}).catchError((error){
      throw '${error?.message??''}';
    });
  }
/*打印bitmap*/
  static Future<bool> weightChannelPrintBitmapNew(HashMap map) async {
    return await _sLabelPrinterMethodChannel.invokeMethod(
        WeightConstants.LABEL_PRINTER_CHANNEL_PRINT_BITMAP_NEW,
        {WeightConstants.PRINT_PARAM_MAP: map}).catchError((error){
      throw '${error?.message??''}';
    });
  }
  /*根据对象打印*/
  static Future<bool> weightChannelPrint(WeighPrintModel weighPrintModel)  {


      //打印机状态正常
      //拼接打印参数
      HashMap hashMap = HashMap<String, String>();
      //标题
      hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TITLE, () => weighPrintModel?.skuName??'');
      //规格
      hashMap.putIfAbsent(
          WeightConstants.PRINT_PARAM_SPEC, () => weighPrintModel?.skuSpec??'');
      //净重
      hashMap.putIfAbsent(
          WeightConstants.PRINT_PARAM_NET_WEIGHT, () => weighPrintModel?.netWeigh??'');

      //生产时间
      String time = weighPrintModel?.createTime ?? '';
      String yyyy_MM_dd = '';
      String yyyyMMdd = '';
      //打印时间(必须是yyyyMMdd)
      if(weighPrintModel.createTime!=null){
        //当前获取到的是yyyy-MM-dd HH:mm:ss
        //打印时间(必须是YYYYMMDD)

        if (time.length >= 10) {
          yyyy_MM_dd = time.substring(0, 10);
          yyyyMMdd = yyyy_MM_dd.replaceAll('-', '');
        }
      }
      hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TIME, () => yyyyMMdd);

      //包装时间
      String packageTime = weighPrintModel?.packageTime ?? '';
      String packageTime_yyyy_MM_dd = '';
      String packageTime_yyyyMMdd = '';
      //打印时间(必须是yyyyMMdd)
      if(weighPrintModel.createTime!=null){
        //当前获取到的是yyyy-MM-dd HH:mm:ss
        //打印时间(必须是YYYYMMDD)

        if (time.length >= 10) {
          packageTime_yyyy_MM_dd = packageTime.substring(0, 10);
          packageTime_yyyyMMdd = packageTime_yyyy_MM_dd.replaceAll('-', '');
        }
      }
      hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_PACKAGE_TIME, () => packageTime_yyyyMMdd);


//批次号
    hashMap.putIfAbsent(
        WeightConstants.PRINT_PARAM_BATCH_CODE, () => weighPrintModel?.batchCode??'');

    //存储条件
      hashMap.putIfAbsent(
          WeightConstants.PRINT_PARAM_STORE_CONDITION, () => weighPrintModel?.storeCondition??'');
      //原料码
      hashMap.putIfAbsent(
          WeightConstants.PRINT_PARAM_MATERIAL_CODE, () => weighPrintModel?.materialCode??'');
      //SKU码
      hashMap.putIfAbsent(
          WeightConstants.PRINT_PARAM_SKU_CODE, () => weighPrintModel?.skuCode??'');
      //唯一码
      hashMap.putIfAbsent(
          WeightConstants.PRINT_PARAM_PACKAGE_NUM, () => weighPrintModel?.snCode??'');

      //开始打印
   return  Weight.weightChannelPrintBitmap(hashMap);


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
    return  _sLabelPrinterMethodChannel
        .invokeMethod(WeightConstants.LABEL_CHANNEL_GET_INT_PRINT_STATUS).catchError((error){
      throw '${error?.message??''}';
    });
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
    return await _sLabelPrinterMethodChannel
        .invokeMethod(WeightConstants.LABEL_CHANNEL_GET_STRING_PRINT_STATUS).catchError((error){
//      throw '${error?.message??''}';
    throw '${error.message}';
    });
  }

  ///称重相关
  /*查看是否是称重设备*/
  static Future<bool> get weightChannelIsWeighDevice async {
    final bool isWeighDevice = await _sWeightMethodChannel
        .invokeMethod(WeightConstants.IS_WEIGHT_PLATFORM).catchError((error){
      throw '${error?.message??''}';
    });
    return isWeighDevice;
  }
  /*开启称重端口*/
  static Future<bool> get weightChannelOpen async {
    return await _sWeightMethodChannel
        .invokeMethod(WeightConstants.WEIGHT_CHANNEL_OPEN).catchError((error){
      throw '${error?.message??''}';
    });
  }

  /*关闭称重端口*/
  static Future<bool> get weightChannelClose async {
    return await _sWeightMethodChannel
        .invokeMethod(WeightConstants.WEIGHT_CHANNEL_CLOSE).catchError((error){
      throw '${error?.message??''}';
    });
  }

  /*获取称重信息*/
  static Future<String> get weightChannelGetWeightMessage async {
    return await _sWeightMethodChannel
        .invokeMethod(WeightConstants.WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE).catchError((e){
          throw "${e.message}";
    });
  }

  /*归零*/
  static Future<String> get weightChannelSetZero async {
    return await _sWeightMethodChannel
        .invokeMethod(WeightConstants.WEIGHT_CHANNEL_SET_ZERO).catchError((error){
      throw '${error?.message??''}';
    });
  }

/*监听重量变化*/
  static Stream<WeighDetailModel> get onWeightChange {
    Stream<dynamic> receiveBroadcastStream =
        _sWeightEventChannel.receiveBroadcastStream();
    var nb = StreamTransformer.fromHandlers(
        //数据回调方法
        handleData: (dynamic data, EventSink<WeighDetailModel> sink) {
      Map<String, dynamic> eventMap = new Map<String, dynamic>.from(data);
      WeighDetailModel weighDetailModel = WeighDetailModel();
      if (eventMap.containsKey(WeightConstants.WEIGHT_PARAM_MODEL)) {
        weighDetailModel.model = eventMap[WeightConstants.WEIGHT_PARAM_MODEL];
      }
      if (eventMap.containsKey(WeightConstants.WEIGHT_PARAM_STATUS)) {
        weighDetailModel.status = eventMap[WeightConstants.WEIGHT_PARAM_STATUS];
      }
      if (eventMap.containsKey(WeightConstants.WEIGHT_PARAM_IS_ZERO)) {
        weighDetailModel.isZero =
            eventMap[WeightConstants.WEIGHT_PARAM_IS_ZERO];
      }
      if (eventMap.containsKey(WeightConstants.WEIGHT_PARAM_NET_WEIGHT)) {
        weighDetailModel.netWeight =
            eventMap[WeightConstants.WEIGHT_PARAM_NET_WEIGHT];
      }
      if (eventMap.containsKey(WeightConstants.WEIGHT_PARAM_NET_WEIGHT)) {
        weighDetailModel.netWeight =
            eventMap[WeightConstants.WEIGHT_PARAM_NET_WEIGHT];
      }
      if (eventMap.containsKey(WeightConstants.WEIGHT_PARAM_IS_STABLE)) {
        weighDetailModel.isStable =
            eventMap[WeightConstants.WEIGHT_PARAM_IS_STABLE];
      }
      sink.add(weighDetailModel);
    });

    Stream<WeighDetailModel> newStream =
        receiveBroadcastStream.transform<WeighDetailModel>(nb);
    return newStream.asBroadcastStream();
  }
}
