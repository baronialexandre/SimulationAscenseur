package control.strategy;

import control.ControlCommand;

public interface ControlStrategy {
    void updateAimedFloor(ControlCommand controlCommand);
    void updateElevatorState(ControlCommand controlCommand);
}
