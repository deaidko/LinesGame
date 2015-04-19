import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class TheGame extends JFrame {
    public TheGame() {
        super("Lines15");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GamePanel());
        pack();
    }

    public static void main(String[] args) {
        TheGame app = new TheGame();
        app.setVisible(true);
        app.setResizable(false);
    }
}

class GamePanel extends JPanel {
    private int BoardSize = 9;
    private int WindowSize = 700; // размер окна
    private int Offset = 5; // отступ от края окна
    private int CellSize = (WindowSize - 2 * Offset) / BoardSize;
    private int Cell_X, Cell_Y; // номера клеток
    private int Matrix[][] = new int[BoardSize][BoardSize]; // матрица значений
    private int Sel_X, Sel_Y; // координаты выбранного элемента
    private int State = 0; // состояние
    private int BallCount = 3; // количество выпадающих шаров
    public int InARowCount = 5; // количество шаров в ряд

    Random Rnd = new Random();

    private Image nul, red, blu, yel, grn, pnk, lbl, drd, drd1, red1, blu1, grn1, yel1, pnk1, lbl1;

    public GamePanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));

        nul = getToolkit().getImage("img/nul.png");
        red = getToolkit().getImage("img/red.png");
        grn = getToolkit().getImage("img/grn.png");
        blu = getToolkit().getImage("img/blu.png");
        yel = getToolkit().getImage("img/yel.png");
        pnk = getToolkit().getImage("img/pnk.png");
        lbl = getToolkit().getImage("img/lbl.png");
        drd = getToolkit().getImage("img/drd.png");

        red1 = getToolkit().getImage("img/red1.png");
        grn1 = getToolkit().getImage("img/grn1.png");
        blu1 = getToolkit().getImage("img/blu1.png");
        yel1 = getToolkit().getImage("img/yel1.png");
        pnk1 = getToolkit().getImage("img/pnk1.png");
        lbl1 = getToolkit().getImage("img/lbl1.png");
        drd1 = getToolkit().getImage("img/drd1.png");

        for (int i = 0; i < BoardSize; i++) {
            for (int k = 0; k < BoardSize; k++) {
                Matrix[i][k] = 0;
            }
        }
        AddBalls(BallCount);

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                Cell_X = (e.getX() - Offset) / CellSize;
                Cell_Y = (e.getY() - Offset) / CellSize;

                switch (State) {
                    case 0: // клик без выбранного элемента
                        if (Matrix[Cell_X][Cell_Y] < 10) { // если элемент уже не выбран
                            if (Matrix[Cell_X][Cell_Y] > 0) { // если элемент не пустой
                                Sel_X = Cell_X; // запоминаем координаты
                                Sel_Y = Cell_Y;
                                Matrix[Cell_X][Cell_Y] += 10; // выделяем его
                                State = 1; // меняем состояние
                            }
                        }
                        break;

                    case 1: // клик при выбранном элементе
                        if (Matrix[Cell_X][Cell_Y] == 0) { // если клик на пустую ячейку
                            Matrix[Cell_X][Cell_Y] = Matrix[Sel_X][Sel_Y] - 10; // копируем цвет
                            Matrix[Sel_X][Sel_Y] = 0; // опустошаем предыдущую ячейку
                            Check(Matrix[Cell_X][Cell_Y]);
                            if (State!=2) // если нет удалений
                                AddBalls(BallCount); // добавляем шары
                            State = 0; // меняем состояние
                        } else { // клик на непустую ячейку (меняем выбираемый элемент)
                            if (Sel_X == Cell_X && Sel_Y == Cell_Y) {// Если клик на ту же ячейку
                                Matrix[Sel_X][Sel_Y] -= 10; // убираем пометку "выбрано"
                                State = 0; // Меняем состояние
                            } else {
                                Matrix[Sel_X][Sel_Y] -= 10; // убираем пометку "выбрано" у одного
                                Sel_X = Cell_X; // запоминаем
                                Sel_Y = Cell_Y;
                                Matrix[Sel_X][Sel_Y] += 10; // и ставим пометку "выбрано"
                            }
                        }
                        break;
                }
                repaint(); // перерисовываем
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(WindowSize, WindowSize);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DrawMatrix(Matrix, g);
    }

    public void DrawMatrix(int Matrix[][], Graphics g) {
        for (int i = 0; i < BoardSize; i++) {
            for (int k = 0; k < BoardSize; k++) {
                switch (Matrix[i][k]) {
                    case 0:
                        g.drawImage(nul, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 1:
                        g.drawImage(red, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 2:
                        g.drawImage(grn, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 3:
                        g.drawImage(blu, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 4:
                        g.drawImage(yel, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 5:
                        g.drawImage(pnk, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 6:
                        g.drawImage(lbl, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 7:
                        g.drawImage(drd, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 11:
                        g.drawImage(red1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 12:
                        g.drawImage(grn1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 13:
                        g.drawImage(blu1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 14:
                        g.drawImage(yel1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 15:
                        g.drawImage(pnk1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 16:
                        g.drawImage(lbl1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                    case 17:
                        g.drawImage(drd1, Offset + CellSize * i, Offset + CellSize * k, CellSize, CellSize, this);
                        break;
                }
            }
        }
    }

    void AddBalls(int BallsCount) { // заполнение методом Монте-Карло :D
        int Color;
        for (int i = 0; i < BallsCount; i++) {
            for (int j = 0; j < 1000; j++) { // пока не поседеет
                Cell_X = Rnd.nextInt(BoardSize);
                Cell_Y = Rnd.nextInt(BoardSize);

                if (Matrix[Cell_X][Cell_Y] == 0) { // если нашел пустую
                    Color = Rnd.nextInt(7) + 1; // рандомный цвет
                    Matrix[Cell_X][Cell_Y] = Color;
                    Check(Color);
                    break;
                }
            }
        }
    }

    void Check(int BallColor) { // Функция для проверки шаров при вставке
        int Lft = Cell_Y, Rgt = Cell_Y; // переменные для хранения границ удаления по горизонтали
        int Up = Cell_X, Dwn = Cell_X;   // переменные для хранения границ удаления по вертикали

        while (Rgt < BoardSize && Matrix[Cell_X][Rgt] == BallColor) Rgt++; // вправо
        while (Lft >= 0 && Matrix[Cell_X][Lft] == BallColor) Lft--; // влево
        while (Dwn < BoardSize && Matrix[Dwn][Cell_Y] == BallColor) Dwn++; //вниз
        while (Up >= 0 && Matrix[Up][Cell_Y] == BallColor) Up--; // вверх

        int Ux1 = Cell_X, Uy1 = Cell_Y; // переменные для верхней левой границы
        int Dx1 = Cell_X, Dy1 = Cell_Y; // переменные для нижней правой границы
        while (Ux1 >= 0 && Uy1 >= 0 && Matrix[Ux1][Uy1] == BallColor) { // находим верхнюю левую
            Ux1--;
            Uy1--;
        }
        while (Dx1 < BoardSize && Dy1 < BoardSize && Matrix[Dx1][Dy1] == BallColor) { // находим нижнюю правую
            Dx1++;
            Dy1++;
        }

        int Ux2 = Cell_X, Uy2 = Cell_Y; // переменные для верхней правой границы
        int Dx2 = Cell_X, Dy2 = Cell_Y; // переменные для нижней левой границы
        while (Uy2 < BoardSize && Ux2 >= 0 && Matrix[Ux2][Uy2] == BallColor) { // находим верхнюю правую
            Uy2++;
            Ux2--;
        }
        while (Dx2 < BoardSize && Dy2 >= 0 && Matrix[Dx2][Dy2] == BallColor) { // находим нижнюю левую
            Dx2++;
            Dy2--;
        }

        /* Удаление элементов */
        if (Rgt - Lft >= InARowCount + 1) { // удаление "-"
            State = 2;
            for (int i = Lft + 1; i < Rgt; i++)
                Matrix[Cell_X][i] = 0;
        }
        if (Dwn - Up >= InARowCount + 1) {    // удаление "|"
            State = 2;
            for (int i = Up + 1; i < Dwn; i++)
                Matrix[i][Cell_Y] = 0;
        }
        if (Dx2 - Ux2 >= InARowCount + 1) {    // удаление "/"
            State = 2;
            for (int i = 1; Ux2 + i < Dx2; i++) {
                Matrix[Ux2 + i][Uy2 - i] = 0;
            }
        }
        if (Dx1 - Ux1 >= InARowCount + 1) {    // удаление "\"
            State = 2;
            for (int i = 1; Ux1 + i < Dx1; i++) {
                Matrix[Ux1 + i][Uy1 + i] = 0;
            }
        }
    }
    /*
    public int[][] Path(int Matrix[][], int X, int Y, int X1, int Y1) { // Функция для нахождения пути

        return Matrix;
    }
    */
}