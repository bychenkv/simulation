package com.bychenkv.simulation.services.input;

public interface UserInputEventProvider {
    void addEventListener(UserInputEventListener listener);
    void removeEventListener(UserInputEventListener listener);
}
