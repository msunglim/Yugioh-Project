package Cards.Effect;

import Cards.Card;
import support.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Destroy implements Destroyable {

    //여기 페널은 존이시다.
private int cardListLength;
private int enemyCardListLength;

    private    Map<Card, JPanel> destroyList = new HashMap<>(cardListLength);
    private Map<Card, JPanel> destroyEnemyList = new HashMap<>(enemyCardListLength);

    private Card[] cardList ;
    private JPanel[] zoneList ;

    public void destroy(Player player, Player enemy) {
        Card[] cardList = new Card[cardListLength];
        JPanel[] zoneList = new JPanel[cardListLength];
        int index = 0;
        for (Map.Entry<Card, JPanel> entry : destroyList.entrySet()) {
            System.out.println(index);
            System.out.println(cardList.length);
            cardList[index] = entry.getKey();
            zoneList[index] = entry.getValue();
            index++;


        }

        for (int i = 0; i < index; i++) {
            //발동자의 필드에서 몬스터를 전부제거하고 발동자의 frame을 새로고침.

            remove(zoneList[i]);

            //묘지로 보내는 의식
            player.getDfp().getCenter().getCemetery().add(cardList[i]);
            player.getDfp().getCenter().addToCemetery(cardList[i]);


            //적의 시야에서 발동자의 필드를 repaint하기.

            enemy.getDfp().getCenter().getEnemyMonsterZone().get(cardList[i]).remove(1);
            enemy.getDfp().getCenter().getEnemyMonsterZone().get(cardList[i]).repaint();
            enemy.getDfp().getCenter().getEnemyMonsterZone().get(cardList[i]).validate();


            //내꺼파괴하니까 겁내햇ㄱ깔리네 ㅋㅋ
            enemy.getDfp().getCenter().getEnemyMonsterList().remove(cardList[i]);
            enemy.getDfp().getCenter().getFightableEnemyMonsterList().remove(cardList[i]);
            // enemy.getDfp().getCenter().getEnemyMonsterZone().remove(entry.getKey());

            player.getDfp().getCenter().getFightableList().remove(cardList[i]);
            player.getDfp().getCenter().getMyFieldMonsterZone().remove(cardList[i]);
            //대충 이정도 코드가 내필드에서 몬스터를 제거했을때 상대방필드와 묘지에도 넣는 작업인듯.

            player.getDfp().getCenter().setNumberOfMonsters(player.getDfp().getCenter().getNumberOfMonsters() - 1);
            enemy.getDfp().getCenter().setNumberOfMonstersOfEnemy(player.getDfp().getCenter().getNumberOfMonsters());

        }
        index = 0;
        cardList = new Card[enemyCardListLength];
        zoneList = new JPanel[enemyCardListLength];
        for (Map.Entry<Card, JPanel> entry : destroyEnemyList.entrySet()) {
            cardList[index] = entry.getKey();
            zoneList[index] = entry.getValue();
            index++;
        }
        for(int i = 0; i < index; i++){
      //  System.out.println("전쟁의 서2막" + cardList[i].getName());
       // System.out.println("마하기전" + destroyEnemyList.entrySet());
        remove(zoneList[i]);

        enemy.getDfp().getCenter().getCemetery().add(cardList[i]);
        enemy.getDfp().getCenter().addToCemetery(cardList[i]);

        player.getDfp().getCenter().getEnemyMonsterZone().get(cardList[i]).remove(1);
        player.getDfp().getCenter().getEnemyMonsterZone().get(cardList[i]).repaint();
        player.getDfp().getCenter().getEnemyMonsterZone().get(cardList[i]).validate();

        player.getDfp().getCenter().getEnemyMonsterList().remove(cardList[i]);
        player.getDfp().getCenter().getFightableEnemyMonsterList().remove(cardList[i]);
        //이거원래포함되는데 위에서 하니까 여기선생략한다. player.getDfp().getCenter().getEnemyMonsterZone().remove(entry.getKey());

        enemy.getDfp().getCenter().getFightableList().remove(cardList[i]);
        enemy.getDfp().getCenter().getMyFieldMonsterZone().remove(cardList[i]);
        //대충 이정도 코드가 내필드에서 몬스터를 제거했을때 상대방필드와 묘지에도 넣는 작업인듯.
        enemy.getDfp().getCenter().setNumberOfMonsters(enemy.getDfp().getCenter().getNumberOfMonsters() - 1);
        player.getDfp().getCenter().setNumberOfMonstersOfEnemy(enemy.getDfp().getCenter().getNumberOfMonsters());
       // System.out.println("마하고나서" + destroyEnemyList.entrySet());
    }


}

    private void remove(JPanel jPanel) {

        jPanel.remove(1);
        jPanel.repaint();
        jPanel.validate();


    }

    public void setDestroyList(Map<Card, JPanel> list) {
        //System.out.println("자기필드 뿌실꺼" + list);
        destroyList = list;
    }

    public void setDestroyEnemyList(Map<Card, JPanel> list) {
    //    System.out.println("남의필드 뿌실꺼" + list);
        destroyEnemyList = list;
    }

    public void setCardListLength(int l){
        cardListLength = l;

    }
    public void setEnemyCardListLength(int l){
        enemyCardListLength = l;
    }
}
