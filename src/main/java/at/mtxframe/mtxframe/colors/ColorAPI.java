package at.mtxframe.mtxframe.colors;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.config.ColorConfig;
import at.mtxframe.mtxframe.colors.format.ColorFormat;

public class ColorAPI {

    private MtxFrame plugin;
    private ColorConfig colorConfig;
    private ColorFormat colorFormat;

    public ColorAPI(MtxFrame plugin) {
        this.plugin = plugin;
        this.colorConfig = new ColorConfig(plugin);
        this.colorFormat = new ColorFormat(this);
    }

    public String translateColorCodes(String text) {
        return colorFormat.formatText(text);
    }

    public ColorConfig getColorConfig() {
        return colorConfig;
    }

}
