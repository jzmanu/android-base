package com.manu.core.http.gson;

/**
 * Created by jzman
 * Powered by 2018/3/17 0017.
 */

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.exception.MException;

import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private Type type;
//    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
//        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //获得Response
        String response = value.string();
        //构建泛型的Type，ResultBean<Type>
        Type resultBeanType = $Gson$Types.newParameterizedTypeWithOwner(null, ResultBean.class,type);

        ResultBean resultBean = gson.fromJson(response,resultBeanType);
        if (resultBean.isError()){
            throw new MException(resultBean.getErrorCode(),resultBean.getMessage());
        }else{
            return (T) resultBean.getResults();
        }

//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            T result = adapter.read(jsonReader);
//            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
//                throw new JsonIOException("JSON document was not fully consumed.");
//            }
//            return result;
//        } finally {
//            value.close();
//        }
    }
}
