class WeightConstants{
  
  /*************MethodChannel方法通道名称**************/
  static const String WEIGHT_METHOD_CHANNEL = "com.missfresh.weight_method_channel";
  static const String LABEL_PRINTER_METHOD_CHANNEL = "com.missfresh.label_printer_method_channel";
  /*************EventChannel通道名称**************/
  static const String WEIGHT_EVENT_CHANNEL = "com.missfresh.weight_event_channel";

  /*************方法名称**************/
  //是否是称重加工设备
  static const String IS_WEIGHT_PLATFORM = "IS_WEIGHT_PLATFORM";


  static const String LABEL_CHANNEL_GET_INT_PRINT_STATUS = "label_channel_get_int_print_status";
  static const String LABEL_CHANNEL_GET_STRING_PRINT_STATUS = "label_channel_get_string_print_status";
  static const String LABEL_PRINTER_CHANNEL_PRINT_BITMAP = "label_printer_channel_print_bitmap";
  static const String LABEL_PRINTER_CHANNEL_PRINT_BITMAP_NEW = "label_printer_channel_print_bitmap_new";

  static const String LABEL_PRINTER_CHANNEL_INIT = "label_printer_channel_init";

  //称重
  //开启称重端口
  static const  String WEIGHT_CHANNEL_OPEN = "weight_channel_open";
  //关闭称重端口
  static const  String WEIGHT_CHANNEL_CLOSE = "weight_channel_close";
  //获取称重信息(RAW),返回String
  static const  String WEIGHT_CHANNEL_GET_RAW_WEIGHT_MESSAGE = "weight_channel_get_raw_weight_message";
  //称重归零
  static const  String WEIGHT_CHANNEL_SET_ZERO = "weight_channel_set_zero";

  /*************参数名称****************/
  static const String WEIGHT_PARAM_MODEL = "WEIGHT_PARAM_MODEL";
  static const String WEIGHT_PARAM_STATUS = "WEIGHT_PARAM_STATUS";
  static const String WEIGHT_PARAM_IS_ZERO = "WEIGHT_PARAM_IS_ZERO";
  static const String WEIGHT_PARAM_UNIT = "WEIGHT_PARAM_UNIT";
  static const String WEIGHT_PARAM_NET_WEIGHT = "WEIGHT_PARAM_NET_WEIGHT";
  static const String WEIGHT_PARAM_TARE_WEIGHT = "WEIGHT_PARAM_TARE_WEIGHT";
  static const String WEIGHT_PARAM_GROSS_WEIGHT = "WEIGHT_PARAM_GROSS_WEIGHT";
  static const String WEIGHT_PARAM_IS_STABLE = "WEIGHT_PARAM_IS_STABLE";

  static const String PRINT_PARAM_MAP = "PRINT_PARAM_MAP";
  //打印标题
  static const String PRINT_PARAM_TITLE = "PRINT_PARAM_TITLE";
  //打印规格
  static const String PRINT_PARAM_SPEC = "PRINT_PARAM_SPEC";
  //净重
  static const String PRINT_PARAM_NET_WEIGHT = "PRINT_PARAM_NET_WEIGHT";
  //生产时间
  static const String PRINT_PARAM_TIME = "PRINT_PARAM_TIME";
  //存储条件
  static const String PRINT_PARAM_STORE_CONDITION = "PRINT_PARAM_STORE_CONDITION";
  //原料编码
  static const String PRINT_PARAM_MATERIAL_CODE = "PRINT_PARAM_MATERIAL_CODE";
  //sku编码
  static const String PRINT_PARAM_SKU_CODE = "PRINT_PARAM_SKU_CODE";
  //包裹号(SN)
  static const String PRINT_PARAM_PACKAGE_NUM = "PRINT_PARAM_PACKAGE_NUM";
  //批次号
  static const  String PRINT_PARAM_BATCH_CODE = "PRINT_PARAM_BATCH_CODE";
  //包装时间
  static const  String PRINT_PARAM_PACKAGE_TIME = "PRINT_PARAM_PACKAGE_TIME";
  //是否打印称重退款
  static const  String PRINT_PARAM_WEIGHT_REFUND_FLAG = "PRINT_PARAM_WEIGHT_REFUND_FLAG";




 // 秤状态  Flow:溢出
  static const String WEIGHT_MODE_FLOW = "Flow";
 // Stable:稳定
  static const String WEIGHT_MODE_STABLE = "Stable";
  // UnStable:不稳定
  static const String WEIGHT_MODE_UNSTABLE = "UnStable";
  // Error:异常
  static const String WEIGHT_MODE_ERROR = "Error";


}