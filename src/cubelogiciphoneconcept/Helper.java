package cubelogiciphoneconcept;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Helper {

    public static void enableAntialiasing(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public static void disableAntialiasing(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public static void drawPixelTime(Graphics2D g, String s, boolean invert) {
        BufferedImage tmp = new BufferedImage(100, 12, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tmpg = (Graphics2D) tmp.getGraphics();
        disableAntialiasing(tmpg);
        tmpg.setFont(new Font("Arial", Font.PLAIN, 9));
        if (invert) {
            tmpg.setColor(Color.BLACK);
        } else {
            tmpg.setColor(Color.WHITE);
        }
        int off = 1;
        tmpg.drawString(s, 2 + off, 8 + off);
        if (invert) {
            tmpg.setColor(Color.WHITE);
        } else {
            tmpg.setColor(Color.BLACK);
        }
        tmpg.drawString(s, 2, 8);
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 4 / 12);
        at.scale(4, 4);
        at.translate(-1, 2);
        g.drawImage(tmp, at, null);

        // Workaround wegen Pixelfehlers //
        if (invert) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3));
            g.drawLine(12, 56, 316, 35);
        }
    }

    public static void drawPixelPoints(Graphics2D g, long points, boolean invert) {

        String s = "" + points + "pt";

        BufferedImage tmp = new BufferedImage(500, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tmpg = (Graphics2D) tmp.getGraphics();
        disableAntialiasing(tmpg);
        tmpg.setFont(new Font("Arial", Font.PLAIN, 9));
        if (invert) {
            tmpg.setColor(Color.BLACK);
        } else {
            tmpg.setColor(Color.WHITE);
        }
        int off = 1;
        tmpg.drawString(s, 2 + off, 8 + off);
        if (invert) {
            tmpg.setColor(Color.WHITE);
        } else {
            tmpg.setColor(Color.BLACK);
        }
        tmpg.drawString(s, 2, 8);
        int sw = tmpg.getFontMetrics().stringWidth(s);
        AffineTransform at = new AffineTransform();
        at.rotate(Math.PI / 4 / 14);
        float factor = 4;
        at.scale(factor, factor);
        at.translate(76 - sw, -2);
        g.drawImage(tmp, at, null);
    }

    public static void drawStartOverlay(Graphics2D g, int width, int height) {
        enableAntialiasing(g);
        {
            g.setColor(Color.BLACK);
            int y = 330;
            int k = 30;
            int[] xs = new int[]{
                0, width / 2, width, width, 0
            };
            int[] ys = new int[]{
                y + k, y, y + k, height, height
            };
            g.fillPolygon(xs, ys, xs.length);
        }
        {
            g.setColor(Color.BLACK);
            int y = 230;
            int k = 30;
            int[] xs = new int[]{
                0, width / 2, width, width, 0
            };
            int[] ys = new int[]{
                y - k, y, y - k, 0, 0
            };
            g.fillPolygon(xs, ys, xs.length);
        }
        enableAntialiasing(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        String s1 = "Tap auf Cube [ 3 ]";
        String s2 = "um zu starten";
        int s1w = g.getFontMetrics().stringWidth(s1);
        int s2w = g.getFontMetrics().stringWidth(s2);
        g.drawString(s1, width / 2 - s1w / 2, height - 120);
        g.drawString(s2, width / 2 - s2w / 2, height - 70);

    }

    public static void drawTippOverlayBG(Graphics2D g, int width, String tipp) {
        if (tipp == null) {
            return;
        }
        enableAntialiasing(g);
        {
            g.setColor(Color.BLACK);
            int y = 70;
            int k = 10;
            int[] xs = new int[]{
                0, width / 2, width, width, 0
            };
            int[] ys = new int[]{
                y - k, y, y - k, 0, 0
            };
            g.fillPolygon(xs, ys, xs.length);
        }
    }

    public static void drawTippOverlayText(Graphics2D g, int width, String tipp) {
        if (tipp == null) {
            return;
        }
        enableAntialiasing(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.ITALIC, 25));
        int tippw = g.getFontMetrics().stringWidth(tipp);
        g.drawString(tipp, width / 2 - tippw / 2, 60);
    }

    public static void drawRainbowBG(Graphics2D g, int width, int heigth, long ri) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < heigth + width; i++) {
            Color c = Color.getHSBColor((ri % 256f) / 256f, 1f, .6f);
            g.setColor(c);
            g.drawLine(0, y, x, 0);
            x++;
            y++;
            ri++;
        }
    }

    public static void drawGameoverScreen(Graphics2D g, int width, int height, int points) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        enableAntialiasing(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 55));
        g.drawString("Score:", 80, 430);

        disableAntialiasing(g);
        String s = "" + points + "pt";
        BufferedImage tmp = new BufferedImage(500, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tmpg = (Graphics2D) tmp.getGraphics();
        disableAntialiasing(tmpg);
        tmpg.setFont(new Font("Arial", Font.PLAIN, 9));
        tmpg.setColor(Color.WHITE);
        tmpg.drawString(s, 2, 8);
        int sw = tmpg.getFontMetrics().stringWidth(s);
        AffineTransform at = new AffineTransform();
        float factor = 5;
        at.scale(factor, factor);
        at.translate(30 - sw / 2, 90);
        g.drawImage(tmp, at, null);

        enableAntialiasing(g);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        AffineTransform orig = g.getTransform();
        g.rotate(-0.2);
        g.setColor(Color.WHITE);
        g.drawString("GAME", -25, 220);
        g.drawString("OVER", -35, 300);
        g.setTransform(orig);

    }

    public static void drawCube(Graphics2D g, int centerX, int centerY, float a, String line1, String line2, Screen screen) {

        if (a > 1) { // muss nicht gezeichnet werden
            return;
        } else if (a < 0) { // voll sichtbar
            a = 0;
        }

        int side = 15;
        int rect = 60;
        int total = side + rect;

        Color white = new Color(1f, 1f, 1f, 1);
        Color grey = new Color(0.7f, 0.7f, 0.7f, 1);
        Color black = new Color(0.1f, 0.1f, 0.1f, 1);

        Font f1 = new Font("HelveticaNeue-Thin", Font.PLAIN, 20);
        Font f2 = new Font("HelveticaNeue-Thin", Font.PLAIN, 15);

        {
            g.setColor(grey);
            int[] xs = new int[]{
                centerX - total / 2 + rect,
                centerX - total / 2 + rect + side,
                centerX - total / 2 + rect + side,
                centerX - total / 2 + rect
            };
            int[] ys = new int[]{
                centerY - total / 2,
                centerY - total / 2 + side,
                centerY - total / 2 + side + rect,
                centerY - total / 2 + rect
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        {
            g.setColor(black);
            int[] xs = new int[]{
                centerX - total / 2,
                centerX - total / 2 + rect,
                centerX - total / 2 + rect + side,
                centerX - total / 2 + side
            };
            int[] ys = new int[]{
                centerY - total / 2 + rect,
                centerY - total / 2 + rect,
                centerY - total / 2 + rect + side,
                centerY - total / 2 + rect + side
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        g.setColor(white);
        g.fillRect(centerX - total / 2, centerY - total / 2, rect, rect);

        g.setColor(black);
        enableAntialiasing(g);

        g.setFont(f1);
        int w1 = g.getFontMetrics().stringWidth(line1);
        g.drawString(line1, centerX - side / 2 - w1 / 2, centerY - side / 2 - 5);

        g.setFont(f2);
        int w2 = g.getFontMetrics().stringWidth(line2);
        g.drawString(line2, centerX - side / 2 - w2 / 2, centerY - side / 2 + 18);

        {
            if (screen.rainbow) {
                g.setColor(new Color(30, 30, 30, (int) (a * 255)));
            } else {
                g.setColor(new Color(Screen.BACKGROUND_COLOR.getRed(), Screen.BACKGROUND_COLOR.getGreen(), Screen.BACKGROUND_COLOR.getBlue(), (int) (a * 255)));
            }
            int[] xs = new int[]{
                centerX - total / 2,
                centerX - total / 2 + rect,
                centerX - total / 2 + rect + side,
                centerX - total / 2 + rect + side,
                centerX - total / 2 + side - 1,
                centerX - total / 2 - 1
            };
            int[] ys = new int[]{
                centerY - total / 2,
                centerY - total / 2 - 1,
                centerY - total / 2 + side - 1,
                centerY - total / 2 + side + rect,
                centerY - total / 2 + side + rect,
                centerY - total / 2 + rect,};
            g.fillPolygon(xs, ys, xs.length);
        }
    }

}
