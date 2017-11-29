package com.kmob.paysdk.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.StringUtils;

/**
 * map工具类，
 *
 * @author verne
 */
public class MapperUtil {
    private static ConcurrentMap<String, BeanMap> beanMapCache =
            new ConcurrentHashMap<String, BeanMap>();

    /**
     * 对象转换为map
     * 
     * @param object pojo对象
     * @return map
     */
    public static Map<String, Object> beanToMap(Object object) {

        BeanMap beanMap = getBeanMap(object);
        beanMap.setBean(object);
        @SuppressWarnings("unchecked")
        Map<String, Object> toMap = beanMap;

        for (Entry<String, Object> entry : toMap.entrySet()) {
            if (entry.getValue() != null) {
                toMap.put(entry.getKey(), entry.getValue());
            }
        }
        return toMap;
    }

    /**
     * 对象转换为map
     * 
     * @param object pojo对象
     * @return map
     */
    public static Map<String, String> beanToStringMap(Object object) {
        BeanMap beanMap = getBeanMap(object);
        beanMap.setBean(object);
        @SuppressWarnings("unchecked")
        Map<String, String> toMap = beanMap;
        Map<String,String> strMap = new HashMap<String,String>();
        for (Entry<String, String> entry : toMap.entrySet()) {
            if (entry.getValue() != null && !StringUtils.isEmpty(entry.getValue())) {
                strMap.put(entry.getKey(), entry.getValue());
            }
        }
        return strMap;
    }

    /**
     * 获取BeanMap
     * 
     * @param object
     * @return BeanMap {@link BeanMap}
     */
    private static BeanMap getBeanMap(Object object) {
        BeanMap beanMap = beanMapCache.get(object.getClass().getName());
        if (beanMap == null) {
            beanMap = BeanMap.create(object);
            beanMapCache.put(object.getClass().getName(), beanMap);
        }
        return beanMap;
    }
}
