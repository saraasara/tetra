package se.mickelus.tetra.module.schema;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import se.mickelus.tetra.capabilities.Capability;
import se.mickelus.tetra.items.ItemModular;
import se.mickelus.tetra.module.GlyphData;
import se.mickelus.tetra.module.ItemModuleMajor;
import se.mickelus.tetra.module.ItemUpgradeRegistry;

import java.util.Collection;
import java.util.Collections;

public class ImprovementSchema implements UpgradeSchema {
    private static final String nameSuffix = ".schema.name";
    private static final String descriptionSuffix = ".schema.description";
    private static final String slotSuffix = ".slot1";

    protected ItemModuleMajor module;
    protected String improvement;

    public ImprovementSchema(ItemModuleMajor module, String improvement) {
        this.module = module;
        this.improvement = improvement;

        ItemUpgradeRegistry.instance.registerSchema(this);
    }

    @Override
    public String getKey() {
        return improvement;
    }

    @Override
    public String getName() {
        return I18n.format(improvement + nameSuffix);
    }

    @Override
    public String getDescription() {
        return I18n.format(improvement + descriptionSuffix);
    }

    @Override
    public int getNumMaterialSlots() {
        return 0;
    }

    @Override
    public String getSlotName(final ItemStack itemStack, final int index) {
        return "";
    }

    @Override
    public int getRequiredQuantity(ItemStack itemStack, int index, ItemStack materialStack) {
        return 0;
    }

    @Override
    public boolean slotAcceptsMaterial(ItemStack itemStack, int index, ItemStack materialStack) {
        return false;
    }

    @Override
    public boolean isMaterialsValid(ItemStack itemStack, ItemStack[] materials) {
        return true;
    }

    @Override
    public boolean canUpgrade(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemModular) {
            ItemModular item = (ItemModular) itemStack.getItem();
            return item.hasModule(itemStack, module);
        }
        return false;
    }

    @Override
    public boolean canApplyUpgrade(EntityPlayer player, ItemStack itemStack, ItemStack[] materials) {
        return !isIntegrityViolation(itemStack, materials)
            && checkCapabilities(player, itemStack, materials);
    }

    @Override
    public boolean isIntegrityViolation(ItemStack itemStack, ItemStack[] materials) {
        ItemStack improvedStack = applyUpgrade(itemStack, materials, false, null);
        return ItemModular.getIntegrityGain(improvedStack) + ItemModular.getIntegrityCost(improvedStack) < 0;
    }

    @Override
    public ItemStack applyUpgrade(ItemStack itemStack, ItemStack[] materials, boolean consumeMaterials, EntityPlayer player) {
        ItemStack upgradedStack = itemStack.copy();
        module.addImprovement(upgradedStack, improvement, 1);

        if (consumeMaterials && player instanceof EntityPlayerMP) {
            // todo: add proper criteria
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, upgradedStack);
        }

        return upgradedStack;
    }

    @Override
    public boolean checkCapabilities(EntityPlayer player, ItemStack targetStack, ItemStack[] materials) {
        return true;
    }

    @Override
    public Collection<Capability> getRequiredCapabilities(ItemStack targetStack, ItemStack[] materials) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public int getRequiredCapabilityLevel(ItemStack targetStack, ItemStack[] materials, Capability capability) {
        return module.getData(targetStack).requiredCapabilities.getCapabilityLevel(capability);
    }

    @Override
    public SchemaType getType() {
        return SchemaType.improvement;
    }

    @Override
    public GlyphData getGlyph() {
        return module.getDefaultData().glyph;
    }
}