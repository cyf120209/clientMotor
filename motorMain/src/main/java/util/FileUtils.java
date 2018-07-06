package util;

import java.io.*;

public class FileUtils {

    public static String readFile(String filePath){
        StringBuilder builder=new StringBuilder(2048);
        BufferedReader bufferedReader=null;
        FileReader fileReader=null;
        try {
            fileReader=new FileReader(filePath);
            bufferedReader=new BufferedReader(fileReader);
            char[] c=new char[2048];
            int length=0;
            while ((length=bufferedReader.read(c))!=-1){
                builder.append(c,0,length);
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    public static String readUserDirFile(String dir,String fileName){
        String userHome = System.getProperty("user.home");
        String fileDir = userHome + File.separator + dir;
        File fd = new File(fileDir);
        if(!fd.exists()){
            return "";
        }
        String filePath= fileDir + File.separator + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            return "";
        }
        return readBufferedFile(filePath);
    }

    public static String readBufferedFile(String filePath){
        StringBuilder builder=new StringBuilder(1024);
        FileReader fr=null;
        BufferedReader br=null;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            String str="";
            while((str = br.readLine()) != null){
                builder.append(str);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fr!=null){
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return builder.toString().trim();
        }
    }

    public static void writeUserDirFile(String dir,String filename,String json){
        String userHome = System.getProperty("user.home");
        String fileDir=userHome+File.separator+dir;
        File file = new File(fileDir);
        if(!file.exists()){
            file.mkdirs();
        }
        String filePath=fileDir+File.separator+filename;
        writeFile(filePath,json);
    }

    public static void writeFile(String filePath,String json){
        FileWriter fw=null;
        BufferedWriter bufferedWriter=null;
        try {
            fw = new FileWriter(filePath);
            bufferedWriter = new BufferedWriter(fw);
            bufferedWriter.write(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fw!=null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(String filePath,StringBuilder builder){
        try {
            File file=new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(builder.toString().getBytes());
            byte[] b=new byte[2048];
            int len=0;
            while ((len=byteArrayInputStream.read(b))!=-1){
                fos.write(b,0,len);
            }
            builder.toString().getBytes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
