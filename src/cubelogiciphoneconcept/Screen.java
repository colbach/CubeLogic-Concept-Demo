package cubelogiciphoneconcept;

import static cubelogiciphoneconcept.Helper.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import javax.swing.JPanel;

public class Screen extends JPanel {

    public static Color BACKGROUND_COLOR = Color.decode("#D1EEEE");
    public static int ITEM_SIZE_FAKTOR = 5;

    boolean needsActiveRedraw = true;
    boolean printPaintComponent = false;
    boolean blank = false;
    double restTime;
    double timeFactor;
    Level level;
    boolean gameover;
    float gameoverAnimation;
    long points;
    long displayPoints;
    double vorsprungProTreffer;
    double punkteProTreffer;
    double punkteProFehler;
    double punkteMultiplikator;
    boolean ersterTreffer;
    String tipp;
    boolean rainbow;
    long rainbowi;
    float licht;

    BufferedImage layer0; // Spielfläche
    int lastLevelSize;

    public Screen() {
        init();
    }

    public final void init() {
        this.restTime = 40000;//30000;
        this.timeFactor = 1;
        this.layer0 = null;
        this.level = null;
        this.lastLevelSize = -1;
        this.gameover = false;
        this.gameoverAnimation = 0;
        this.points = 0;
        this.displayPoints = 0;
        this.vorsprungProTreffer = 5000;
        this.punkteProTreffer = 10;
        this.punkteProFehler = 2;
        this.punkteMultiplikator = 1;
        this.ersterTreffer = false;
        this.tipp = null;
        this.rainbowi = 0;
        this.rainbow = false;
        this.licht = 0;
    }

    public boolean needsActiveRedraw() {
        return needsActiveRedraw;
    }

    public void decrementTime(long representedTime) {
        long step = (long) (punkteProTreffer / 3);
        if(licht > 0) {
            licht -= ((float) representedTime)/4000;
        } else {
            licht = 0;
        }
        if (displayPoints + step < points) {
            displayPoints += step;
        } else {
            displayPoints = points;
        }
        rainbowi += timeFactor * representedTime / 10;
        if (ersterTreffer) {
            if (level != null) {
                level.advanceTime(representedTime, this);
            }
            restTime -= timeFactor * representedTime;
            if (gameover) {
                if (gameoverAnimation < 1) {
                    gameoverAnimation += 0.1;
                } else {
                    gameoverAnimation = 1;
                }
            }
            if (restTime < 0) {
                gameover = true;
                return;
            }
        }
        if(rainbow && timeFactor < 1) {
            timeFactor += 0.004;
        } else if(rainbow) {
            timeFactor = 1;
            rainbow = false;
            level.change = true;
        }
    }

    public void click(int x, int y) {
        String match = level.click(x, y);
        if (match.equals("match")) {
            ersterTreffer = true;
            restTime += vorsprungProTreffer;
            vorsprungProTreffer *= 0.95;
            points += punkteProTreffer * punkteMultiplikator;
            tipp = null;
        } else if (match.equals(Level.PILLE)) {
            rainbow = true;
            timeFactor = 0.55;
            licht = 0;
        } else if (match.equals(Level.GLUEHBIRNE)) {
            rainbow = false;
            licht = 1;
        } else if (match.equals("no match")) {
            points -= punkteProFehler;
            tipp = level.getTipp();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (printPaintComponent) {
            System.out.println("paintComponent()");
        }
        Graphics2D g = (Graphics2D) graphics;
        if (gameover) {
            drawGameoverScreen(g, getWidth(), getHeight(), (int) (points * gameoverAnimation));
            return;
        }
        if (level == null) {
            this.level = new Level(getWidth(), getHeight());
        }
        if (blank) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            return;
        }
        if (layer0 == null) {
            layer0 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        // Hintergrund...
        if (rainbow) {
            drawRainbowBG(g, getWidth(), getHeight(), rainbowi);
        } else {
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
            if(licht > 0) {
                g.setColor(new Color(1f, 1f, 0f, licht));
                g.fillRect(0, 0, getWidth(), getHeight());
            } 
        }

        // layer0...
        if (lastLevelSize != level.size() || level.somethingChanged()) {
            System.out.println("Cubes neu zeichnen");
            layer0 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g0 = (Graphics2D) layer0.getGraphics();

            for (int i = level.size() - 1; i >= 0; i--) {

                float a =  (i / 40f) - (0.2f*(1f-licht));
                if(i!=0) {
                    a += 0.1f;
                }
                
                Helper.drawCube(g0, level.getCubeX(i), level.getCubeY(i), a, level.getCubeTitle(i), level.getCubeSub(i), this);
            }
            lastLevelSize = level.size();

            if (level.isItemItem1()) {
                ImageAsset b = ImageAsset.tryToGetImageAssetForName(level.getItem1());
                if (level.isItem1Flagged()) {
                    b.drawCenteredInverted(g0, level.getItem1X(), level.getItem1Y(), ITEM_SIZE_FAKTOR);
                } else {
                    b.drawCentered(g0, level.getItem1X(), level.getItem1Y(), ITEM_SIZE_FAKTOR);
                }
            }
            if (level.isItemItem2()) {
                ImageAsset b = ImageAsset.tryToGetImageAssetForName(level.getItem2());
                if (level.isItem2Flagged()) {
                    b.drawCenteredInverted(g0, level.getItem2X(), level.getItem2Y(), ITEM_SIZE_FAKTOR);
                } else {
                    b.drawCentered(g0, level.getItem2X(), level.getItem2Y(), ITEM_SIZE_FAKTOR);
                }
            }
            if (level.isItemItem3()) {
                ImageAsset b = ImageAsset.tryToGetImageAssetForName(level.getItem3());
                if (level.isItem3Flagged()) {
                    b.drawCenteredInverted(g0, level.getItem3X(), level.getItem3Y(), ITEM_SIZE_FAKTOR);
                } else {
                    b.drawCentered(g0, level.getItem3X(), level.getItem3Y(), ITEM_SIZE_FAKTOR);
                }
            }
        }

        // Layer zeichnen...
        //BufferedImage blured = Blur.blur(layer0, Math.random());
        //g.drawImage(blured, 0, 0, this);
        g.drawImage(layer0, 0, 0, this);

        /* ImageAsset h = ImageAsset.tryToGetImageAssetForName("Herz.png");
        h.drawCentered(g, getWidth()/2, getHeight()/2, 4);
        
        ImageAsset b = ImageAsset.tryToGetImageAssetForName("Brille.png");
        b.drawCentered(g, getWidth()/3, getHeight()/3, 4);
         */
        // Start-Overlay...
        if (!ersterTreffer) {
            drawStartOverlay(g, getWidth(), getHeight());
        }

        // Tipp-BG zeichnen...
        drawTippOverlayBG(g, getWidth(), tipp);

        // Immer-Variablen zeichnen...
        long sec = ((long) restTime) / 1000;
        long zehn = (((long) restTime) / 100) % 10;
        drawPixelTime(g, sec + "," + zehn + "s", !ersterTreffer || tipp != null);

        drawPixelPoints(g, displayPoints, !ersterTreffer || tipp != null);

        // Tipp-Text zeichnen...
        drawTippOverlayText(g, getWidth(), tipp);
    }

}
