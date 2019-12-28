package Cards;

import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.function.BinaryOperator;

public class Monster extends Card {


    private int level;
    private String attribute; //종족
    private String type; //속성
    private int ATK;
    private int DEF;

    public Monster() {
        name = "푸른눈의 백룡";
        img = new ImageIcon("data/images/sampleMonster.png");
        img.setImage(img.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        image = new JLabel(img);


        description = "높은 공격력을 자랑하는 전설의 드래곤. 어떤 상대라도 분쇄해 버리는 파괴력은 상상을 초월한다";
    }

    public Monster(String name, String imgURL, String description, int level, String attribute, String type, int ATK, int DEF) {
        this.name = name;
        img = new ImageIcon(imgURL);
        img.setImage(img.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        image = new JLabel(img);
        cardType = 0;
        this.level = level;
        this.description = description;
        this.type = type;
        this.attribute = attribute;
        this.ATK = ATK;
        this.DEF = DEF;
    }


    public String getName() {
        return name;
    }

    public JLabel getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public ImageIcon getIcon() {
        return img;
    }

    public JPanel getCardImage() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        //   p.setPreferredSize(new Dimension(350, 450)); 이걸설정해버리면 여기에 내용물을 꽉채울려는 경향이있다.

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(300, 40));

        JLabel title = new JLabel(getName());
        title.setPreferredSize(new Dimension(300, 40));

        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        titlePanel.add(title, BorderLayout.WEST);

        JLabel typeLabel = new JLabel(type);
        //   typeLabel.setPreferredSize(new Dimension(30, 20));
        typeLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        typeLabel.setAlignmentX(0.5f);

        titlePanel.add(typeLabel, BorderLayout.EAST);


        p.add(titlePanel);

        p.add(Box.createRigidArea(new Dimension(0, 3)));

        //level
        JPanel levelPanel = new JPanel();
        // levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.X_AXIS)); 오른쪽정렬할꺼면 차라리 이렇게하라!@!!@!!
        levelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        levelPanel.setPreferredSize(new Dimension(300, 30));

        //     levelPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        for (int a = 0; a < level; a++) {
            JLabel levelLabel = new JLabel("★");
            levelLabel.setBorder(BorderFactory.createLineBorder(Color.orange));

            // levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            levelPanel.add(levelLabel);
        }
        p.add(levelPanel);


        p.add(Box.createRigidArea(new Dimension(0, 3)));


        ImageIcon bigImg = new ImageIcon(img.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
        JLabel i = new JLabel(bigImg);
        i.setAlignmentX(Component.CENTER_ALIGNMENT);
        i.setBorder(BorderFactory.createLineBorder(Color.black));
        p.add(i);
        p.add(Box.createRigidArea(new Dimension(0, 3)));

        //밑에
        JPanel bottom = new JPanel();

        bottom.setPreferredSize(new Dimension(300,125));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        //간격
        bottom.add(Box.createRigidArea(new Dimension(0, 1)));

        // bottom.setPreferredSize(new Dimension(300, 250));

        //종족
        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new GridLayout(1, 1)); //이걸해야 align이됨... 뭐든 레이어가 필요하구나.
        attributePanel.setPreferredSize(new Dimension(300, 15));


        JLabel attributeLabel = new JLabel("[" + attribute + "]", SwingConstants.LEFT);
        //   attributeLabel.setHorizontalAlignment(JLabel.LEFT)

        attributePanel.add(attributeLabel);
        //간격

        bottom.add(attributePanel);


        JTextArea des2 = new JTextArea(getDescription());
        //des2.setText(getDescription());
        des2.setText(description);
        // des2.setPreferredSize(new Dimension(300, 60));
        // des2.setBounds(0,0,300,200); 전부 work안함~!
        des2.setSize(new Dimension(300, 60));
        des2.setLineWrap(true);
        des2.setEditable(false);
        des2.setWrapStyleWord(false);
        //des2.setBorder(BorderFactory.createLineBorder(Color.black));


        JScrollPane scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(300, 60)); //textArea에 prefferedSize하면 스크롤이안되거나 지맘대로 들쭉날쭉한 사이즈가된다. scrollPane에 적용하자.
        des2.setCaretPosition(0); //scrollPane에서 초점을 처음부분으로 맞춰줌. 이거없음 스크롤페인은 textArea에 꽁무니만 쫒을것.

        scroll.getViewport().add(des2);
        bottom.add(scroll);
        bottom.setBorder(BorderFactory.createLineBorder(Color.black));


        //atk 와 def의 약자
        JPanel adPanel = new JPanel();
        adPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel ad = new JLabel("ATK/ " + ATK + "  DEF/" + DEF);
        adPanel.add(ad);

        bottom.add(adPanel);
        p.add(bottom);

        return p;
    }
}
