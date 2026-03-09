package hua.huase.shanhaicontinent.item.shenjie.wupin;

import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hungufen extends Item {
    public Hungufen(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        CompoundTag tag = stack.getTag();

        // 检查是否存在tag，如果存在则显示继承属性
        if (tag != null && tag.contains("shanhaiitematuble", Tag.TAG_COMPOUND)) {
            CompoundTag attributesTag = tag.getCompound("shanhaiitematuble");

            Map<String, String> attributeNames = new HashMap<>();
            attributeNames.put("wugong", "物攻");
            attributeNames.put("wufang", "物防");
            attributeNames.put("wuchuan", "物穿");
            attributeNames.put("maxshengming", "最大生命");
            attributeNames.put("baojilv", "暴击率");
            attributeNames.put("baojishanghai", "暴击伤害");
            attributeNames.put("kangbao", "抗暴");
            attributeNames.put("xixue", "吸血");
            attributeNames.put("zhenshang", "真实伤害");
            attributeNames.put("mingzhong", "命中");
            attributeNames.put("shanbi", "闪避");
            attributeNames.put("shengminghuifu", "生命回复");

            // 遍历tag中的所有属性，并根据映射表来显示中文名称
            for (String key : attributesTag.getAllKeys()) {
                // 统一使用getFloat读取属性值，确保兼容性
                float value = 0.0f;
                if (attributesTag.contains(key, Tag.TAG_FLOAT)) {
                    value = attributesTag.getFloat(key);
                } else if (attributesTag.contains(key, Tag.TAG_INT)) {
                    value = (float) attributesTag.getInt(key);
                }

                // 检查属性是否存在映射，如果有则显示中文名称
                String displayName = attributeNames.getOrDefault(key, key);

                // 根据不同属性设置颜色和格式化
                switch (key) {
                    case "wugong":
                    case "wufang":
                    case "wuchuan":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.GREEN);
                        break;
                    case "maxshengming":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.RED);
                        break;
                    case "baojilv":
                    case "baojishanghai":
                    case "kangbao":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.GOLD);
                        break;
                    case "xixue":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.RED);
                        break;
                    case "zhenshang":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.BLUE);
                        break;
                    case "mingzhong":
                    case "shanbi":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.LIGHT_PURPLE);
                        break;
                    case "shengminghuifu":
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.BLUE);
                        break;
                    default:
                        addTooltipLine(tooltip, displayName + ": " + value, ChatFormatting.GRAY);
                        break;
                }
            }
        } else {
            tooltip.add(Component.literal("§7无附加属性"));
        }
    }

    private void addTooltipLine(List<Component> tooltip, String text, ChatFormatting color) {
        tooltip.add(Component.literal(text).withStyle(color));
    }
}