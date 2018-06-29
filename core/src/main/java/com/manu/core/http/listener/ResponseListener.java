package com.manu.core.http.listener;

import com.manu.core.http.progress.ProgressListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by jzman
 * Powered by 2018/6/9 0009.
 */

public abstract class ResponseListener<T> extends ProgressListener{

    public Type getType() {
        Type superclass = getClass().getGenericSuperclass();
        return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(String msg);

}

