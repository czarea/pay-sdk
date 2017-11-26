# 千陌内部支付SDK
---
## 一、完成与Springboot的集成，使用公司框架（SpringCloud微服务架构）开箱即用，分三步。
### 1.1 只需在pom.xml中加入以下依赖
```
<dependency>
	<groupId>com.kmob</groupId>
	<artifactId>paysdk</artifactId>
	<version>0.0.1</version>
	<exclusions>
		<exclusion>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
		</exclusion>
		<exclusion>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-simple</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

### 1.2 application.*.yaml文件中配置好微信或者支付宝相关配置,其中use: true表示启用相关支付

```
alipay:
  use: true
  appId: xxx
  partner: xxx
  notifyUrl: xxx
  privateKey: xxx
  alipayPublicKey: xxx
wxpay:
  use: true
  appId: xxx
  mchId: xxx
  key: xxx
  notifyUrl: xxx
```

### 1.3 使用
```
@Autowired
private AliPaysdkService aliPaysdkService;
AlipayTradeAppPayResponse appResponse = aliPaysdkService.appPay(aliPayModel);
```

```
@Autowired
private WeixinPaysdkService weixinPayService;
Map<String, String> unifiedOrderResult = weixinPayService.unifiedOrder(weixinPayModel);
```

## 二、微信支付

## 三、支付宝支付

## 四、银联支付