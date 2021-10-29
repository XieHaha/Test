package com.keydom.mianren.ih_doctor.utils;


import com.blankj.utilcode.util.LogUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

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

    /**
     * @param imgPaths         图片地址
     * @param width
     * @param height
     * @param pdf_save_address pdf保存地址
     */
    public static boolean imageToPDF(List<String> imgPaths, int width, List<Integer> height,
                                     String pdf_save_address) {
        try {
            Document document = new Document();
            Rectangle rect1 = new Rectangle(width, height.get(0));
            LogUtils.e("width:" + width + "  height:" + height);
            document.setPageSize(rect1);
            PdfWriter.getInstance(document, new FileOutputStream(pdf_save_address));
            document.setMargins(10, 10, 10, 10);
            document.open();
            for (int i = 0; i < imgPaths.size(); i++) {
                String path = imgPaths.get(i);
                Image img = Image.getInstance(path);
                float scale = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - 0) / img.getWidth()) * 100;
                img.scalePercent(scale);
                img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

                //为每一页重新设置高度
                Rectangle rectMore;
                if (i != 0) {
                    rectMore = new Rectangle(width, height.get(i));
                    document.setPageSize(rectMore);
                    document.newPage();
                }
                document.add(img);
            }
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
