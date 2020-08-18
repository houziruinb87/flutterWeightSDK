import 'dart:collection';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:weight/weight.dart';
import 'package:weight/weight_constants.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      print("1");
      await Weight.weightChannelInitPrint;
      print("2");

    } on PlatformException {
      print("error");

      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
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

          child:Column(
            children: <Widget>[
              Text('Running on: $_platformVersion\n'),
              Container(
                height: 100,
                width: 100,
                color: Colors.red,
                child: InkWell(
                   onTap: ()async{
                    String platformVersion;
                    // Platform messages may fail, so we use a try/catch PlatformException.
                    try {
                      platformVersion = await Weight.weightChannelGetStringPrintStats;
                      HashMap hashMap = HashMap<String,String>();
                      hashMap.putIfAbsent(WeightConstants.PRINT_PARAM_TITLE, () => "a");
                    await Weight.weightChannelPrintBitmap(hashMap);

                    } on PlatformException {
                      platformVersion = 'Failed to get platform version.';
                    }

                    // If the widget was removed from the tree while the asynchronous platform
                    // message was in flight, we want to discard the reply rather than calling
                    // setState to update our non-existent appearance.
                    if (!mounted) return;

                    setState(() {
                      _platformVersion = platformVersion;
                    });
                  },
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
