package scripts.clayminer.nodes;

import org.tribot.api2007.*;
import scripts.clayminer.framework.Node;
import scripts.clayminer.data.Vars;

public class WalkBank extends Node {
    @Override
    public void execute() {
        System.out.println("The WalkVarrockBank Node has been Validated! Executing...");
        WebWalking.walkTo(Vars.BANK_TILE);
        // maybe add sleep
    }

    @Override
    public boolean validate() {
        return (Player.getPosition().getPlane() == 1 &&
                !Banking.isInBank() &&
                Inventory.isFull());
    }
}
