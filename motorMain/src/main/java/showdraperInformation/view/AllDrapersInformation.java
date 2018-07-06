package showdraperInformation.view;

import com.serotonin.bacnet4j.type.primitive.SignedInteger;
import model.DraperInformation;
import rx.functions.Action1;
import showdraperInformation.presenter.AllDrapersInformationPresenterImpl;
import showdraperInformation.presenter.IAllDrapersInformationPresenter;
import util.RxBus;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AllDrapersInformation extends JFrame implements IAllDrapersInformation, ActionListener {

    private JLabel lbId=new JLabel("id");
    private JLabel lbUpLimit=new JLabel("上限位");
    private JLabel lbLowerLimit=new JLabel("下限位");
    private JLabel lbCurPosition=new JLabel("当前位置");
    public JList showInformations = new JList();
    JScrollPane showInformationJSP = new JScrollPane(showInformations);

    private JButton btnAuto = new JButton("获取限位信息");
    private JButton btnUp = new JButton("上");
    private JButton btnStop = new JButton("停");
    private JButton btnDown = new JButton("下");

    private IAllDrapersInformationPresenter mAllDrapersInformationPresenter;

    private List<Integer> idList = new ArrayList<>();
    private int selectedIndex = -1;

    public AllDrapersInformation() throws HeadlessException {
        setTitle("LimitsAndStops");
        setLayout(null);
        mAllDrapersInformationPresenter = new AllDrapersInformationPresenterImpl(this);
        setSize(700, 600);

        lbId.setBounds(15, 10, 50, 20);
        lbUpLimit.setBounds(65, 10, 50, 20);
        lbLowerLimit.setBounds(120, 10, 50, 20);
        lbCurPosition.setBounds(170, 10, 60, 20);



        showInformationJSP.setBounds(10, 30, 400, 500);
        btnAuto.setBounds(450, 10, 150, 20);
        btnUp.setBounds(450, 40, 150, 20);
        btnStop.setBounds(450, 60, 150, 20);
        btnDown.setBounds(450, 80, 150, 20);

        add(lbId);
        add(lbUpLimit);
        add(lbLowerLimit);
        add(lbCurPosition);
        add(showInformationJSP);
        add(btnAuto);
        add(btnUp);
        add(btnStop);
        add(btnDown);

        btnAuto.addActionListener(this);
        btnUp.addActionListener(this);
        btnStop.addActionListener(this);
        btnDown.addActionListener(this);


        RxBus.getDefault().toObservable(DraperInformation.class)
                .subscribe(new Action1<DraperInformation>() {
                    @Override
                    public void call(DraperInformation draperInformation) {
                        updateDraperInfomation(draperInformation);
                    }
                });

        showInformations.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean valueIsAdjusting = e.getValueIsAdjusting();
                if (!valueIsAdjusting) {
                    return;
                }
                JList deviceList = (JList) e.getSource();
                selectedIndex = deviceList.getSelectedIndex();
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAuto)) {
            auto();
        } else if (e.getSource().equals(btnUp)) {
            if (selectedIndex != -1) {
                mAllDrapersInformationPresenter.retracted(idList.get(selectedIndex));
            }
        } else if (e.getSource().equals(btnStop)) {
            if (selectedIndex != -1) {
                mAllDrapersInformationPresenter.stop(idList.get(selectedIndex));
            }
        } else if (e.getSource().equals(btnDown)) {
            if (selectedIndex != -1) {
                mAllDrapersInformationPresenter.extended(idList.get(selectedIndex));
            }
        }
    }

    private void auto() {
        mAllDrapersInformationPresenter.autoGetAllInformation();
    }

    public void updateDraperInfomation(DraperInformation draperInformation) {
        String str = "";
//        directionLabel.setText(""+draperInformation.getReverse());
        SignedInteger id = draperInformation.getId();
        SignedInteger upperLimit = draperInformation.getUpperLimit();
        SignedInteger lowerLimit = draperInformation.getLowerLimit();
        SignedInteger curPosition = draperInformation.getCurPosition();
        str = String.format("%7s", String.valueOf(id.intValue())) + "   " +
                String.format("%7s", String.valueOf(upperLimit.intValue())) + "   " +
                String.format("%10s", String.valueOf(lowerLimit.intValue())) + "   " +
                String.format("%15s", String.valueOf(curPosition.intValue()));
        if (curPosition.intValue() < upperLimit.intValue() || curPosition.intValue() > lowerLimit.intValue()) {
            str += "  异常";
        }
        idList.add(new Integer(id.intValue()));
        show(str);
        mAllDrapersInformationPresenter.setWait(false);
        System.out.println(str);

    }

    DefaultListModel<String> info = new DefaultListModel<>();

    private void show(final String str) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    info.addElement(str);
                    showInformations.setModel(info);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearAll() {
        info.clear();
        showInformations.setModel(info);
    }
}
