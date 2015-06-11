package com.voitenko.dutyhelper.BL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataConverter {
    public static String timeStampToString(Timestamp time) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");
        int microFraction = time.getNanos() / 1000;

        StringBuilder sb = new StringBuilder(fmt.format(time));
        String tail = String.valueOf(microFraction);
        for (int i = 0; i < 6 - tail.length(); i++) {
            sb.append('0');
        }
        sb.append(tail);
        return sb.toString().substring(0, sb.length() - 7);
    }

    public static Timestamp stringToTimeStamp(String s) {
        Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(s);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
        }
        return timestamp;
    }

    public static String getTime(String time) {
        StringBuilder sb = new StringBuilder();
        sb.append(time.substring(0, 10)).append(" ").append(time.substring(11, 16));
        return sb.toString();
    }

    public static String setRightTime(String time) {
        StringBuilder sb = new StringBuilder();
        sb.append(time.substring(0, 10)).append("T").append(time.substring(11, 16)).append(":00").append("Z");
        return sb.toString();
    }

    public static String getDate(String time) {
        StringBuilder sb = new StringBuilder();
        sb.append(time.substring(0, 10));
        return sb.toString();
    }

    public static Date parseDate(String str_date) throws ParseException {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(str_date);
    }

    public static void writeFile(String text, File file) throws IOException {
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(text.getBytes());
        stream.close();
    }

    public static String readFile(File file) throws IOException {
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        return new String(bytes);
    }
}
