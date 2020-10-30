package com.keydom.ih_common.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {


    private static final String TAG = FileUtils.class.getSimpleName();
    private static final int BYTE = 1024;

    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static final String DST_FOLDER_NAME = "tfzl";
    private static String imgPath;

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * Formatting unit
     *
     * @param size files size
     * @return Format files size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / BYTE;
        if (kiloByte < 1) {
            return Double.toString(size) + "B";
        }

        double megaByte = kiloByte / BYTE;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / BYTE;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / BYTE;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * Gets the cache file size
     *
     * @param context context
     * @return Cache file size
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * Clear the application cache file
     *
     * @param context context
     */
    public static void clearAllCache(Context context) {
        delete(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            delete(context.getExternalCacheDir());
        }
    }

    /**
     * 获取文件大小
     *
     * @param file file
     * @return file size
     */
    //Context.getExternalFilesDir (/files/) package name your application directory -- >
    // SDCard/Android/data/, generally put some long time saved data.
    //(Context.getExternalCacheDir) -- > SDCard/Android/data/ your application package name
    // /cache/ directory, the general store temporary data cache.
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0, length = fileList.length; i < length; i++) {
                // If there are more files
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * Read file content
     *
     * @param file    file
     * @param charset charset
     * @return file content
     */
    public static String readFile(File file, String charset) {
        String fileContent = null;
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            read = new InputStreamReader(new FileInputStream(file), charset);
            reader = new BufferedReader(read);
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i == 0) {
                    fileContent = line;
                } else {
                    fileContent = fileContent + "\n" + line;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

    /**
     * Read file content
     *
     * @param file file
     * @return file content
     */
    public static String readFile(File file) {
        return readFile(file, "UTF-8");
    }

    /**
     * Get the SHA1 value of the file
     *
     * @param file file
     * @return The SHA1 value of the file
     */
    public static String getSHA1ByFile(File file) {
        if (file == null || !file.exists()) {
            return "FileNotFoundException";
        }
        long time = System.currentTimeMillis();
        InputStream in = null;
        String value = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            int numRead = 0;
            while (numRead != -1) {
                numRead = in.read(buffer);
                if (numRead > 0) {
                    digest.update(buffer, 0, numRead);
                }
            }
            byte[] sha1Bytes = digest.digest();
            //            String t = new String(buffer);
            value = convertHashToString(sha1Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * byte[] to  String
     *
     * @param hashBytes byte[]
     * @return string
     */
    private static String convertHashToString(byte[] hashBytes) {
        String returnVal = "";
        for (int i = 0, length = hashBytes.length; i < length; i++) {
            returnVal += Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return returnVal.toLowerCase();
    }

    /**
     * Gets the file name of the uploaded file
     *
     * @param filePath filePath
     * @return file name
     */
    public static String getFileName(String filePath) {
        String filename = new File(filePath).getName();
        if (filename.length() > 80) {
            filename = filename.substring(filename.length() - 80, filename.length());
        }
        return filename;
    }

    /**
     * mkdirs
     *
     * @param mkdirs mkdirs
     * @return result [boolean]
     */
    public static boolean createMkdirs(File mkdirs) {
        return mkdirs.mkdirs();
    }

    /**
     * mkdirs
     *
     * @param file file
     * @return result [boolean]
     */
    public static boolean createFile(File file) {
        if (file != null) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                createMkdirs(parentFile);
            }
        }
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Get file name
     *
     * @param url url
     * @return file name
     */
    public static String getDownloadFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * Get the picture saved directory
     *
     * @param context context
     * @return picture saved directory
     */
    public static String getPicDirectory(Context context) {
        File picFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (picFile != null) {
            return picFile.getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath() + "/Pictures";
        }
    }

    /**
     * Unzip the assets zip compressed file to the specified directory
     *
     * @param context       context
     * @param assetName     zip file names
     * @param outputDirPath outputDirPath
     * @param isReWrite     param cover
     */
    public static void unZipInAsset(Context context, String assetName, String outputDirPath,
                                    boolean isReWrite) {
        // Creating a decompression target directory.
        File file = new File(outputDirPath);
        // If the destination directory does not exist, create.
        if (!file.exists()) {
            file.mkdirs();
        }
        // Open zip file.
        InputStream inputStream = null;
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(assetName);
            zipInputStream = new ZipInputStream(inputStream);
            // Read an entry point.
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            // Using 1Mbuffer
            byte[] buffer = new byte[1024 * 1024];
            // Byte count at decompression
            int count;
            // If the entry point is empty that has all the traversal of compressed files and
            // directories.
            while (zipEntry != null) {
                // If it is a directory
                if (zipEntry.isDirectory()) {
                    file = new File(outputDirPath + File.separator + zipEntry.getName());
                    // The file needs to be covered or the file does not exist
                    if (isReWrite || !file.exists()) {
                        file.mkdir();
                    }
                } else {
                    // If it is a file
                    file = new File(outputDirPath + File.separator + zipEntry.getName());
                    // The file needs to be overridden or the file does not exist, then unpack
                    // the file.
                    if (isReWrite || !file.exists()) {
                        file.createNewFile();
                        fileOutputStream = new FileOutputStream(file);
                        while ((count = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, count);
                        }
                        fileOutputStream.flush();
                    }
                }
                // Locate next file entry.
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Unzip the assets zip compressed file to the specified directory
     *
     * @param zipPath       zip file Path
     * @param outputDirPath outputDirPath
     * @param isReWrite     param cover
     */
    public static void unZipInSdCard(String zipPath, String outputDirPath, boolean isReWrite) {
        // Creating a decompression target directory.
        File file = new File(outputDirPath);
        // If the destination directory does not exist, create.
        if (!file.exists()) {
            file.mkdirs();
        }
        // Open zip file.
        InputStream inputStream = null;
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = new FileInputStream(new File(zipPath));
            zipInputStream = new ZipInputStream(inputStream);
            // Read an entry point.
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            // Using 1Mbuffer
            byte[] buffer = new byte[1024 * 1024];
            // Byte count at decompression
            int count = 0;
            // If the entry point is empty that has all the traversal of compressed files and
            // directories.
            while (zipEntry != null) {
                // If it is a directory
                if (zipEntry.isDirectory()) {
                    file = new File(outputDirPath + File.separator + zipEntry.getName());
                    // The file needs to be covered or the file does not exist
                    if (isReWrite || !file.exists()) {
                        file.mkdir();
                    }
                } else {
                    // If it is a file
                    file = new File(outputDirPath + File.separator + zipEntry.getName());
                    // The file needs to be overridden or the file does not exist, then unpack
                    // the file
                    if (isReWrite || !file.exists()) {
                        file.createNewFile();
                        fileOutputStream = new FileOutputStream(file);
                        while ((count = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, count);
                        }
                        fileOutputStream.flush();
                    }
                }
                // Locate next file entry.
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Delete file
     *
     * @param filePath filePath
     * @return Delete redult [boolean]
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        return delete(file);
    }

    /**
     * Delete file
     *
     * @param file file
     * @return Delete redult [boolean]
     */
    public static boolean delete(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isFile()) {
            final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(to);
            to.delete();
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File innerFile : files) {
                    delete(innerFile);
                }
            }
            final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(to);
            return to.delete();
        }
        return false;
    }

    /**
     * Get file content
     *
     * @param filePath filePath
     * @return file content
     */
    public static String getFileContent(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));//Construct a BufferedReader class
                // to read the file.
                String result = null;
                String s = null;
                while ((s = br.readLine()) != null) {//Using the readLine method, read one line
                    // at a time.
                    result = result + "\n" + s;
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * Save text to file
     *
     * @param fileName fileName
     * @param content  content
     * @param append   append
     * @return if you are successful [boolean]
     */
    public static boolean saveTextValue(String fileName, String content, boolean append) {
        FileOutputStream os = null;
        try {
            File textFile = new File(fileName);
            if (!append && textFile.exists()) {
                textFile.delete();
            }
            os = new FileOutputStream(textFile);
            os.write(content.getBytes("UTF-8"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Delete all files in the directory
     *
     * @param Path path
     */
    public static void deleteAllFile(String Path) {
        // Delete all files in the directory
        File path = new File(Path);
        File files[] = path.listFiles();
        if (files != null) {
            for (File tfi : files) {
                if (tfi.isDirectory()) {
                    System.out.println(tfi.getName());
                } else {
                    tfi.delete();
                }
            }
        }
    }

    /**
     * save file
     *
     * @param in       file input stream
     * @param filePath output directory
     */
    public static File saveFile(InputStream in, String filePath) {
        File file = new File(filePath);
        byte[] buffer = new byte[4096];
        int len = 0;
        FileOutputStream fos = null;
        try {
            FileUtils.createFile(file);
            fos = new FileOutputStream(file);
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * file exists
     *
     * @param path path
     * @return file exists
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * File Base64 encryption
     *
     * @param path path
     * @return Base64 encryption string
     */
    public static String fileToBase64String(String path) {
        FileInputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            return Base64.encodeToString(fileBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 初始化保存路径
     *
     * @return
     */
    public static String initPath() {
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**
     * 保存Bitmap到sdcard
     *
     * @param b 得到的图片
     */
    public static void saveBitmap(Bitmap b) {
        String path = initPath();
        long dataTake = System.currentTimeMillis();
        imgPath = path + "/" + dataTake + ".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(imgPath);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return 保存到sd卡的图片路径
     */
    public static String getImgPath() {
        return imgPath;
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads" +
                        "/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs
                    , null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
