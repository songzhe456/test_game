package com.game.client.gui;

import javax.swing.*;

public class StartWindow extends JFrame {
    private JTextArea commandArea;
    private static StartWindow startWindow;
    public StartWindow(){
        //初始化窗口
        initJFrame();
        //初始化按钮
        initButton();
        //初始化命令行
        commandArea = initCommandLine();
        this.setLayout(null);
        this.setVisible(true);
        startWindow = this;
    }

    public static StartWindow getStartWindow() {
        return startWindow;
    }

    public JTextArea getCommandArea() {
        return commandArea;
    }

    private void initButton() {
        JButton startOfflineBtn = new JButton("开始战斗");
        startOfflineBtn.setBounds(getWidth() / 3,getHeight() / 2,120,50);
        startOfflineBtn.addActionListener(new StartButtonActionListener());
        //将按钮添加到窗口中
        this.getContentPane().add(startOfflineBtn);
    }

    private JTextArea initCommandLine(){
        JTextArea commandArea = new JTextArea();
        commandArea.setBounds(getWidth() / 2,getHeight() / 2,350,350);
        this.getContentPane().add(commandArea);
        return commandArea;
    }

    private void initJFrame() {
        this.setSize(800,800);
        this.setTitle("test game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗口居中
        this.setLocationRelativeTo(null);
    }
}
