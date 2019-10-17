package control;

import ui.ElevatorViewPanel;
import utils.ElevatorState;

import static utils.ElevatorState.*;

public class ElevatorSimulator extends Thread {

    private static final long sleepTime = 10;

    private ElevatorState state=STOPPED;

    private int y = 50;

    private int floorNumber;

    ElevatorSimulator(int floorNumber) {
        this.floorNumber = floorNumber;
    }

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
        // doit tourner à l'infini
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


    public void stopUntilOrder() {state = STOPPED; }

    public void setGoingNextDown() {
        state = GOINGNEXTDOWN;
    }
    public void setGoingNextUp() {
        state = GOINGNEXTUP;
    }
    public void setGoingDown() {
        state = GOINGDOWN;
    }
    public void setGoingUp() {
        state = GOINGUP;
    }

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
            System.out.println("SIM: on va aller chercher un étage");
            reachNextFloor();
            //System.out.println("SIM: Etage atteint, on s'arrete");
            //stopUntilOrder();
        }
    }
}


