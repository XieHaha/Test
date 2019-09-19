package com.keydom.ih_common.utils;

public class JsonFormat {
    /*** 默认每次缩进两个空格*/
    private static final String empty = "  ";

    public static String format(String json) {

            StringBuilder indent = new StringBuilder();//缩进
            StringBuilder sb = new StringBuilder();

            for (char c : json.toCharArray()) {
                switch (c) {
                    case '{':
                        indent.append(" ");
                        sb.append("{\n").append(indent);
                        break;
                    case '}':
                        indent.deleteCharAt(indent.length() - 1);
                        sb.append("\n").append(indent).append("}");
                        break;
                    case '[':
                        indent.append(" ");
                        sb.append("[\n").append(indent);
                        break;
                    case ']':
                        indent.deleteCharAt(indent.length() - 1);
                        sb.append("\n").append(indent).append("]");
                        break;
                    case ',':
                        sb.append(",\n").append(indent);
                        break;
                    default:
                        sb.append(c);
                }
            }
            return sb.toString();
        }


}