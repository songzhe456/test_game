package com.game.client.gui;

import com.game.Main;
import com.game.server.GameRoll;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonActionListener implements ActionListener {
    private StartWindow startWindow;
    private JTextArea commandArea;
    @Override
    public void actionPerformed(ActionEvent e) {
        new GameRoll().fight();
        if(StartWindow.getStartWindow() != null){
            startWindow = StartWindow.getStartWindow();
            if(startWindow.getCommandArea() != null){
                commandArea = startWindow.getCommandArea();
                commandArea.append(Main.getLogContent().toString());
            }
        }
    }
}
