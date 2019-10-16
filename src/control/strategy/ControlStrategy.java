package control.strategy;

import control.ControlCommand;

public interface ControlStrategy {
    public void updateAimedFloor(ControlCommand controlCommand);
    public void updateElevatorState(ControlCommand controlCommand);
}
