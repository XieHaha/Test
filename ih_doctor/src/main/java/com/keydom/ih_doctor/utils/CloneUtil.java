package com.keydom.ih_doctor.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Author：song
 * @Date：19/1/19 下午3:49
 * 修改人：xusong
 * 修改时间：19/1/19 下午3:49
 */
public class CloneUtil {

    private CloneUtil() {
        throw new AssertionError();
    }

    public static <T extends Serializable> T clone(T object) throws IOException,
            ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }
}
