package panel;

import main.Game;

import javax.swing.*;
import java.awt.*;

public class PhasePanel extends JPanel {

    public PhasePanel(Game game) {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(100,700));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        add(new JButton("스텐바이"));
        add(new JButton("드로우"));
        add(new JButton("메인1"));
        add(new JButton("배틀"));
        add(new JButton("메인2"));
        add(new JButton("엔드"));

    }
}
