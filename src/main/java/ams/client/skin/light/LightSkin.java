package ams.client.skin.light;

import com.alee.api.resource.ClassResource;
import com.alee.managers.style.XmlSkin;

public class LightSkin extends XmlSkin {
    public LightSkin() {
        // 引入皮肤样式
        super(new ClassResource(LightSkin.class, "resources/light-skin.xml"));
    }
}