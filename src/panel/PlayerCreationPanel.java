package panel;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerCreationPanel extends JPanel {

    private ImageIcon[] imageList = new ImageIcon[6];
    private JButton[] characterList = new JButton[6];

    private JPanel characterPanel = new JPanel();
    private String[] names = {"왕님", "범골", "카사장", "안면예술가", "중2병", "외눈박이"};
    private int selectedCharacter;
    private Game game;

    public PlayerCreationPanel(Game game) {

        this.game = game;
        setSize(new Dimension(1280, 720));
        setLayout(new BorderLayout());
        JButton goToDeckPage = new JButton("Create Deck");
        add(goToDeckPage, BorderLayout.SOUTH);

        goToDeckPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //    selectedCharacter = 1;
           //     game.setCharacter(0);

                //CREATE DECK 누르기전까진 그어떤 플레이어로의 엑세스를 없게하라!!!
                game.setCharacter(selectedCharacter);

                game.showDeckCreationPaenl(selectedCharacter);



            }
        });

        showCharacterList();
        add(characterPanel, BorderLayout.CENTER);

    }

    private void showCharacterList() {
        for (int i = 0; i < 6; i++) {
            String img = "data/images/characters/c" + (i + 1) + ".PNG";

            imageList[i] =
                    new ImageIcon(img);
            imageList[i].setImage(imageList[i].getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            characterList[i] = new JButton(names[i], imageList[i]);
            characterList[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            characterList[i].setHorizontalTextPosition(SwingConstants.CENTER);
            characterList[i].setOpaque(false);
            characterList[i].setContentAreaFilled(false);
            characterList[i].setBorderPainted(false);
            final int j = i;
            characterList[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setCharacter(j);

                }
            });

        }
        characterPanel.setLayout(new GridLayout(2, 3));

        for (int i = 0; i < 6; i++) {


            characterPanel.add(characterList[i]);

        }
    }

    private void setCharacter(int i) {

        selectedCharacter = i;

    }
}
