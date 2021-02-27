import 'dart:collection';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:weight/weight.dart';
import 'package:weight/weight_constants.dart';
import 'package:weight/weigh_detail_model.dart';
import 'package:weight/weigh_print_model.dart';

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
      if (event is WeighDetailModel) {
        setState(() {
          weightNetWeight = event.netWeight;
        });
      }
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
//                    //拼接打印参数
//                    HashMap hashMap = HashMap<String, String>();
//                    //标题
//                    hashMap.putIfAbsent(
//                        WeightConstants.PRINT_PARAM_TITLE, () => "电饭锅梵蒂冈");
//                    //规格
//                    hashMap.putIfAbsent(
//                        WeightConstants.PRINT_PARAM_SPEC, () => "100ml*100");
//                    //净重
//                    hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_NET_WEIGHT,
//                        () => weightNetWeight);
//                    //打印时间(必须是YYYYMMDD)
//                    hashMap.putIfAbsent(
//                        WeightConstants.PRINT_PARAM_TIME, () => "20200101");
//                    //存储条件
//                    hashMap.putIfAbsent(
//                        WeightConstants.PRINT_PARAM_STORE_CONDITION,
//                        () => "常温");
//                    //原料码
//                    hashMap.putIfAbsent(
//                        WeightConstants.PRINT_PARAM_MATERIAL_CODE,
//                        () => "34234562");
//                    //SKU码
//                    hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_SKU_CODE,
//                        () => "dfgdfgdfgdfg-34435-2");
//                    //唯一码
//                    hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_PACKAGE_NUM,
//                        () => "WH00000000012345");

                    WeighPrintModel weighPrintModel = WeighPrintModel(
                        "电饭锅梵蒂冈",
                        "100ml*100",
                        weightNetWeight + "",
                        "2020-01-01 15:12:12",
                        "常温",
                        "34234562",
                        "dfgdfgdfgdfg-34435-2",
                        "WH00000000012345",batchCode: 'sdfsdf',packageTime: '2020-01-01 15:12:12');
                    await Weight.weightChannelPrintNew(weighPrintModel)
                        .catchError((error) {
                        print("${error}");
                    });
                  },
                ),
              ),
              Container(
                height: 100,
                width: 100,
                color: Colors.blue,
                child: InkWell(
                  onTap: () async {
                    String zeroString =
                        await Weight.weightChannelSetZero.catchError((error) {
                      if (error is PlatformException) {
                        print("code==" + error.code);
                        print("message==" + error.message);
                        print("detail==" + error.details);
                      }
                    });
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
                    String printStatusString = await Weight
                        .weightChannelGetStringPrintStats
                        .catchError((error) {
                      print("error==" );

                      if (error is PlatformException) {
                        print("code==" + error.code);
                        print("message==" + error.message);
                        print("detail==" + error.details);
                      }else{
                        print("code==" + error.toString());
                      }
                    });
                    // print(printStatusString);
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
                    int printStatusInt = await Weight
                        .weightChannelGetIntPrintStatus
                        .catchError((error) {
                      if (error is PlatformException) {
                        print("code==" + error.code);
                        print("message==" + error.message);
                        print("detail==" + error.details);
                      }
                    });
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
      WeighPrintModel weighPrintModel = WeighPrintModel(
          "刚哥威武霸气吊炸天",
          "10000ml*19",
          "200",
          "2020-11-12 23:31:43",
          "刚哥咋这nb",
          "1203912389",
          "SJ989-23SDF-23RK",
          "SJ989-SDFKJ1-1498723SDF-23RK",
      );
      weighPrintModel.skuName = "刚哥威武霸气吊炸天";
      weighPrintModel.skuSpec = "10000ml*19";
      weighPrintModel.netWeigh = "200";
      weighPrintModel.createTime = "2020-11-12 23:31:43";
      weighPrintModel.storeCondition = "刚哥咋这nb";
      weighPrintModel.materialCode = "1203912389";
      weighPrintModel.skuCode = "SJ989-23SDF-23RK";
      weighPrintModel.snCode = "SJ989-SDFKJ1-1498723SDF-23RK";
      isCurrentPrintSuccess = await Weight.weightChannelPrint(weighPrintModel);
      if (!isCurrentPrintSuccess) {
        //打印机状态不正常,查询具体异常信息
        String printStatusString =
            await Weight.weightChannelGetStringPrintStats; //具体异常信息
      }
      return isCurrentPrintSuccess;

//      //判断打印机状态
//      int printStatusInt = await Weight.weightChannelGetIntPrintStatus;
//      if (printStatusInt == 0) {
//
//        //打印机状态正常
//        //拼接打印参数
//        HashMap hashMap = HashMap<String, String>();
//        //标题
//        hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TITLE, () => "电饭锅梵蒂冈");
//        //规格
//        hashMap.putIfAbsent(
//            WeightConstants.PRINT_PARAM_SPEC, () => "100ml*100");
//        //净重
//        hashMap.putIfAbsent(
//            WeightConstants.PRINT_PARAM_NET_WEIGHT, () => weightNetWeight);
//        //打印时间(必须是YYYYMMDD)
//        hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TIME, () => "20200101");
//        //存储条件
//        hashMap.putIfAbsent(
//            WeightConstants.PRINT_PARAM_STORE_CONDITION, () => "常温");
//        //原料码
//        hashMap.putIfAbsent(
//            WeightConstants.PRINT_PARAM_MATERIAL_CODE, () => "34234562");
//        //SKU码
//        hashMap.putIfAbsent(
//            WeightConstants.PRINT_PARAM_SKU_CODE, () => "dfgdfgdfgdfg-34435-2");
//        //唯一码
//        hashMap.putIfAbsent(
//            WeightConstants.PRINT_PARAM_PACKAGE_NUM, () => "WH00000000012345");
//
//        //开始打印
//        isCurrentPrintSuccess = await Weight.weightChannelPrintBitmap(hashMap);
//        if (isCurrentPrintSuccess) {
//          print("打印成功");
//        } else {
//          print("打印失败");
//        }
//        return isCurrentPrintSuccess;
//      } else {
//        //打印机状态不正常,查询具体异常信息
//        String printStatusString =
//            await Weight.weightChannelGetStringPrintStats; //具体异常信息
//        return false;
//      }
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
