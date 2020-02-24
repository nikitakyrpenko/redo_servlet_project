package domain.abstraction;

import domain.Operation;

public interface OperationProcessable {

    void processTransfer(Operation operation);
}
