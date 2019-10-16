package control;

import ui.ElevatorViewPanel;
import utils.Direction;
import utils.ControllerState;
import utils.ElevatorState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;
import static utils.ElevatorState.*;

public class ControlCommand
{
    private static final long sleepTime = 1000;
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
        this.direction = Direction.UP;
        this.currentFloor = 0;
        this.emergency = false;
        this.floorNumber = floorNumber;

        this.elevatorSimulator = new ElevatorSimulator(floorNumber);
        elevatorSimulator.start();

        ElevatorListener elevatorListener = new ElevatorListener(elevatorSimulator, this, elevatorViewPanel);
        elevatorListener.start();
    }

    public void addCallUp(int floorNb){
        if (emergency || floorNb == currentFloor && state == ControllerState.WAIT)
            return;
        if(!callsUp.contains(floorNb)){
            callsUp.add(floorNb);
            Collections.sort(callsUp);
            updateAimedFloor();
        }
    }

    public void addCallDown(int floorNb){
        if (emergency || floorNb == currentFloor && state == ControllerState.WAIT)
            return;
        if(!callsDown.contains(floorNb)){
            callsDown.add(floorNb);
            //Collections.sort(callsDown,Collections.reverseOrder());
            callsDown.sort(Collections.reverseOrder());
            updateAimedFloor();
        }
    }

    public void addCall(int aimedFloorNb){
        if (emergency || aimedFloorNb == currentFloor && state == ControllerState.WAIT)
            return;
        if (aimedFloorNb > currentFloor && !callsUp.contains(aimedFloorNb))
            addCallUp(aimedFloorNb);
        else if (aimedFloorNb < currentFloor && !callsDown.contains(aimedFloorNb))
            addCallDown(aimedFloorNb);
        else if (aimedFloorNb == currentFloor){
            if(direction.equals(Direction.UP))
                addCallDown(aimedFloorNb);
            else
                addCallUp(aimedFloorNb);
        }
    }


    public void updateAimedFloor()
    {

        System.out.println(this);

        if (emergency || callsUp.isEmpty() && callsDown.isEmpty()){
            return;
        }

        if(direction.equals(Direction.UP)){
            if(!callsUp.isEmpty()) {
                for (Integer i : callsUp) {
                    if (currentFloor < i) {
                        aimedFloor = i;
                        updateElevatorState();
                        return;
                    }
                }
            }
            if(!callsDown.isEmpty()){
                Collections.reverse(callsDown);
                for (Integer i : callsDown) {
                    if (currentFloor < i){
                        aimedFloor = i;
                        updateElevatorState();
                        Collections.reverse(callsDown);
                        return;
                    }
                }
                Collections.reverse(callsDown);
            }
            if (state == ControllerState.WAIT){
                direction = Direction.DOWN;
                updateAimedFloor();
            }
            return;
        }

        if(direction.equals(Direction.DOWN)){
            if(!callsDown.isEmpty()) {
                for (Integer i : callsDown) {
                    if (currentFloor > i) {
                        aimedFloor = i;
                        updateElevatorState();
                        return;
                    }
                }
            }
            if(!callsUp.isEmpty()){
                Collections.reverse(callsUp);
                for (Integer i : callsUp) {
                    if (currentFloor > i){
                        aimedFloor = i;
                        updateElevatorState();
                        Collections.reverse(callsUp);
                        return;
                    }
                }
                Collections.reverse(callsUp);
            }
            if (state == ControllerState.WAIT){
                direction = Direction.UP;
                updateAimedFloor();
            }
            return;

        }

        /*
        //aimedFloor ne change jamais
        // si direction est a UP ou NONE, il y a un appel vers le haut et currentfloor n'est pas l'étage maximal
        if(direction != Direction.DOWN && !callsUp.isEmpty() && currentFloor < floorNumber - 1) {
            for(int i = 0; i < callsUp.size(); ++i){
                if(aimedFloor != callsUp.get(i) && currentFloor < callsUp.get(i) && callsUp.get(i) < floorNumber){
                    aimedFloor = callsUp.get(i);/*
                    System.out.println("CC on va UP a letage " + aimedFloor);
                    System.out.println("UPs "+callsUp);
                    System.out.println("DOWNs "+callsDown);
                    updateElevatorState();
                    return;
                }
            }
        }
        if(direction != Direction.UP && !callsDown.isEmpty() && currentFloor >= 0) {
            for(int i = 0; i < callsDown.size(); ++i){
                if(aimedFloor != callsDown.get(i) && currentFloor > callsDown.get(i) && callsDown.get(i) >= 0){
                    aimedFloor = callsDown.get(i);/*
                    System.out.println("CC on va DOWN a letage " + aimedFloor);
                    System.out.println("UPs "+callsUp);
                    System.out.println("DOWNs "+callsDown);
                    updateElevatorState();
                    return;
                }
            }
        }
        if(!callsUp.isEmpty() && callsDown.isEmpty() && currentFloor < floorNumber - 1) {
            for(int i = 0; i < callsUp.size(); ++i){
                if(aimedFloor != callsUp.get(i) && callsUp.get(i) < floorNumber && currentFloor > callsUp.get(i)){
                    aimedFloor = callsUp.get(i);/*
                    System.out.println("CC on va UP down a letage " + aimedFloor);
                    System.out.println("UPs "+callsUp);
                    System.out.println("DOWNs "+callsDown);
                    updateElevatorState();
                    return;
                }
            }
        }
//        System.out.println(direction != Direction.UP);
//        System.out.println(!callsDown.isEmpty());
//        System.out.println(currentFloor >= 0);
        if(!callsDown.isEmpty() && callsUp.isEmpty() && currentFloor >= 0) {
            for(int i = 0; i < callsDown.size(); ++i){
//                System.out.println(aimedFloor != callsDown.get(i));
//                System.out.println(callsDown.get(i) >= 0);
                if(aimedFloor != callsDown.get(i) && callsDown.get(i) >= 0 && currentFloor < callsDown.get(i)){
                    aimedFloor = callsDown.get(i);/*
                    System.out.println("CC on va DOWN up a letage " + aimedFloor);
                    System.out.println("UPs "+callsUp);
                    System.out.println("DOWNs "+callsDown);
                    updateElevatorState();
                    return;
                }
            }
        }


        System.out.println("UPs "+callsUp);
        System.out.println("DOWNs "+callsDown);*/
    }

    public void updateElevatorState(){

        if (emergency)
            return;

        System.out.println("UpdateElevatorState "+this);
        if(currentFloor == aimedFloor) {
            //aimedFloorReached
            System.out.println("CC on est arrivé etage"+ currentFloor);
            elevatorSimulator.stopUntilOrder();
            state = ControllerState.WAIT;
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callsUp.remove(Integer.valueOf(currentFloor));
            callsDown.remove(Integer.valueOf(currentFloor));
            System.out.println(this);
            if(currentFloor == floorNumber-1)
                direction = Direction.DOWN;
            else if(currentFloor == 0)
                direction = Direction.UP;
            updateAimedFloor();
        }
        if(currentFloor - aimedFloor == 1) {
            elevatorSimulator.setGoingNextDown();
            direction = Direction.DOWN;
            state = ControllerState.MOVEMENT;
            //System.out.println("CC direction DOWN next DOWN");
        }
        else if(currentFloor - aimedFloor == -1) {
            elevatorSimulator.setGoingNextUp();
            direction = Direction.UP;
            state = ControllerState.MOVEMENT;
            //System.out.println("CC direction UP next UP");
        }
        else if(currentFloor - aimedFloor > 1) {
            elevatorSimulator.setGoingDown();
            direction = Direction.DOWN;
            state = ControllerState.MOVEMENT;
            //System.out.println("CC direction DOWN");
        }
        else if(currentFloor - aimedFloor < -1) {
            elevatorSimulator.setGoingUp();
            direction = Direction.UP;
            state = ControllerState.MOVEMENT;
            //System.out.println("CC direction UP");
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
        System.out.println("emergency "+this);
        elevatorSimulator.stopUntilOrder();
    }

    public void restart(){
        emergency = false;
        System.out.println("restart "+this);
        updateElevatorState();
    }

    @Override
    public String toString() {
        return "ControlCommand{" +
                "floorNumber=" + floorNumber +
                ", callsUp=" + callsUp +
                ", callsDown=" + callsDown +
                ", state=" + state +
                ", direction=" + direction +
                ", currentFloor=" + currentFloor +
                ", aimedFloor=" + aimedFloor +
                ", emergency=" + emergency +
                ", elevatorSimulator=" + elevatorSimulator +
                '}';
    }
}
