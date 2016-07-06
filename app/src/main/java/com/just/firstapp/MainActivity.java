package com.just.firstapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Bean> mList;
    private MyRecyclerAdapter recyclerAdapter;
    private TextView showTxt;
    private Handler mHandler = new Handler();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);

        //厨房环境检测
        showTxt = (TextView) findViewById(R.id.env_txt);

        mList = new ArrayList<>();

        Bean bean = new Bean();
        bean.setName("西瓜");
        bean.setEvaluate("新鲜度适中");
        bean.setSrc(R.drawable.watermelon);
        bean.setNum("3个");
        bean.setQuality("预计还剩7天变质");
        mList.add(bean);

        Bean bean2 = new Bean();
        bean2.setName("苹果");
        bean2.setEvaluate("新鲜度较差");
        bean2.setSrc(R.drawable.apple);
        bean2.setNum("6个");
        bean2.setQuality("预计还剩6天变质");
        mList.add(bean2);

        Bean bean3 = new Bean();
        bean3.setName("草莓");
        bean3.setEvaluate("新鲜度良好");
        bean3.setSrc(R.drawable.strawberry);
        bean3.setNum("23个");
        bean3.setQuality("预计还剩17天变质");
        mList.add(bean3);

        Bean bean4 = new Bean();
        bean4.setName("茄子");
        bean4.setEvaluate("新鲜度很差");
        bean4.setSrc(R.drawable.eggplant);
        bean4.setNum("5个");
        bean4.setQuality("预计还剩1天变质");
        mList.add(bean4);

        Bean bean5 = new Bean();
        bean5.setName("玉米");
        bean5.setEvaluate("新鲜度很差");
        bean5.setSrc(R.drawable.corn);
        bean5.setNum("7个");
        bean5.setQuality("预计还剩1天变质");
        mList.add(bean5);

        Bean bean6 = new Bean();
        bean6.setName("洋葱");
        bean6.setEvaluate("新鲜度良好");
        bean6.setSrc(R.drawable.onion);
        bean6.setNum("3个");
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

        Thread mReceiveData = new MyReceiveDataThread();
        mReceiveData.start();


    }

    class MyReceiveDataThread extends Thread {
        private String hostName = "192.168.1.101";
        private int portNumber = 8899;

        public void run() {


            while (true) {
                try {
                    Socket clientSocket = new Socket(hostName, portNumber);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    final String inputLine = in.readLine();
//                    while ((inputLine = in.readLine()) != null) {
//                        Log.d("已接受数据  ", inputLine);
////                                showTxt.setText("                                        " + inputLine);
//                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showTxt.setText("　　　　　　　　　　　当前温度"+inputLine+"　　　　　　　　　　　　　　　　　　　　当前湿度　");
                        }
                    });

                    in.close();
                    clientSocket.close();

                    Thread.sleep(3000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

}
