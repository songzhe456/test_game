package com.game.client.gui;

import javax.swing.*;

public class StartWindow extends JFrame {
    public StartWindow(){
        //初始化窗口
        initJFrame();
        //初始化按钮
        initButton();
        //初始化命令行
        initCommandLine();
        this.setLayout(null);
        this.setVisible(true);
    }

    private void initButton() {
        JButton startOfflineBtn = new JButton("开始战斗");
        startOfflineBtn.setBounds(0,0,120,50);
        startOfflineBtn.addActionListener(new StartButtonActionListener());
        //将按钮添加到窗口中
        this.getContentPane().add(startOfflineBtn);
    }

    private void initCommandLine(){
        JTextArea commandArea = new JTextArea();
        commandArea.setBounds(getWidth() / 2,getHeight() / 2,250,250);
        commandArea.append("test");
        this.getContentPane().add(commandArea);
    }

    private void initJFrame() {
        this.setSize(800,800);
        this.setTitle("test game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗口居中
        this.setLocationRelativeTo(null);
    }
}
