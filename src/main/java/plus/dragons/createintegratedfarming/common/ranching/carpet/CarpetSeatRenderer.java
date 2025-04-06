/*
 * Copyright (C) 2025  DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.createintegratedfarming.common.ranching.carpet;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class CarpetSeatRenderer extends EntityRenderer<CarpetSeatEntity> {
    public CarpetSeatRenderer(Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(CarpetSeatEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(CarpetSeatEntity entity) {
        return MissingTextureAtlasSprite.getLocation();
    }
}
