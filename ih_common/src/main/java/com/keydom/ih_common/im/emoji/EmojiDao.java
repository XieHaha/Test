package com.keydom.ih_common.im.emoji;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keydom.ih_common.CommonApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @link Author: song
 * <p>
 * Create: 19/4/17 下午10:25
 * <p>
 * Changes (from 19/4/17)
 * <p>
 * 19/4/17 : Create EmojiDao.java (song);
 */
public class EmojiDao {
    private static final String TAG = "EmojiDao";
    private String path;
    private static EmojiDao dao;

    public static EmojiDao getInstance(String packageName) {
        if (dao == null) {
            synchronized (EmojiDao.class) {
                if (dao == null) {
                    dao = new EmojiDao(packageName);
                }
            }
        }
        return dao;
    }

    private EmojiDao(String packageName) {
        try {
            path = CopySqliteFileFromRawToDatabases("emoji.db", packageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<EmojiBean> getEmojiBean() {
        List<EmojiBean> emojiBeanList = new ArrayList<EmojiBean>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.query("emoji", new String[]{"unicodeInt", "_id"}, null, null, null,
                null, null);
        while (cursor.moveToNext()) {
            EmojiBean bean = new EmojiBean();
            int unicodeInt = cursor.getInt(0);
            int id = cursor.getInt(1);
            bean.setUnicodeInt(unicodeInt);
            bean.setId(id);
            emojiBeanList.add(bean);
        }
        return emojiBeanList;
    }


    /**
     * 将assets目录下的文件拷贝到database中
     *
     * @return 存储数据库的地址
     */
    public static String CopySqliteFileFromRawToDatabases(String SqliteFileName,
                                                          String packageName) throws IOException {
        // 第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/<db_name>
        //复制的话这里需要换成自己项目的包名*
        //        File dir = new File("data/data/" + "com.keydom.mianren.ih_doctor" + "/databases");
        File dir = new File("data/data/" + packageName + "/databases");
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }


        File file = new File(dir, SqliteFileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。

        if (!file.exists()) {
            try {
                file.createNewFile();
                inputStream =
                        CommonApp.mApplication.getClass().getClassLoader().getResourceAsStream(
                                "assets/" + SqliteFileName);
                outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return file.getPath();
    }
}
