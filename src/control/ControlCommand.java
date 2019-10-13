package control;

import ui.ElevatorViewPanel;
import utils.Direction;
import utils.ControllerState;
import utils.ElevatorState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.ElevatorState.*;

public class ControlCommand
{
    private static final double time = 1000;
    private final int floorNumber;
    private List<Integer> callsUp, callsDown;
    private ControllerState state;
    private Direction direction;
    private int currentFloor;
    private int aimedFloor;
    private boolean emergency;
    private ElevatorSimulator elevatorSimulator;

    public ControlCommand(int floorNumber, ElevatorViewPanel elevatorViewPanel) {
        this.callsUp = new ArrayList<>();
        this.callsDown = new ArrayList<>();
        this.state = ControllerState.WAIT;
        this.direction = Direction.NONE;
        this.currentFloor = 0;
        this.emergency = false;
        this.floorNumber = floorNumber;

        this.elevatorSimulator = new ElevatorSimulator(floorNumber);
        elevatorSimulator.start();

        ElevatorListener elevatorListener = new ElevatorListener(elevatorSimulator, this, elevatorViewPanel);
        elevatorListener.start();

        updateElevatorState();
    }

    public void addCallUp(int floorNb){
        if(!callsUp.contains(floorNb)){
            callsUp.add(floorNb);
            Collections.sort(callsUp);
            updateAimedFloor();
        }
    }

    public void addCallDown(int floorNb){
        if(!callsDown.contains(floorNb)){
            callsDown.add(floorNb);
            //Collections.sort(callsDown,Collections.reverseOrder());
            callsDown.sort(Collections.reverseOrder());
            updateAimedFloor();
        }
    }

    public void addCall(int aimedFloorNb){
        if (aimedFloorNb > currentFloor && !callsUp.contains(aimedFloorNb))
            addCallUp(aimedFloorNb);
        else if (aimedFloorNb < currentFloor && !callsDown.contains(aimedFloorNb))
            addCallDown(aimedFloorNb);
    }


    public void updateAimedFloor()
    {
        //aimedFloor ne change jamais
        if(direction != Direction.DOWN && !callsUp.isEmpty() && currentFloor < floorNumber) {
            for(int i = 0; i < callsUp.size(); ++i){
                if(aimedFloor != callsUp.get(i) && currentFloor < callsUp.get(i) && callsUp.get(i) < floorNumber){
                    aimedFloor = callsUp.get(i);
                    System.out.println("CC on va UP a letage " + aimedFloor);
                    updateElevatorState();
                    break;
                }
            }
        }

        if(direction != Direction.UP && !callsDown.isEmpty() && currentFloor >= 0) {
            for(int i = 0; i < callsDown.size(); ++i){/*
                System.out.println(aimedFloor != callsDown.get(i));
                System.out.println(currentFloor > callsDown.get(i));
                System.out.println(currentFloor >= 0);*/
                if(aimedFloor != callsDown.get(i) && currentFloor > callsDown.get(i) && callsDown.get(i) >= 0){
                    aimedFloor = callsDown.get(i);
                    System.out.println("CC on va DOWN a letage " + aimedFloor);
                    updateElevatorState();
                    return;
                }
            } //todo: cette deuxieme boucle est la clée au probleme actuel
            for(int i = 0; i < callsDown.size(); ++i){
                System.out.println(aimedFloor != callsDown.get(i));
                System.out.println(currentFloor >= 0);
                if(aimedFloor != callsDown.get(i) && callsDown.get(i) >= 0){
                    aimedFloor = callsDown.get(i);
                    System.out.println("CC on va up a letage DOWN  " + aimedFloor);
                    updateElevatorState();
                    break;
                }
            }

        }
        System.out.println("UPs "+callsUp);
        System.out.println("DOWNs "+callsDown);
    }

    public void updateElevatorState(){
        if(currentFloor == aimedFloor) {
            //aimedFloorReached
            System.out.println("CC on est arrivé etage"+ currentFloor);
            if(direction.equals(Direction.UP)){
                callsUp.remove(Integer.valueOf(currentFloor));
                if(currentFloor == floorNumber-1)
                    direction = Direction.DOWN;
                else
                    direction = Direction.NONE;
                updateAimedFloor();
            }
            else if(direction.equals(Direction.DOWN)){
                callsDown.remove(Integer.valueOf(currentFloor));
                if(currentFloor == 0)
                    direction = Direction.UP;
                else
                    direction = Direction.NONE;
                updateAimedFloor();
            }
            elevatorSimulator.stopUntilOrder();
        }
        if(currentFloor - aimedFloor == 1) {
            elevatorSimulator.setGoingNextDown();
            direction = Direction.DOWN;
            System.out.println("CC direction DOWN next DOWN");
        }
        else if(currentFloor - aimedFloor == -1) {
            elevatorSimulator.setGoingNextUp();
            direction = Direction.UP;
            System.out.println("CC direction UP next UP");
        }
        else if(currentFloor - aimedFloor > 1) {
            elevatorSimulator.setGoingDown();
            direction = Direction.DOWN;
            System.out.println("CC direction DOWN");
        }
        else if(currentFloor - aimedFloor < -1) {
            elevatorSimulator.setGoingUp();
            direction = Direction.UP;
            System.out.println("CC direction UP");
        }
    }


    void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        updateElevatorState();
    }


    public void emergency(){
        emergency = true;
        callsUp.clear();
        callsDown.clear();
        elevatorSimulator.stopUntilOrder();
    }

    public void restart(){
        emergency = false;
    }

}
