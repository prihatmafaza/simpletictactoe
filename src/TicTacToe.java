import java.util.*;

public class TicTacToe {

    static ArrayList<Integer> playerPositions = new ArrayList<>();
    static ArrayList<Integer> botPositions = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter The Dimension of TicTacToe : ");
        int dimension = scan.nextInt();
        List<List<String>> gameBoards = createGameBoard(dimension);
        printGameBoard(gameBoards);


        while (true) {
            System.out.println("Enter your placement : ");
            int playerPos = scan.nextInt();
            while (playerPositions.contains(playerPos) || botPositions.contains(playerPos)){
                System.out.println("Position Taken! Please Input New Position ");
                playerPos = scan.nextInt();
            }
            placePieceDynamic(gameBoards,playerPos,"player",dimension);
            String result = checkWinner(dimension);
            if (result.length() > 0){
                System.out.println(result);
                printGameBoard(gameBoards);
                break;
            }

            Random random = new Random();
            int botPos = random.nextInt(dimension * dimension) + 1;
            while (playerPositions.contains(botPos) || botPositions.contains(botPos)){
                botPos = random.nextInt(dimension * dimension) + 1;
            }
            placePieceDynamic(gameBoards,botPos,"bot",dimension);
            result = checkWinner(dimension);
            if (result.length() > 0){
                System.out.println(result);
                printGameBoard(gameBoards);
                break;
            }

            printGameBoard(gameBoards);

        }


    }

    public static List<List<String>> createGameBoard(Integer dimension ){
        //CREATE GAME BOARD
        List<List<String>> gameBoardList = new ArrayList<>();

        Integer realDimension = dimension * 2 - 1;

        for (int i = 1 ; i <= realDimension ; i ++){
            List<String> newChar = new ArrayList<>();
            for (int x = 1; x <= realDimension; x ++){
                if (x%2 == 0 && i%2 == 0){
                    newChar.add("+");
                } else if (x%2 == 0 && i%2 != 0){
                    newChar.add("|");
                } else if (x%2 != 0 && i%2 == 0 ){
                    newChar.add("-");
                } else if (x%2 != 0 && i%2 != 0 ){
                    newChar.add(" ");
                }
            }
            gameBoardList.add(newChar);
        }


        return gameBoardList;
    }


    public static void printGameBoard(List<List<String>> gameBoards){
        //SHOW GAMEBOARD
        for(List<String> row : gameBoards){
            for (String c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }


    public static void placePieceDynamic(List<List<String>> gameBoard, int pos , String user , Integer dimension){
        //PLACE PIECE
        String symbol = " ";

        if (user.equals("player")){
            symbol = "X";
            playerPositions.add(pos);
        } else if(user.equals("bot")){
            symbol = "O";
            botPositions.add(pos);
        }

        Integer realColumn = ((pos-1) / dimension);
        Integer realRow = (pos-1)%dimension;
        Integer realDimension = dimension * 2 - 1;
        List<Integer> indexRowBoard = new ArrayList<>();
        List<Integer> indexColBoard = new ArrayList<>();

        for (int i = 0; i < realDimension; i ++){
            if (i%2 == 0){
                indexColBoard.add(i);
            }
        }

        for (int x = 0; x < realDimension; x++){
            if (x%2 == 0){
                indexRowBoard.add(x);
            }
        }

        gameBoard.get(indexRowBoard.get(realColumn)).set(indexColBoard.get(realRow),symbol);

    }

    public static String checkWinner(Integer dimension){

        if (playerPositions.size() + botPositions.size() == (dimension * dimension)) return "DRAW !";

        //Winning Condition

        // Horizontal Winner
        for (int i = 1; i <= dimension * dimension; i+=dimension){
            List<Integer> listWin = new ArrayList<>();
            for (int x = i; x < dimension + i; x++){
                listWin.add(x);
            }
            if (playerPositions.containsAll(listWin)) return "You Won!";
            if (botPositions.containsAll(listWin)) return "You Lose!";
        }


        //Vertical Winner LEFT Side
        List<Integer> listWin = new ArrayList<>();
        for (int i = 1; i <= dimension * dimension; i+=dimension){
            listWin.add(i);
        }
        if (playerPositions.containsAll(listWin)) return "You Won!";
        if (botPositions.containsAll(listWin)) return "You Lose!";

        //Vertical Winner Right Side
        listWin = new ArrayList<>();
        for (int i = dimension; i <= dimension * dimension; i+=dimension){
            listWin.add(i);
        }
        if (playerPositions.containsAll(listWin)) return "You Won!";
        if (botPositions.containsAll(listWin)) return "You Lose!";


        //Diagonal Winner From  Top Left to Bottom Right
        listWin = new ArrayList<>();
        for (int i = 1; i <= dimension * dimension; i +=dimension+1){
            listWin.add(i);
        }
        System.out.println(listWin);
        if (playerPositions.containsAll(listWin)) return "You Won!";
        if (botPositions.containsAll(listWin)) return "You Lose!";


        //Diagonal Winner From  Top Right to Bottom LEFT
        listWin = new ArrayList<>();
        for (int i = dimension; i < dimension * dimension; i+=dimension-1){
            listWin.add(i);
        }
        if (playerPositions.containsAll(listWin)) return "You Won!";
        if (botPositions.containsAll(listWin)) return "You Lose!";

        return "";
    }

}
