package com.just.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Bean> mList;
    private MyRecyclerAdapter recyclerAdapter;
    private TextView showTxt;
    private static Handler mHandler = new Handler();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private int tomato_num = 3;
    private int cuamber_num = 5;
    private int vegetable_num = 7;
    private int eggplant_num = 2;
    private int banana_num = 2;
    private int egg_num = 20;

    private Thread mReceiveTempData;

    private Thread mReceivePicData;

    private Context mContext;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);


        mContext = this;


        //厨房环境检测
        showTxt = (TextView) findViewById(R.id.env_txt);

        mList = new ArrayList<>();


        Bean bean0 = new Bean();
        bean0.setName("番茄");
        bean0.setEvaluate("新鲜度适中");
        bean0.setSrc(R.drawable.tomato);
        bean0.setNum(tomato_num + "个");
        bean0.setQuality("预计还剩7天变质");
        mList.add(bean0);

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


        mReceiveTempData = new MyReceiveDataThread();
        mReceiveTempData.start();

        mReceivePicData = new MyPicReceiveThread();
        mReceivePicData.start();


    }

    protected Handler handlerTemp = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String status = (String) msg.obj;
            showTxt.setText("当前温度：" + status.substring(0, 5) + "℃      " + "当前湿度：" + status.substring(12, 17) + "%");
        }
    };


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    tomato_num++;
                    Bean bean0 = new Bean();
                    bean0.setName("番茄");
                    bean0.setEvaluate("新鲜度适中");
                    bean0.setSrc(R.drawable.tomato);
                    bean0.setNum(tomato_num + "个");
                    bean0.setQuality("预计还剩7天变质");
                    mList.set(0, bean0);


                    recyclerAdapter.notifyDataSetChanged();

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加番茄成功！")
                            .setContentText("让我们看看它能做什么！")
                            .show();
                    break;
                case 2:
                    cuamber_num++;
                    Bean bean2 = new Bean();
                    bean2.setName("黄瓜");
                    bean2.setEvaluate("新鲜度较差");
                    bean2.setSrc(R.drawable.cucamber);
                    bean2.setNum(cuamber_num + "个");
                    bean2.setQuality("预计还剩6天变质");
                    mList.set(1, bean2);
                    recyclerAdapter.notifyDataSetChanged();

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加黄瓜成功！")
                            .setContentText("让我们看看它能做什么！")
                            .show();

                    break;
                case 3:
                    vegetable_num++;
                    Log.d("Jay", String.valueOf(vegetable_num));

                    Bean bean3 = new Bean();
                    bean3.setName("青菜");
                    bean3.setEvaluate("新鲜度良好");
                    bean3.setSrc(R.drawable.vegetable);
                    bean3.setNum(vegetable_num + "个");
                    bean3.setQuality("预计还剩17天变质");
                    mList.set(2, bean3);
                    recyclerAdapter.notifyDataSetChanged();


                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加青菜成功！")
                            .setContentText("让我们看看它能做什么！")
                            .show();
                    break;
                case 4:
                    eggplant_num++;
                    Bean bean4 = new Bean();
                    bean4.setName("茄子");
                    bean4.setEvaluate("新鲜度很差");
                    bean4.setSrc(R.drawable.eggplant);
                    bean4.setNum(eggplant_num + "个");
                    bean4.setQuality("预计还剩1天变质");
                    mList.set(3, bean4);
                    recyclerAdapter.notifyDataSetChanged();

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加茄子成功！")
                            .setContentText("让我们看看它能做什么！")
                            .show();
                    break;
                case 5:
                    banana_num++;
                    Bean bean5 = new Bean();
                    bean5.setName("香蕉");
                    bean5.setEvaluate("新鲜度很差");
                    bean5.setSrc(R.drawable.banana);
                    bean5.setNum(banana_num + "个");
                    bean5.setQuality("预计还剩1天变质");
                    mList.set(4, bean5);
                    recyclerAdapter.notifyDataSetChanged();

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加香蕉成功！")
                            .setContentText("让我们看看它能做什么！")
                            .show();
                    break;
                case 6:
                    egg_num++;
                    Bean bean6 = new Bean();
                    bean6.setName("鸡蛋");
                    bean6.setEvaluate("新鲜度良好");
                    bean6.setSrc(R.drawable.egg);
                    bean6.setNum(egg_num + "个");
                    bean6.setQuality("预计还剩14天变质");
                    mList.set(5, bean6);
                    recyclerAdapter.notifyDataSetChanged();

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加鸡蛋成功！")
                            .setContentText("让我们看看它能做什么！")
                            .show();
                    break;



            }
            super.handleMessage(msg);
        }
    };

    /**
     * 获取温湿度信息线程
     */
    class MyReceiveDataThread extends Thread {
        private String hostName = "192.168.1.102";
        private String hostName2 = "192.168.1.193";
        private int portNumber = 7171;

        public void run() {


            while (true) {
                try {
                    Socket clientSocket = new Socket(hostName2, portNumber);
                    Log.d("Jay", "连接成功");


                    byte[] buffer = new byte[1024];
                    StringBuffer stringBuffer = new StringBuffer();
                    int len = -1;

                    InputStream inputStream = clientSocket.getInputStream();
                    String inputLine = null;

                    while ((len = inputStream.read(buffer)) != -1) {
                        inputLine = new String(buffer);

                        Log.d("Jay", inputLine);
                        Message message = handlerTemp.obtainMessage();

                        message.obj = inputLine;
                        handlerTemp.sendMessage(message);

                    }

                    clientSocket.close();

                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    /**
     * 获取图像处理信息
     */
    class MyPicReceiveThread extends Thread {

        public void run() {

//            while (true) {


//            //开始
//            ServerSocket serverSocket = null;
//            try {
//
//
//                if (serverSocket == null) {
//                    serverSocket = new ServerSocket(8866);
//
//                } else {
//                    serverSocket.setReuseAddress(true);
//                    serverSocket.bind(new InetSocketAddress(8866));
//                }
//
//                Socket socket = null;
//
//                BufferedReader in = null;
//
//                while (true) {
//
//
//                    try {
//                        socket = serverSocket.accept();
//
//                        Log.d("Jay", "连接成功");
//                        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//                        String inputLine = in.readLine();
//
//
//                        switch (inputLine) {
//                            case "vegetable":
//                                Log.d("Jay", "vegetable");
//
//                                handler.sendEmptyMessage(3);
//                                break;
//                            case "tomato":
//                                handler.sendEmptyMessage(0);
//
//                                Log.d("Jay", "tomato");
//                                break;
//                            case "cucumber":
//                                handler.sendEmptyMessage(2);
//
//                                Log.d("Jay", "cucumber");
//                                break;
//                            case "eggplant":
//                                Log.d("Jay", "eggplant");
//                                handler.sendEmptyMessage(4);
//
//                                break;
//                            case "banana":
//                                Log.d("Jay", "banana");
//                                handler.sendEmptyMessage(5);
//
//                                break;
//                            case "egg":
//                                Log.d("Jay", "egg");
//                                handler.sendEmptyMessage(6);
//                                break;
//                        }
//                    } catch (Exception e) {
//                        socket.close();
//
//                        in.close();
//                        continue;
//                    }
//                    socket.close();
//
//                    in.close();
//
//
//                }
//
//
////                Log.d("Jay", "连接已关闭");
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//
//            } finally {
//
//                try {
//                    serverSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
////            }

            while (true) {
                try {
                    ServerSocket serverSocket = null;

                    if (serverSocket == null) {
                        serverSocket = new ServerSocket();
                        serverSocket.setReuseAddress(true);
                        serverSocket.bind(new InetSocketAddress(8866));
                    }

                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    String inputLine = in.readLine();

                    switch (inputLine) {
                        case "vegetable":
                            Log.d("Jay", "vegetable");



                            handler.sendEmptyMessage(3);
                            break;
                        case "tomato":
                            handler.sendEmptyMessage(0);

                            Log.d("Jay", "tomato");
                            break;
                        case "cucumber":
                            handler.sendEmptyMessage(2);

                            Log.d("Jay", "cucumber");
                            break;
                        case "eggplant":
                            Log.d("Jay", "eggplant");
                            handler.sendEmptyMessage(4);

                            break;
                        case "banana":
                            Log.d("Jay", "banana");
                            handler.sendEmptyMessage(5);

                            break;
                        case "egg":
                            Log.d("Jay", "egg");
                            handler.sendEmptyMessage(6);
                            break;
                    }

                    in.close();
                    socket.close();
                    serverSocket.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();        //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}

