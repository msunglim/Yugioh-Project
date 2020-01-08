package panel;

import Cards.Card;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    //이번턴에 표시형식을 변경할수없는 카드들
    private ArrayList<Card> unchangableList = new ArrayList<>(10);

    //공격 할 수 있는 자신의 몬스터들
    private Map<Card, JPanel> fightableList = new HashMap<>(5);

    //나의 필드에 있는 몬스터들. 배틀패이지에서 변화가있을때 쓰인다.
    private Map<Card, JPanel> myFieldMonsterZone = new HashMap<>(5);
    //적의 몬스터들 //배틀패이즈 개시시 모든 몬스터에 addListner 할때 쓰인다
    private Map<Card, JPanel> enemyMonsterList = new HashMap<>(5);
    //공격 표시로 존재하는 적의 몬스터들//상대방 공격표시몬스터를 파악할때 쓰인다
    private ArrayList<Card> fightableEnemyMonsterList = new ArrayList<>(5);
    //적의 몬스터존을 가져올때 쓰인다 제이페널은 존을 담는다.
    private Map<Card, JPanel> enemyMonsterZone = new HashMap<>(5);
    //뒷면수비표시로 존재하는 적의 몬스터들
    private Map<Card, JPanel> setEnemyMonsterList = new HashMap<>(5);


    private ArrayList<Card> cemetery = new ArrayList<>();

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


                        JPopupMenu pm = new JPopupMenu("리스트보자꾸나");
                        JMenuItem m1 = new JMenuItem("묘지리스트보기");


                        pm.add(m1);

                        JFrame enemyCemetery = new JFrame();

                        enemyCemetery.setPreferredSize(new Dimension(300, 150));
                        enemyCemetery.pack();
                        enemyCemetery.setLocationRelativeTo(null);
                        enemyCemetery.setTitle("묘지리스트");
                        enemyZone.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                pm.show(e.getComponent(), e.getX(), e.getY());

                                m1.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        enemyCemetery.setVisible(true);
                                        cemeteryListShow(enemyCemetery, enemy.getDfp().getCenter().getCemetery());

                                        m1.removeActionListener(m1.getActionListeners()[0]);

                                    }
                                });
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                super.mouseEntered(e);
                                if (enemy.getDfp().getCenter().getCemetery().size() != 0) {
                                    player.getDfp().addAtWest(enemy.getDfp().getCenter().getCemetery().get(enemy.getDfp().getCenter().getCemetery().size() - 1).getCardImage());
                                }
                            }
                        });
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


                        JPopupMenu pm = new JPopupMenu("리스트보자꾸나");
                        JMenuItem m1 = new JMenuItem("묘지리스트보기");


                        pm.add(m1);

                        JFrame Cemetery = new JFrame();

                        Cemetery.setPreferredSize(new Dimension(300, 150));
                        Cemetery.pack();
                        Cemetery.setLocationRelativeTo(null);
                        Cemetery.setTitle("묘지리스트");
                        zone.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                pm.show(e.getComponent(), e.getX(), e.getY());

                                m1.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        Cemetery.setVisible(true);
                                        cemeteryListShow(Cemetery, cemetery);

                                        m1.removeActionListener(m1.getActionListeners()[0]);
                                    }
                                });
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                super.mouseEntered(e);
                                if (cemetery.size() != 0) {
                                    player.getDfp().addAtWest(cemetery.get(cemetery.size() - 1).getCardImage());
                                }
                            }
                        });
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
        // JPanel cardBack2 = card.getCardBack();
        unchangableList.add(card);
        //  unchangeableListCheck(card);
        if (y == 1) {
//            System.out.println("row좌표는: " + y);
//            System.out.println("column좌표는: " + numberOfMonsters);

            final int location = numberOfMonsters + 1;
            if (!set) {
                System.out.println("넘버몬" + numberOfMonsters);
                zones[y][numberOfMonsters + 1].add(cardImage);
                zones[y][numberOfMonsters + 1].validate();


                //지금은 공격표시로 소환된상황입니다.


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

                        monsterMouseClickSetting(card, cardImage, cardImage2, pm, m1, e, zones[y][location], enemy.getDfp().getCenter().getEnemyZones()[(y == 1) ? 0 : 1][location]);
                    }
                });

                //공격표시로 소환되었기때문에 공격준비완료
                fightableList.put(card, cardImage);
                enemy.getDfp().getCenter().getFightableEnemyMonsterList().add(card);

                //         System.out.println("더해진카드:" + card.getName());
            } else {//세트할시.
                ImageIcon img = new ImageIcon(card.getBackIcon().getImage()); //왜 이렇게 instantiate 하냐면 그냥 img = card.getIcon()하면 밑에서 setImage할때 img.getImage는 card.getIcon.getImage하는것과같아,
                //image의 quality 를 2번 scaledInstance하는것과같고, 그 여파로 west에 image와 preview의 image가 전부 doomed 되는것을 볼수있다.
                //하지만 이렇게 instiantiate를 하면, 이미지 본연을 받아 다시 scale을 하기때문에 1번 만 shirnk되는것을 볼수있다.

                img.setImage(img.getImage().getScaledInstance(80, 60, Image.SCALE_DEFAULT));

                //세트된 에네미몬스터명단에 추가
                enemy.getDfp().getCenter().addToSetEnemyMonsterList(card, cardImage);

                JLabel previewImage = new JLabel(null, img, JLabel.CENTER) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.rotate(Math.PI / 2f, img.getIconWidth() / 2, img.getIconHeight() / 2);
                        //             g2.drawImage(img.getImage(), 0, 0, null); 이거있음 중복되는경향있다..
                    }
                };
                cardBack.setPreferredSize(new Dimension(80, 60));
                cardBack.removeAll();
                cardImage.add(Box.createRigidArea(new Dimension(0, 5)));

                cardBack.add(previewImage);

                cardBack.validate();
                JPopupMenu pm = new JPopupMenu("선택창");
                JMenuItem m1 = new JMenuItem("반전소환");


                pm.add(m1);


                zones[y][location].add(cardBack);

                // addToMyFieldMonsterZone(card, zones[y][numberOfMonsters+1]);
                zones[y][location].validate();


                cardBack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        //자기턴이아니면, 카드에 손대도 발동안할것.
                        if (player.getMyTurn() && game.getCurrnetPhase().isMainPhase() && !unchangableList.contains(card)) {


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

                                    //뒤집어졌으니 제거해야죠.
                                    enemy.getDfp().getCenter().removeFromSetEnemyMonsterList(card);

                                    //반전소환되었다는말은, 공격표시로 되었기때문에, 전투가능요원에 배치
                                    fightableList.put(card, cardImage);
                                    enemy.getDfp().getCenter().getFightableEnemyMonsterList().add(card);

                                    // System.out.println("더해진카드:" + card.getName());
                                    enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, enemy.getDfp().getCenter().getEnemyZones()[(y == 1) ? 0 : 1][location]);
                                    enemy.getDfp().getCenter().addToEnemyMonsterList(card, cardImage2);
                                    JPopupMenu pm = new JPopupMenu("선택창");
                                    JMenuItem m1 = new JMenuItem("수비표시");


                                    pm.add(m1);
                                    cardImage.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            super.mouseClicked(e);

                                            monsterMouseClickSetting(card, cardImage, cardImage2, pm, m1, e, zones[y][location], enemy.getDfp().getCenter().getEnemyZones()[(y == 1) ? 0 : 1][location]);
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

            addToMyFieldMonsterZone(card, zones[y][numberOfMonsters]);


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
                        //System.out.println("뭡니까 이거 발동이안되요 " + (!unchangableList.contains(card) && (card.getCardType() == 2 || player.getMyTurn())));
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


                                    enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, enemy.getDfp().getCenter().getEnemyZones()[(y == 1) ? 0 : 1][location]);
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


    //그래픽 도 약간하시지만 적의 dfp에있는 플레이어의 정보를 업데이트하는 역할도합니다.
    public void updateEnemyField(Card card, int y, boolean set) {
        JPanel cardImage = card.getCardPreviewImage();

        JPanel cardBack = card.getCardBack();
        if (y == 0) {

            final int location = numberOfMonstersOfEnemy + 1;
            if (!set) {
                enemyZones[y][location].add(cardImage);
                enemyZones[y][location].validate();
                addToEnemyMonsterList(card, cardImage);
                System.out.println("존" + y + " 로케이션 " + location);
                addToEnemyMonsterZone(card, enemyZones[y][location]);
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

                addToEnemyMonsterList(card, cardBack);

                System.out.println("존" + y + " 로케이션 " + location);
                addToEnemyMonsterZone(card, enemyZones[y][location]);
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

    public void updateEnemyFieldGraphic(Card card, JPanel cardImage, JPanel enemyzone) {
//몬스터에 넣고싶음 y를 1로하세요.

        // int row = (y == 1) ? 0 : 1;
        enemyzone.remove(1);
        enemyzone.repaint();
        enemyzone.add(cardImage);
        enemyzone.validate();


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

    public int getNumberOfMonstersOfEnemy() {
        return numberOfMonstersOfEnemy;
    }

    public void setNumberOfMonstersOfEnemy(int newNumberOfMonsters) {
        numberOfMonstersOfEnemy = newNumberOfMonsters;
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

    public Map<Card, JPanel> getFightableList() {
// jpanel을 프린트할순없잖아

//        for (int i = 0; i < player.getDfp().getCenter().getFightableList().size(); i++) {
//            System.out.println(fightableList.get(i).getName());
//        }
        return fightableList;
    }

    public void addToEnemyMonsterList(Card card, JPanel cardImage) {
        //   System.out.println("새로넣어진카드는?" + card.getName());
        enemyMonsterList.put(card, cardImage);
    }

    public Map<Card, JPanel> getEnemyMonsterList() {
        return enemyMonsterList;
    }


    public ArrayList<Card> getFightableEnemyMonsterList() {
        return fightableEnemyMonsterList;
    }

    public void addToSetEnemyMonsterList(Card card, JPanel cardImage) {
        setEnemyMonsterList.put(card, cardImage);
    }

    public void removeFromSetEnemyMonsterList(Card card) {
        setEnemyMonsterList.remove(card);
    }

    public Map<Card, JPanel> getSetEnemyMonsterList() {
        return setEnemyMonsterList;
    }

    public void reverseSetMonster(Card card, JPanel cardImage) {

        setEnemyMonsterList.remove(card);
        getRotateImage(card, cardImage);
        cardImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                player.getDfp().addAtWest(card.getCardImage());
            }
        });

    }


    public void getRotateImage(Card card, JPanel cardImage) {
        // System.out.println("돌려돌려");

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


        // System.out.println("지워진카드:" + card.getName());

        //    return cardImage;
    }

    public void addToMyFieldMonsterZone(Card card, JPanel zone) {
        // System.out.println("내필드 존에 놓여진 몬스터들" + card.getName());
        myFieldMonsterZone.put(card, zone);
    }

    public void removeFromMyFieldMonsterZone(Card card) {
        myFieldMonsterZone.remove(card);
    }

    public Map<Card, JPanel> getMyFieldMonsterZone() {
        return myFieldMonsterZone;
    }

    public void updateMyMonsterZone(Card card, JPanel reversedImage) {
        JPanel defensePositionImage = card.getCardPreviewImage();
        //낡은 이미지를 청산했는데 이미지가 2개가 보이거나 노란색배경화면이 아닌가요?
        //이문제를 3번겪어서 후세에 또 겪을시 보고 참조하라고 남김니다. 일단 그 올드이미지를 ㅍ ㅔ널에서 지우고 새 jpanel을 넣으시고 validate하십시오.
        //  System.out.println("꿍짜꿍짜");
        myFieldMonsterZone.get(card).remove(1);
        getRotateImage(card, defensePositionImage);
        JPopupMenu pm = new JPopupMenu("선택창");
        JMenuItem m1 = new JMenuItem("공격표시");


        pm.add(m1);
        defensePositionImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                monsterMouseClickSetting(card, defensePositionImage, reversedImage, pm, m1, e, myFieldMonsterZone.get(card), (JPanel) reversedImage.getParent());

            }
        });
        defensePositionImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                player.getDfp().addAtWest(card.getCardImage());
            }
        });
        myFieldMonsterZone.get(card).add(defensePositionImage);
        myFieldMonsterZone.get(card).repaint();
        myFieldMonsterZone.get(card).validate();


    }

    public void getRotateZeroImage(Card card, JPanel cardImage) {

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

    }

    public void monsterMouseClickSetting(Card card, JPanel cardImage, JPanel cardImage2, JPopupMenu pm, JMenuItem m1, MouseEvent e, JPanel zone, JPanel enemyZone) {
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
                        //    unchangeableListCheck(card);

                        getRotateImage(card, cardImage);
                        zone.add(cardImage);
                        zone.validate();
//
//                                                            //수비로간놈제거
                        fightableList.remove(card, cardImage);
                        enemy.getDfp().getCenter().getFightableEnemyMonsterList().remove(card);
                        //       System.out.println("지워진카드:" + card.getName());
                        m1.removeActionListener(m1.getActionListeners()[0]);

                        getRotateImage(card, cardImage2);

                        //frame2 update
                        enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, enemyZone);
                        enemy.getDfp().getCenter().addToEnemyMonsterList(card, cardImage2);
                    }

                });
            } else {
                m1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        unchangableList.add(card);
                        m1.setText("수비표시");
                        //       unchangeableListCheck(card);
                        //             m1.validate();
                        getRotateZeroImage(card, cardImage);


                        zone.add(cardImage);
                        zone.validate();


                        m1.removeActionListener(m1.getActionListeners()[0]);

                        //공격으로 전향하신분
                        fightableList.put(card, cardImage);
                        enemy.getDfp().getCenter().getFightableEnemyMonsterList().add(card);

//                                                            System.out.println("더해진카드:" + card.getName());
//                                                            //물밑작업 반전소환 적의필드 업뎅티ㅡ
//
//                                                            ImageIcon img2 = new ImageIcon(card.getIcon().getImage());
//
//                                                            img2.setImage(img2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); //아마 여기서 ㅇㅈㄹ하기때문에 이미지가 shrink 되는거같다.
//                                                            JLabel previewImage2 = new JLabel(null, img2, JLabel.CENTER) {
//                                                                @Override
//                                                                protected void paintComponent(Graphics g) {
//                                                                    super.paintComponent(g);
//                                                                    Graphics2D g2 = (Graphics2D) g;
//                                                                    g2.rotate(0, img2.getIconWidth() / 2, img2.getIconHeight() / 2);
//                                                                    g2.drawImage(img2.getImage(), 0, 0, null);
//                                                                }
//                                                            };
//
//                                                            previewImage2.setAlignmentX(0.75f);
//                                                            previewImage2.setAlignmentY(Component.CENTER_ALIGNMENT);
//                                                            cardImage2.removeAll();
//                                                            cardImage2.repaint();
//                                                            cardImage2.setPreferredSize(new Dimension(60, 80));
//                                                            cardImage2.add(Box.createRigidArea(new Dimension(0, 5)));
//                                                            cardImage2.add(previewImage2);
//                                                            cardImage2.validate();
                        getRotateZeroImage(card, cardImage2);

                        //frame2 update
                        enemy.getDfp().getCenter().updateEnemyFieldGraphic(card, cardImage2, enemyZone);
                        enemy.getDfp().getCenter().addToEnemyMonsterList(card, cardImage2);
                    }

                });

            }


        }
    }

    public void goToCemetery(Card card, JPanel cardImage) {
        System.out.println("삼가 " + card.getName() + " 의 명복을 빕니다.");
        cemetery.add(card);

        JPanel zone = (JPanel) cardImage.getParent();
        zone.remove(1);
        zone.repaint();
        zone.validate();


        //묘지용이미지들

        JPanel newCardImage = card.getCardPreviewImage();
        if (zones[1][6].getComponentCount() != 1) {
            zones[1][6].remove(1);
            enemy.getDfp().getCenter().getEnemyZones()[0][0].remove(1);
        }
        zones[1][6].add(newCardImage);
        zones[1][6].repaint();
        zones[1][6].validate();


        JPanel newCardImage2 = card.getCardPreviewImage();
        enemy.getDfp().getCenter().getEnemyZones()[0][0].add(newCardImage2);
        enemy.getDfp().getCenter().getEnemyZones()[0][0].repaint();
        enemy.getDfp().getCenter().getEnemyZones()[0][0].validate();

    }

    public ArrayList<Card> getCemetery() {
        return cemetery;
    }

    public void addToEnemyMonsterZone(Card card, JPanel zone) {
        System.out.println(card.getName());
        enemyMonsterZone.put(card, zone);
    }

    public Map<Card, JPanel> getEnemyMonsterZone() {
        return enemyMonsterZone;
    }

    public void cemeteryListShow(JFrame Cemetary, ArrayList<Card> selectedCemetery) {

        JPanel cp = new JPanel();
        cp.setLayout(new BorderLayout());
        //   cp.setPreferredSize(new Dimension(50,280));
        JPanel showCards = new JPanel();

        showCards.setLayout(new FlowLayout(FlowLayout.LEFT));
        showCards.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (Card card : selectedCemetery) {
            //   System.out.println("묘지에있는놈" + card.getName());
            JPanel cardImage = card.getCardPreviewImage();

            showCards.add(cardImage);
        }

        //큰 panel과 스크롤페널, 그리고 스크롤페널이 담을 수있는 작은 페널이필요합니다.
        JScrollPane scroll = new JScrollPane();
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //
        //    scroll.setPreferredSize(new Dimension(750, 100)); //textArea에 prefferedSize하면 스크롤이안되거나 지맘대로 들쭉날쭉한 사이즈가된다. scrollPane에 적용하자.

        scroll.getViewport().add(showCards);
        cp.add(scroll, BorderLayout.CENTER);
        cp.setBorder(BorderFactory.createLineBorder(Color.black));

        Cemetary.add(cp);

    }

}