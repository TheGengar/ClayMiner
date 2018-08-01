package scripts.clayminer.nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.clayminer.data.Vars;
import scripts.clayminer.framework.Node;

public class LeaveMine extends Node {
    @Override
    public void execute() {
        System.out.println("The LeaveMine Node has been Validated! Executing...");
        WebWalking.walkTo(Vars.INSIDE_MINE_ROOT_TILE);
        Vars.passRoots();
    }

    @Override
    public boolean validate() {
        return (Inventory.isFull() && Vars.isInMine());
    }
}
