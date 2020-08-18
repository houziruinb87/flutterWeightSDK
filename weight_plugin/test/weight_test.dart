import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:weight/weight.dart';

void main() {
  const MethodChannel channel = MethodChannel('weight');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Weight.platformVersion, '42');
  });
}
