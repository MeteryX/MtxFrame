package at.mtxframe.mtxframe.colors.format;

import at.mtxframe.mtxframe.colors.ColorAPI;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorFormat {
    private ColorAPI colorAPI;

    public ColorFormat(ColorAPI colorAPI) {
        this.colorAPI = colorAPI;
    }

    public String formatText(String text) {

        String colorsCode = colorAPI.getColorConfig().getColorsCode();

        java.util.regex.Pattern pattern = getCustomPattern(colorsCode);

        StringBuilder stringBuilder = new StringBuilder();

        Matcher matcher = pattern.matcher(text);

        int lastIndex = 0;

        // '&'
        while (matcher.find()) {

            int startIndex = matcher.start();
            int endIndex = matcher.end();

            // Before color code
            if (startIndex > lastIndex) {
                stringBuilder.append(text.substring(lastIndex, startIndex));
            }
            if (startIndex + 1 < text.length()) {

                // Get color code
                char colorChar = text.charAt(startIndex + 1);

                String colorCode = String.valueOf(colorChar);

                // Get Gradient
                List<String> hexGradient = colorAPI.getColorConfig().getGradient(colorCode);

                // Gradient
                if (hexGradient != null) {
                    // Create Gradient
                    List<ChatColor> gradiant = Gradient.generateGradient(
                            hexGradient.get(0), hexGradient.get(1), endIndex - startIndex - 2
                    );

                    String gradiantPart = formatGradiant(gradiant, text.substring(startIndex + 2, endIndex));

                    stringBuilder.append(gradiantPart);
                }
                else {
                    // Custom colors
                    ChatColor color = colorAPI.getColorConfig().getColor(colorCode);

                    if (color != null) {
                        stringBuilder.append(color)
                                . append(text.substring(startIndex + 2, endIndex));
                    }
                    else { // No colors
                        stringBuilder.append(text.substring(startIndex, endIndex));
                    }
                }
            } // No color character
            else {
                stringBuilder.append(text.substring(lastIndex, endIndex));
            }
            lastIndex = endIndex + 1;
        }

        // End of message
        if (lastIndex < text.length()) {
            stringBuilder.append(text.substring(lastIndex));
        }

        return stringBuilder.toString();
    }

    private java.util.regex.Pattern getCustomPattern(String colorsCode) {
        // "&[c].*?(?=&[b]|$)"
        // '&c ... to ... &b or end' (for example)
        java.util.regex.Pattern pattern = Pattern.compile(
                "&[" + colorsCode + "].*?(?=&[" + colorsCode + "]|$)");
        return pattern;
    }

    private String formatGradiant(List<ChatColor> gradiant, String part) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < part.length(); i++) {

            char c = part.charAt(i);
            stringBuilder.append("" + gradiant.get(i) + c);
        }
        return stringBuilder.toString();
    }






    public String applyGradientToLine(String text, Color startColor, Color endColor, String pattern) {
        Pattern customPattern = Pattern.compile(pattern);
        Matcher matcher = customPattern.matcher(text);
        StringBuilder stringBuilder = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();

            if (startIndex > lastIndex) {
                stringBuilder.append(text.substring(lastIndex, startIndex));
            }

            String textToGradient = matcher.group();
            List<ChatColor> gradient = generateGradient(startColor, endColor, textToGradient.length());

            for (int i = 0; i < textToGradient.length(); i++) {
                stringBuilder.append(gradient.get(i)).append(textToGradient.charAt(i));
            }

            lastIndex = endIndex;
        }

        if (lastIndex < text.length()) {
            stringBuilder.append(text.substring(lastIndex));
        }

        return stringBuilder.toString();
    }

    private List<ChatColor> generateGradient(Color start, Color end, int steps) {
        List<ChatColor> gradient = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (float) (steps - 1);
            int red = (int) (start.getRed() * (1 - ratio) + end.getRed() * ratio);
            int green = (int) (start.getGreen() * (1 - ratio) + end.getGreen() * ratio);
            int blue = (int) (start.getBlue() * (1 - ratio) + end.getBlue() * ratio);

            gradient.add(ChatColor.of(new Color(red, green, blue)));
        }

        return gradient;
    }





}
