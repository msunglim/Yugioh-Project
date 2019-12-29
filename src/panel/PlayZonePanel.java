package panel;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import main.Game;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayZonePanel extends JPanel {
    private Game game;

    private JPanel[][] zones = new JPanel[2][7];

    private int numberOfMonsters;
    private int numberOfMagicTrap;

    public PlayZonePanel(Game game) {
        this.game = game;
        numberOfMonsters = 0;
        numberOfMagicTrap = 0;
        setLayout(new GridLayout(2, 7));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {

                JPanel zone = new JPanel();
                //     zone.setLayout(null);
                if (i == 0) {
                    if (j == 0) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                        zone.add(new JLabel("융합"));
                    } else if (j == 6) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        zone.add(new JLabel("덱" + game.getPlayer().getDeckStack().size()));
                        //남은 덱카드수만큼 add.

                        JLabel deckSurface = new JLabel(new ImageIcon("data/images/cards/back.PNG"));

                        zone.add(deckSurface);

                    } else {
                        zone.setBorder(BorderFactory.createLineBorder(Color.blue));
                        zone.add(new JLabel("마/함"));
                    }
                } else {

                    if (i == 1 && j == 0) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.green));
                        zone.add(new JLabel("필드"));
                    } else if (i == 1 && j == 6) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        zone.add(new JLabel("묘지"));

                    } else {
                        zone.setBorder(BorderFactory.createLineBorder(Color.RED));
                        zone.add(new JLabel("몬스터"));
                    }
                }
                add(zone, i, j);
                zones[i][j] = zone;
            }
        }


    }

    public void setCard(JPanel cardImage, int y) {
        if (y == 1) {
            System.out.println("row좌표는: " + y);
            System.out.println("column좌표는: " + numberOfMonsters);

            zones[y][numberOfMonsters + 1].add(cardImage);
            zones[y][numberOfMonsters + 1].validate();
            numberOfMonsters++;
            cardImage.removeMouseListener(cardImage.getMouseListeners()[1]);
            JPopupMenu pm = new JPopupMenu("선택창");
            JMenuItem m1 = new JMenuItem("수비표시");
            System.out.println("첫번째입니다" + m1.getText());

            pm.add(m1);
            cardImage.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);


                    pm.show(e.getComponent(),
                            e.getX(), e.getY());
                    //       System.out.println("텍스트:" + m1.getText().equals("공격표시"));

                    if (m1.getText().equals("수비표시")) {
                        m1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                m1.setText("공격표시");
                    //            m1.validate();

                                m1.removeActionListener(m1.getActionListeners()[0]);
                            }

                        });
                    } else {
                        m1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                m1.setText("수비표시");
                   //             m1.validate();
                                m1.removeActionListener(m1.getActionListeners()[0]);
                            }

                        });

                    }


                }

            });

//            cardImage.addMouseListener(new MouseAdapter() { 오히려 이거있으면 에러가뜨는..
//                @Override
//                public void mouseEntered(MouseEvent e) {
//                    super.mouseEntered(e);
//                    System.out.println("둠다");
//
//
//                }
//            });
            //     zones[x][y].repaint();
        } else {
            System.out.println("야호");
            zones[y][numberOfMagicTrap + 1].add(cardImage);
            zones[y][numberOfMagicTrap + 1].validate();
            numberOfMagicTrap++;
        }
    }
}
