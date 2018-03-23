package test;

import util.Public;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2017/7/14.
 */
public class StringStringBuffer {

    public static void main(String[] args) {

        String reg="WS-01/02";
        if(Public.matchString(reg, "^WS-")){
            String substring = reg.substring(3);
            String[] split = substring.split("/");
            boolean match=false;
            for (int i=0;i<split.length;i++){
                if(Public.matchString("WS-01", "WS-"+split[i])){
                    match=true;
                    break;
                }
            }
            if(match){
            }
        }
//        String regx = "WS-03/02";
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile("WS");
//        // 忽略大小写的写法
//        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(regx);
//        // 查找字符串中是否有匹配正则表达式的字符/字符串
//        boolean rs = matcher.find();
//
//        Boolean aBoolean = Public.matchString(regx, "^WS-");
//
////        String s = Public.matchStr("regx", "WS-");
//        String substring = regx.substring(3);
//        String[] split = substring.split("/");
//        boolean match=false;
//        for (int i=0;i<split.length;i++){
//            if(Public.matchString("WS-01", "WS-"+split[i])){
//                match=true;
//                break;
//            }
//        }

//        System.out.println("aBoolean1:"+match);

/*
        Boolean aBoolean = Public.matchString("dslef(A)", "(B)");
        String s1="hello";
        String s2="world";
        String s3=new String("hello");
        System.out.println(s1==s3);
        System.out.println(s1+"---"+s2);
        change(s1,s2);
        System.out.println(s1+"---"+s2);
        StringBuffer sb1=new StringBuffer("hello");
        StringBuffer sb2=new StringBuffer("world");
        System.out.println(sb1+"---"+sb2);
        change(sb1,sb2);
        System.out.println(sb1+"---"+sb2);
        String a = new String("hello");
        String b = new String("hello");
//        String a = "hello";
//        String b = "hello";
        System.out.println(a==b);
        System.out.println(a.equals(b));
        HashMap<Integer,String> map=new HashMap<>(1024);
        map.put(1,"1");

        String s = map.get(1);*/
    }

    private static void change(StringBuffer sb1, StringBuffer sb2) {
        sb1=sb2;
        sb2.append(sb1);

    }

    private static void change(String s1, String s2) {
        s1=s2;
        s2=s1+s2;
    }
}
