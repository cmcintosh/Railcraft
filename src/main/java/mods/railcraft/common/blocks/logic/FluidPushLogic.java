/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2020
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.blocks.logic;

import mods.railcraft.common.fluids.TankManager;
import mods.railcraft.common.util.misc.Predicates;
import net.minecraft.util.EnumFacing;

/**
 * Created by CovertJaguar on 1/28/2019 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class FluidPushLogic extends Logic {
    private final int tankIndex;
    private final int outputRate;
    private final EnumFacing[] outputFaces;

    public FluidPushLogic(Adapter adapter, int tankIndex, int outputRate, EnumFacing[] outputFaces) {
        super(adapter);
        this.tankIndex = tankIndex;
        this.outputRate = outputRate;
        this.outputFaces = outputFaces;
    }

    @Override
    protected void updateServer() {
        super.updateServer();
        adapter.tile().ifPresent(tile -> getLogic(TankLogic.class).ifPresent(tank -> {
            TankManager tMan = tank.getTankManager();
            if (!tMan.isEmpty()) {
                tMan.push(tile.getTileCache(), Predicates.notInstanceOf(tile.getClass()), outputFaces, tankIndex, outputRate);
            }
        }));
    }
}
