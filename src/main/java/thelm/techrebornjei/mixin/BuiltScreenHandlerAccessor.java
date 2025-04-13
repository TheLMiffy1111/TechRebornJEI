package thelm.techrebornjei.mixin;

import java.util.List;

import org.apache.commons.lang3.Range;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import reborncore.common.screen.BuiltScreenHandler;

@Mixin(BuiltScreenHandler.class)
public interface BuiltScreenHandlerAccessor {

	@Accessor("playerSlotRanges")
	List<Range<Integer>> trjei$playerSlotRanges();

	@Accessor("blockEntitySlotRanges")
	List<Range<Integer>> trjei$blockEntitySlotRanges();
}
