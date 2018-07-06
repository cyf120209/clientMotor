package updownstop.view;

import common.Common;
import pojo.CmdTime;
import updownstop.presenter.IUpDownStopPresenter;
import updownstop.presenter.UpDownStopPresenterImpl;
import util.Draper;
import util.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UpDownStop extends JFrame implements ActionListener, IUpDownStop {

    private IUpDownStopPresenter mUpDownStopPresenter;
    private JLabel jlUp = new JLabel("上");
    private JLabel jlUpStop = new JLabel("停");
    private JLabel jlDown = new JLabel("下");
    private JLabel jlDownStop = new JLabel("停");

    private JLabel beizhu = new JLabel("时间(秒)");
    private JLabel countdown = new JLabel("倒计时");

    private JTextField jfUp = new JTextField("10");
    private JTextField jfUpStop = new JTextField("10");
    private JTextField jfDown = new JTextField("10");
    private JTextField jfDownStop = new JTextField("10");

    private JLabel jlUpTime = new JLabel("0");
    private JLabel jlUpStopTime = new JLabel("0");
    private JLabel jlDownTime = new JLabel("0");
    private JLabel jlDownStopTime = new JLabel("0");

    private JButton jbStart = new JButton("开始");
    private JLabel jlN = new JLabel("次数: ");
    private JLabel jlNum = new JLabel("0");

    //    private boolean run;
    private Integer up;
    private Integer upStop;
    private Integer down;
    private Integer downStop;
    private boolean isRunning = true;
    private boolean isLoop;
    private int count = 0;

    /**
     * 开始，暂停的标志
     */
    private boolean startFlag = true;

    /**
     * 各阶段的标志
     */
    private int flag=-1;

    /**
     * 各阶段的等待时间
     */
    private int totalTime;
    private int curTime;

    public UpDownStop() throws HeadlessException {
        setTitle("LimitsAndStops");
        setLayout(null);
        setSize(700, 400);
        jlUp.setBounds(35, 35, 50, 20);
        jlUpStop.setBounds(35, 55, 50, 20);
        jlDown.setBounds(35, 75, 50, 20);
        jlDownStop.setBounds(35, 95, 50, 20);

        beizhu.setBounds(85, 15, 50, 20);
        countdown.setBounds(190, 15, 50, 20);


        jfUp.setBounds(85, 35, 100, 20);
        jfUpStop.setBounds(85, 55, 100, 20);
        jfDown.setBounds(85, 75, 100, 20);
        jfDownStop.setBounds(85, 95, 100, 20);

        jlUpTime.setBounds(190, 35, 50, 20);
        jlUpStopTime.setBounds(190, 55, 50, 20);
        jlDownTime.setBounds(190, 75, 50, 20);
        jlDownStopTime.setBounds(190, 95, 50, 20);

        jbStart.setBounds(250, 95, 100, 20);
        jlN.setBounds(250, 75, 50, 20);
        jlNum.setBounds(290, 75, 100, 20);

        add(jlUp);
        add(jlUpStop);
        add(jlDown);
        add(jlDownStop);

        add(beizhu);
        add(countdown);

        add(jfUp);
        add(jfUpStop);
        add(jfDown);
        add(jfDownStop);

        add(jlUpTime);
        add(jlUpStopTime);
        add(jlDownTime);
        add(jlDownStopTime);

        add(jbStart);
        add(jlN);
        add(jlNum);

        jbStart.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                isRunning = false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                send();
            }
        }).start();

        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curTime+=1;
                showCountdown(flag,curTime);
            }
        }).start();
        mUpDownStopPresenter = new UpDownStopPresenterImpl(this);


    }

    @Override
    public void update(String[] times) {
        jfUp.setText(times[0]);
        jfUpStop.setText(times[1]);
        jfDown.setText(times[2]);
        jfDownStop.setText(times[3]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbStart)) {
            if (jbStart.getText().trim().toString().equals("开始")) {
                if (startFlag) {
                    start();
                    count = 0;
                    jbStart.setText("停止");
                } else {
                    JOptionPane.showMessageDialog(null, "请等待程序完全停止，等待时间 " + totalTime + " 秒", "waring", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                stop();
                startFlag = false;
                jbStart.setText("开始");
            }
        }
    }

    private void stop() {
        isLoop = false;
        mUpDownStopPresenter.stop();
    }

    private void start() {
        String _up = jfUp.getText().trim().toString();
        String _upStop = jfUpStop.getText().trim().toString();
        String _down = jfDown.getText().trim().toString();
        String _downStop = jfDownStop.getText().trim().toString();

        up = Integer.valueOf(("".equals(_up)) ? "0" : _up);
        upStop = Integer.valueOf(("".equals(_upStop)) ? "0" : _upStop);
        down = Integer.valueOf(("".equals(_down)) ? "0" : _down);
        downStop = Integer.valueOf(("".equals(_downStop)) ? "0" : _downStop);

        isLoop = true;
        flag=0;
        mUpDownStopPresenter.stop();
        FileUtils.writeUserDirFile("cmdTime", "cmdTime.txt", new CmdTime(up, upStop, down, downStop).toString());
    }

    private void send() {
        while (isRunning) {
            try {
//            System.out.println("-------isLoop");
                Thread.sleep(100);
                if (isLoop) {
                    count++;
                    jlNum.setText("" + count);
                    Draper.sendCmd(Draper.DRAPER_CMD_RETRACTED);
                    totalTime = up;
                    flag=0;
                    curTime=0;
                    System.out.println("up");
                    Thread.sleep(up * 1000);
                    if (!isLoop) {
                        startFlag = true;
                        flag=-1;
                        continue;
                    }
                    Draper.sendCmd(Draper.DRAPER_CMD_STOP);
                    totalTime = upStop;
                    flag=1;
                    curTime=0;
                    System.out.println("stop");
                    Thread.sleep(upStop * 1000);
                    if (!isLoop) {
                        startFlag = true;
                        flag=-1;
                        continue;
                    }
                    Draper.sendCmd(Draper.DRAPER_CMD_EXTENDED);
                    totalTime = down;
                    flag=2;
                    curTime=0;
                    System.out.println("down");
                    Thread.sleep(down * 1000);
                    if (!isLoop) {
                        startFlag = true;
                        flag=-1;
                        continue;
                    }
                    Draper.sendCmd(Draper.DRAPER_CMD_STOP);
                    totalTime = downStop;
                    flag=3;
                    curTime=0;
                    System.out.println("stop");
                    Thread.sleep(downStop * 1000);
                    if (!isLoop) {
                        startFlag = true;
                        flag=-1;
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showCountdown(int i,int time){
        int cd = totalTime - time;
        switch (i){
            case 0:
                jlUpTime.setText((cd==0)?"0":""+ cd);
                break;
            case 1:
                jlUpStopTime.setText((cd==0)?"0":""+ cd);
                break;
            case 2:
                jlDownTime.setText((cd==0)?"0":""+ cd);
                break;
            case 3:
                jlDownStopTime.setText((cd==0)?"0":""+ cd);
                break;
        }
    }
}
