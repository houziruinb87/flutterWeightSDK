package com.missfresh.weight;

public class WeightConstants {
    /*************MethodChannel方法通道名称**************/
    public static final String WEIGHT_METHOD_CHANNEL = "com.missfresh.weight_method_channel";
    public static final String LABEL_PRINTER_METHOD_CHANNEL = "com.missfresh.label_printer_method_channel";
    /*************EventChannel通道名称**************/
    public static final String WEIGHT_EVENT_CHANNEL = "com.missfresh.weight_event_channel";

    /*************方法名称**************/
    //查询打印机状态,返回int
    public static final String LABEL_CHANNEL_GET_INT_PRINT_STATUS = "label_channel_get_int_print_status";
    //查询打印机状态,返回String
    public static final String LABEL_CHANNEL_GET_STRING_PRINT_STATUS = "label_channel_get_string_print_status";
    //查询打印机打印bitmap
    public static final String LABEL_PRINTER_CHANNEL_PRINT_BITMAP = "label_printer_channel_print_bitmap";
    public static final String LABEL_PRINTER_CHANNEL_INIT = "label_printer_channel_init";


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