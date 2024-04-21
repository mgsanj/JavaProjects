import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private int x;	//x position of the player
    private AtomicInteger y;	//y position of the player
    private int hp;		//health point of the player
    public static Object obj = new Object();

    public Player(int x, int y, int hp){
        this.x = x;
        this.y = new AtomicInteger(y);
        this.hp = hp;
    }

    public void printPlayer(){
        System.out.printf("x position:\t%d\ny position:\t%d\nhealth point:\t%d\n", x, y.intValue(), hp);
    }

    public synchronized void moveLeft(){
        x --;
    }
    public synchronized void moveRight(){
        x ++;
    }

    public void moveUp(){
        y.decrementAndGet();
    }
    public void moveDown(){
        y.addAndGet(1);
    }

    public void loseHealth(){
        synchronized(obj) {
            hp --;
        }
    }
    public void gainHealth(){
        synchronized(obj) {
            hp ++;
        }
    }

}
