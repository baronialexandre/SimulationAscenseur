package control;

import utils.ElevatorState;

import static utils.ElevatorState.*;

public class ElevatorSimulator extends Thread {

    private static final long sleepTime = 10;

    private ElevatorState state=STOPPED;

    private int y = 50;

    int getY() {
        return y;
    }

    private void goUp() {
        y++;
    }

    private void goDown() {
        y--;
    }


    public void run() {
        for(;;) {
            try {
                nextStep();
                while (state == STOPPED) {
                    sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setState(ElevatorState elevatorState){this.state = elevatorState;}

    private void reachNextFloor() throws InterruptedException {
        if (state == GOINGNEXTDOWN) {
            goDown();
            sleep(sleepTime);
        }
        if (state == GOINGNEXTUP) {
            goUp();
            sleep(sleepTime);
        }
        while (state == GOINGNEXTDOWN && y - 50 % 100 != 0) {
            goDown();
            sleep(sleepTime);
        }
        while (state == GOINGNEXTUP && y - 50 %100 != 0) {
            goUp();
            sleep(sleepTime);
        }
    }

    private void nextStep() throws InterruptedException {
        while (state == GOINGDOWN) {
            goDown();
            sleep(sleepTime);
        }
        while (state == GOINGUP) {
            goUp();
            sleep(sleepTime);
        }
        if(state == GOINGNEXTDOWN || state == GOINGNEXTUP) {
            reachNextFloor();
        }
    }
}


