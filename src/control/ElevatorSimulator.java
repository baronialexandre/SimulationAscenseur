package control;

import utils.ElevetorState;

public class ElevatorSimulator extends Thread{

    private int y;

    public ElevatorSimulator( int floorNumber, int y){
        this.y = y;
    }

    public int getY(){ return y;}

    public void goUp(){ y++ ; }

    public void goDown(){ y -- ;}

    public void stopUntilOrder(){}

    public void stopAtNextFloor(){

    }

    public void nextStep(){

    }
}
