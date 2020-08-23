package com.missfresh.weight;

import android.content.Context;
import android.util.Log;

import com.missfresh.labelprinter.LabelPrinterMethodChannel;
import com.missfresh.labelprinter.MFLabelPrinter;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * WeightPlugin
 */
public class WeightPlugin implements FlutterPlugin {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private WeightMethodChannel sWeightMethodChannel;
    private LabelPrinterMethodChannel sLabelPrinterMethodChannel;
    private WeightEventChannel sWeightEventChannel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        Log.i("nb", "onAttachedToEngine");
        try {
            MFLabelPrinter.init(flutterPluginBinding.getApplicationContext());
            MFWeigh.init(flutterPluginBinding.getApplicationContext());
            onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());

        }catch (Exception e){
            Log.e("WeightPlugin", e.getMessage());

        }
         }

    public void onAttachedToEngine(Context context, BinaryMessenger messenger) {
        sLabelPrinterMethodChannel = new LabelPrinterMethodChannel(context, messenger);
        sWeightMethodChannel = new WeightMethodChannel(context, messenger);
        sWeightEventChannel = new WeightEventChannel(context, messenger);

    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        Log.i("nb", "registerWith");
        WeightPlugin weigh = new WeightPlugin();
        weigh.onAttachedToEngine(registrar.context(), registrar.messenger());
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        sLabelPrinterMethodChannel.getsLabelPrinterMethodChannel().setMethodCallHandler(null);
              sWeightMethodChannel.getWeightMethodChannel().setMethodCallHandler(null);
        sWeightEventChannel.getsWeightEventChannel().setStreamHandler(null);
    }
}
