package ru.homestead.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;
import ru.homestead.block.MediaBlock;
import ru.homestead.block.entity.MediaBlockEntity;
import ru.homestead.client.video.VideoManager;

public class MediaBlockRenderer implements BlockEntityRenderer<MediaBlockEntity> {
    public MediaBlockRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(MediaBlockEntity be, float partial, PoseStack pose, MultiBufferSource buffers,
                       int light, int overlay) {
        VideoManager.sync(be.getBlockPos(), be);
        VideoManager.tickUpload(be.getBlockPos());

        ResourceLocation tex = VideoManager.texture(be.getBlockPos());
        if (tex == null) return;
        if (be.getPlayState() != MediaBlockEntity.PlayState.PLAYING
                && be.getPlayState() != MediaBlockEntity.PlayState.PAUSED) {
            return;
        }

        BlockState state = be.getBlockState();
        Direction facing = state.hasProperty(MediaBlock.FACING) ? state.getValue(MediaBlock.FACING) : Direction.NORTH;

        pose.pushPose();
        // Center and rotate to face outward
        pose.translate(0.5, 0.5, 0.5);
        float yRot = switch (facing) {
            case SOUTH -> 0;
            case WEST -> 90;
            case NORTH -> 180;
            case EAST -> 270;
            default -> 0;
        };
        pose.mulPose(Axis.YP.rotationDegrees(yRot));
        // Move to front face of TV (slightly outside the block)
        pose.translate(0, 0, 0.51);
        pose.scale(1.0F, 1.0F, 1.0F);

        Matrix4f matrix = pose.last().pose();
        VertexConsumer vc = buffers.getBuffer(RenderType.entitySolid(tex));
        float w = 0.9F;
        float h = 0.55F;
        // Quad facing +Z after rotation (viewer)
        vertex(vc, matrix, -w / 2, -h / 2, 0, 0, 1, light, overlay);
        vertex(vc, matrix, -w / 2,  h / 2, 0, 0, 0, light, overlay);
        vertex(vc, matrix,  w / 2,  h / 2, 0, 1, 0, light, overlay);
        vertex(vc, matrix,  w / 2, -h / 2, 0, 1, 1, light, overlay);

        pose.popPose();
    }

    private static void vertex(VertexConsumer vc, Matrix4f m, float x, float y, float z,
                               float u, float v, int light, int overlay) {
        vc.vertex(m, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(0, 0, 1)
                .endVertex();
    }
}
