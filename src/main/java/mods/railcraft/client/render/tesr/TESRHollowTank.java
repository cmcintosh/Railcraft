/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2020
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.client.render.tesr;

import mods.railcraft.client.render.models.resource.FluidModelRenderer;
import mods.railcraft.client.render.tools.OpenGL;
import mods.railcraft.client.render.tools.RenderTools;
import mods.railcraft.common.blocks.structures.TileTankBase;
import mods.railcraft.common.blocks.structures.TileTankIronValve;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.fluids.TankManager;
import mods.railcraft.common.fluids.tanks.StandardTank;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public final class TESRHollowTank extends TileEntitySpecialRenderer<TileTankBase> {

    private float getVerticalScaleSide(TileTankBase tile) {
        int y = tile.getPatternPosition().getY();
        if (!RailcraftConfig.allowTankStacking())
            y--;
        return y - RenderTools.PIXEL * 5;
    }

    private int getTankHeight(TileTankBase tile) {
        int height = tile.getPattern().getPatternHeight();
        if (!RailcraftConfig.allowTankStacking())
            height -= 2;
        return height;
    }

    private void draw(FluidStack fluidStack, int skyLight, int blockLight) {
        preGL(skyLight, blockLight);

        OpenGL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        OpenGL.glTranslatef(-0.5F, -0.501F + 0.0625f, -0.5F);

        FluidModelRenderer.INSTANCE.renderFluid(fluidStack, 16);
//        FluidRenderer.setColorForFluid(fluidStack);
//        CubeRenderer.render(fillBlock);
        postGL();
    }

    private void preGL(int skyLight, int blockLight) {
        OpenGL.glPushMatrix();
        OpenGL.glPushAttrib(GL11.GL_ENABLE_BIT);
        OpenGL.glEnable(GL11.GL_CULL_FACE);
        OpenGL.glEnable(GL11.GL_BLEND);
        OpenGL.glDisable(GL11.GL_LIGHTING);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, blockLight * 16f, skyLight * 16f);
        // second param is block light, third is sky light
    }

    private void postGL() {
        OpenGL.glDisable(GL11.GL_BLEND);
        OpenGL.glEnable(GL11.GL_LIGHTING);
        OpenGL.glPopAttrib();
        OpenGL.glPopMatrix();
    }

    @Override
    public void render(TileTankBase tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!tile.isStructureValid())
            return;

        int skyLight = getWorld().getLightFor(EnumSkyBlock.SKY, tile.getPos().up());
        int blockLight = getWorld().getLightFor(EnumSkyBlock.BLOCK, tile.getPos().up());

        if (tile instanceof TileTankIronValve) {
            TileTankIronValve valve = (TileTankIronValve) tile;
            StandardTank fillTank = valve.getFillTank();
            FluidStack fillStack = fillTank.getFluid();
            if (fillStack != null && fillStack.amount > 0) {
                blockLight = Math.max(blockLight, fillStack.getFluid().getLuminosity(fillStack));
                OpenGL.glPushMatrix();
                if (valve.getPattern().getPatternMarkerChecked(valve.getPatternPosition().down()) == 'A') {

//                    prepFillTexture(fillStack);

                    int height = getTankHeight(valve);
                    float yOffset = height / 2f;
                    float vScale = height - 2f;
                    OpenGL.glTranslatef((float) x + 0.5F, (float) y + yOffset - height + 1, (float) z + 0.5F);
                    OpenGL.glScalef(1f, vScale, 1f);

                    draw(fillStack, skyLight, blockLight);
                } else if (valve.getPattern().getPatternMarkerChecked(valve.getPatternPosition().add(-1, 0, 0)) == 'A') {

//                    prepFillTexture(fillStack);

                    float vScale = getVerticalScaleSide(valve);
                    float yOffset = 0.5f - vScale / 2f + RenderTools.PIXEL * 3;
                    OpenGL.glTranslatef((float) x - 0.5F + RenderTools.PIXEL * 5, (float) y + yOffset, (float) z + 0.5F);
                    OpenGL.glScalef(1f, vScale, 1f);

                    draw(fillStack, skyLight, blockLight);
                } else if (valve.getPattern().getPatternMarkerChecked(valve.getPatternPosition().add(1, 0, 0)) == 'A') {

//                    prepFillTexture(fillStack);

                    float vScale = getVerticalScaleSide(valve);
                    float yOffset = 0.5f - vScale / 2f + RenderTools.PIXEL * 3;
                    OpenGL.glTranslatef((float) x + 1.5F - RenderTools.PIXEL * 5, (float) y + yOffset, (float) z + 0.5F);
                    OpenGL.glScalef(1f, vScale, 1f);

                    draw(fillStack, skyLight, blockLight);
                } else if (valve.getPattern().getPatternMarkerChecked(valve.getPatternPosition().add(0, 0, -1)) == 'A') {

//                    prepFillTexture(fillStack);

                    float vScale = getVerticalScaleSide(valve);
                    float yOffset = 0.5f - vScale / 2f + RenderTools.PIXEL * 3;
                    OpenGL.glTranslatef((float) x + 0.5F, (float) y + yOffset, (float) z - 0.5F + RenderTools.PIXEL * 5);
                    OpenGL.glScalef(1f, vScale, 1f);

                    draw(fillStack, skyLight, blockLight);
                } else if (valve.getPattern().getPatternMarkerChecked(valve.getPatternPosition().add(0, 0, 1)) == 'A') {

//                    prepFillTexture(fillStack);

                    float vScale = getVerticalScaleSide(valve);
                    float yOffset = 0.5f - vScale / 2f + RenderTools.PIXEL * 3;
                    OpenGL.glTranslatef((float) x + 0.5F, (float) y + yOffset, (float) z + 1.5F - RenderTools.PIXEL * 5);
                    OpenGL.glScalef(1f, vScale, 1f);

                    draw(fillStack, skyLight, blockLight);
                }
                OpenGL.glPopMatrix();
            }
        }

        if (!tile.isValidMaster() || tile.isInvalid())
            return;
        int height = getTankHeight(tile);
        float yOffset = height / 2f;
        float vScale = height - 2;
        float hScale = tile.getPattern().getPatternWidthX() - 2;

        TankManager tankManager = tile.getTankManager();
        if (tankManager.isEmpty())
            return;
        StandardTank tank = tankManager.get(0);
        if (tank == null)
            return;

        FluidStack fluidStack = tank.getFluid();
        if (fluidStack != null && fluidStack.amount > 0) {
            blockLight = Math.max(blockLight, fluidStack.getFluid().getLuminosity(fluidStack));
            preGL(skyLight, blockLight);
            OpenGL.glTranslatef((float) x + 0.5F, (float) y + yOffset + 0.01f, (float) z + 0.5F);
            OpenGL.glScalef(hScale, vScale, hScale);

            OpenGL.glScalef(0.999f, 1, 0.999f);
//            int[] displayLists = FluidRenderer.getLiquidDisplayLists(fluidStack); this broke
//            OpenGL.glPushMatrix();

            //
            OpenGL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            float cap = tank.getCapacity();

            OpenGL.glTranslatef(-0.5F, -0.501F, -0.5F);

            float level = Math.min(fluidStack.amount / cap, cap);

            FluidModelRenderer.INSTANCE.renderFluid(fluidStack, Math.min(16, (int) Math.ceil(level * 16F)));

            //

//            bindTexture(FluidRenderer.getFluidSheet(fluidStack));
//            FluidRenderer.setColorForFluid(fluidStack);
//            OpenGL.glCallList(displayLists[(int) (level * (float) (FluidRenderer.DISPLAY_STAGES - 1))]);

            postGL();
//            OpenGL.glPopMatrix();
        }
    }
}
