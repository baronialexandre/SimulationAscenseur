package control.strategy;

import control.ControlCommand;
import utils.ControllerState;
import utils.Direction;

import java.util.Collections;

import static java.lang.Thread.sleep;

public class BasicControlStrategy implements ControlStrategy{
 // todo: JE SAIS PAS QUOI FAIRE COMME TRUC BASIQUE
    public void updateAimedFloor(ControlCommand controlCommand) {
        if (controlCommand.isEmergency() || controlCommand.getCallsUp().isEmpty() && controlCommand.getCallsDown().isEmpty()){
            return;
        }

        if(controlCommand.getDirection().equals(Direction.UP)){
            if(!controlCommand.getCallsUp().isEmpty()) {
                for (Integer i : controlCommand.getCallsUp()) {
                    if (controlCommand.getCurrentFloor() < i) {
                        controlCommand.setAimedFloor(i);
                        updateElevatorState(controlCommand);
                        return;
                    }
                }
            }
            return;
        }

        if(controlCommand.getDirection().equals(Direction.DOWN)){
            if(!controlCommand.getCallsDown().isEmpty()) {
                for (Integer i : controlCommand.getCallsDown()) {
                    if (controlCommand.getCurrentFloor() > i) {
                        controlCommand.setAimedFloor(i);
                        updateElevatorState(controlCommand);
                        return;
                    }
                }
            }
        }
    }

    public void updateElevatorState(ControlCommand controlCommand) {
        if (controlCommand.isEmergency())
            return;

        if(controlCommand.getCurrentFloor() == controlCommand.getAimedFloor()) {
            controlCommand.getElevatorSimulator().stopUntilOrder();
            controlCommand.setState(ControllerState.WAIT);
            try {
                sleep(controlCommand.getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controlCommand.getCallsUp().remove(Integer.valueOf(controlCommand.getCurrentFloor()));
            controlCommand.getCallsDown().remove(Integer.valueOf(controlCommand.getCurrentFloor()));
            if(controlCommand.getCurrentFloor() == controlCommand.getFloorNumber()-1)
                controlCommand.setDirection(Direction.DOWN);
            else if(controlCommand.getCurrentFloor() == 0)
                controlCommand.setDirection(Direction.UP);
            updateAimedFloor(controlCommand);
        }
        if(controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() == 1) {
            controlCommand.getElevatorSimulator().setGoingNextDown();
            controlCommand.setDirection(Direction.DOWN);
            controlCommand.setState(ControllerState.MOVEMENT);
            //System.out.println("CC direction DOWN next DOWN");
        }
        else if(controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() == -1) {
            controlCommand.getElevatorSimulator().setGoingNextUp();
            controlCommand.setDirection(Direction.UP);
            controlCommand.setState(ControllerState.MOVEMENT);
            //System.out.println("CC direction UP next UP");
        }
        else if(controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() > 1) {
            controlCommand.getElevatorSimulator().setGoingDown();
            controlCommand.setDirection(Direction.DOWN);
            controlCommand.setState(ControllerState.MOVEMENT);
            //System.out.println("CC direction DOWN");
        }
        else if(controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() < -1) {
            controlCommand.getElevatorSimulator().setGoingUp();
            controlCommand.setDirection(Direction.UP);
            controlCommand.setState(ControllerState.MOVEMENT);
            //System.out.println("CC direction UP");
        }
    }
}
