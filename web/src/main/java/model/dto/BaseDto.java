package model.dto;

public abstract class BaseDto<T> {
    abstract T toBean();

    abstract BaseDto<T> fromBean(T bean);
}
