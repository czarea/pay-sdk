package com.kmob.paysdk.wxpay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kmob.paysdk.dto.ResultInfo;
import com.kmob.paysdk.wxpay.request.WxEntrustRequest;
import com.kmob.paysdk.wxpay.request.WxPappayRequest;
import com.kmob.paysdk.wxpay.request.WxPayMicropayRequest;
import com.kmob.paysdk.wxpay.request.WxPayRefundQueryRequest;
import com.kmob.paysdk.wxpay.request.WxPayRefundRequest;
import com.kmob.paysdk.wxpay.request.WxPayUnifiedOrderRequest;
import com.kmob.paysdk.wxpay.response.WxNotifyResponse;
import com.kmob.paysdk.wxpay.response.WxPappayResponse;
import com.kmob.paysdk.wxpay.response.WxPayOrderCloseResponse;
import com.kmob.paysdk.wxpay.response.WxPayOrderQueryResponse;
import com.kmob.paysdk.wxpay.response.WxPayRefundQueryResponse;
import com.kmob.paysdk.wxpay.response.WxPayRefundResponse;
import com.kmob.paysdk.wxpay.response.WxPayReverseResponse;
import com.kmob.paysdk.wxpay.response.WxPayUnifiedOrderResponse;

/**
 * 微信支付抽象接口
 *
 * @author verne
 */
public interface WxPaysdkService {

    /**
     * <pre>
     * 提交刷卡支付
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
     * 应用场景：
     * 收银员使用扫码设备读取微信用户刷卡授权码以后，二维码或条码信息传送至商户收银台，由商户收银台或者商户后台调用该接口发起支付。
     * 提醒1：提交支付请求后微信会同步返回支付结果。当返回结果为“支付失败”时，调用【查询订单API】，查询支付实际交易结果；当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议1秒)重新查询支付结果，查询10次直到支付成功
     * 接口地址：   https://api.mch.weixin.qq.com/pay/micropay
     * 是否需要证书：不需要。
     * </pre>
     */
    WxPayOrderQueryResponse micropay(WxPayMicropayRequest request) throws Exception;

    /**
     * 签约返回url地址
     * 
     * @param request
     * @return
     * @throws Exception
     */
    String entrustWeb(WxEntrustRequest request) throws Exception;


    /**
     * 委托代扣
     * 
     * @param request
     * @return
     * @throws Exception
     */
    WxPappayResponse pappay(WxPappayRequest request) throws Exception;

    /**
     * 统一下单
     * 
     * @param weixinPayModel 请求实体
     * @return 下单结果xml解析后的map类型，详细参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @throws Exception
     */
    WxPayUnifiedOrderResponse unifiedOrder(WxPayUnifiedOrderRequest request) throws Exception;

    /**
     * APP支付 完成了统一下单 ->组装参数给到app
     * 
     * @param params
     * @return 返回APP支付调起支付接口所需参数
     */
    ResultInfo appPay(WxPayUnifiedOrderRequest request) throws Exception;

    /**
     * 
     * 
     * @param request
     * @param response
     * @param notifyService
     * @return
     */
    WxNotifyResponse notify(HttpServletRequest request, HttpServletResponse response,
            WxNotifyHandlerService notifyService) throws Exception;

    /**
     * 查询订单
     * 
     * @param tradeNo 微信订单号或者系统内部订单号
     * @return 微信返回xml封装的map对象 ，详细可参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
     * @throws Exception
     */
    WxPayOrderQueryResponse queryOrder(String transactionId, String tradeNo) throws Exception;

    /**
     * 关闭订单
     * 
     * @param outTradeNo 系统内部订单号
     * @return 微信返回xml封装的map对象 ，详细可参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
     * @throws Exception
     */
    WxPayOrderCloseResponse closedOrder(String outTradeNo) throws Exception;

    /**
     * 刷卡支付撤销订单
     * 
     * @param transactionId 微信订单号
     * @param outTradeNo 系统内部订单号
     * @return 微信返回xml封装的map对象
     *         ，详细可参考：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
     */
    WxPayReverseResponse reverseOrder(String transactionId, String outTradeNo) throws Exception;


    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param request 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    WxPayRefundResponse refund(WxPayRefundRequest request) throws Exception;

    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param request 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    WxPayRefundQueryResponse refundQuery(WxPayRefundQueryRequest request) throws Exception;
}
