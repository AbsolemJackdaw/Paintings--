package subaraki.paintings.util;

import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class PaintingUtility {

    // copied this from vanilla , the original one is protected for some reason
    public void updatePaintingBoundingBox(HangingEntity painting) {

        if (painting.getHorizontalFacing() != null) {
            double hangX = (double) painting.getHangingPosition().getX() + 0.5D;
            double hangY = (double) painting.getHangingPosition().getY() + 0.5D;
            double hangZ = (double) painting.getHangingPosition().getZ() + 0.5D;
            double offsetWidth = painting.getWidthPixels() % 32 == 0 ? 0.5D : 0.0D;
            double offsetHeight = painting.getHeightPixels() % 32 == 0 ? 0.5D : 0.0D;
            hangX = hangX - (double) painting.getHorizontalFacing().getXOffset() * 0.46875D;
            hangZ = hangZ - (double) painting.getHorizontalFacing().getZOffset() * 0.46875D;
            hangY = hangY + offsetHeight;
            Direction enumfacing = painting.getHorizontalFacing().rotateYCCW();
            hangX = hangX + offsetWidth * (double) enumfacing.getXOffset();
            hangZ = hangZ + offsetWidth * (double) enumfacing.getZOffset();
            
            painting.setRawPosition(hangX, hangY, hangZ);
            double widthX = (double) painting.getWidthPixels();
            double height = (double) painting.getHeightPixels();
            double widthZ = (double) painting.getWidthPixels();

            if (painting.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
                widthZ = 1.0D;
            } else {
                widthX = 1.0D;
            }

            widthX = widthX / 32.0D;
            height = height / 32.0D;
            widthZ = widthZ / 32.0D;
            painting.setBoundingBox(new AxisAlignedBB(hangX - widthX, hangY - height, hangZ - widthZ, hangX + widthX, hangY + height, hangZ + widthZ));
        }
    }
    
    public void setArt(PaintingEntity painting, PaintingType type) {
        CompoundNBT tag = new CompoundNBT();
        painting.writeAdditional(tag);
        tag.putString("Motive", type.getRegistryName().toString());
        painting.readAdditional(tag);
    }

}
