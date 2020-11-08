package se.mickelus.tetra.gui.statbar;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import se.mickelus.tetra.gui.statbar.getter.*;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.effect.ItemEffect;

public class GuiStatBarBlockingDuration extends GuiStatBar {
    private static final IStatGetter durationGetter = new StatGetterEffectLevel(ItemEffect.blocking, 1);

    public GuiStatBarBlockingDuration(int x, int y, int width) {
        super(x, y, width, I18n.format("tetra.stats.blocking"), 0, ItemModularHandheld.blockingDurationLimit,
                false, durationGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterBlockingDuration(durationGetter));

        setIndicators(new GuiStatIndicator(0, 0, "tetra.stats.blocking_reflect", 2, new StatGetterEffectLevel(ItemEffect.blockingReflect, 1), new TooltipGetterBlockingReflect()));

    }

    @Override
    public void update(PlayerEntity player, ItemStack currentStack, ItemStack previewStack, String slot, String improvement) {
        super.update(player, currentStack, previewStack, slot, improvement);

        if (durationGetter.getValue(player, currentStack) >= ItemModularHandheld.blockingDurationLimit
                || durationGetter.getValue(player, previewStack) >= ItemModularHandheld.blockingDurationLimit) {
            valueString.setString("");
        }
    }
}
