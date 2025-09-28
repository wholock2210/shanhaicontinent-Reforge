package hua.huase.shanhaicontinent.entity.shenjieentity;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;


public class QiluotulipModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("shanhaicontinent", "qiluotulip"), "main");
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart bone7;
	private final ModelPart bone8;
	private final ModelPart bone9;
	private final ModelPart bone10;
	private final ModelPart bone12;
	private final ModelPart bone11;
	private final ModelPart bone13;
	private final ModelPart bb_main;

	public QiluotulipModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = root.getChild("bone2");
		this.bone3 = root.getChild("bone3");
		this.bone4 = root.getChild("bone4");
		this.bone5 = root.getChild("bone5");
		this.bone6 = root.getChild("bone6");
		this.bone7 = root.getChild("bone7");
		this.bone8 = root.getChild("bone8");
		this.bone9 = root.getChild("bone9");
		this.bone10 = root.getChild("bone10");
		this.bone12 = root.getChild("bone12");
		this.bone11 = root.getChild("bone11");
		this.bone13 = root.getChild("bone13");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 25).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 6).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 3).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0926F, 23.9592F, 0.2275F, 0.0F, 1.6144F, 0.0F));

		PartDefinition cube_r5 = bone2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 25).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8718F, -3.0516F, -0.8701F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r6 = bone2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(10, 3).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.8597F, -3.0066F, -0.8701F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r7 = bone2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 0).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.818F, -2.8192F, -0.8701F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r8 = bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8329F, -2.1714F, -0.8701F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 24.0F, 1.0F, 0.0F, 3.098F, 0.0F));

		PartDefinition cube_r9 = bone3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(14, 25).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r10 = bone3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r11 = bone3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(10, 9).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r12 = bone3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(10, 6).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r13 = bone4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(18, 25).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r14 = bone4.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(10, 15).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r15 = bone4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r16 = bone4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(10, 12).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 23.0F, 0.0F, 0.0F, -0.7418F, 0.0F));

		PartDefinition cube_r17 = bone5.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(22, 25).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r18 = bone5.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(20, 0).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r19 = bone5.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(10, 18).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r20 = bone5.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 23.0F, 0.0F, 0.0F, 0.829F, 0.0F));

		PartDefinition cube_r21 = bone6.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(2, 26).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r22 = bone6.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(20, 9).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r23 = bone6.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(20, 6).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r24 = bone6.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(20, 3).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone7 = partdefinition.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 23.0F, 1.0F, 0.0F, -2.3126F, 0.0F));

		PartDefinition cube_r25 = bone7.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(6, 26).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r26 = bone7.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(20, 18).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r27 = bone7.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(20, 15).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r28 = bone7.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(20, 12).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone8 = partdefinition.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 23.0F, 1.0F, 0.0F, 2.3998F, 0.0F));

		PartDefinition cube_r29 = bone8.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(10, 26).addBox(-3.0F, 0.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0665F, -3.0924F, -1.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r30 = bone8.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(20, 21).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0543F, -3.0474F, -1.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r31 = bone8.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(10, 21).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0127F, -2.8599F, -1.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r32 = bone8.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, 0.0F, 0.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0275F, -2.2122F, -1.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition bone9 = partdefinition.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(22, 28).addBox(-1.5F, -1.0F, -7.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 8.0F));

		PartDefinition cube_r33 = bone9.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(18, 26).addBox(-1.5F, 0.0F, -7.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.1092F, -3.201F, -1.1345F, 0.0F, 0.0F));

		PartDefinition cube_r34 = bone9.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(14, 26).addBox(-0.5F, -0.1832F, -0.7943F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.631F, -6.3738F, 0.829F, 0.0F, 0.0F));

		PartDefinition cube_r35 = bone9.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 24).addBox(-1.5F, -5.0F, -7.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6807F, 0.1471F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r36 = bone9.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(24, 28).addBox(-1.5F, -1.0F, -7.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0126F, 0.0664F, -0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r37 = bone9.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(26, 28).addBox(-1.5F, -1.0F, -7.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.0212F, -0.0641F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(18, 24).addBox(-0.4624F, 0.1686F, -0.1803F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 15.0F, 2.0F, -0.9163F, 0.0F, 0.0873F));

		PartDefinition cube_r38 = bone10.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(2, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4624F, 0.1686F, 0.8198F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r39 = bone10.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(22, 26).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5376F, 0.1686F, -0.1803F, 0.0F, 0.0F, -0.3491F));

		PartDefinition cube_r40 = bone10.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(14, 27).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4624F, -0.0478F, -1.1568F, -0.2182F, 0.0F, 0.0F));

		PartDefinition cube_r41 = bone10.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(14, 24).addBox(-1.0112F, 0.0022F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4624F, 0.1677F, -0.1803F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone12 = partdefinition.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(-0.5341F, 19.8191F, 0.5938F));

		PartDefinition cube_r42 = bone12.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(8, 29).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0341F, -3.8032F, 1.7604F, 0.6545F, 0.0F, 0.0F));

		PartDefinition cube_r43 = bone12.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(6, 29).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0341F, -6.0145F, -1.5866F, -2.3998F, 0.0F, 0.0F));

		PartDefinition cube_r44 = bone12.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(4, 29).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0341F, -5.3832F, -0.3091F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r45 = bone12.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(2, 29).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0341F, -6.0367F, 0.4042F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r46 = bone12.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0341F, -5.083F, 0.7048F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r47 = bone12.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(28, 28).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0341F, -4.5971F, 1.1525F, 0.6545F, 0.0F, 0.0F));

		PartDefinition bone11 = partdefinition.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(2, 28).addBox(-0.7037F, 0.4309F, 0.7519F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 28).addBox(-0.7037F, 0.4309F, -0.2481F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 13.0F, -1.0F, 1.3467F, -0.5877F, -1.549F));

		PartDefinition cube_r48 = bone11.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(22, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2963F, 0.4309F, 0.7519F, 0.0436F, 0.0F, -0.1745F));

		PartDefinition cube_r49 = bone11.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(26, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6887F, 0.2573F, 0.7519F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cube_r50 = bone11.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(6, 27).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6953F, 0.3004F, -0.2481F, 0.0F, 0.0F, 0.1309F));

		PartDefinition cube_r51 = bone11.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(2, 27).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2963F, 0.4309F, -0.2481F, 0.0F, 0.0F, -0.1745F));

		PartDefinition cube_r52 = bone11.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(10, 28).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7037F, 0.4309F, 1.7521F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r53 = bone11.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(22, 27).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7037F, 0.1721F, -1.2142F, -0.2618F, 0.0F, 0.0F));

		PartDefinition bone13 = partdefinition.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(18, 27).addBox(-4.7318F, 3.612F, 1.8722F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 28).addBox(-4.7318F, 3.612F, 0.8722F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 13.0F, -1.0F, 1.1285F, -0.5877F, -1.549F));

		PartDefinition cube_r54 = bone13.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(6, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7318F, 3.612F, 1.8722F, 0.0F, 0.0F, -0.1745F));

		PartDefinition cube_r55 = bone13.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(10, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 27).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.7234F, 3.4814F, 1.8722F, 0.0F, 0.0F, 0.1309F));

		PartDefinition cube_r56 = bone13.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(18, 28).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.7318F, 3.6119F, 2.8724F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r57 = bone13.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(26, 27).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.7318F, 3.3531F, -0.094F, -0.2618F, 0.0F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(2, 25).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r58 = bb_main.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(26, 25).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5341F, -4.1809F, 0.5938F, -1.1078F, 0.1671F, -0.2024F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone9.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone10.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone12.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone11.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone13.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}