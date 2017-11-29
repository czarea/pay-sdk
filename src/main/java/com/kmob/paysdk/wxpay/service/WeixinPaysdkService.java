package com.kmob.paysdk.wxpay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kmob.paysdk.dto.ResultInfo;
import com.kmob.paysdk.wxpay.model.WeixinDownLoadBillRequest;
import com.kmob.paysdk.wxpay.model.WeixinNotifyResponse;
import com.kmob.paysdk.wxpay.model.WeixinPayRequest;
import com.kmob.paysdk.wxpay.model.WeixinRefundRequest;

/**
 * 微信支付抽象接口
 *
 * @author verne
 */
public interface WeixinPaysdkService {
    /**
     * 统一下单
     * 
     * @param weixinPayModel 请求实体
     * @return 下单结果xml解析后的map类型，详细参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @throws Exception
     */
    Map<String, String> unifiedOrder(WeixinPayRequest weixinPayModel) throws Exception;
    
    /**
     * 统一下单
     * 
     * @param params 所有参数都上传到微信
     * @return 下单结果xml解析后的map类型，详细参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @throws Exception
     */
    Map<String, String> unifiedOrder(Map<String,String> params) throws Exception;
    
    /**
     * APP支付
     * 完成了统一下单 ->组装参数给到app
     * 
     * @param params
     * @return 返回APP支付调起支付接口所需参数
     */
    ResultInfo appPay(Map<String,String> params) throws Exception;
    
    /**
     * 
     * 
     * @param request
     * @param response
     * @param notifyService
     * @return
     */
    WeixinNotifyResponse notify(HttpServletRequest request, HttpServletResponse response,NotifyService notifyService) throws Exception;

    /**
     * 查询订单
     * 
     * @param tradeNo 微信订单号或者系统内部订单号
     * @return 微信返回xml封装的map对象 ，详细可参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
     * @throws Exception
     */
    Map<String, String> queryOrder(String tradeNo) throws Exception;

    /**
     * 关闭订单
     * 
     * @param outTradeNo 系统内部订单号
     * @return 微信返回xml封装的map对象 ，详细可参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
     * @throws Exception
     */
    Map<String, String> closedOrder(String outTradeNo) throws Exception;

    /**
     * 刷卡支付撤销订单
     * 
     * @param transactionId 微信订单号
     * @param outTradeNo 系统内部订单号
     * @return 微信返回xml封装的map对象 ，详细可参考：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
     */
    Map<String, String> reverseOrder(String transactionId, String outTradeNo) throws Exception;
    
    
    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param request 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    Map<String,String> refund(WeixinRefundRequest request) throws Exception;
    
    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param request 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    Map<String,String> refundQuery(WeixinRefundRequest request) throws Exception;
    
    /**
     * 作用：对账单下载（成功时返回对账单数据，失败时返回XML格式数据）<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param request 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    Map<String, String> downloadBill(WeixinDownLoadBillRequest request) throws Exception;
}
