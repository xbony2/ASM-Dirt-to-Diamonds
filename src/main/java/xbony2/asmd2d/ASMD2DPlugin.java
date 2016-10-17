package xbony2.asmd2d;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion("1.10.2")
@TransformerExclusions("xbony2.asmd2d")
public class ASMD2DPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass(){
		return new String[]{ASMD2DClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass(){
		return null;
	}

	@Override
	public String getSetupClass(){
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data){}

	@Override
	public String getAccessTransformerClass(){
		return null;
	}
}
