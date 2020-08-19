package com.sdgtt.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.matches;

public class ValidationUtil {

    public static boolean validatePhoneNo(String phoneNo) {
        if (StringUtils.isEmpty(phoneNo)) {
            return false;
        }
        String regExp = "^1\\d{10}$";
        return matches(regExp, phoneNo);
    }

    public static boolean validateEmail(String email) {
        String regexExpress = "\\b^[a-zA-Z0-9][a-zA-Z0-9_\\-\\.]{0,50}@(?:[a-zA-Z0-9\\-]+\\.)+[a-zA-Z]+$\\b";
        Matcher matcher = compile(regexExpress).matcher(email);
        return matcher.matches();
    }

    /**
     * 校验实体对象属性是否全为null,注意：此方法仅考虑对象属性皆为String类型
     * @param obj
     * @return      true : 全为null
     *              false : 不全为null
     */
    public static boolean isALlNullByObjectField(Object obj, String... excludeFields) {
        if (Objects.isNull(obj)) {
            return true;
        }

        List<String> excludeFieldsList = Arrays.asList(excludeFields);
        Class<? extends Object> objClass = obj.getClass();
        Field[] objField = objClass.getDeclaredFields();
        try {
            for (int i = 0; i < objField.length; i++) {
                Field field = objField[i];
                if (excludeFieldsList.contains(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if (fieldValue != null) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            return false;
        }
        return true;
    }

    /**
     * 校验实体对象属性是否全不为null,注意：此方法仅考虑对象属性皆为String类型
     *
     * @param obj
     * @return
     *          true : 全部不为空;
     *          false : 有空存在
     */
    public static boolean isALlNotNullByObjectField(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (Objects.isNull(obj)) {
            return false;
        }
        Class<? extends Object> objClass = obj.getClass();
        Field[] objField = objClass.getDeclaredFields();
        for (int i = 0; i < objField.length; i++) {
            Field field = objField[i];
            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            if (fieldValue != null && fieldValue instanceof String && StringUtils.isNotEmpty((String) fieldValue)) {
            } else if (fieldValue != null) {
                /**
                 * 增加一个功能，判断非字符串类型的空判断，目前枚举测试过，没问题
                 * 如果要用到此功能，请先测试一下，或是进一步优化
                 */
            } else {
                // 只要找到一个为空的字符串，即该对象至少有一个为null、""的属性，返回fasle
                return false;
            }
        }
        return true;
    }

    /**
     * 指定字段之外的字段不为空
     * @param obj
     * @return
     *          true:全部不为空（exclude : fields）
     *          false:有空存在（exclude : fields）
     */
    public static boolean isNotNullExclusionFields(Object obj, String[] fields) {
        if (Objects.isNull(obj)) {
            return false;
        }
        List<String> fieldsList = Arrays.asList(fields);

        Class<? extends Object> objClass = obj.getClass();
        Field[] objField = objClass.getDeclaredFields();
        try {
            for (int i = 0; i < objField.length; i++) {
                Field field = objField[i];
                if (fieldsList.contains(field.getName())) {
                    // fields 中的属性 不判断
                    continue;
                }
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if (fieldValue != null && fieldValue instanceof String && StringUtils.isNotEmpty((String) fieldValue)) {
                } else if (fieldValue != null) {
                    /**
                     * 增加一个功能，判断非字符串类型的空判断，目前枚举测试过，没问题
                     * 如果要用到此功能，请先测试一下，或是进一步优化
                     */
                } else {
                    // 只要找到一个为空的字符串，即该对象至少有一个为null、""的属性，返回fasle
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            return false;
        }
        return true;
    }

    /**
     * 方法二：推荐，速度最快
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    public static <T> boolean checkEmpty(T... objects) {
        if (null == objects) {
            return true;
        }
        for (T object : objects) {
            if (object instanceof String && StringUtils.isEmpty((String) object)) {
                return true;
            }
            if (null == object) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Title: getNullPropertyNames
     * @Description: 获取值为空的属性名称
     * @createdBy:byrc
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
            if (srcValue instanceof String && StringUtils.isEmpty(String.valueOf(srcValue))) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }



}
