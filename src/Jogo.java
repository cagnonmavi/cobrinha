import javax.swing.*;

public class Jogo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo da Cobrinha");
        Painel painel = new Painel();
        frame.add(painel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
