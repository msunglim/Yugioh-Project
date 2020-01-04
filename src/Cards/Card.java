package Cards;

import javax.swing.*;
import java.awt.*;

public abstract class Card {

    protected int cardType;
    protected String name;
    protected ImageIcon img;
    protected JLabel image;
    protected String description;

    private JPanel cardBack;
    private static Card card;
    private ImageIcon bimg;


    public Card() {
        // Card card = new Monster();
    }

    public abstract String getName();

    public abstract JLabel getImage();

    public abstract ImageIcon getIcon();

    public abstract String getDescription();

    public static Card getCard() {

        card = new Monster();
        return card;
    }

    //나중에 레벨로 차별화할꺼임. 속성 종족 등.
    public static Card getCard(String name, String imgURL, String description, int level, String attribute, String type, int ATK, int DEF) {
        card = new Monster(name, imgURL, description, level, attribute, type, ATK, DEF);


        return card;
    }

    public ImageIcon getBackIcon() {
        return bimg;
    }

    //descriptin 넣으면되겠다. 속성이랑도. 지금은 몬스터 작업중이라.
    public static Card getCard(String name, String imgURL, int type, String description) {


        switch (type) {


            case 1:
                card = new Magic(name, imgURL, description);

                return card;

            case 2:

                card = new Trap(name, imgURL, description);
                return card;

            default:
                card = new Magic(name, imgURL, description);
                return card;

        }

    }

    public JPanel getCardBack() {

        cardBack = new JPanel();

        //.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        cardBack.setPreferredSize(new Dimension(60, 80));
        cardBack.setLayout(new BorderLayout());
        bimg = new ImageIcon("data/images/cards/back.PNG");
        bimg.setImage(bimg.getImage().getScaledInstance(60, 80, Image.SCALE_DEFAULT));

        JLabel back = new JLabel(bimg);

        cardBack.add(back, BorderLayout.CENTER);
        cardBack.setBorder(BorderFactory.createLineBorder(Color.black));

        return cardBack;
    }

    public String toString() {
        return getName();
    }

    public JPanel getCardPreviewImage() {
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(60, 80));

        switch (cardType) {
            case 0:
                p.setBackground(Color.orange);

                break;
            case 1:
                p.setBackground(Color.green);
                break;
            case 2:
                p.setBackground(Color.pink);

                break;
            default:
                p.setBackground(Color.orange);

                break;
        }
        JLabel previewImage = new JLabel(new ImageIcon(getIcon().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        previewImage.setAlignmentY(Component.CENTER_ALIGNMENT);


        p.add(Box.createRigidArea(new Dimension(0, 5)));
        p.add(previewImage);
        p.setBorder(BorderFactory.createLineBorder(Color.black));


        return p;

    }

    public abstract JPanel getCardImage();

    public int getCardType() {
        return cardType;
    }

}
