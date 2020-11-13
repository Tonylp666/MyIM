package com.tonylp.server.utils;

import com.tonylp.server.common.protocol.OperationType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.Objects;

public class EnumUtil {
    private static final Log LOG = LogFactory.getLog(EnumUtil.class);

    public static <E> E getByCode(Class<E> clz, Byte code) {

            try {
                for (E e : clz.getEnumConstants()) {
                    Field field = clz.getDeclaredField("type");
                    field.setAccessible(true);
                    if (Objects.equals(field.get(e),code)){
                        return e;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOG.error("Exeception in EnumUtil#getByCode(), getByCode() faild.");
                e.printStackTrace();
            }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(EnumUtil.getByCode(OperationType.class,(byte)1));
        System.out.println(EnumUtil.getByCode(OperationType.class,(byte)10));
        System.out.println(EnumUtil.getByCode(OperationType.class,(byte)20));
    }
}
