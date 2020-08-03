package com.fh.order.controller;

import com.fh.common.ConstantCommon;
import com.fh.common.ServerResponse;
import com.fh.sdk.MyConfig;
import com.fh.sdk.WXPay;
import com.fh.util.Idempotent;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("WXPayExample")
public class WXPayExample {

    @RequestMapping("wxPayExample")
    public ServerResponse wxPayExample(String orderNo) {
        try {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "泽泽商城充值中心");
        data.put("out_trade_no", orderNo);
        data.put("device_info", "WEB");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");
        SimpleDateFormat smf =new SimpleDateFormat("yyyyMMddHHmmss");
        data.put("time_expire", smf.format(DateUtils.addMinutes(new Date(),2)));

            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            if (!resp.get("return_code").equalsIgnoreCase("SUCCESS")){
                return ServerResponse.error(resp.get("return_msg"));
            }
            if (!resp.get("result_code").equalsIgnoreCase("SUCCESS")){
                return ServerResponse.error(resp.get("err_code_des"));
            }
            String code_url = resp.get("code_url");
            return ServerResponse.success(code_url);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(e.getMessage());
        }
    }

    @RequestMapping("queryOrder")
    public ServerResponse queryOrder(String orderNo){
        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);
            int count=0;
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", orderNo);
            for (;;){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);
                if (!resp.get("return_code").equalsIgnoreCase("SUCCESS")){
                    return ServerResponse.error("11");
                }
                if (!resp.get("result_code").equalsIgnoreCase("SUCCESS")){
                    return ServerResponse.error("22");
                }
                if (resp.get("trade_state").equalsIgnoreCase("SUCCESS")){
                    return ServerResponse.success();
                }
                count++;
                Thread.sleep(3000);
                if (count>20){
                    return ServerResponse.error("");
                }
            }
        } catch (Exception e) {
            return ServerResponse.error(e.getMessage());
        }
    }



}
