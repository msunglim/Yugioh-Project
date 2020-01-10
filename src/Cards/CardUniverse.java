package Cards;

import characters.Character;
import main.Game;

import java.io.File;


public class CardUniverse {
    private Card[] monsters = new Card[100];
    private Card[] magics = new Card[100];

    private Card[] traps = new Card[100];

    private String[] monsterCardNames = {"푸른눈의 백룡", "블랙매지션", "블랙매지션걸", "붉은눈의 흑룡", "베이비드래곤", "X헤드캐논", "랜드스타의검사", "램프의 요정 라 진", "블러드볼스", "암석거인", "암흑기사 가이아", "어둠의 어릿광대 사기", "엘프검사", "외눈거인", "철의기사 기어프리드", "커스오브드래곤", "크리보", "화염의검사"};
    private String[] monsterCardDescription = {"높은 공격력을 자랑하는 전설의 드래곤. 어떤 상대라도 분쇄해 버리는 파괴력은 상상을 초월한다.", "마법사 중에서 공격력, 수비력이 동시에 가장 높은 계급.",
            " 이 카드의 공격력은, 서로 묘지의 \"블랙 매지션\" \"매지션 오브 블랙 카오스\"의 수 × 300 올린다", "공격력은 상급 레벨. 환상의 레어 카드.",
            "새끼 드래곤이라고 얕보면 큰 코 다치기 쉽다. 그 안에 잠재된 힘은 누구도 헤아릴 수 없다.", "강력한 캐논 포를 장착한 합체 능력을 가진 몬스터. 합체와 분리를 구사하여 다양한 공격을 퍼붓는다.", "검의 팔은 미숙하지만, 신기한 능력을 지닌 요정검사.", "자신을 불러내준 주인의 소원은 무엇이든 들어주는 램프의 요정.",
            "악행의 한계를 다하고 그것을 기쁨으로 삼는 마수인. 손에 든 도끼는 항상 피로 얼룩져있다.", "암석의 거인병. 거대한 팔 공격은 대지를 뒤흔들 정도로 강력하다.", "바람보다도 빨리 달리는 말을 탄 기사. 돌진 공격에 주의.",
            "어디라 할 것 없이 아무곳에나 출현하는 어릿광대. 엉뚱한 동작으로 공격에 대응한다.", "뛰어난 검술 실력을 자랑하는 엘프. 재빠른 공격으로 적을 당황하게 만든다.", "외눈박이의 거인. 거대한 근육질의 팔로 적을 압도한다. 방심은 금물.",
            "이 카드에 장착 카드가 장착되었을 경우에 발동한다. 그 장착 카드를 파괴한다.", "사악한 드래곤. 어둠의 힘을 사용한 공격은 강력하다.", "상대 몬스터가 공격했을 경우, 그 데미지 계산시에 이 카드를 패에서 버리고 발동할 수 있다. 그 전투로 발생하는 자신에게로의 전투 데미지는 0이 된다.",
            "화염으로 둘러싼 검을 가진, 굉장한 실력의 검사. 그 검에서 내뿜는 공격은 강력하다!"
    };

    private int[] monsterCardLevel = {8,7,6,7,3,4,3,4,4,3,7,3,4,4,4,6,1,5};
    private String[] monsterCardAttribute ={"드래곤족","마법사족","마법사족","드래곤족","드래곤족","기계족","전사족","악마족","전사족","암석족","전사족","마법사족","전사족","악마족","전사족","드래곤족","악마족","전사족"};
    private String[] monsterCardType = {"光","闇","闇","闇","風","光","地","闇","闇","地","地","闇","地","闇","地","闇","闇","火"};
    private int[] ATK ={3000,2500,2000,2400,1200,1800,500,1800,1900,1300,2300,600,1400,1200,1800,2000,300,1800};
    private int[] DEF = {2500,2100,1700,1400,800,1500,1200,1600,1200,2000,2100,1500,1200,1000,1600,1500,200,1600};




    private String[] magicCardNames = {"데스 메테오", "블랙홀", "수축", "에너미 컨트롤러", "죽은자의 소생", "천사의 주사위", "하늘의 선물", "희생양"};
    private String [] magicCardDescription = {"상대 플레이어에게 1000 포인트의 데미지를 준다.","필드의 몬스터를 전부 파괴한다.",
    "필드의 앞면 표시 몬스터 1장을 대상으로 하고 발동할 수 있다. 그 몬스터의 원래 공격력은 턴 종료시까지 절반이 된다","이하의 효과에서 1개를 선택하고 발동할 수 있다.\n" +
            "●상대 필드의 앞면 표시 몬스터 1장을 대상으로 하고 발동할 수 있다. 그 상대의 앞면 표시 몬스터의 표시 형식을 변경한다.\n" +
            "●자신 필드의 몬스터 1장을 릴리스하고 상대 필드의 앞면 표시 몬스터 1장을 대상으로 하여 발동할 수 있다. 그 앞면 표시 몬스터의 컨트롤을 엔드 페이즈까지 얻는다.",
    "자신 또는 상대 묘지의 몬스터 1장을 대상으로 하고 발동할 수 있다. 그 몬스터를 자신 필드에 특수 소환한다.","주사위를 1회 던진다. 자신 필드의 몬스터의 공격력 / 수비력은, 턴 종료시까지 나온 눈의 수 × 100 올린다.",
    "양쪽 플레이어는 패가 6장이 되도록 덱에서 카드를 드로우한다.","자신 필드에 \"양 토큰\"(야수족 / 땅 / 레벨 1 / 공 0 / 수 0) 4장을 수비 표시로 특수 소환한다. 이 토큰은 어드밴스 소환을 위해서는 릴리스할 수 없다."};

    private String[] trapCardNames = {"아공간 물질 전송장치", "악마의 주사위", "파괴륜", "성스러운 방어막 거울의 힘", "매직 실린더"};
    private String[] trapCardDescription = {"자신 필드의 앞면 표시 몬스터 1장을 대상으로 하고 발동할 수 있다. 그 자신의 앞면 표시 몬스터를 엔드 페이즈까지 제외한다.","주사위를 1회 던진다. 상대 필드의 몬스터의 공격력 / 수비력은, 턴 종료시까지 나온 눈의 수 × 100 내린다."
    ,"필드의 앞면 표시 몬스터 1장을 대상으로 하고 발동할 수 있다. 그 몬스터를 파괴하고, 양쪽 플레이어는 파괴한 몬스터의 공격력만큼의 데미지를 받는다.",
    "상대 몬스터의 공격 선언시에 발동할 수 있다. 상대 필드의 공격 표시 몬스터를 전부 파괴한다.","상대 몬스터의 공격 선언시, 공격 몬스터 1장을 대상으로 하고 발동할 수 있다. 그 공격 몬스터의 공격을 무효로 하고, 그 몬스터의 공격력만큼의 데미지를 상대에게 준다."};

    private Game game;

    public CardUniverse(Game game) {
        this.game = game;
        //몬스터카드추가
        File folder = new File("data/images/cards/Monsters");

        File[] filesDefault = folder.listFiles();


        int u = 1;
        String cardImageURL = "data/images/cards/Monsters/" + u + ".PNG";
        for (int j = 0; j < filesDefault.length; j++) {
            cardImageURL = "data/images/cards/Monsters/" + u + ".PNG";
            monsters[j] = Card.getCard(monsterCardNames[j], cardImageURL, monsterCardDescription[j],monsterCardLevel[j],monsterCardAttribute[j],monsterCardType[j],ATK[j],DEF[j]);
            u++;
        }

        //마법카드추가
        folder = new File("data/images/cards/Magic");
        u = 1;
        cardImageURL = "data/images/cards/Magic/" + u + ".PNG";
        filesDefault = folder.listFiles();
        for (int j = 0; j < filesDefault.length; j++) {
            cardImageURL = "data/images/cards/Magic/" + u + ".PNG";
            magics[j] = Card.getCard(magicCardNames[j], cardImageURL, 1, magicCardDescription[j]);
            u++;
        }

        //함정카드추가
        folder = new File("data/images/cards/Trap");
        u = 1;
        cardImageURL = "data/images/cards/Trap/" + u + ".PNG";
        filesDefault = folder.listFiles();
        for (int j = 0; j < filesDefault.length; j++) {
            cardImageURL = "data/images/cards/Trap/" + u + ".PNG";
            traps[j] = Card.getCard(trapCardNames[j], cardImageURL, 2, trapCardDescription[j]);
            u++;
        }


    }

    public Card getMonsterCard(int i) {
        return monsters[i];
    }

    public Card getMagicCard(int i) {
        return magics[i];
    }

    public Card getTrapCard(int i) {
        return traps[i];
    }


    public void assignMonsterDeck(Character character, int i) {

        File folder = new File("data/images/cards/Monsters");

        File[] filesDefault = folder.listFiles();


        String cardImageURL = "data/images/cards/Monsters/" + i + ".PNG";

                 character.addCard(Card.getCard(monsterCardNames[i-1],
                         cardImageURL,
                         monsterCardDescription[i-1],
                         monsterCardLevel[i-1],
                         monsterCardAttribute[i-1],
                         monsterCardType[i-1]
                         ,ATK[i-1],
                         DEF[i-1]));


    }

    public void assignMagicDeck(Character character, int i) {

//        for (int k : i) {
//            character.addCard(magics[k - 1]);
//        }
        File folder = new File("data/images/cards/Monsters");

        File[] filesDefault = folder.listFiles();


        String cardImageURL = "data/images/cards/Magic/" + i + ".PNG";

        character.addCard(Card.getMagicCard(magicCardNames[i-1],
                cardImageURL,
                i,magicCardDescription[i-1]
               ));

    }

    public void assignTrapDeck(Character character, int... i) {

        for (int k : i) {

            character.addCard(traps[k - 1]);
        }


    }
}
