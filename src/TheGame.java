import javax.swing.*;

public class TheGame extends JFrame {
    public static void main(String[] args) {
        JFrame app=new JFrame("GAME");
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GamePanel Gmp = new GamePanel();
        app.add(Gmp);
        app.pack();
        app.setVisible(true);
        app.setResizable(false);
    }
}
