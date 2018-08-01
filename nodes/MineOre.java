package scripts.clayminer.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import scripts.clayminer.utilities.Antiban;
import scripts.clayminer.framework.Node;
import scripts.clayminer.data.Vars;

public class MineOre extends Node {

    private RSObject[] clayRocks;
    private RSObject nextTarget;

    // Mining statistics; used in ABC2
    private long lastMiningWaitTime;
    private long averageMiningWaitTime = 1800;
    private long totalMiningWaitTime;
    private long totalMiningInstances = 0;

    @Override
    public boolean validate() {
        return (!Inventory.isFull() && Vars.CLAY_TILE.isOnScreen());
    }

    private void updateMiningStatistics(long waitTime){

        lastMiningWaitTime = waitTime;
        totalMiningWaitTime += lastMiningWaitTime;
        totalMiningInstances++;
        averageMiningWaitTime = totalMiningWaitTime / totalMiningInstances;

        General.println("Average Mining Time is: " + averageMiningWaitTime);
    }


    @Override
    public void execute() {
        System.out.println("The MineOre Node has been Validated! Executing...");

        // Find nearby clay rocks to mine if next target holds no value.
        clayRocks = Objects.findNearest(15, Vars.CLAY_ROCKS_IDS[0], Vars.CLAY_ROCKS_IDS[1]);

        // If rocks are found and you are not mining
        if (clayRocks.length > 0 && Player.getAnimation() == -1) {

            // Ensure nextTarget holds a valid value
            if (nextTarget == null){
                nextTarget = Objects.findNearest(15, Vars.CLAY_ROCKS_IDS[0], Vars.CLAY_ROCKS_IDS[1])[0];
            }

            // Ensure menu isn't open for next entity, if it is, click it via options.
            if (ChooseOption.isOpen()){
                ChooseOption.select("Mine Rocks");
            } else {
                nextTarget.click();
            }

            // Prior to idle Antiban procedures
            nextTarget = (RSObject) Antiban.get().getNextTarget(clayRocks);     // Store the next rock to mine.
            Antiban.get().setHoverAndMenuBoolValues();
            Antiban.get().generateTrackers((int)averageMiningWaitTime);
            waitToStartAnimating();

            long startMiningTime = System.currentTimeMillis();

            if (isMining()){
                while (isMining()) {
                    General.sleep(100);

                    // Idling Antiban procedures
                    Antiban.get().executeShouldHoverAndMenu(nextTarget);
                    Antiban.get().timedActions();
                }

                // Mining is now complete. Update statistics and sleep the reaction time generated.
                updateMiningStatistics(System.currentTimeMillis() - startMiningTime);
                Antiban.get().sleepReactionTime((int) averageMiningWaitTime);
            }
        }
    }

    private boolean isMining(){
        return (Player.getAnimation() == Vars.MINING_ANIMATION);
    }

    private void waitToStartAnimating() {
        // Wait to stop walking
        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(300);
                return !Player.isMoving();
            }
        }, General.random(4000, 6000));

        //Wait to start animating
        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(300);
                return Player.getAnimation() != -1;
            }
        }, General.random(2000, 4000));
    }
}
