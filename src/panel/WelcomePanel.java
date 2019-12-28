package panel;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {

    public WelcomePanel(Game game) {
        setSize(new Dimension(1280, 720));
        setLayout(new BorderLayout());
        JButton goToCreatePage = new JButton("Create Character");
        add(goToCreatePage, BorderLayout.SOUTH);

        ImageIcon background = new ImageIcon("data/images/start.png");

        background.setImage(background.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));

        JLabel bg = new JLabel(background);
        add(bg, BorderLayout.CENTER);

        goToCreatePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showPlayerCreationPanel();
            }
        });

    }
}
