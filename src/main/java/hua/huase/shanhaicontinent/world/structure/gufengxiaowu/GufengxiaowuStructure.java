package hua.huase.shanhaicontinent.world.structure.gufengxiaowu;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import hua.huase.shanhaicontinent.world.structure.SHStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
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


        Rotation rotation = Rotation.getRandom(pContext.random());
        BlockPos blockpos = this.getLowestYIn5by5BoxOffset7Blocks(pContext, rotation);
        return blockpos.getY() < 60 ? Optional.empty() : Optional.of(new Structure.GenerationStub(blockpos, (structurePiecesBuilder) -> {
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
