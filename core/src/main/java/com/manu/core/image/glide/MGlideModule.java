package com.manu.core.image.glide;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Powered by jzman.
 * Created on 2018/10/19 0019.
 */
@GlideModule
public class MGlideModule extends AppGlideModule{
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
