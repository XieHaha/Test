package com.keydom.mianren.ih_doctor.utils;

import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class LocalizationUtils {
    //保存在本地
    public static void fileSave2Local(Context context, Object obj, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            //通过openFileOutput方法得到一个输出流
            try {
                fos = context.openFileOutput(fileName + ".ctx", Context.MODE_PRIVATE);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(obj); //写入
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close(); //最后关闭输出流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean stringSave2Local(Context context, String string, String fileName) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("fileName" + ".txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(string);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //保存在sd卡
    public static boolean fileSave2SDCard(Object obj, String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
            File sdFile = new File(sdCardDir, fileName + ".ctx");
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(sdFile);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(obj); //写入
                oos.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (oos != null) oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static Object readFileFromLocal(Context context, String fileName) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        if (!fileExists(context, fileName + ".ctx")) {
            return null;
        }
        try {
            //fileInputStream = new FileInputStream(fileName + ".ctx");
            fileInputStream = context.openFileInput(fileName + ".ctx");
            objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (objectInputStream != null) objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object readObjFromSDCard(String fileName) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Object obj;
            File sdCardDir = Environment.getExternalStorageDirectory(); //获取SDCard目录
            File sdFile = new File(sdCardDir, fileName + ".ctx");
            if (sdFile.exists()) {
                FileInputStream fis = null;
                ObjectInputStream ois = null;
                try {
                    fis = new FileInputStream(sdFile); //获得输入流
                    ois = new ObjectInputStream(fis);
                    obj = ois.readObject();
                    Logger.e("解析成功准备返回");
                    return obj;
                } catch (IOException | ClassNotFoundException e) {
                    Logger.e("错误：" + e.toString());
                    e.printStackTrace();

                } finally {
                    try {
                        if (fis != null) fis.close();
                        if (ois != null) ois.close();
                    } catch (IOException e) {
                        Logger.e("错误：" + e.toString());
                        e.printStackTrace();

                    }
                }
            } else {
                Logger.e("文件不存在");
            }
        }

        return null;
    }

    public static boolean deleteFileFromSDCard(String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Object obj;
            File sdCardDir = Environment.getExternalStorageDirectory(); //获取SDCard目录
            File sdFile = new File(sdCardDir, fileName + ".ctx");
            return sdFile.delete();
        } else {
            return false;
        }

    }

    public static boolean deleteFileFromLocal(Context context, String fileName) {
        return context.deleteFile(fileName + ".ctx");
    }

    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}
