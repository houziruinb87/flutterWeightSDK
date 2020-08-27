package com.missfresh.weight;

public class WeightConstants {
    /*************MethodChannel方法通道名称**************/
    public static final String WEIGHT_METHOD_CHANNEL = "com.missfresh.weight_method_channel";
    public static final String LABEL_PRINTER_METHOD_CHANNEL = "com.missfresh.label_printer_method_channel";
    /*************EventChannel通道名称**************/
    public static final String WEIGHT_EVENT_CHANNEL = "com.missfresh.weight_event_channel";

    /*************方法名称**************/
    //是否是称重加工设备
    public static final String IS_WEIGHT_PLATFORM = "IS_WEIGHT_PLATFORM";

    //打印
     //初始化
    public static final String LABEL_CHANNEL_PRINT_INIT = "label_channel_print_init";
    //查询打印机状态,返回int
    public static final String LABEL_CHANNEL_GET_INT_PRINT_STATUS = "label_channel_get_int_print_status";
    //查询打印机状态,返回String
    public static final String LABEL_CHANNEL_GET_STRING_PRINT_STATUS = "label_channel_get_string_print_status";
    //查询打印机打印bitmap
    public static final String LABEL_PRINTER_CHANNEL_PRINT_BITMAP = "label_printer_channel_print_bitmap";
    public static final String LABEL_PRINTER_CHANNEL_INIT = "label_printer_channel_init";

    //称重
    //初始化
    public static final String WEIGHT_CHANNEL_WEIGH_INIT = "weight_channel_weigh_init";
    //开启称重端口
    public static final String WEIGHT_CHANNEL_OPEN = "weight_channel_open";
   //关闭称重端口
    public static final String WEIGHT_CHANNEL_CLOSE = "weight_channel_close";
    //获取称重信息(RAW),返回String
    public static final String WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE = "weight_channel_get_raw_weight_message";
    //称重归零
    public static final String WEIGHT_CHANNEL_SET_ZERO = "weight_channel_set_zero";


    /*************参数名称****************/
    public static final String WEIGHT_PARAM_MODEL = "WEIGHT_PARAM_MODEL";
    public static final String WEIGHT_PARAM_STATUS = "WEIGHT_PARAM_STATUS";
    public static final String WEIGHT_PARAM_IS_ZERO = "WEIGHT_PARAM_IS_ZERO";
    public static final String WEIGHT_PARAM_UNIT = "WEIGHT_PARAM_UNIT";
    public static final String WEIGHT_PARAM_NET_WEIGHT = "WEIGHT_PARAM_NET_WEIGHT";
    public static final String WEIGHT_PARAM_TARE_WEIGHT = "WEIGHT_PARAM_TARE_WEIGHT";
    public static final String WEIGHT_PARAM_GROSS_WEIGHT = "WEIGHT_PARAM_GROSS_WEIGHT";
    public static final String WEIGHT_PARAM_IS_STABLE = "WEIGHT_PARAM_IS_STABLE";


    public static final String PRINT_PARAM_MAP = "PRINT_PARAM_MAP";

            //打印标题
    public static final String PRINT_PARAM_TITLE = "PRINT_PARAM_TITLE";
    //打印规格
    public static final String PRINT_PARAM_SPEC = "PRINT_PARAM_SPEC";
    //净重
    public static final String PRINT_PARAM_NET_WEIGHT = "PRINT_PARAM_NET_WEIGHT";
    //生产时间
    public static final String PRINT_PARAM_TIME = "PRINT_PARAM_TIME";
    //存储条件
    public static final String PRINT_PARAM_STORE_CONDITION = "PRINT_PARAM_STORE_CONDITION";
    //原料编码
    public static final String PRINT_PARAM_MATERIAL_CODE = "PRINT_PARAM_MATERIAL_CODE";
    //sku编码
    public static final String PRINT_PARAM_SKU_CODE = "PRINT_PARAM_SKU_CODE";
    //包裹号
    public static final String PRINT_PARAM_PACKAGE_NUM = "PRINT_PARAM_PACKAGE_NUM";


}
