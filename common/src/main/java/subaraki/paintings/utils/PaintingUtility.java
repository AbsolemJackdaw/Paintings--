package subaraki.paintings.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.phys.AABB;

public class PaintingUtility {

    public static final ArtComparator ART_COMPARATOR = new ArtComparator();

    // copied this from vanilla , the original one is protected for some reason
    public void updatePaintingBoundingBox(HangingEntity painting) {
        double hangX = (double) painting.getPos().getX() + 0.5D;
        double hangY = (double) painting.getPos().getY() + 0.5D;
        double hangZ = (double) painting.getPos().getZ() + 0.5D;
        double offsetWidth = painting.getBbWidth() % 32 == 0 ? 0.5D : 0.0D;
        double offsetHeight = painting.getBbHeight() % 32 == 0 ? 0.5D : 0.0D;
        hangX = hangX - (double) painting.getDirection().getStepX() * 0.46875D;
        hangZ = hangZ - (double) painting.getDirection().getStepZ() * 0.46875D;
        hangY = hangY + offsetHeight;
        Direction enumfacing = painting.getDirection().getCounterClockWise();
        hangX = hangX + offsetWidth * (double) enumfacing.getStepX();
        hangZ = hangZ + offsetWidth * (double) enumfacing.getStepZ();

        painting.setPosRaw(hangX, hangY, hangZ);
        double widthX = painting.getBbWidth();
        double height = painting.getBbHeight();
        double widthZ = painting.getBbWidth();

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

//    public void setArt(Painting painting, PaintingVariant variant) {
//        painting.level().registryAccess().lookup(Registries.PAINTING_VARIANT).ifPresent(registry -> {
//            var key = registry.getKey(variant);
//            if (key != null)
//                registry.get(key).ifPresent(painting::setVariant);
//        });
//    }
}
