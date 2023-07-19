package subaraki.paintings.utils;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.phys.AABB;

public class PaintingUtility {

    public static final ArtComparator ART_COMPARATOR = new ArtComparator();

    // copied this from vanilla , the original one is protected for some reason
    public void updatePaintingBoundingBox(HangingEntity painting) {
        double hangX = (double) painting.getPos().getX() + 0.5D;
        double hangY = (double) painting.getPos().getY() + 0.5D;
        double hangZ = (double) painting.getPos().getZ() + 0.5D;
        double offsetWidth = painting.getWidth() % 32 == 0 ? 0.5D : 0.0D;
        double offsetHeight = painting.getHeight() % 32 == 0 ? 0.5D : 0.0D;
        hangX = hangX - (double) painting.getDirection().getStepX() * 0.46875D;
        hangZ = hangZ - (double) painting.getDirection().getStepZ() * 0.46875D;
        hangY = hangY + offsetHeight;
        Direction enumfacing = painting.getDirection().getCounterClockWise();
        hangX = hangX + offsetWidth * (double) enumfacing.getStepX();
        hangZ = hangZ + offsetWidth * (double) enumfacing.getStepZ();

        painting.setPosRaw(hangX, hangY, hangZ);
        double widthX = painting.getWidth();
        double height = painting.getHeight();
        double widthZ = painting.getWidth();

        if (painting.getDirection().getAxis() == Direction.Axis.Z) {
            widthZ = 1.0D;
        } else {
            widthX = 1.0D;
        }

        widthX /= 32.0D;
        height /= 32.0D;
        widthZ /= 32.0D;
        painting.setBoundingBox(new AABB(hangX - widthX, hangY - height, hangZ - widthZ, hangX + widthX, hangY + height, hangZ + widthZ));
    }

    public void setArt(Painting painting, PaintingVariant type) {
        CompoundTag tag = new CompoundTag();
        painting.addAdditionalSaveData(tag);
        String name = BuiltInRegistries.PAINTING_VARIANT.getKey(type).toString();
        tag.putString("variant", name);
        painting.readAdditionalSaveData(tag);
    }

}
