package com.keydom.ih_common.net.factory;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * <b>类名称：</b> FastJsonResponseBodyConverter <br/>
 * <b>类描述：</b> ResponseBody转换器<br/>
 * <b>创建人：</b> Lincoln <br/>
 * <b>修改人：</b> Lincoln <br/>
 * <b>修改时间：</b> 2016年03月08日 下午3:58<br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
public class NoJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    NoJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /**
     * 转换方法
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return (T) tempStr;
    }
}