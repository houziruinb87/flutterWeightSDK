import 'dart:collection';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:weight/weight.dart';
import 'package:weight/weight_constants.dart';
import 'package:weight/weigh_detail_model.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String weightNetWeight; //打印回调净重
  String weightUnit;
  bool isZero;
  bool isStable;
  bool isCurrentPrintSuccess = false;

  @override
  void initState() {
    super.initState();
    Weight.weightChannelOpen;
    Weight.onWeightChange.listen((event) {
      if(event is WeighDetailModel){
      String weightModel =event.model;
      String weightStatus = event.status;
      isZero = event.isZero;
      isStable = event.isStable;
      weightNetWeight = event.netWeight;

      printWhenStable();
      if (isZero) {
        isCurrentPrintSuccess = false;
      }
      if (mounted) {
        setState(() {
          weightNetWeight = event.netWeight;
        });
      }
      }
//      print("回调");
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              Container(
                height: 100,
                width: 100,
                color: Colors.red,
                child: InkWell(
                  onTap: () async {
                    //拼接打印参数
                    HashMap hashMap = HashMap<String, String>();
                    //标题
                    hashMap.putIfAbsent(
                        WeightConstants.PRINT_PARAM_TITLE, () => "电饭锅梵蒂冈");
                    //规格
                    hashMap.putIfAbsent(
                        WeightConstants.PRINT_PARAM_SPEC, () => "100ml*100");
                    //净重
                    hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_NET_WEIGHT,
                        () => weightNetWeight);
                    //打印时间(必须是YYYYMMDD)
                    hashMap.putIfAbsent(
                        WeightConstants.PRINT_PARAM_TIME, () => "20200101");
                    //存储条件
                    hashMap.putIfAbsent(
                        WeightConstants.PRINT_PARAM_STORE_CONDITION,
                        () => "常温");
                    //原料码
                    hashMap.putIfAbsent(
                        WeightConstants.PRINT_PARAM_MATERIAL_CODE,
                        () => "34234562");
                    //SKU码
                    hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_SKU_CODE,
                        () => "dfgdfgdfgdfg-34435-2");
                    //唯一码
                    hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_PACKAGE_NUM,
                        () => "WH00000000012345");

                    //打印前先判断打印机状态
                    int printStatusInt =
                        await Weight.weightChannelGetIntPrintStatus;
                    if (printStatusInt == 0) {
                      //打印机状态正常
                      bool printBool =
                          await Weight.weightChannelPrintBitmap(hashMap);
                      if (printBool) {
                        print("打印成功");
                      } else {
                        print("打印失败");
                      }
                    } else {
                      //打印机状态异常,查询具体异常信息
                      String printStatusString =
                          await Weight.weightChannelGetStringPrintStats;
                      print(printStatusString);
                    }
                  },
                ),
              ),
              Container(
                height: 100,
                width: 100,
                color: Colors.blue,
                child: InkWell(
                  onTap: () async {
                    String zeroString = await Weight.weightChannelSetZero;
                    print(zeroString);
                  },
                ),
              ),
              Container(
                height: 100,
                width: 100,
                color: Colors.green,
                child: InkWell(
                  onTap: () async {
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
                    String printStatusString =
                        await Weight.weightChannelGetStringPrintStats;
                    print(printStatusString);
                  },
                ),
              ),
              Container(
                height: 100,
                width: 100,
                color: Colors.yellow,
                child: InkWell(
                  onTap: () async {
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
                    int printStatusInt =
                        await Weight.weightChannelGetIntPrintStatus;
                    print(printStatusInt);
                  },
                ),
              ),
              Container(
                height: 100,
                width: 100,
                color: Colors.lightBlueAccent,
                child: Row(
                  children: <Widget>[
                    Text(weightNetWeight ?? '0'),
                    Text(weightUnit ?? '')
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Future<bool> printWhenStable() async {
    //不在0点并且稳定, 且当前物品没有打印成功 开始自动打印
    if (!isZero && isStable && !isCurrentPrintSuccess) {
      //判断打印机状态
      int printStatusInt = await Weight.weightChannelGetIntPrintStatus;
      if (printStatusInt == 0) {
        //打印机状态正常
        //拼接打印参数
        HashMap hashMap = HashMap<String, String>();
        //标题
        hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TITLE, () => "电饭锅梵蒂冈");
        //规格
        hashMap.putIfAbsent(
            WeightConstants.PRINT_PARAM_SPEC, () => "100ml*100");
        //净重
        hashMap.putIfAbsent(
            WeightConstants.PRINT_PARAM_NET_WEIGHT, () => weightNetWeight);
        //打印时间(必须是YYYYMMDD)
        hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TIME, () => "20200101");
        //存储条件
        hashMap.putIfAbsent(
            WeightConstants.PRINT_PARAM_STORE_CONDITION, () => "常温");
        //原料码
        hashMap.putIfAbsent(
            WeightConstants.PRINT_PARAM_MATERIAL_CODE, () => "34234562");
        //SKU码
        hashMap.putIfAbsent(
            WeightConstants.PRINT_PARAM_SKU_CODE, () => "dfgdfgdfgdfg-34435-2");
        //唯一码
        hashMap.putIfAbsent(
            WeightConstants.PRINT_PARAM_PACKAGE_NUM, () => "WH00000000012345");

        //开始打印
        isCurrentPrintSuccess = await Weight.weightChannelPrintBitmap(hashMap);
        if (isCurrentPrintSuccess) {
          print("打印成功");
        } else {
          print("打印失败");
        }
        return isCurrentPrintSuccess;
      } else {
        //打印机状态不正常,查询具体异常信息
        String printStatusString =
            await Weight.weightChannelGetStringPrintStats; //具体异常信息
        return false;
      }
    } else {
      return false;
    }
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    Weight.weightChannelClose;
  }
}
