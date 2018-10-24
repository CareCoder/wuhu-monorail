package net.cdsunrise.wm.base.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 当src对象的键值为Null时,使用如下方式就不会把target对象的对应键值覆盖成空了
 */
public class BeanUtilIgnore {
    public static String[] getNullPropertyNames ( Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    public static void copyPropertiesIgnoreNull( Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
