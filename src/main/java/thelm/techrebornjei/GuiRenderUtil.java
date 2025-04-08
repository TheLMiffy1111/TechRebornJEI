package thelm.techrebornjei;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class GuiRenderUtil {

	public static void blitTiledSprite(PoseStack poseStack, TextureAtlasSprite sprite, int x, int y, float width, float height, int textureWidth, int textureHeight) {
		for(int i = 0; i < width; i += textureWidth) {
			float drawWidth = Math.min(textureWidth, width - i);
			for(int j = 0; j < height; j += textureHeight) {
				float drawHeight = Math.min(textureHeight, height - j);
				blitSprite(poseStack, sprite, x + i, y + j, 0, 0, drawWidth, drawHeight, textureWidth, textureHeight);
			}
		}
	}

	public static void blitSprite(PoseStack poseStack, TextureAtlasSprite sprite, int x, int y, float uOffset, float vOffset, int textureWidth, int textureHeight) {
		blitSprite(poseStack, sprite, x, y, uOffset, vOffset, textureWidth, textureHeight, textureWidth, textureHeight);
	}

	public static void blitSprite(PoseStack poseStack, TextureAtlasSprite sprite, int x, int y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		float spriteWidth = sprite.getU1() - sprite.getU0();
		float spriteHeight = sprite.getV1() - sprite.getV0();
		float uMin = sprite.getU0() + uOffset / textureWidth * spriteWidth;
		float uMax = sprite.getU0() + (uOffset + width) / textureWidth * spriteWidth;
		float vMin = sprite.getV0() + vOffset / textureHeight * spriteHeight;
		float vMax = sprite.getV0() + (vOffset + height) / textureHeight * spriteHeight;
		RenderSystem.setShaderTexture(0, sprite.atlasLocation());
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(matrix, x, y, 0).uv(uMin, vMin).endVertex();
		bufferBuilder.vertex(matrix, x, y + height, 0).uv(uMin, vMax).endVertex();
		bufferBuilder.vertex(matrix, x + width, y + height, 0).uv(uMax, vMax).endVertex();
		bufferBuilder.vertex(matrix, x + width, y, 0).uv(uMax, vMin).endVertex();
		BufferUploader.drawWithShader(bufferBuilder.end());
	}
}
