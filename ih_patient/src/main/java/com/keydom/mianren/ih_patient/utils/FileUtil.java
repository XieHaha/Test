/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.keydom.mianren.ih_patient.utils;

import android.content.Context;

import java.io.File;

public class FileUtil {
    public static File getSaveFile(Context context,String name) {
        File file = new File(context.getFilesDir(), name+".jpg");
        return file;
    }
}
