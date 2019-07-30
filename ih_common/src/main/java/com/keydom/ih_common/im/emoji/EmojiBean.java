package com.keydom.ih_common.im.emoji;

/**
 *
 * @link
 *
 * Author: song
 *
 * Create: 19/4/17 下午10:56
 *
 * Changes (from 19/4/17)
 *
 * 19/4/17 : Create EmojiBean.java (song);
 *
 *
 *
 */

public class EmojiBean {
    private int id;
    private int unicodeInt;

    public String getEmojiString() {
        return  getEmojiStringByUnicode(unicodeInt);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnicodeInt() {
        return getEmojiStringByUnicode(unicodeInt);
    }

    public void setUnicodeInt(int unicodeInt) {
        this.unicodeInt = unicodeInt;
    }

    public static String getEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    @Override
    public String toString() {
        return "EmojiBean{" +
                "id=" + id +
                ", unicodeInt=" + unicodeInt +
                '}';
    }
}
