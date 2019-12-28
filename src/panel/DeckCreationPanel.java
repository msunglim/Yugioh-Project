package panel;

import main.Game;
import characters.Character;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

//이제부터는 player 에 access가 가능해집니다.
public class DeckCreationPanel extends JPanel {
    public DeckCreationPanel(Game game, int i) {
        setSize(new Dimension(1280, 720));
        setLayout(new BorderLayout());
        JButton play = new JButton("play game");
        add(play, BorderLayout.SOUTH);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showDuelField();
            }
        });

        Character character = game.getCharacter();

        JPanel top = new JPanel();

        JLabel msg = new JLabel("덱을 만들어주세요");
        top.add(msg);
        add(top, BorderLayout.NORTH);



        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));

        west.setPreferredSize(new Dimension(350, 450));

        JLabel profile = new JLabel(character.getName(),
                character.getIcon(), JLabel.CENTER);
        profile.setAlignmentX(Component.CENTER_ALIGNMENT);

        profile.setVerticalTextPosition(JLabel.BOTTOM);

        profile.setHorizontalTextPosition(JLabel.CENTER);
        west.add(profile);

        JLabel l = new JLabel("덱의 수: "+ game.getPlayer().getDeckSize());

        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(l);
        JLabel l2 = new JLabel("한마디");

        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(l2);

        add(west, BorderLayout.WEST);


        JPanel center = new JPanel();
        center.setBorder(BorderFactory.createLineBorder(Color.black));
        center.setPreferredSize(new Dimension(800, 600));
        center.setLayout(new FlowLayout(FlowLayout.LEFT));
        center.setAlignmentX(Component.LEFT_ALIGNMENT);



        //mouse hover될때마다 이미지를 바꾸기위해 east를 여기다가 넣는점 양해바랍니다.
        JPanel east = new JPanel();
        east.setBorder(BorderFactory.createLineBorder(Color.black));

        east.add(character.getDeck()[0].getCardImage());

        for(int k= 0; k < game.getPlayer().getCharacter().getDeckSize(); k++) {
           JPanel cardPanel = character.getDeck()[k].getCardPreviewImage();
           final int kk= k;
            cardPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    east.removeAll();
                    east.repaint();
                    east.add(character.getDeck()[kk].getCardImage());
                    east.validate();


                }
            });

            center.add(cardPanel);

        }
        add(center, BorderLayout.CENTER);
        add(east, BorderLayout.EAST);


    }


}
