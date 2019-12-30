package panel;

import Cards.Card;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import main.Game;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class PlayZonePanel extends JPanel {
    private Game game;

    private JPanel[][] zones = new JPanel[2][7];

    private int numberOfMonsters;
    private int numberOfMagicTrap;
    // private JLabel cardBack;

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


                        zone.add(((Card) game.getPlayer().getDeckStack().peek()).getCardBack());

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

    public void setCard(Card card, int y, boolean set) {
        JPanel cardImage = card.getCardPreviewImage();

        //   JPanel cardBack = card.getCardPreviewImage();
        JPanel cardBack = card.getCardBack();
        if (y == 1) {
//            System.out.println("row좌표는: " + y);
//            System.out.println("column좌표는: " + numberOfMonsters);


            if (!set) {
                zones[y][numberOfMonsters + 1].add(cardImage);
                zones[y][numberOfMonsters + 1].validate();

                //     cardImage.removeMouseListener(cardImage.getMouseListeners()[1]);
                JPopupMenu pm = new JPopupMenu("선택창");
                JMenuItem m1 = new JMenuItem("수비표시");


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

                                    ImageIcon img = card.getIcon();
                                    img.setImage(img.getImage().getScaledInstance(80,60,Image.SCALE_DEFAULT));
                                    JLabel yoink = new JLabel(null, img, JLabel.CENTER) {
                                        @Override
                                        protected void paintComponent(Graphics g) {
                                            super.paintComponent(g);
                                            System.out.println("돌려돌려");
                                            Graphics2D g2 = (Graphics2D) g;
                                            g2.rotate(90, img.getIconWidth() / 2, img.getIconHeight() / 2);
                                            g2.drawImage(img.getImage(), 0, 0, null);
                                        }
                                    };
                                    cardImage.removeAll();
                                    cardImage.add(yoink);
                                    cardImage.validate();


                                    zones[y][numberOfMonsters ].add(cardImage);
                                    zones[y][numberOfMonsters ].validate();


                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                }

                            });
                        } else {
                            m1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    m1.setText("수비표시");
                                    //             m1.validate();


                                    ImageIcon img = card.getIcon();
                                    img.setImage(img.getImage().getScaledInstance(80,60,Image.SCALE_DEFAULT));
                                    JLabel yoink = new JLabel(null, img, JLabel.CENTER) {
                                        @Override
                                        protected void paintComponent(Graphics g) {
                                            super.paintComponent(g);
                                            System.out.println("돌려돌려");
                                            Graphics2D g2 = (Graphics2D) g;
                                            g2.rotate(0, img.getIconWidth() / 2, img.getIconHeight() / 2);
                                            g2.drawImage(img.getImage(), 0, 0, null);
                                        }
                                    };
                                    cardImage.removeAll();
                                    cardImage.add(yoink);
                                    cardImage.validate();


                                    zones[y][numberOfMonsters ].add(cardImage);
                                    zones[y][numberOfMonsters ].validate();


                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                }

                            });

                        }


                    }

                });
            } else {//세트할시.
                JPopupMenu pm = new JPopupMenu("선택창");
                JMenuItem m1 = new JMenuItem("반전소환");


                pm.add(m1);


                zones[y][numberOfMonsters + 1].add(cardBack);
                zones[y][numberOfMonsters + 1].validate();

                cardBack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);


                        pm.show(e.getComponent(),
                                e.getX(), e.getY());
                        //       System.out.println("텍스트:" + m1.getText().equals("공격표시"));


                        m1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                System.out.println("반전소환되었따");
                                //카드덱지우고 카드이미지ㄴ허기
                                //            m1.validate();

                                m1.removeActionListener(m1.getActionListeners()[0]);
                                zones[y][numberOfMonsters].remove(cardBack);

                                zones[y][numberOfMonsters].add(cardImage);

                                zones[y][numberOfMonsters].validate();
                                JPopupMenu pm = new JPopupMenu("선택창");
                                JMenuItem m1 = new JMenuItem("수비표시");


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
                            }

                        });


                    }

                });


                //    numberOfMonsters++;

            }
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
            numberOfMonsters++;
        } else {
            //그냥 발동이었을때
            if (!set) {
                //그냥 발동이면, 아무것도 add할필요없잖아?
                zones[y][numberOfMagicTrap + 1].add(cardImage);
                zones[y][numberOfMagicTrap + 1].validate();

                //    cardImage.removeMouseListener(cardImage.getMouseListeners()[1]); 이제 새로운 cardImage기 때문에 preview와 연관이없다.
            } //이하는 세트일때
            else {

                zones[y][numberOfMagicTrap + 1].add(cardBack);
                zones[y][numberOfMagicTrap + 1].validate();

                JPopupMenu pm = new JPopupMenu("선택창");
                JMenuItem m1 = new JMenuItem("발동(리버스)");


                pm.add(m1);


                cardBack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);


                        pm.show(e.getComponent(),
                                e.getX(), e.getY());
                        //       System.out.println("텍스트:" + m1.getText().equals("공격표시"));


                        m1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                System.out.println("리버스발동되었다.");
                                //카드덱지우고 카드이미지ㄴ허기
                                //            m1.validate();

                                m1.removeActionListener(m1.getActionListeners()[0]);
                                zones[y][numberOfMagicTrap].remove(cardBack);

                                zones[y][numberOfMagicTrap].add(cardImage);

                                zones[y][numberOfMagicTrap].validate();
                            }

                        });


                    }

                });


                //    numberOfMonsters++;


            }
            numberOfMagicTrap++;

        }


        //카드에 마우스를 올렸을때 왼쪽에 add하기.
        cardImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                game.getDfp().addAtWest(card.getCardImage());
            }
        });
        cardBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);


                game.getDfp().addAtWest(card.getCardImage());
            }
        });
    }
}