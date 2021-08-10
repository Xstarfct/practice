package com.fct.rpc.core.serialization;

import lombok.Getter;

/**
 *
 * @author fct
 * @date 2021-08-09 14:23
 */
public enum  SerializationTypeEnum {

    HESSIAN((byte) 0),
    JSON((byte) 1);

    @Getter
    private final byte type;

    SerializationTypeEnum(byte type) {
        this.type = type;
    }

    public static SerializationTypeEnum parseByName(String typeName) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.name().equalsIgnoreCase(typeName)) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

    public static SerializationTypeEnum parseByType(byte type) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.getType() == type) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

}
