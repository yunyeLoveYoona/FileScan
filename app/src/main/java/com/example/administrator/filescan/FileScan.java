package com.example.administrator.filescan;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 15-7-20.
 */
public class FileScan extends Thread{
    private String filesStr;
    private Handler handler;
    public static final int NOW_SCAN_FOLDER = 2001;
    public static final int FIND_FILE = 2002;
    public static final int NOT_FOUNT_SDCARD = 2003;
    public FileScan(String name,Handler handler){
        filesStr = name;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();
            searchFile(sdDir.getPath());
        }else{
            Message msg = new Message();
            msg.what =NOT_FOUNT_SDCARD;
            handler.sendMessage(msg);
        }


    }

    private void searchFile(String filePath){
        File file = new File(filePath);
        Message msg = new Message();
        msg.obj = file;
        msg.what =NOW_SCAN_FOLDER;
        handler.sendMessage(msg);
        List<File> folderList = new ArrayList<File>();
        if(file.isDirectory()){
            if(file.listFiles()!=null) {
                for (File childFile : file.listFiles()) {
                    if (childFile.isDirectory()) {
                        folderList.add(childFile);
                    } else {
                        checkChild(childFile);
                    }
                }
            }
        }else{
            checkChild(file);
        }
        for(File folder : folderList){
            searchFile(folder.getPath());
        }
    }
    private void checkChild(File childFile){
        if(childFile.getName().contains(filesStr)){
            Message msg = new Message();
            msg.obj = childFile;
            msg.what =FIND_FILE;
            handler.sendMessage(msg);
        }
    }

}
