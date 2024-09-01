package hua.huase.shanhaicontinent.world.structure;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.List;

public class GufengxiaowuPiecees {


   public static void startHouseTower(StructureTemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> pieceList, RandomSource randomSource) {

      for (int i = 0; i < 10; i++) {
         for (int j = 0; j < 10; j++) {

            SHPiece gufengxiaowu01 = addHelper(pieceList, new SHPiece(templateManager, "gufengxiaowu01", blockPos.east(20*i).above(20*j), rotation, true));
         }
      }


//      SHPiece gufengxiaowu01 = addHelper(pieceList, new SHPiece(templateManager, "gufengxiaowu01", blockPos, rotation, true));
   }

   static SHPiece addHelper(List<StructurePiece> pieceList, SHPiece SHPiece) {
      pieceList.add(SHPiece);
      return SHPiece;
   }


   public static class SHPiece extends TemplateStructurePiece {
      public SHPiece(StructureTemplateManager structureTemplateManager, String s, BlockPos blockPos, Rotation rotation, boolean b) {
         super(StructurePieceType.END_CITY_PIECE, 0, structureTemplateManager, makeResourceLocation(s), s, makeSettings(b, rotation), blockPos);
      }

      public SHPiece(StructureTemplateManager structureTemplateManager, CompoundTag compoundTag) {
         super(StructurePieceType.END_CITY_PIECE, compoundTag, structureTemplateManager, (p_227512_) -> {
            return makeSettings(compoundTag.getBoolean("OW"), Rotation.valueOf(compoundTag.getString("Rot")));
         });
      }

      private static StructurePlaceSettings makeSettings(boolean b, Rotation rotation) {
         BlockIgnoreProcessor blockignoreprocessor = b ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
         return (new StructurePlaceSettings()).setIgnoreEntities(true).addProcessor(blockignoreprocessor).setRotation(rotation);
      }

      protected ResourceLocation makeTemplateLocation() {
         return makeResourceLocation(this.templateName);
      }

      private static ResourceLocation makeResourceLocation(String s) {
//         return new ResourceLocation("end_city/" + p_227503_);
         return new ResourceLocation(SHMainBus.MOD_ID,"gufengxiaowu01");
      }

      protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
         super.addAdditionalSaveData(pContext, pTag);
         pTag.putString("Rot", this.placeSettings.getRotation().name());
         pTag.putBoolean("OW", this.placeSettings.getProcessors().get(0) == BlockIgnoreProcessor.STRUCTURE_BLOCK);
      }

      protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
         if (pName.startsWith("Chest")) {
            BlockState blockstate = Blocks.CHEST.defaultBlockState();

            this.createChest(pLevel, pBox, pRandom, pPos, BuiltInLootTables.WOODLAND_MANSION, blockstate);


         } else if (pBox.isInside(pPos) && Level.isInSpawnableBounds(pPos)) {
//            if (pName.startsWith("Sentry")) {
//               Shulker shulker = EntityType.SHULKER.create(pLevel.getLevel());
//               if (shulker != null) {
//                  shulker.setPos((double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D);
//                  pLevel.addFreshEntity(shulker);
//               }
//            } else if (pName.startsWith("Elytra")) {
//               ItemFrame itemframe = new ItemFrame(pLevel.getLevel(), pPos, this.placeSettings.getRotation().rotate(Direction.SOUTH));
//               itemframe.setItem(new ItemStack(Items.ELYTRA), false);
//               pLevel.addFreshEntity(itemframe);
//            }
         }

      }
   }

   interface SectionGenerator {
      void init();

      boolean generate(StructureTemplateManager p_227517_, int p_227518_, SHPiece p_227519_, BlockPos p_227520_, List<StructurePiece> p_227521_, RandomSource p_227522_);
   }
}