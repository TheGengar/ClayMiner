package scripts.clayminer;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.clayminer.framework.Node;
import scripts.clayminer.nodes.*;
import scripts.clayminer.nodes.floors.LeaveBottomFloor;
import scripts.clayminer.nodes.floors.LeaveTopFloor;
import scripts.clayminer.nodes.floors.MainFloor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ScriptManifest(
        authors =       "TheGengar",
        name =          "Clay Miner",
        category =      "Mining",
        description =   "Mines clay rocks in the dungeon of the grand tree.",
        version =       1.0)

public class ClayMiner extends Script {

    // All actions are stored here.
    private final List<Node> nodes = new ArrayList<>();

    @Override
    public void run() {
        General.useAntiBanCompliance(true);

        Collections.addAll(nodes,
                new WalkMine(),
                new MineOre(),
                new LeaveMine(),
                new LeaveBottomFloor(),
                new MainFloor(),
                new WalkBank(),
                new BankOre(),
                new LeaveTopFloor(),
                new MainFloor(),
                new EnterMine());

        loop(250, 500);
    }

    private void loop(int min, int max) {
        while (true) {
            for (final Node node : nodes) {
                if (node.validate()) {
                    node.execute();
                    sleep(General.random(min, max));	//time in between executing nodes
                }
            }
        }
    }
}
