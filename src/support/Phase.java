package support;

import main.Game;

public abstract class Phase {


    private static Phase phase;
    protected static Game game;
    protected static Player player;
    protected static Player enemy;

    protected static String phaseName;
    protected static boolean mainPhase2;
    //abstract에서 이따구로하면안된다...
//    public Phase(){
//
//    }
//    public Phase(Game game, Player player){
//        this.game = game;
//        this.player = player;
//        //시작은 항상 스텐바이.ㅎ ㅏ이큐
//        this.phase = new StandbyPhase();
//    }

    public static Phase getPhase(Game game, Player player, Player enemy) {

        phase = new StandbyPhase(game, player, enemy);
        System.out.println("스텐바이완료했구요 드로우가겠습니다");

        ((StandbyPhase) (phase)).goDraw();
        System.out.println("드로우완료했구요 메인1가겠습니다.");
        ((DrawPhase) (phase)).goMain();


        //  setPhase(new StandbyPhase(game, player, enemy));

        return ((Phase) phase);
    }

    public String toString() {
        return phaseName;
    }

    public static void setPhase(Phase newPhase) {


        phase = newPhase;

        //   game.setPhase(newPhase);
    }

    public Phase getCurrentPhase() {

        return phase;
    }
    public boolean isMainPhase(){
        return (phase instanceof MainPhase);
    }

    public boolean isMainPhase2(){
        return mainPhase2;
    }
}
