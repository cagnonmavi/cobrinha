import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Painel extends JPanel implements ActionListener {
    private Cobra cobra;
    private Point food;
    private boolean running = false;

    public Painel() {
        cobra = new Cobra();
        spawnFood();

        Timer timer = new Timer(100, this);
        timer.start();

        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: if (cobra.getDirection() != 'D') cobra.setDirection('U'); break;
                    case KeyEvent.VK_DOWN: if (cobra.getDirection() != 'U') cobra.setDirection('D'); break;
                    case KeyEvent.VK_LEFT: if (cobra.getDirection() != 'R') cobra.setDirection('L'); break;
                    case KeyEvent.VK_RIGHT: if (cobra.getDirection() != 'L') cobra.setDirection('R'); break;
                    case KeyEvent.VK_ENTER: if (!running) restartGame(); break; // Reiniciar
                }
            }
        });
        startGame();
    }

    private void startGame() {
        running = true;
    }

    private void spawnFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(30) * 20;
            y = random.nextInt(20) * 20;
        } while (cobra.getBody().contains(new Point(x, y)));
        food = new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(food.x, food.y, 20, 20); // Fruta como círculo

            g.setColor(Color.PINK);
            for (Point point : cobra.getBody()) {
                g.fillRect(point.x, point.y, 20, 20);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + cobra.getBody().size(), 10, 20); // pontuação
        } else {
            showGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }


    private void move() {
        cobra.move();
        if (cobra.getBody().getFirst().equals(food)) {
            cobra.grow(); // Cresce
            spawnFood();
        } else {
            cobra.removeTail();
        }
    }

    private void checkCollision() {
        Point head = cobra.getBody().getFirst();
        if (head.x < 0 || head.x >= 600 || head.y < 0 || head.y >= 400 || cobra.collidesWithSelf()) {
            running = false;
        }
    }

    private void showGameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Game Over", 100, 150);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Pressione ENTER para reiniciar", 80, 250);
    }

    private void restartGame() {
        cobra = new Cobra();
        spawnFood();
        running = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
        }
        repaint();
    }
}
