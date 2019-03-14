package se.mickelus.tetra.generation.processor;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;
import se.mickelus.tetra.blocks.forged.BlockForgedCrate;
import se.mickelus.tetra.blocks.forged.container.BlockForgedContainer;
import se.mickelus.tetra.blocks.forged.container.TileEntityForgedContainer;

import javax.annotation.Nullable;
import java.util.Random;

public class ForgedCrateProcessor implements ITemplateProcessor {

    private Random random;

    public ForgedCrateProcessor(Random random) {
        this.random = random;
    }

    @Nullable
    @Override
    public Template.BlockInfo processBlock(World world, BlockPos pos, Template.BlockInfo blockInfo) {
        if (blockInfo.blockState.getBlock() instanceof BlockForgedCrate) {

            return new Template.BlockInfo(pos, blockInfo.blockState.withProperty(BlockForgedCrate.propIntegrity, random.nextInt(4)), null);
        }
        return blockInfo;
    }
}