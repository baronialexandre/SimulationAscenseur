package control;

import ui.ElevatorViewPanel;
import utils.ElevatorState;

import static utils.ElevatorState.*;

public class ElevatorSimulator extends Thread {

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

    void stopUntilOrder() {state = STOPPED; }

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

    void setGoingNextDown() {
        state = GOINGNEXTDOWN;
    }
    void setGoingNextUp() {
        state = GOINGNEXTUP;
    }
    void setGoingDown() {
        state = GOINGDOWN;
    }
    void setGoingUp() {
        state = GOINGUP;
    }
    ElevatorState getGoingState() {
        return state;
    }

    private void reachNextFloor() throws InterruptedException {
        if (state == GOINGNEXTDOWN) {
            goDown();
            sleep(10);
        }
        if (state == GOINGNEXTUP) {
            goUp();
            sleep(10);
        }
        while (state == GOINGNEXTDOWN && y - 50 % 100 != 0) {
            goDown();
            sleep(10);
        }
        while (state == GOINGNEXTUP && y - 50 %100 != 0) {
            goUp();
            sleep(10);
        }
    }

    private void nextStep() throws InterruptedException {
        while (state == GOINGDOWN) {
            goDown();
            sleep(10);
        }
        while (state == GOINGUP) {
            goUp();
            sleep(10);
        }
        if(state == GOINGNEXTDOWN || state == GOINGNEXTUP) {
            System.out.println("on va aller chercher un étage");
            reachNextFloor();
            System.out.println("Etage atteint, on s'arrete");
            stopUntilOrder();
        }
    }
}


