package main.view;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/18.
 */
public class Main{

    private static BoxLayoutCase mBoxLayoutCase;
    private static List<String> mListPort=new LinkedList<>();

    public static void main(String[] args) throws Exception {
        guiInit();
    }

    private static void guiInit() throws ParseException {
        mBoxLayoutCase = new BoxLayoutCase();
    }
}