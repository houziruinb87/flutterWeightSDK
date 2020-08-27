class WeighDetailModel {
   // 当前称量方式  Net：净重称量 Tare：皮重称量
   String model;
   //打印机状态   // 秤状态  Flow:溢出   Stable:稳定   UnStable:不稳定   Error:异常
   String status;
   //当前是否是零点
   bool isZero;
   //净重
   String netWeight;
   //是否稳定,(打印机状态是否为Stable)
   bool isStable;

}