package com.yanhua.mvvmlibrary.binding.command;

/**
 * Created by king on 2019.1.11 *
 * @param <T> the first argument type
 */
public interface BindingConsumer<T> {
    void call(T t);
}
