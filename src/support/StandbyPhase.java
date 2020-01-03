package support;

import main.Game;

public class StandbyPhase extends Phase{


    public StandbyPhase(Game game, Player player, Player enemy){
        this.game = game;
        this.player = player;
        this.enemy = enemy;


        player.setMyTurn(true);
        enemy.setMyTurn(false);
        enemy.getDfp().getCenter().emptyUnchangableList();
        mainPhase2 = false;
        phaseName = "스텐바이";
        System.out.println("스텐바이 페이즈입니다.");


     }

     public void goDraw(){
         setPhase(new DrawPhase(game, player, enemy));
     }




}
