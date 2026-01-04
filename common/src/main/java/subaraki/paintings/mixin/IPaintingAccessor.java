package subaraki.paintings.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Painting.class)
public interface IPaintingAccessor {

    @Invoker
    void callSetVariant(Holder<PaintingVariant> variant);
}
