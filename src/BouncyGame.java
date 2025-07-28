import javazoom.jl.decoder.JavaLayerException;

public class BouncyGame {
    private char[][] board;
    private int rows;
    private int cols;
    private int ballX = 0;
    private int ballY = 0;
    private int exitX = 0;
    private int exitY = 0;
    private int horizontalRodCount = 3;
    private int verticalRodCount = 3;
    private boolean isStageClear = false;
    private int stage = 0;
    private Game game;
    private Sound sound;
    private boolean isJumpActive = false;
    private int upCount = 5;

    public BouncyGame(int rows, int cols, Game game,Sound sound) {
        this.rows = rows;
        this.cols = cols;
        this.game = game;
        this.sound = sound;
    }

    public void setBoard() {
        board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i % 2 == 0 && j != 0 &&  j != cols - 1) {
                    board[i][j] = '-';
                } else {
                    if (j == 0 || j == cols - 1) {
                        board[i][j] = '|';
                    } else {
                        board[i][j] = ' ';
                    }
                }
            }
            int spikeCount = 0;
            int indexCol = 0;
            int randomDeleteRodCount = 0;
            int randomRow = 0;
            if (i != 0 && i != rows - 1 && i % 2 == 0) {
                do {
                    spikeCount = (int) (Math.random() * cols / 6) + (cols / 3);
                } while (spikeCount == 30);
                spikeCount /= 4;
                for (int j = 0; j < spikeCount; j++) {
                    do {
                        indexCol = (int) (Math.random() * cols);
                    } while (board[i - 1][indexCol] != ' ');
                    board[i - 1][indexCol] = '˄';
                }

                do {
                    randomDeleteRodCount = (int) (Math.random() * cols / 6) + (cols / 3);
                    randomDeleteRodCount /= 4;
                } while (randomDeleteRodCount > spikeCount);

                for (int j = 0; j < randomDeleteRodCount; j++) {
                    do {
                        randomRow = (int) (Math.random() * cols);
                    } while (board[i][randomRow] == ' ' || randomRow == cols - 1 || randomRow == 0 || board[i - 1][randomRow] == '˄');
                    board[i][randomRow] = ' ';
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void information() {
        game.cn.getTextWindow().setCursorPosition(cols + 1,0);
        System.out.print("Current stage: " + stage);
        game.cn.getTextWindow().setCursorPosition(cols + 1,1);
        System.out.print("Horizontal rod count: ");
        for (int i = 0; i < horizontalRodCount; i++) {
            System.out.print('-');
            if (i < horizontalRodCount - 1) {
                System.out.print(',');
            }
        }
        game.cn.getTextWindow().setCursorPosition(cols + 1,2);
        System.out.print("Vertical rod count: ");
        for (int i = 0; i < verticalRodCount; i++) {
            System.out.print('|');
            if (i < verticalRodCount - 1) {
                System.out.print(',');
            }
        }
        game.cn.getTextWindow().setCursorPosition(0,0);
    }

    public void start() {
        setBoard();
        ballX = 1;
        ballY = board.length - 2;
        exitX = board[0].length - 2;
        exitY = 1;
        board[ballY][ballX] = '●';
        board[exitY][exitX] = 'X';
        while (true) {
            game.clear();
            printBoard();
            information();
            mouse();
            move();
            jump();
            if (isStageClear) {
                sound.playSound("exit");
                isStageClear = false;
                stage++;
                setBoard();
                ballX = 1;
                ballY = board.length - 2;
                exitX = board[0].length - 2;
                exitY = 1;
                board[ballY][ballX] = '●';
                board[exitY][exitX] = 'X';
                horizontalRodCount = 4;
                verticalRodCount = 5;
                isJumpActive = false;
                upCount = 5;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void mouse() {
        if (Game.isMousePressed()) {
            if ((game.getMousex() < cols - 2 && game.getMousex() > 0) && (game.getMousey() < rows - 2 && game.getMousey() > 0)) {
                if (game.getMousex() % 2 == 0 && game.getMousey() % 2 == 1) {
                    if (verticalRodCount > 0) {
                        if (board[game.getMousey()][game.getMousex()] == ' ') {
                            board[game.getMousey()][game.getMousex()] = '|';
                            verticalRodCount--;
                        }
                    }
                }
                if (game.getMousex() % 2 == 1 && game.getMousey() % 2 == 0) {
                    if (horizontalRodCount > 0) {
                        if (board[game.getMousey()][game.getMousex()] == ' ') {
                            board[game.getMousey()][game.getMousex()] = '-';
                            horizontalRodCount--;
                        }
                    }
                }
            }
            Game.setMousePressed(false);
        }
    }

    public void jump() {
        if (Game.isBouncyGameJumpPressed()) {
            if (board[ballY + 1][ballX] == '-' || board[ballY + 1][ballX] == '|') {
                // sesi aç
                sound.playSound("jump");
                isJumpActive = true;
            }
            Game.setBouncyGameJumpPressed(false);
        }
        if (isJumpActive) {
            //zıplama
            if (board[ballY - 1][ballX] != '-' && board[ballY - 1][ballX] != '|') {
                if (upCount > 0) {
                    board[ballY][ballX] = ' ';
                    ballY--;
                    board[ballY][ballX] = '●';
                    if (ballY == exitY && ballX == exitX) {
                        isStageClear = true;
                    }
                    upCount--;
                } else {
                    upCount = 5;
                    isJumpActive = false;
                }
            } else {
                upCount = 5;
                isJumpActive = false;
            }
        } else {
            if (board[ballY + 1][ballX] != '-' && board[ballY + 1][ballX] != '|') {
                board[ballY][ballX] = ' ';
                ballY++;
                board[ballY][ballX] = '●';
            }
        }

    }

    public void move() {
        if (Game.isBouncyGameRightPressed()) {
            if (ballX + 2 < cols && board[ballY][ballX + 1] != '-' && board[ballY][ballX + 1] != '|') {
                board[ballY][ballX] = ' ';
                ballX++;
                if (ballY == exitY && ballX == exitX) {
                    isStageClear = true;
                }
                board[ballY][ballX] = '●';
            }
            Game.setBouncyGameRightPressed(false);
        }
        if (Game.isBouncyGameLeftPressed()) {
            if (ballX - 1 > 0 && board[ballY][ballX - 1] != '-' && board[ballY][ballX - 1] != '|') {
                board[ballY][ballX] = ' ';
                ballX--;
                if (ballY == exitY && ballX == exitX) {
                    isStageClear = true;
                }
                board[ballY][ballX] = '●';
            }
            Game.setBouncyGameLeftPressed(false);
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

}
