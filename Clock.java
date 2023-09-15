import javax.swing.*;
import java.util.TimerTask;

class Clock extends TimerTask {
    JLabel timer;
    Clock(JLabel timer) {
        this.timer = timer;
    }

    public int i = 0;

    // TimerTask.run() method will be used to perform the action of the task

    public void run() {
        i++;
        this.updateTime();
    }

    public void updateTime() {
        this.timer.setText(String.valueOf(i));
    }

}
