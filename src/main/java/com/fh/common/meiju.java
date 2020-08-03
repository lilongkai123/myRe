package com.fh.common;

public enum meiju {
 SUCCESS(200,"成功"),
 ERROR(201,"失败"),
 LOGINERROR(202,"失败"),
 PRODUCT_NOT_EXIST(203,"该商品不存在"),
 PRODUCT_NOT_GROUNDING(204,"该商品没有上架")
 ;
 private int code;
 private String runame;

 meiju(int code, String runame) {
  this.code = code;
  this.runame = runame;
 }

 public String getRuname() {
  return runame;
 }

 public void setRuname(String runame) {
  this.runame = runame;
 }

 meiju() {
 }

 public int getCode() {
  return code;
 }

 public void setCode(int code) {
  this.code = code;
 }
}
