class WeighPrintModel {
   //产品名称
  String skuName;
  //产品规格
  String skuSpec;
  //净重
  String netWeigh;
  //必须转为yyyyMMdd
  //生产时间
  String createTime;
  //存储条件
  String storeCondition;
  //原料编码
  String materialCode;
  //skuCode
  String skuCode;
  //唯一码
  String snCode;

  //批次号
  String batchCode;
//真实生产日期
  String packageTime;
  String weightRefundFlag;

  WeighPrintModel(this.skuName, this.skuSpec, this.netWeigh, this.createTime,
      this.storeCondition, this.materialCode, this.skuCode, this.snCode,this.weightRefundFlag,{this.batchCode,this.packageTime});



}