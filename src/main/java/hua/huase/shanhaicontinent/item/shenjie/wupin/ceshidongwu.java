package hua.huase.shanhaicontinent.item.shenjie.wupin;

import hua.huase.shanhaicontinent.util.procedures.ceshidongwulei;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ceshidongwu extends Item {
    public ceshidongwu(Properties properties) {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ceshidongwulei.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
        return ar;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.literal("§c注意！本工具还有很多bug，请谨慎使用"));
        list.add(Component.literal("§e目前只能生成一只自定义年限的鸡"));
        list.add(Component.literal("§e其他属性暂未开发！"));
    }
}
