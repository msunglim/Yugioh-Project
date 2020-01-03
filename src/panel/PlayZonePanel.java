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
import java.util.ArrayList;

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

    private JLabel text;
    private JLabel text2;
    private HandPanel enemyHand;

    private ArrayList<Card> unchangableList = new ArrayList<>(10);

    public PlayZonePanel(Game game, Player player, Player enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        numberOfMonsters = 0;
        numberOfMagicTrap = 0;

        numberOfMonstersOfEnemy = 0;
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
                text = new JLabel();
                text.setPreferredSize(new Dimension(100, 30));
                text.setHorizontalAlignment(JLabel.CENTER);
                text.setVerticalAlignment(JLabel.NORTH);
                //적의진영
                JPanel enemyZone = new JPanel();

                enemyZone.setPreferredSize(new Dimension(75, 130));
                //     zone.setLayout(null);

                text2 = new JLabel();
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

        //적의 수중의 패.
        enemyHand = new HandPanel(enemy);
        add(enemyHand, BorderLayout.NORTH);


        add(myField, BorderLayout.SOUTH);
        add(enemyField, BorderLayout.CENTER);
    }

    public void setCard(Card card, int y, boolean set) {
        JPanel cardImage = card.getCardPreviewImage();
        JPanel cardImage2 = card.getCardPreviewImage();

        //   JPanel cardBack = card.getCardPreviewImage();
        //좀 추잡한 method지만 상황마다 해야할게 다다르니, 저렇게 그냥 중복되는거있어도 세세한게달라 저렇게한점 양해하자..
        JPanel cardBack = card.getCardBack();
        JPanel cardBack2 = card.getCardBack();
        unchangableList.add(card);
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


                        //자기턴이아니면, 카드에 손대도 발동안할것. 플레이어를 막론하고 메인페이즈 아닐때는 표시형식변경불가야!!!
                        //unchangeable list 에 없는 카드만 표시형식변경가능합니다.
                        if (player.getMyTurn() && game.getCurrnetPhase().isMainPhase() && !unchangableList.contains(card)) {

                            pm.show(e.getComponent(),
                                    e.getX(), e.getY());
                            //       System.out.println("텍스트:" + m1.getText().equals("공격표시"));

                            if (m1.getText().equals("수비표시")) {
                                m1.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        //현재는 공격표시인데 수비표시로 바꾸ㅡ는 상황임. 그래서 m1을 공격표시로 바꾸지. 수비표시일때 공격표시버튼뜨라구
                                        unchangableList.add(card);
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
                                        cardImage.repaint();
                                        cardImage.setPreferredSize(new Dimension(80, 60));
                                        cardImage.add(Box.createRigidArea(new Dimension(0, 4)));
                                        cardImage.add(previewImage);
                                        cardImage.validate();
                                        zones[y][location].add(cardImage);
                                        zones[y][location].validate();
                                        //깨끗하게.
                                        m1.removeActionListener(m1.getActionListeners()[0]);


                                        //물밑작업
                                        ImageIcon img2 = new ImageIcon(card.getIcon().getImage());

                                        img2.setImage(img2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
                                        JLabel previewImage2 = new JLabel(null, img2, JLabel.CENTER) {
                                            @Override
                                            protected void paintComponent(Graphics g) {
                                                super.paintComponent(g);
                                                Graphics2D g2 = (Graphics2D) g;
                                                g2.rotate(Math.PI / 2f, img2.getIconWidth() / 2, img2.getIconHeight() / 2);
                                                g2.drawImage(img2.getImage(), 0, 0, null);
                                            }
                                        };

                                        previewImage2.setAlignmentX(0.75f);
                                        previewImage2.setAlignmentY(Component.CENTER_ALIGNMENT);
                                        cardImage2.removeAll();
                                        cardImage2.repaint();
                                        cardImage2.setPreferredSize(new Dimension(80, 60));
                                        cardImage2.add(Box.createRigidArea(new Dimension(0, 4)));
                                        cardImage2.add(previewImage2);
                                        cardImage2.validate();


                                        //frame2 update
                                        enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, y, location);
                                    }

                                });
                            } else {
                                m1.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        unchangableList.add(card);
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


                                        //물밑작업

                                        ImageIcon img2 = new ImageIcon(card.getIcon().getImage());

                                        img2.setImage(img2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
                                        JLabel previewImage2 = new JLabel(null, img2, JLabel.CENTER) {
                                            @Override
                                            protected void paintComponent(Graphics g) {
                                                super.paintComponent(g);
                                                Graphics2D g2 = (Graphics2D) g;
                                                g2.rotate(0, img2.getIconWidth() / 2, img2.getIconHeight() / 2);
                                                g2.drawImage(img2.getImage(), 0, 0, null);
                                            }
                                        };

                                        previewImage2.setAlignmentX(0.75f);
                                        previewImage2.setAlignmentY(Component.CENTER_ALIGNMENT);
                                        cardImage2.removeAll();
                                        cardImage2.repaint();
                                        cardImage2.setPreferredSize(new Dimension(60, 80));
                                        cardImage2.add(Box.createRigidArea(new Dimension(0, 5)));
                                        cardImage2.add(previewImage2);
                                        cardImage2.validate();


                                        //frame2 update
                                        enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, y, location);
                                    }

                                });

                            }


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

                //적의 duelfiled 에 반전소환된것을 업데이트
                ImageIcon img2 = new ImageIcon(card.getBackIcon().getImage());
                img2.setImage(img2.getImage().getScaledInstance(80, 60, Image.SCALE_DEFAULT));

                JLabel previewImage2 = new JLabel(null, img2, JLabel.CENTER) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.rotate(Math.PI / 2f, img2.getIconWidth() / 2, img2.getIconHeight() / 2);
                        //             g2.drawImage(img.getImage(), 0, 0, null); 이거있음 중복되는경향있다..
                    }
                };

                //        previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                //      previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);


                cardBack2.setPreferredSize(new Dimension(80, 60));
                cardBack2.removeAll();
                cardImage2.add(Box.createRigidArea(new Dimension(0, 5)));

                cardBack2.add(previewImage2);

                cardBack2.validate();


                //   enemy.getDfp().getCenter().updateEnemyFieldGraphicForSet(card, cardBack2, y, location);


                cardBack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        //자기턴이아니면, 카드에 손대도 발동안할것.
                        if (player.getMyTurn() && !unchangableList.contains(card)) {


                            pm.show(e.getComponent(),
                                    e.getX(), e.getY());
                            //       System.out.println("텍스트:" + m1.getText().equals("공격표시"));


                            m1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    unchangableList.add(card);
                                    System.out.println("반전소환되었다");
                                    //카드덱지우고 카드이미지ㄴ허기
                                    //            m1.validate();

                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                    zones[y][location].remove(cardBack);

                                    zones[y][location].add(cardImage);


                                    zones[y][location].validate();


                                    enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, y, location);

                                    JPopupMenu pm = new JPopupMenu("선택창");
                                    JMenuItem m1 = new JMenuItem("수비표시");


                                    pm.add(m1);
                                    cardImage.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            super.mouseClicked(e);

                                            if (player.getMyTurn() && game.getCurrnetPhase().isMainPhase() && !unchangableList.contains(card)) {
                                                pm.show(e.getComponent(),
                                                        e.getX(), e.getY());
                                                //       System.out.println("텍스트:" + m1.getText().equals("공격표시"));

                                                if (m1.getText().equals("수비표시")) {
                                                    m1.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            unchangableList.add(card);
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

                                                            //물밑작업 적의 필드업데이트
                                                            ImageIcon img2 = new ImageIcon(card.getIcon().getImage());

                                                            img2.setImage(img2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
                                                            JLabel previewImage2 = new JLabel(null, img2, JLabel.CENTER) {
                                                                @Override
                                                                protected void paintComponent(Graphics g) {
                                                                    super.paintComponent(g);
                                                                    Graphics2D g2 = (Graphics2D) g;
                                                                    g2.rotate(Math.PI / 2f, img2.getIconWidth() / 2, img2.getIconHeight() / 2);
                                                                    g2.drawImage(img2.getImage(), 0, 0, null);
                                                                }
                                                            };

                                                            previewImage2.setAlignmentX(0.75f);
                                                            previewImage2.setAlignmentY(Component.CENTER_ALIGNMENT);
                                                            cardImage2.removeAll();
                                                            cardImage2.repaint();
                                                            cardImage2.setPreferredSize(new Dimension(80, 60));
                                                            cardImage2.add(Box.createRigidArea(new Dimension(0, 4)));
                                                            cardImage2.add(previewImage2);
                                                            cardImage2.validate();


                                                            //frame2 update
                                                            enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, y, location);
                                                        }

                                                    });
                                                } else {
                                                    m1.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            unchangableList.add(card);
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

                                                            //물밑작업 반전소환 적의필드 업뎅티ㅡ

                                                            ImageIcon img2 = new ImageIcon(card.getIcon().getImage());

                                                            img2.setImage(img2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
                                                            JLabel previewImage2 = new JLabel(null, img2, JLabel.CENTER) {
                                                                @Override
                                                                protected void paintComponent(Graphics g) {
                                                                    super.paintComponent(g);
                                                                    Graphics2D g2 = (Graphics2D) g;
                                                                    g2.rotate(0, img2.getIconWidth() / 2, img2.getIconHeight() / 2);
                                                                    g2.drawImage(img2.getImage(), 0, 0, null);
                                                                }
                                                            };

                                                            previewImage2.setAlignmentX(0.75f);
                                                            previewImage2.setAlignmentY(Component.CENTER_ALIGNMENT);
                                                            cardImage2.removeAll();
                                                            cardImage2.repaint();
                                                            cardImage2.setPreferredSize(new Dimension(60, 80));
                                                            cardImage2.add(Box.createRigidArea(new Dimension(0, 5)));
                                                            cardImage2.add(previewImage2);
                                                            cardImage2.validate();


                                                            //frame2 update
                                                            enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, y, location);
                                                        }

                                                    });

                                                }


                                            }
                                        }
                                    });
                                }

                            });


                        }
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

                        //자기턴이아니면, 카드에 손대도 발동안할것. 함정일경우 가능하다.
                        System.out.println("뭡니까 이거 발동이안되요 " + (!unchangableList.contains(card) && (card.getCardType() == 2 || player.getMyTurn())));
                        if (!unchangableList.contains(card) && (card.getCardType() == 2 || player.getMyTurn())) {

                            pm.show(e.getComponent(),
                                    e.getX(), e.getY());


                            m1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    unchangableList.add(card);
                                    System.out.println("리버스발동되었다.");


                                    m1.removeActionListener(m1.getActionListeners()[0]);
                                    zones[y][location].remove(cardBack);

                                    zones[y][location].add(cardImage);

                                    zones[y][location].validate();


                                    enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, y, location);
                                }

                            });


                        }
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

    }

    public void updateEnemyField(Card card, int y, boolean set) {
        JPanel cardImage = card.getCardPreviewImage();

        JPanel cardBack = card.getCardBack();
        if (y == 0) {

            final int location = numberOfMonstersOfEnemy + 1;
            if (!set) {
                enemyZones[y][location].add(cardImage);
                enemyZones[y][location].validate();


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
            final int location = numberOfMagicTrapOfEnemy + 1;

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

    public void updateEnemyFieldGraphic(Card card, JPanel cardImage, int y, int location) {


        if (y == 0) {
            enemyZones[1][location].remove(1);
            enemyZones[1][location].repaint();
            enemyZones[1][location].add(cardImage);
            enemyZones[1][location].validate();
        } else {

            //    enemyZones[0][numberOfMonstersOfEnemy].removeAll();

            enemyZones[0][location].remove(1);
            enemyZones[0][location].repaint();
            enemyZones[0][location].add(cardImage);
            enemyZones[0][location].validate();
        }

        cardImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                player.getDfp().addAtWest(card.getCardImage());
            }
        });
    }


    //상대의 frame 에나오는 enemyHand를 의미함. center North에 있는 그것.
    public HandPanel getEnemyHand() {
        return enemyHand;
    }

    public void updateDeckAmount() {
        // text.setText("덱" + player.getDeckStack().size());
        ((JLabel) (zones[0][6].getComponent(0))).setText("덱" + player.getDeckStack().size());
        if (player.getDeckStack().size() == 0) {
            zones[0][6].remove(1);
            enemy.getDfp().getCenter().getEnemyZones()[1][0].remove(1);
        }
        ((JLabel) (enemy.getDfp().getCenter().getEnemyZones()[1][0].getComponent(0))).setText("덱" + player.getDeckStack().size());
        repaint();
        validate();
        enemy.getDfp().getCenter().repaint();
        enemy.getDfp().getCenter().validate();
    }

    public JPanel[][] getEnemyZones() {
        return enemyZones;
    }

    public int getNumberOfMonsters() {
        return numberOfMonsters;
    }

    public int getNumberOfMagicTrap() {
        return numberOfMagicTrap;
    }

    public void setNumberOfMonsters(int newNumberOfMonster) {
        this.numberOfMonsters = newNumberOfMonster;
    }

    public void setNumberOfMagicTrap(int newNumberOfMagicTrap) {
        this.numberOfMagicTrap = newNumberOfMagicTrap;
    }


    public void emptyUnchangableList() {
        unchangableList = new ArrayList<>(10);
    }
}


