package com.hfr.blocks;

import net.minecraft.block.Block;

public class ModSoundType extends Block.SoundType {

	public ModSoundType(String name, float volume, float pitch) {
		super(name, volume, pitch);
	}
    public String getBreakSound()
    {
        return "hfr:" + super.getBreakSound();
    }

    public String getStepResourcePath()
    {
        return "hfr:" + super.getStepResourcePath();
    }

}
