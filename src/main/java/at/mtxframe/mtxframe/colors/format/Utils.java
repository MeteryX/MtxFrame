package at.mtxframe.mtxframe.colors.format;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.config.ColorConfig;

import java.awt.*;

public class Utils {

    public static java.awt.Color hexToRgb(String hexColor) {
        hexColor = hexColor.replace("#", "");
        int rgb = Integer.parseInt(hexColor, 16);

        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return new Color(red, green, blue);
    }
}
