package Cards;

import support.Player;

import javax.swing.*;
import java.awt.*;

public class Trap extends Card {
    public Trap(String name, String imgURL,String description){
        this.name = name;
        img = new ImageIcon(imgURL);
        img.setImage(img.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT));
        image = new JLabel(img);
        cardType= 2;
        this.description = description;
    }
    public String getName(){
        return name;
    }
    public JLabel getImage(){
        return image;
    }
    public String getDescription(){
        return description;
    }
    public ImageIcon getIcon(){
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

        JLabel typeLabel = new JLabel("罠");
       //   typeLabel.setPreferredSize(new Dimension(30, 20));
        typeLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        typeLabel.setAlignmentX(0.5f);

        titlePanel.add(typeLabel, BorderLayout.EAST);

        p.add(titlePanel);

        p.add(Box.createRigidArea(new Dimension(0, 3)));


        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        levelPanel.setPreferredSize(new Dimension(300, 30));


        levelPanel.add(new JLabel("[함정카드]"));

        p.add(levelPanel);


        ImageIcon bigImg = new ImageIcon(img.getImage().getScaledInstance(300,300, Image.SCALE_DEFAULT));
        JLabel i = new JLabel(bigImg);
        i.setAlignmentX(Component.CENTER_ALIGNMENT);
        i.setBorder(BorderFactory.createLineBorder(Color.black));
        p.add(i);
        p.add(Box.createRigidArea(new Dimension(0, 3)));

        JPanel bottom = new JPanel();

        bottom.setPreferredSize(new Dimension(300,125));
        JTextArea des2 = new JTextArea(getDescription());
        //des2.setText(getDescription());
        des2.setText(description);
        //  des2.setPreferredSize(new Dimension(300, 100));
        // des2.setBounds(0,0,300,200); 전부 work안함~!
         des2.setSize(new Dimension(300,60)); //set size는 임시의 사이즈고 필요시 scrollpane의 크기를 사용한다.
        des2.setLineWrap(true);
        des2.setEditable(false);
        des2.setWrapStyleWord(false);
      //  des2.setBorder(BorderFactory.createLineBorder(Color.black));



        JScrollPane scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(300,60)); //textArea에 prefferedSize하면 스크롤이안되거나 지맘대로 들쭉날쭉한 사이즈가된다. scrollPane에 적용하자.
        des2.setCaretPosition(0); //scrollPane에서 초점을 처음부분으로 맞춰줌. 이거없음 스크롤페인은 textArea에 꽁무니만 쫒을것.

        scroll.getViewport().add(des2);
        bottom.add(scroll);
        bottom.setBorder(BorderFactory.createLineBorder(Color.black));
        p.add(bottom);

        return p;
    }

    public void activate(Player player, Player enemey){
        System.out.println("함정발동이다");
    }
}
