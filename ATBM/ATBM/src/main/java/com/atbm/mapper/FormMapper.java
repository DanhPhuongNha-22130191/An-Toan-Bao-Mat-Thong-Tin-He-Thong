package com.atbm.mapper;

import com.atbm.utils.LogUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class FormMapper {
    public static <T> T bind(Map<String, ?> properties, Class<T> clazz) {
        try {
            T bean = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.populate(bean, properties);
            return bean;
        } catch (Exception e) {
            LogUtils.debug(FormMapper.class, e.getMessage());
            throw new RuntimeException("Không thể bind form vào object", e);
        }
    }
}

