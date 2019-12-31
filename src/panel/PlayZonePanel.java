package panel;

import Cards.Card;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import main.Game;
import org.w3c.dom.ls.LSOutput;
import support.Player;

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
    private JPanel[][] enemyZones = new JPanel[2][7];

    private int numberOfMonsters;
    private int numberOfMagicTrap;
    // private JLabel cardBack;
    private Player player;
    private Player enemy;

    private int numberOfMonstersOfEnemy;
    private int numberOfMagicTrapOfEnemy;
    public PlayZonePanel(Game game, Player player, Player enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        numberOfMonsters = 0;
        numberOfMagicTrap = 0;

        numberOfMonstersOfEnemy =0;
        numberOfMagicTrapOfEnemy = 0;

        setLayout(new BorderLayout());

        JPanel myField = new JPanel();
        myField.setLayout(new GridLayout(2, 7));

        JPanel enemyField = new JPanel();
        enemyField.setLayout(new GridLayout(2, 7));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {

                JPanel zone = new JPanel();

                zone.setPreferredSize(new Dimension(75, 130));

                //     zone.setLayout(null);
                JLabel text = new JLabel();
                text.setPreferredSize(new Dimension(100, 30));
                text.setHorizontalAlignment(JLabel.CENTER);
                text.setVerticalAlignment(JLabel.NORTH);
                //적의진영
                JPanel enemyZone = new JPanel();

                enemyZone.setPreferredSize(new Dimension(75, 130));
                //     zone.setLayout(null);

                JLabel text2 = new JLabel();
                text2.setPreferredSize(new Dimension(100, 30));
                text2.setHorizontalAlignment(JLabel.CENTER);
                text2.setVerticalAlignment(JLabel.NORTH);
                if (i == 0) {
                    if (j == 0) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                        enemyZone.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        text.setText("융합");
                        text2.setText("묘지");
                    } else if (j == 6) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        enemyZone.setBorder(BorderFactory.createLineBorder(Color.green));
                        text.setText("덱" + player.getDeckStack().size());
                        //남은 덱카드수만큼 add.
                        text2.setText("필드");


                    } else {
                        zone.setBorder(BorderFactory.createLineBorder(Color.blue));
                        enemyZone.setBorder(BorderFactory.createLineBorder(Color.RED));
                        text.setText("마/함");
                        text2.setText("몬스터");

                    }
                } else {

                    if (i == 1 && j == 0) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.green));
                        enemyZone.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        text.setText("필드");
                        text2.setText("덱" + enemy.getDeckStack().size());
                    } else if (i == 1 && j == 6) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        enemyZone.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                        text.setText("묘지");
                        text2.setText("융합");
                    } else {
                        zone.setBorder(BorderFactory.createLineBorder(Color.RED));
                        enemyZone.setBorder(BorderFactory.createLineBorder(Color.blue));
                        text.setText("몬스터");
                        text2.setText("마/함");
                    }
                }
                zone.add(text);
                enemyZone.add(text2);

                myField.add(zone, i, j);
                enemyField.add(enemyZone, i, j);

                zones[i][j] = zone;
                enemyZones[i][j] = enemyZone;


            }
        }


        zones[0][6].add(((Card) player.getDeckStack().peek()).getCardBack());


        enemyZones[1][0].add(((Card) enemy.getDeckStack().peek()).getCardBack());
        add(new HandPanel(enemy), BorderLayout.NORTH);
        add(myField, BorderLayout.SOUTH);
        add(enemyField, BorderLayout.CENTER);
    }

    public void setCard(Card card, int y, boolean set) {
        JPanel cardImage = card.getCardPreviewImage();

        //   JPanel cardBack = card.getCardPreviewImage();
        //좀 추잡한 method지만 상황마다 해야할게 다다르니, 저렇게 그냥 중복되는거있어도 세세한게달라 저렇게한점 양해하자..
        JPanel cardBack = card.getCardBack();

        if (y == 1) {
//            System.out.println("row좌표는: " + y);
//            System.out.println("column좌표는: " + numberOfMonsters);

            final int location = numberOfMonsters + 1;
            if (!set) {
                zones[y][numberOfMonsters + 1].add(cardImage);
                zones[y][numberOfMonsters + 1].validate();


             //   enemyZones[y][numberOfMonsters + 1].add(cardImage);
            //    enemyZones[y][numberOfMonsters + 1].validate();

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
                                    //현재는 공격표시인데 수비표시로 바꾸ㅡ는 상황임. 그래서 m1을 공격표시로 바꾸지. 수비표시일때 공격표시버튼뜨라구

                                    m1.setText("공격표시");
                                    //            m1.validate();


                                    ImageIcon img = new ImageIcon(card.getIcon().getImage());

                                    img.setImage(img.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
                                    JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                                        @Override
                                        protected void paintComponent(Graphics g) {
                                            super.paintComponent(g);
                                            Graphics2D g2 = (Graphics2D) g;
                                            g2.rotate(Math.PI / 2f, img.getIconWidth() / 2, img.getIconHeight() / 2);
                                            g2.drawImage(img.getImage(), 0, 0, null);
                                        }
                                    };

                                    previewImage.setAlignmentX(0.75f);
                                    previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);

                                    //초강수 삼총사
                                    cardImage.removeAll();
                                    cardImage.revalidate();
                                    cardImage.repaint();

                                    cardImage.setPreferredSize(new Dimension(80, 60));

                                    cardImage.add(Box.createRigidArea(new Dimension(0, 4)));

                                    cardImage.add(previewImage);

                                    cardImage.validate();

                                    zones[y][location].add(cardImage);
                                    zones[y][location].validate();


                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                }

                            });
                        } else {
                            m1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    m1.setText("수비표시");
                                    //             m1.validate();


                                    ImageIcon img = new ImageIcon(card.getIcon().getImage()); //왜 이렇게 instantiate 하냐면 그냥 img = card.getIcon()하면 밑에서 setImage할때 img.getImage는 card.getIcon.getImage하는것과같아,
                                    //image의 quality 를 2번 scaledInstance하는것과같고, 그 여파로 west에 image와 preview의 image가 전부 doomed 되는것을 볼수있다.
                                    //하지만 이렇게 instiantiate를 하면, 이미지 본연을 받아 다시 scale을 하기때문에 1번 만 shirnk되는것을 볼수있다.

                                    img.setImage(img.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                                    JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                                        @Override
                                        protected void paintComponent(Graphics g) {
                                            super.paintComponent(g);
                                            Graphics2D g2 = (Graphics2D) g;
                                            g2.rotate(0, img.getIconWidth() / 2, img.getIconHeight() / 2);
                                            g2.drawImage(img.getImage(), 0, 0, null);
                                        }
                                    };

                                    previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);


                                    cardImage.setPreferredSize(new Dimension(60, 80));
                                    cardImage.removeAll();
                                    cardImage.add(Box.createRigidArea(new Dimension(0, 5)));

                                    cardImage.add(previewImage);

                                    cardImage.validate();


                                    zones[y][location].add(cardImage);
                                    zones[y][location].validate();


                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                }

                            });

                        }


                    }

                });
            } else {//세트할시.
                ImageIcon img = new ImageIcon(card.getBackIcon().getImage()); //왜 이렇게 instantiate 하냐면 그냥 img = card.getIcon()하면 밑에서 setImage할때 img.getImage는 card.getIcon.getImage하는것과같아,
                //image의 quality 를 2번 scaledInstance하는것과같고, 그 여파로 west에 image와 preview의 image가 전부 doomed 되는것을 볼수있다.
                //하지만 이렇게 instiantiate를 하면, 이미지 본연을 받아 다시 scale을 하기때문에 1번 만 shirnk되는것을 볼수있다.

                img.setImage(img.getImage().getScaledInstance(80, 60, Image.SCALE_DEFAULT));

                JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.rotate(Math.PI / 2f, img.getIconWidth() / 2, img.getIconHeight() / 2);
                        //             g2.drawImage(img.getImage(), 0, 0, null); 이거있음 중복되는경향있다..
                    }
                };

                //        previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                //      previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);


                cardBack.setPreferredSize(new Dimension(80, 60));
                cardBack.removeAll();
                cardImage.add(Box.createRigidArea(new Dimension(0, 5)));

                cardBack.add(previewImage);

                cardBack.validate();
                JPopupMenu pm = new JPopupMenu("선택창");
                JMenuItem m1 = new JMenuItem("반전소환");


                pm.add(m1);


                zones[y][location].add(cardBack);
                zones[y][location].validate();

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

                                System.out.println("반전소환되었다");
                                //카드덱지우고 카드이미지ㄴ허기
                                //            m1.validate();

                                m1.removeActionListener(m1.getActionListeners()[0]);
                                zones[y][location].remove(cardBack);

                                zones[y][location].add(cardImage);

                                zones[y][location].validate();
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

                                                    ImageIcon img = new ImageIcon(card.getIcon().getImage());

                                                    img.setImage(img.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
                                                    JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                                                        @Override
                                                        protected void paintComponent(Graphics g) {
                                                            super.paintComponent(g);
                                                            Graphics2D g2 = (Graphics2D) g;
                                                            g2.rotate(Math.PI / 2f, img.getIconWidth() / 2, img.getIconHeight() / 2);
                                                            g2.drawImage(img.getImage(), 0, 0, null);
                                                        }
                                                    };

                                                    previewImage.setAlignmentX(0.75f);
                                                    previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);

                                                    //초강수 삼총사
                                                    cardImage.removeAll();
                                                    cardImage.revalidate();
                                                    cardImage.repaint();

                                                    cardImage.setPreferredSize(new Dimension(80, 60));

                                                    cardImage.add(Box.createRigidArea(new Dimension(0, 4)));

                                                    cardImage.add(previewImage);

                                                    cardImage.validate();

                                                    zones[y][location].add(cardImage);
                                                    zones[y][location].validate();


                                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                                }

                                            });
                                        } else {
                                            m1.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {

                                                    m1.setText("수비표시");
                                                    //             m1.validate();

                                                    ImageIcon img = new ImageIcon(card.getIcon().getImage()); //왜 이렇게 instantiate 하냐면 그냥 img = card.getIcon()하면 밑에서 setImage할때 img.getImage는 card.getIcon.getImage하는것과같아,
                                                    //image의 quality 를 2번 scaledInstance하는것과같고, 그 여파로 west에 image와 preview의 image가 전부 doomed 되는것을 볼수있다.
                                                    //하지만 이렇게 instiantiate를 하면, 이미지 본연을 받아 다시 scale을 하기때문에 1번 만 shirnk되는것을 볼수있다.

                                                    img.setImage(img.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                                                    JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                                                        @Override
                                                        protected void paintComponent(Graphics g) {
                                                            super.paintComponent(g);
                                                            Graphics2D g2 = (Graphics2D) g;
                                                            g2.rotate(0, img.getIconWidth() / 2, img.getIconHeight() / 2);
                                                            g2.drawImage(img.getImage(), 0, 0, null);
                                                        }
                                                    };

                                                    previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                                                    previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);


                                                    cardImage.setPreferredSize(new Dimension(60, 80));
                                                    cardImage.removeAll();
                                                    cardImage.add(Box.createRigidArea(new Dimension(0, 5)));
                                                    cardImage.add(previewImage);
                                                    cardImage.validate();


                                                    zones[y][location].add(cardImage);
                                                    zones[y][location].validate();


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
            final int location = numberOfMagicTrap + 1;
            //그냥 발동이었을때
            if (!set) {
                //그냥 발동이면, 아무것도 add할필요없잖아?
                zones[y][location].add(cardImage);
                zones[y][location].validate();

                //    cardImage.removeMouseListener(cardImage.getMouseListeners()[1]); 이제 새로운 cardImage기 때문에 preview와 연관이없다.
            } //이하는 세트일때
            else {

                zones[y][location].add(cardBack);
                zones[y][location].validate();

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
                                zones[y][location].remove(cardBack);

                                zones[y][location].add(cardImage);

                                zones[y][location].validate();
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

                player.getDfp().addAtWest(card.getCardImage());
            }
        });
        cardBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);


                player.getDfp().addAtWest(card.getCardImage());
            }
        });

      //  updateEnemyField(card, y, set);
    }

    public void updateEnemyField(Card card, int y, boolean set){
        JPanel cardImage = card.getCardPreviewImage();

        JPanel cardBack = card.getCardBack();
        if (y == 0) {

            final int location = numberOfMonstersOfEnemy+1;
            if (!set) {
                enemyZones[y][location ].add(cardImage);
                enemyZones[y][location ].validate();


            } else {//세트할시.
                ImageIcon img = new ImageIcon(card.getBackIcon().getImage()); //왜 이렇게 instantiate 하냐면 그냥 img = card.getIcon()하면 밑에서 setImage할때 img.getImage는 card.getIcon.getImage하는것과같아,
                //image의 quality 를 2번 scaledInstance하는것과같고, 그 여파로 west에 image와 preview의 image가 전부 doomed 되는것을 볼수있다.
                //하지만 이렇게 instiantiate를 하면, 이미지 본연을 받아 다시 scale을 하기때문에 1번 만 shirnk되는것을 볼수있다.

                img.setImage(img.getImage().getScaledInstance(80, 60, Image.SCALE_DEFAULT));

                JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.rotate(Math.PI / 2f, img.getIconWidth() / 2, img.getIconHeight() / 2);
                        //             g2.drawImage(img.getImage(), 0, 0, null); 이거있음 중복되는경향있다..
                    }
                };

                //        previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                //      previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);


                cardBack.setPreferredSize(new Dimension(80, 60));
                cardBack.removeAll();
                cardImage.add(Box.createRigidArea(new Dimension(0, 5)));

                cardBack.add(previewImage);

                cardBack.validate();



                enemyZones[y][location].add(cardBack);
                enemyZones[y][location].validate();





            }

            numberOfMonstersOfEnemy++;

        } else {
            final int location = numberOfMagicTrapOfEnemy+1;

            //그냥 발동이었을때
            if (!set) {
                //그냥 발동이면, 아무것도 add할필요없잖아?
                enemyZones[y][location].add(cardImage);
                enemyZones[y][location].validate();

                //    cardImage.removeMouseListener(cardImage.getMouseListeners()[1]); 이제 새로운 cardImage기 때문에 preview와 연관이없다.
            } //이하는 세트일때
            else {

                enemyZones[y][location].add(cardBack);
                enemyZones[y][location].validate();






            }
            numberOfMagicTrapOfEnemy++;

        }
        cardImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                player.getDfp().addAtWest(card.getCardImage());
            }
        });

    }

}
