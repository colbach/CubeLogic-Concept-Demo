package cubelogiciphoneconcept;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Diese Klasse representiert eine Bildresource.
 */
public class ImageAsset {

    private static final HashMap<String, ImageAsset> IMAGE_ASSETS = new HashMap<>();

    private final String assetName;
    private final BufferedImage image;
    private BufferedImage invertedImageOrNull;

    private String PATH_FOR_ASSETS = new File("assets").getAbsolutePath();

    /**
     * Konstruktor um ImageAsset zu erzeugen. Dieser Konstruktor ist privat da
     * die Methode getImageAssetForName(String) benutzt werden soll.
     *
     * @param assetName
     * @throws IOException
     */
    private ImageAsset(String assetName) throws IOException {

        this.assetName = assetName;

        File file = new File(PATH_FOR_ASSETS + "/" + assetName);

        // Test ob Bild existiert...
        if (!file.exists()) { // Bild existiert nicht

            // Dateiendungen testen...
            File pngFile = new File(PATH_FOR_ASSETS + "/" + assetName + ".png");
            File jpegFile = new File(PATH_FOR_ASSETS + "/" + assetName + ".jpeg");
            File jpgFile = new File(PATH_FOR_ASSETS + "/" + assetName + ".jpg");
            if (pngFile.exists()) {
                file = pngFile;
            } else if (jpegFile.exists()) {
                file = jpegFile;
            } else if (jpegFile.exists()) {
                file = jpegFile;
            }
        }

        if (!file.exists()) { // Warnung ausgeben
            System.err.println(file.getAbsolutePath() + " existiert nicht!");
        }

        // Buffered Image in den Speicher laden...
        image = ImageIO.read(file);
    }
    
    public static ImageAsset tryToGetImageAssetForName(String assetName) {
        try {
            return getImageAssetForName(assetName);
        } catch (IOException ex) {}
        return null;
    }

    /**
     * Gibt ImageAsset fuer Dateiname zurueck. Diese werden nur ein mal von der
     * Festplatte geladen und dann im Speicher gehalten.
     */
    public static ImageAsset getImageAssetForName(String assetName) throws IOException {

        // Nach bereits geladenem ImageAsset mit diesem Namen suchen... 
        ImageAsset asset = IMAGE_ASSETS.get(assetName);

        if (asset != null) { // Asset existiert bereits im Speicher
            return asset;
        } else { // Asset existiert noch nicht
            asset = new ImageAsset(assetName);
            IMAGE_ASSETS.put(assetName, asset);
            return asset;
        }
    }

    /**
     * Zeichnet Bild.
     */
    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    /**
     * Zeichnet Bild zentriert.
     */
    public void drawCentered(Graphics2D g, int x, int y) {
        g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
    }
    
    /**
     * Zeichnet Bild zentriert mit Factor.
     */
    public void drawCentered(Graphics2D g, int x, int y, double factor) {
        g.drawImage(image, x - (int)(getWidth()*factor) / 2, y - (int)(getHeight()*factor) / 2, (int)(getWidth()*factor), (int)(getHeight()*factor), null);
    }

    public void drawCenteredInverted(Graphics2D g, int x, int y) {
        BufferedImage inverted = getInvertedImage();
        g.drawImage(inverted, x - inverted.getWidth() / 2, y - inverted.getHeight() / 2, null);
    }

    void drawCenteredInverted(Graphics2D g, int x, int y, double factor) {
        BufferedImage inverted = getInvertedImage();
        g.drawImage(inverted, x - (int)(getWidth()*factor) / 2, y - (int)(getHeight()*factor) / 2, (int)(getWidth()*factor), (int)(getHeight()*factor), null);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public String getName() {
        return assetName;
    }

    public BufferedImage getImage() {
        return image;
    }

    public synchronized BufferedImage getInvertedImage() {

        if (invertedImageOrNull == null) {

            // Abmasse...
            int width = image.getWidth();
            int height = image.getHeight();

            // neues BufferedImage erstellen...
            invertedImageOrNull = new BufferedImage(width, height, image.getType());

            // Pixel kopieren...
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    int rgbValue = image.getRGB(x, y);
                    Color color = new Color(rgbValue);
                    int red = 255 - color.getRed();
                    int green = 255 - color.getGreen();
                    int blue = 255 - color.getBlue();
                    color = new Color(red, green, blue);
                    invertedImageOrNull.setRGB(x, y, color.getRGB());
                }
            }

            // Alpha-Raster kopieren...
            invertedImageOrNull.getAlphaRaster().setRect(image.getAlphaRaster());

        }
        
        return invertedImageOrNull;
    }
}
