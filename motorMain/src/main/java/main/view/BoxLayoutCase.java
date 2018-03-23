package main.view;

import GroupOperation.view.GroupOperation;
import com.serotonin.bacnet4j.RemoteDevice;
import common.Common;
import limitsAndStops.view.LimitsAndStops;
import main.presenter.BoxLayoutCasePresenter;
import main.presenter.BoxLayoutCasePresenterImpl;
import show.ShowAllDevice;
import update.view.Update;
import util.ComPortutils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by lenovo on 2017/1/18.
 */
public class BoxLayoutCase extends JFrame implements ActionListener,BoxLayoutView{

    private BoxLayoutCasePresenter mBoxLayoutCasePresenter;
    private int WIDTH=Common.SCREEN_WEIGHT;
    private int HEIGHT=Common.SCREEN_HEIGHT;

    public JButton startstopbt=new JButton("start");
    public JButton groupOperation=new JButton("groupOperation");
    public JButton update=new JButton("update");
    public JButton showAllDevice=new JButton("showAllDevice");

    public JComboBox comBox=new JComboBox();
    public JButton upComBox=new JButton("update port");

    public JButton upBt=new JButton("up");
    public JButton downbt=new JButton("down");
    public JButton stopButton=new JButton("stop");

    public JComboBox devBox=new JComboBox();
    public JLabel versionLabel= new JLabel("Versin: No Version");
    public JLabel draperID= new JLabel("draperID: ");
    public JLabel address= new JLabel("address: ");

    public JTextField cmdTextEdit=new JTextField(10);
    public JButton cmdTestbt=new JButton("SendCmdBorcast");
    public JButton cmdTestOneBT=new JButton("SendCmd");

    public JButton limitsSetting=new JButton("LimitsSetting");

    public BoxLayoutCase() throws HeadlessException {
        mBoxLayoutCasePresenter = new BoxLayoutCasePresenterImpl(this);
        setTitle("Draper");
        setSize(WIDTH,HEIGHT);

        setLocationRelativeTo(null);
        setLayout(null);
        //总在最前面
//        setAlwaysOnTop(true);
        //不能改变大小
        setResizable(false);
        //最大化
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //不要边框 需放置在组件添加之前，否则不生效
//        setUndecorated(true);

        URL url = BoxLayoutCase.class.getResource("/img/bg.jpg");
        ImageIcon imageIcon = new ImageIcon(url);
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel(imageIcon);
        // 把标签的大小位置设置为图片刚好填充整个面板
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        startstopbt.setBounds(new Rectangle(10, 10, 100, Common.HEIGHT));
        groupOperation.setBounds(new Rectangle(100,10,150,Common.HEIGHT));
        update.setBounds(new Rectangle(250,10,100,Common.HEIGHT));
        showAllDevice.setBounds(new Rectangle(350,10,120,Common.HEIGHT));
        comBox.setBounds(10,35,150, Common.HEIGHT);
        upComBox.setBounds(170,35,100,Common.HEIGHT);

        upBt.setBounds(new Rectangle(210, 130, 100, Common.HEIGHT));
        downbt.setBounds(new Rectangle(210, 170, 100, Common.HEIGHT));
        stopButton.setBounds(new Rectangle(210, 150, 100, Common.HEIGHT));

        devBox.setBounds(10,100,450,Common.HEIGHT);

        versionLabel.setBounds(10,130,200,Common.HEIGHT);
        draperID.setBounds(10,150,200,Common.HEIGHT);
        address.setBounds(10,170,200,Common.HEIGHT);

        cmdTestbt.setBounds(100,200,150,Common.HEIGHT);
        cmdTestOneBT.setBounds(250,200,100,Common.HEIGHT);
        cmdTextEdit.setBounds(10,200,100,Common.HEIGHT);
        limitsSetting.setBounds(200,250,150,Common.HEIGHT);

//        Container con=getContentPane();
//        Box horBox1 = Box.createHorizontalBox();
//        horBox1.add(startstopbt);
//        horBox1.add(groupOperation);
//        horBox1.add(update);
//        horBox1.setBounds(100,10,270,20);
//        con.add(horBox1);

        add(cmdTextEdit);

        add(startstopbt);
        add(groupOperation);
        add(update);
        add(showAllDevice);

        add(upBt);
        add(downbt);
        add(stopButton);
        add(comBox);
        add(upComBox);

        add(devBox);
        add(versionLabel);
        add(draperID);
        add(address);

        add(cmdTestbt);
        add(cmdTextEdit);
        add(cmdTestOneBT);

        add(limitsSetting);

        setLocationRelativeTo(this);
        setVisible(true);
        startstopbt.addActionListener(this);
        upComBox.addActionListener(this);
        showAllDevice.addActionListener(this);

        upBt.addActionListener(this);
        stopButton.addActionListener(this);
        downbt.addActionListener(this);
        cmdTestbt.addActionListener(this);
        cmdTestOneBT.addActionListener(this);

        limitsSetting.addActionListener(this);
        groupOperation.addActionListener(this);
        update.addActionListener(this);

        addWindowListener(new MyWindowEventHandle());
        listPort();
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(startstopbt.equals(e.getSource())){
            mBoxLayoutCasePresenter.startstopbt();
        }else if(upComBox.equals(e.getSource())){
            listPort();
        }else if(upBt.equals(e.getSource())){
            mBoxLayoutCasePresenter.upBt();
        }else if(stopButton.equals(e.getSource())){
            mBoxLayoutCasePresenter.stopButton();
        }else if(downbt.equals(e.getSource())){
            mBoxLayoutCasePresenter.downbt();
        }else if(cmdTestbt.equals(e.getSource())){
            mBoxLayoutCasePresenter.cmdTestbt();
        }else if(cmdTestOneBT.equals(e.getSource())){
            mBoxLayoutCasePresenter.cmdTestOneBT();
        }else if(limitsSetting.equals(e.getSource())){
//            mBoxLayoutCasePresenter.limitsSetting();
            LimitsAndStops limitsAndStops = new LimitsAndStops();
            limitsAndStops.setVisible(true);
        }else if(groupOperation.equals(e.getSource())){
            GroupOperation groupOperation = new GroupOperation();
            groupOperation.setLocationRelativeTo(null);
            groupOperation.setVisible(true);
        }else if(update.equals(e.getSource())){
            Update update1 = new Update();
            update1.setLocationRelativeTo(null);
            update1.setVisible(true);
        }else if(showAllDevice.equals(e.getSource())){
            ShowAllDevice showAllDevice = new ShowAllDevice();
            showAllDevice.setLocationRelativeTo(null);
            showAllDevice.setVisible(true);
        }
    }

    @Override
    public void AddItem(RemoteDevice d) {
        devBox.addItem(d);
    }

    @Override
    public void updatedevBox(String text, boolean isRemove) {
        startstopbt.setText(text);
        if(isRemove){
            devBox.removeAllItems();
        }
    }

    /**
     * 更新COM端口
     */
    public void listPort(){
        comBox.removeAllItems();
        LinkedList<String> listPort= ComPortutils.listPort();
        for (String port :listPort) {
            comBox.addItem(port);
        }
        if(mBoxLayoutCasePresenter.getRunningState()){
            mBoxLayoutCasePresenter.startstopbt();
        }
    }

    @Override
    public void updateVersionLabel(String text) {
        versionLabel.setText(text);
    }

    @Override
    public String getCmdTextEdit() {
        return cmdTextEdit.getText().trim();
    }

    @Override
    public String getComBoxSelectedItem() {
        return (String)comBox.getSelectedItem();
    }

    @Override
    public RemoteDevice getdevBoxSelectedItem() {
        return (RemoteDevice)devBox.getSelectedItem();
    }
}
