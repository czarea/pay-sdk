# 千陌内部支付SDK
1. 做好了与Springboot的集成，使用公司框架（SpringCloud微服务架构）开箱即用，只需在pom.xml中加入如下
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

2. 已经对接微信、支付宝，银联待对接
---

## 一、微信支付

## 二、支付宝支付

## 三、银联支付