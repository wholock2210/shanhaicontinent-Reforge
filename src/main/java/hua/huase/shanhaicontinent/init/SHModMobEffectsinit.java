package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.potion.*;
import hua.huase.shanhaicontinent.potion.Iceandfireeyes.Coldice;
import hua.huase.shanhaicontinent.potion.Iceandfireeyes.HotYQ;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.effect.MobEffect;

public class SHModMobEffectsinit {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SHMainBus.MOD_ID);
	public static final RegistryObject<MobEffect> JISHENG = REGISTRY.register("shbasemobeffect", SHBaseMobEffect::new);
	public static final RegistryObject<MobEffect> jineng_jgb_6 		= REGISTRY.register("jineng_jgb_6", Jineng_jgb_6::new);
	public static final RegistryObject<MobEffect> jineng_jgb_7 		= REGISTRY.register("jineng_jgb_7", Jineng_jgb_7::new);
	public static final RegistryObject<MobEffect> jineng_jgb_8 		= REGISTRY.register("jineng_jgb_8", Jineng_jgb_8::new);
	public static final RegistryObject<MobEffect> jineng_huang_6 	= REGISTRY.register("jineng_huang_6", Jineng_huang_6::new);
	public static final RegistryObject<MobEffect> jineng_huang_8 	= REGISTRY.register("jineng_huang_8", Jineng_huang_8::new);
	public static final RegistryObject<MobEffect> jineng_htsc_2 	= REGISTRY.register("jineng_htsc_2", Jineng_htsc_2::new);
	public static final RegistryObject<MobEffect> jineng_htsc_5 	= REGISTRY.register("jineng_htsc_5", Jineng_htsc_5::new);
	public static final RegistryObject<MobEffect> jineng_htsc_6 	= REGISTRY.register("jineng_htsc_6", Jineng_htsc_6::new);
	public static final RegistryObject<MobEffect> zhiwu_bianhua 	= REGISTRY.register("zhiwu_bianhua", ZhiwuBianhua::new);
	public static final RegistryObject<MobEffect> tianshibiyou = REGISTRY.register("tianshibiyou", TianshibiyouMobEffect::new);
	public static final RegistryObject<MobEffect> xiuluo_shixue = REGISTRY.register("xiuluo_shixue", ShixueMobEffect::new);
	public static final RegistryObject<MobEffect> haishen_xinyang = REGISTRY.register("haishen_xinyang", HaiShenxinyang::new);
	public static final RegistryObject<MobEffect> luocha_tianwei = REGISTRY.register("luocha_tianwei", LuochaTianwei::new);
	public static final RegistryObject<MobEffect> COLDICE = REGISTRY.register("coldice", Coldice::new);
	public static final RegistryObject<MobEffect> HOT_YANGQUAN = REGISTRY.register("hot_yangquan", HotYQ::new);
	public static final RegistryObject<MobEffect> FROST_BINDING = REGISTRY.register("frost_binding", FrostBindingEffect::new);

	public static void register(IEventBus eventBus) {
		REGISTRY.register(eventBus);
	}

}
