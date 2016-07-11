package com.just.firstapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Bean> mList;
    private MyRecyclerAdapter recyclerAdapter;
    private TextView showTxt;
    private Handler mHandler = new Handler();

    private int tomato_num = 3;
    private int cuamber_num = 5;
    private int vegetable_num = 7;
    private int eggplant_num = 2;
    private int banana_num = 2;
    private int egg_num = 20;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);

        //厨房环境检测
        showTxt = (TextView) findViewById(R.id.env_txt);

        mList = new ArrayList<>();

        Bean bean = new Bean();
        bean.setName("番茄");
        bean.setEvaluate("新鲜度适中");
        bean.setSrc(R.drawable.tomato);
        bean.setNum(tomato_num + "个");
        bean.setQuality("预计还剩7天变质");
        mList.add(bean);

        Bean bean2 = new Bean();
        bean2.setName("黄瓜");
        bean2.setEvaluate("新鲜度较差");
        bean2.setSrc(R.drawable.cucamber);
        bean2.setNum(cuamber_num + "个");
        bean2.setQuality("预计还剩6天变质");
        mList.add(bean2);

        Bean bean3 = new Bean();
        bean3.setName("青菜");
        bean3.setEvaluate("新鲜度良好");
        bean3.setSrc(R.drawable.vegetable);
        bean3.setNum(vegetable_num + "个");
        bean3.setQuality("预计还剩17天变质");
        mList.add(bean3);

        Bean bean4 = new Bean();
        bean4.setName("茄子");
        bean4.setEvaluate("新鲜度很差");
        bean4.setSrc(R.drawable.eggplant);
        bean4.setNum(eggplant_num + "个");
        bean4.setQuality("预计还剩1天变质");
        mList.add(bean4);

        Bean bean5 = new Bean();
        bean5.setName("香蕉");
        bean5.setEvaluate("新鲜度很差");
        bean5.setSrc(R.drawable.banana);
        bean5.setNum(banana_num + "个");
        bean5.setQuality("预计还剩1天变质");
        mList.add(bean5);

        Bean bean6 = new Bean();
        bean6.setName("鸡蛋");
        bean6.setEvaluate("新鲜度良好");
        bean6.setSrc(R.drawable.egg);
        bean6.setNum(egg_num + "个");
        bean6.setQuality("预计还剩14天变质");
        mList.add(bean6);


//        recyclerAdapter.notifyDataSetChanged();

        recyclerAdapter = new MyRecyclerAdapter(MainActivity.this, mList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new ItemDivider();
        recyclerView.addItemDecoration(itemDecoration);



        Thread mReceiveTempData = new MyReceiveDataThread();
        mReceiveTempData.start();

        Thread mReceivePicData = new MyPicReceiveThread();
        mReceivePicData.start();


    }

    /**
     * 获取温湿度信息线程
     */
    class MyReceiveDataThread extends Thread {
        private String hostName = "192.168.1.102";
        private String hostName2 = "192.168.1.193";
        private int portNumber = 7171;

        public void run() {


//            while (true) {
            try {
                Socket clientSocket = new Socket(hostName2, portNumber);
                Log.d("Jay", "连接成功");


                byte[] buffer = new byte[1024];
                StringBuffer stringBuffer = new StringBuffer();
                int len = -1;
//                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                InputStream inputStream = clientSocket.getInputStream();
                String inputLine = null;

                while ((len = inputStream.read(buffer)) != -1) {
                    inputLine = new String(buffer);

                    Log.d("Jay", inputLine);
                }
//                    in.read(buffer);
//                    String s = buffer.toString();

//                final String s = in.readLine();
//                Log.d("Jay", s);
//                    InputStream in = clientSocket.getInputStream();


//                    while ((inputLine = in.readLine()) != null) {
//                        Log.d("Jay", inputLine);
//
//
//                    }
//                    inputBytes = inputString.getBytes();
//                    final byte inputByte = Byte.parseByte(in.readLine();
//                    while ((inputLine = in.readLine()) != null) {
//                        Log.d("已接受数据  ", inputLine);
////                                showTxt.setText("                                        " + inputLine);
//                    }

//                    Log.d("Jay", inputLine);
                final String finalInputLine = inputLine;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showTxt.setText("　　　　　　　　　　　当前温度" + finalInputLine);
//                            showTxt.setText("　　　　　　　　　　　当前温度" + inputBytes.toString().substring(0, 5) + "　　　　　　　　　　　　　　　　　　　　当前湿度　");

                    }
                });

//                in.close();
                clientSocket.close();

                Thread.sleep(3000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            }


        }
    }

    /**
     * 获取图像处理信息
     */
    class MyPicReceiveThread extends Thread {

        public void run() {

            while (true) {
                try {

                    ServerSocket serverSocket = new ServerSocket(8866);
                    Socket socket = serverSocket.accept();
                    Log.d("Jay", "连接成功");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    String inputLine = null;
                    Log.d("Jay", "inputLine is :" + inputLine);

                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.equals("vegetable")) {
                            Log.d("Jay", "vegetable");
                        } else {
                            Log.d("Jay", "这是0");
                        }
                    }
                    in.close();
                    socket.close();
                    serverSocket.close();
                    Log.d("Jay", "连接已关闭");

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

    }

}

