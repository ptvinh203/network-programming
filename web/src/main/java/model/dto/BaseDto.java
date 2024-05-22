package model.dto;

public abstract class BaseDto<T> {
    public abstract T toBean();

    public abstract BaseDto<T> fromBean(T bean);
}
