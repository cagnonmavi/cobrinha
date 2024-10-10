import java.awt.Point;
import java.util.LinkedList;

public class Cobra {
    private LinkedList<Point> body;
    private char direction;

    public Cobra() {
        body = new LinkedList<>();
        body.add(new Point(0, 0)); // Inicia a cobra
        direction = 'R'; // Direção inicial
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case 'U': newHead.translate(0, -20); break;
            case 'D': newHead.translate(0, 20); break;
            case 'L': newHead.translate(-20, 0); break;
            case 'R': newHead.translate(20, 0); break;
        }

        body.addFirst(newHead);
    }

    public void grow() {
        // Não remove o último segmento para crescer
    }

    public void removeTail() {
        body.removeLast();
    }

    public boolean collidesWithSelf() {
        Point head = body.getFirst();
        return body.stream().skip(1).anyMatch(point -> point.equals(head));
    }
}
