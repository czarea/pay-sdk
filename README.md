# 千陌内部支付SDK

完成与Springboot的集成，使用公司框架（SpringCloud微服务架构）开箱即用，分三步


---
## 一、“三部曲”
### 1.1 加入sdk包

```
<repositories>
	<repository>
		<id>1000mob-nexus-wan</id>
		<name>1000mob nexus repository</name>
		<url>http://59.110.6.79:9081/repository/maven-public/</url>
	</repository>
</repositories>

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

### 1.2 新增配置

```
alipay:
  use: true #启用支付宝支付
  useController: true #使用sdk中的Controller接收支付通知
  appId: xxx
  partner: xxx
  notifyUrl: xxx
  privateKey: xxx
  alipayPublicKey: xxx
wxpay:
  use: true #启用微信支付
  useController: true #使用sdk中的Controller接收支付通知
  appId: xxx
  mchId: xxx
  key: xxx
  notifyUrl: xxx
```

### 1.3 实现业务代码
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

---

## 二、微信支付详细


---

## 三、支付宝支付详细


---

## 四、银联支付详细