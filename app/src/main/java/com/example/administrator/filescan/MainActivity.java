package com.example.administrator.filescan;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView fileName,searchPath,file;
    private FileScan fileScan;
    private Handler handler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FileScan.FIND_FILE:
                    file.setText(file.getText()+"\n\r"+msg.obj);
                    break;
                case FileScan.NOW_SCAN_FOLDER:
                    searchPath.setText("当前查找文件夹："+msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileName= (TextView) findViewById(R.id.file_name);
        searchPath= (TextView) findViewById(R.id.search_path);
        file= (TextView) findViewById(R.id.file);
        String str = ".jpg";
        fileScan = new FileScan(str,handler);
        fileName.setText("当前查找文件名："+str);
        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileScan.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
