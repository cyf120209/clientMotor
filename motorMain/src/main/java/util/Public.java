package util;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.service.acknowledgement.ReadPropertyAck;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2017/9/14.
 */
public class Public {

    /**
     * 正则匹配
     * @param str
     * @param regx
     * @return true：匹配到，false：未匹配到
     */
    public static Boolean matchString(String str, String regx) {
        //1.将正在表达式封装成对象Patten 类来实现
        Pattern pattern = Pattern.compile(regx);
        //2.将字符串和正则表达式相关联
        Matcher matcher = pattern.matcher(str);
        //3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
//        System.out.println(matcher.matches());
//        String group = "";
        //查找符合规则的子串
        while (matcher.find()) {
            //获取 字符串
//            group = matcher.group();
            //获取的字符串的首位置和末位置
//            System.out.println(matcher.start() + "--" + matcher.end());
            return true;
        }
        return false;
    }

    /**
     * 则匹配
     * @param str
     * @param regx
     * @return 返回匹配到的第一个字符串
     */
    public static String matchStr(String str, String regx) {
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
//        System.out.println(matcher.matches());
        String group = "";
        while (matcher.find()) {
            group = matcher.group();
//            System.out.println(matcher.start() + "--" + matcher.end());
            return group;
        }
        return "";
    }

    /**
     * 正则匹配
     * @param str
     * @param regx
     * @return 返回匹配到所有的字符串
     */
    public static String getAllString(String str, String regx) {
        //1.将正在表达式封装成对象Patten 类来实现
        Pattern pattern = Pattern.compile(regx);
        //2.将字符串和正则表达式相关联
        Matcher matcher = pattern.matcher(str);
        //3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
//        System.out.println(matcher.matches());
        String group = "";
        //查找符合规则的子串
        while (matcher.find()) {
            //获取 字符串
            group += matcher.group();
            //获取的字符串的首位置和末位置
//            System.out.println(matcher.start() + "--" + matcher.end());
        }
        return group;
    }

    /**
     * 读取modelName
     *
     * @param remoteDevice
     * @return
     */
    public static synchronized String readModelName(RemoteDevice remoteDevice) {
        try {
            LocalDevice localDevice = MyLocalDevice.getInstance();
            ReadPropertyAck ack = (ReadPropertyAck) localDevice.send(remoteDevice, new ReadPropertyRequest(remoteDevice.getObjectIdentifier(), PropertyIdentifier.modelName));
            return ack.getValue().toString();
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    /**
     * 读取版本号
     *
     * @param remoteDevice
     * @return
     */
    public static synchronized String readVersion(RemoteDevice remoteDevice) {
        try {
            LocalDevice localDevice = MyLocalDevice.getInstance();
            ReadPropertyAck ack = (ReadPropertyAck) localDevice.send(remoteDevice, new ReadPropertyRequest(remoteDevice.getObjectIdentifier(), PropertyIdentifier.firmwareRevision));
            return ack.getValue().toString();
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public synchronized void save(String str, String fileName) {
        File file = new File("log");
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH");
        String dateString = formatter.format(currentTime);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = fmt.format(currentTime);
        try {
            FileOutputStream fos = new FileOutputStream(new File(file, dateString + fileName), true);
            str = date + "    " + str;
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int crcCalcData(byte[] dataValues) {
        int crc=0;
        for (byte tmp : dataValues)
            crc = calcDataCRC(tmp & 0xff, crc);
        return crc;
    }

    private static int calcDataCRC(int dataValue, int crcValue) {
        int crcLow = (crcValue & 0xff) ^ dataValue; /* XOR C7..C0 with D7..D0 */
        /* Exclusive OR the terms in the table (top down) */
        int crc = (crcValue >> 8) ^ (crcLow << 8) ^ (crcLow << 3) ^ (crcLow << 12) ^ (crcLow >> 4) ^ (crcLow & 0x0f)
                ^ ((crcLow & 0x0f) << 7);
        return crc & 0xffff;
    }


}
