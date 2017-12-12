package com.kmob.paysdk.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmob.paysdk.wxpay.constant.WxPayInteractiveConstants.BillColumnNumber;
import com.kmob.paysdk.wxpay.response.WxAllBillResponse;
import com.kmob.paysdk.wxpay.response.WxRefundBillResponse;
import com.kmob.paysdk.wxpay.response.WxSuccessBillResponse;

public class WxBillUtil {

    private static final Logger logger = LoggerFactory.getLogger(WxBillUtil.class);

    public static List<WxSuccessBillResponse> getSuccessBill(String resultData) {
        String[] splitArray = resultData.split("\r\n");

        int countLine = splitArray.length;

        System.out.println("pay count " + countLine);
        int headLine = 0;

        int statHeadLine = countLine - 2;
        int statDataLine = countLine - 1;

        List<WxSuccessBillResponse> responseList = new ArrayList<WxSuccessBillResponse>();

        for (int i = 0; i < countLine; i++) {
            String lineData = splitArray[i];
            if (i == headLine) {
                System.out.println("headLine is " + splitArray[i]);
                int columnCount = lineData.split(",").length;
                if (columnCount != BillColumnNumber.SUCCESS_COUNT) {
                    logger.warn("微信下载账单格式有变，请更新程序！");
                    break;
                }
                continue;
            }

            if (i == statHeadLine) {
                logger.debug("statHeadLine is {}", splitArray[i]);
                continue;
            }

            if (i == statDataLine) {
                logger.debug("statDataLine is {}", splitArray[i]);
                continue;
            }

            String removeFirstLetterStr = lineData.substring(1);

            String[] removeFirstLetterArrayStr = removeFirstLetterStr.split(",`");

            WxSuccessBillResponse res = new WxSuccessBillResponse();

            res.setTradeDate(removeFirstLetterArrayStr[0]);
            res.setAppId(removeFirstLetterArrayStr[1]);
            res.setMchId(removeFirstLetterArrayStr[2]);
            res.setSubMchId(removeFirstLetterArrayStr[3]);
            res.setDeviceInfo(removeFirstLetterArrayStr[4]);
            res.setTransactionId(removeFirstLetterArrayStr[5]);
            res.setTradeNo(removeFirstLetterArrayStr[6]);
            res.setUserFlag(removeFirstLetterArrayStr[7]);
            res.setTradeType(removeFirstLetterArrayStr[8]);

            res.setTradeStatus(removeFirstLetterArrayStr[9]);
            res.setBankType(removeFirstLetterArrayStr[10]);
            res.setFeeType(removeFirstLetterArrayStr[11]);
            res.setTotalFee(removeFirstLetterArrayStr[12]);
            res.setCouponDiscount(removeFirstLetterArrayStr[13]);


            res.setProductId(removeFirstLetterArrayStr[14]);
            res.setAttach(removeFirstLetterArrayStr[15]);
            res.setPoundage(removeFirstLetterArrayStr[16]);
            res.setRate(removeFirstLetterArrayStr[17]);

            responseList.add(res);

        }
        return responseList;
    }

    public static List<WxAllBillResponse> getAllBill(String resultData) {
        String[] splitArray = resultData.split("\r\n");

        int countLine = splitArray.length;

        System.out.println("pay count " + countLine);
        int headLine = 0;

        int statHeadLine = countLine - 2;
        int statDataLine = countLine - 1;

        List<WxAllBillResponse> responseList = new ArrayList<WxAllBillResponse>();

        for (int i = 0; i < countLine; i++) {
            String lineData = splitArray[i];
            if (i == headLine) {
                System.out.println("headLine is " + splitArray[i]);
                int columnCount = lineData.split(",").length;
                if (columnCount != BillColumnNumber.ALL_COUNT) {
                    logger.warn("微信下载账单格式有变，请更新程序！");
                    break;
                }
                continue;
            }

            if (i == statHeadLine) {
                logger.debug("statHeadLine is {}", splitArray[i]);
                continue;
            }

            if (i == statDataLine) {
                logger.debug("statDataLine is {}", splitArray[i]);
                continue;
            }

            String removeFirstLetterStr = lineData.substring(1);

            String[] removeFirstLetterArrayStr = removeFirstLetterStr.split(",`");

            WxAllBillResponse res = new WxAllBillResponse();

            res.setTradeDate(removeFirstLetterArrayStr[0]);
            res.setAppId(removeFirstLetterArrayStr[1]);
            res.setMchId(removeFirstLetterArrayStr[2]);
            res.setSubMchId(removeFirstLetterArrayStr[3]);
            res.setDeviceInfo(removeFirstLetterArrayStr[4]);
            res.setTransactionId(removeFirstLetterArrayStr[5]);
            res.setTradeNo(removeFirstLetterArrayStr[6]);
            res.setUserFlag(removeFirstLetterArrayStr[7]);
            res.setTradeType(removeFirstLetterArrayStr[8]);

            res.setTradeStatus(removeFirstLetterArrayStr[9]);
            res.setBankType(removeFirstLetterArrayStr[10]);
            res.setFeeType(removeFirstLetterArrayStr[11]);
            res.setTotalFee(removeFirstLetterArrayStr[12]);
            res.setCouponDiscount(removeFirstLetterArrayStr[13]);

            res.setTransactionRefundId(removeFirstLetterArrayStr[14]);
            res.setRefundId(removeFirstLetterArrayStr[15]);
            res.setRefundFee(removeFirstLetterArrayStr[16]);
            res.setCouponRefundDiscount(removeFirstLetterArrayStr[17]);
            res.setRefundType(removeFirstLetterArrayStr[18]);
            res.setRefundStatus(removeFirstLetterArrayStr[19]);


            res.setProductId(removeFirstLetterArrayStr[20]);
            res.setAttach(removeFirstLetterArrayStr[21]);
            res.setPoundage(removeFirstLetterArrayStr[22]);
            res.setRate(removeFirstLetterArrayStr[23]);

            responseList.add(res);

        }
        return responseList;
    }

    public static List<WxRefundBillResponse> getRefundBill(String resultData) {
        String[] splitArray = resultData.split("\r\n");

        int countLine = splitArray.length;

        System.out.println("pay count " + countLine);
        int headLine = 0;

        int statHeadLine = countLine - 2;
        int statDataLine = countLine - 1;

        List<WxRefundBillResponse> responseList = new ArrayList<WxRefundBillResponse>();

        for (int i = 0; i < countLine; i++) {
            String lineData = splitArray[i];
            if (i == headLine) {
                System.out.println("headLine is " + splitArray[i]);
                int columnCount = lineData.split(",").length;
                if (columnCount != BillColumnNumber.REFUND_COUNT) {
                    logger.warn("微信下载账单格式有变，请更新程序！");
                    break;
                }
                continue;
            }

            if (i == statHeadLine) {
                logger.debug("statHeadLine is {}", splitArray[i]);
                continue;
            }

            if (i == statDataLine) {
                logger.debug("statDataLine is {}", splitArray[i]);
                continue;
            }

            String removeFirstLetterStr = lineData.substring(1);

            String[] removeFirstLetterArrayStr = removeFirstLetterStr.split(",`");

            WxRefundBillResponse res = new WxRefundBillResponse();

            res.setTradeDate(removeFirstLetterArrayStr[0]);
            res.setAppId(removeFirstLetterArrayStr[1]);
            res.setMchId(removeFirstLetterArrayStr[2]);
            res.setSubMchId(removeFirstLetterArrayStr[3]);
            res.setDeviceInfo(removeFirstLetterArrayStr[4]);
            res.setTransactionId(removeFirstLetterArrayStr[5]);
            res.setTradeNo(removeFirstLetterArrayStr[6]);
            res.setUserFlag(removeFirstLetterArrayStr[7]);
            res.setTradeType(removeFirstLetterArrayStr[8]);

            res.setTradeStatus(removeFirstLetterArrayStr[9]);
            res.setBankType(removeFirstLetterArrayStr[10]);
            res.setFeeType(removeFirstLetterArrayStr[11]);
            res.setTotalFee(removeFirstLetterArrayStr[12]);
            res.setCouponDiscount(removeFirstLetterArrayStr[13]);

            res.setRefundBeginTime(removeFirstLetterArrayStr[14]);
            res.setRefundSuccessedTime(removeFirstLetterArrayStr[15]);
            res.setTransactionRefundId(removeFirstLetterArrayStr[16]);
            res.setRefundId(removeFirstLetterArrayStr[17]);
            res.setRefundFee(removeFirstLetterArrayStr[18]);
            res.setCouponRefundDiscount(removeFirstLetterArrayStr[19]);
            res.setRefundType(removeFirstLetterArrayStr[20]);
            res.setRefundStatus(removeFirstLetterArrayStr[21]);


            res.setProductId(removeFirstLetterArrayStr[22]);
            res.setAttach(removeFirstLetterArrayStr[23]);
            res.setPoundage(removeFirstLetterArrayStr[24]);
            res.setRate(removeFirstLetterArrayStr[25]);

            responseList.add(res);
        }
        return responseList;
    }
}
