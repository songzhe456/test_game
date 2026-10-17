package com.game.client.gui;

import com.game.server.GameRoll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new GameRoll(null,"").fight();
    }
}
