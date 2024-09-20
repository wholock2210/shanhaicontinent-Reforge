package hua.huase.shanhaicontinent.world.structure.gufengxiaowu;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import hua.huase.shanhaicontinent.world.structure.SHStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class GufengxiaowuStructure extends Structure  {
    public static final Codec<GufengxiaowuStructure> CODEC = simpleCodec(GufengxiaowuStructure::new);

    public GufengxiaowuStructure(Structure.StructureSettings structureSettings) {
        super(structureSettings);
    }



    public StructureStart generate(RegistryAccess registryAccess, ChunkGenerator chunkGenerator, BiomeSource biomeSource, RandomState randomState, StructureTemplateManager structureTemplateManager, long l, ChunkPos chunkPos, int i, LevelHeightAccessor levelHeightAccessor, Predicate<Holder<Biome>> holderPredicate) {
   
        return super.generate( registryAccess,  chunkGenerator,  biomeSource,  randomState,  structureTemplateManager,  l,  chunkPos,  i,  levelHeightAccessor,  holderPredicate);
    }


    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext pContext) {


        int seaLevel = pContext.chunkGenerator().getSeaLevel();
        Rotation rotation = Rotation.getRandom(pContext.random());
//        末地
        if(seaLevel == 0){
            BlockPos blockpos = this.getLowestYIn5by5BoxOffset7Blocks(pContext, rotation);
            if(blockpos.getY() < 40){
                blockpos = blockpos.above(32);
            }
            BlockPos blockpoend = blockpos;
            return blockpoend.getY() < 14 ? Optional.empty() : Optional.of(new Structure.GenerationStub(blockpoend, (structurePiecesBuilder) -> {
                this.generatePieces(structurePiecesBuilder, blockpoend, rotation, pContext);
            }));
        }
//主世界
        BlockPos blockpos = this.getLowestYIn5by5BoxOffset7Blocks(pContext, rotation);
        return blockpos.getY() < 40 ? Optional.empty() : Optional.of(new Structure.GenerationStub(blockpos, (structurePiecesBuilder) -> {
            this.generatePieces(structurePiecesBuilder, blockpos, rotation, pContext);
        }));
    }

    private void generatePieces(StructurePiecesBuilder structurePiecesBuilder, BlockPos blockPos, Rotation rotation, Structure.GenerationContext generationContext) {
        List<StructurePiece> list = Lists.newArrayList();
        GufengxiaowuPiecees.startHouseTower(generationContext.structureTemplateManager(), blockPos, rotation, list, generationContext.random());
        list.forEach(structurePiecesBuilder::addPiece);
    }

    public StructureType<?> type() {
        return SHStructureTypes.gufengxiaowu01.get();
    }


}
