package cubelogiciphoneconcept;

import java.util.ArrayList;
import java.util.Arrays;

public class Level {

    public static String PILLE = "Pille_1.png";
    public static String GLUEHBIRNE = "Vision.png";

    ArrayList<String[]> cubes;
    ArrayList<int[]> coorinates;
    String tipp;
    long time;
    int flagup = 5;

    String item1;
    int item1X, item1Y;
    long restTimeItem1;
    int flag1;
    String item2;
    int item2X, item2Y;
    long restTimeItem2;
    int flag2;
    String item3;
    int item3X, item3Y;
    int flag3;
    long restTimeItem3;

    int w, h;

    boolean change = false;

    public Level(int w, int h) {
        this.cubes = new ArrayList<>();
        this.w = w;
        this.h = h;
        cubes.addAll(Arrays.asList(LEVEL));
        coorinates = new ArrayList<>();
        for (byte i = 0; i < cubes.size(); i++) {
            int[] c = new int[]{40 + (int) (Math.random() * (w - 80)), 90 + (int) (Math.random() * (h - 120))};
            coorinates.add(c);
        }
        coorinates.set(0, new int[]{w / 2, h / 2});
        tipp = getCubeTitle(0);
    }

    public boolean isItemItem1() {
        return item1 != null;
    }

    public boolean isItemItem2() {
        return item2 != null;
    }

    public boolean isItemItem3() {
        return item3 != null;
    }

    public String getItem1() {
        return item1;
    }

    public String getItem2() {
        return item2;
    }

    public String getItem3() {
        return item3;
    }

    public int getItem1X() {
        return item1X;
    }

    public int getItem1Y() {
        return item1Y;
    }

    public int getItem2X() {
        return item2X;
    }

    public int getItem2Y() {
        return item2Y;
    }

    public int getItem3X() {
        return item3X;
    }

    public int getItem3Y() {
        return item3Y;
    }

    public boolean isItem1Flagged() {
        return flag1 > 0;
    }

    public boolean isItem2Flagged() {
        return flag2 > 0;
    }

    public boolean isItem3Flagged() {
        return flag3 > 0;
    }

    public boolean somethingChanged() {
        boolean save = change;
        change = false;
        return save;
    }

    public void advanceTime(long add, Screen screen) {
        time += add;
        flag1--;
        flag2--;
        flag3--;
        restTimeItem1 -= add;
        restTimeItem2 -= add;
        restTimeItem3 -= add;
        if (flag1 == 0) {
            item1 = null;
            change = true;
        }
        if (flag2 == 0) {
            item2 = null;
            change = true;
        }
        if (flag3 == 0) {
            item3 = null;
            change = true;
        }
        if(restTimeItem1 < 0) {
            item1 = null;
            change = true;
        }
        if(restTimeItem2 < 0) {
            item2 = null;
            change = true;
        }
        if(restTimeItem3 < 0) {
            item3 = null;
            change = true;
        }
        if (restTimeItem1 < 0 && time % 20000 > 2000 && time % 20000 < 3000 && item1 == null && !screen.rainbow) {
            item1 = PILLE;
            item1X = 40 + (int) (Math.random() * (w - 80));
            item1Y = 90 + (int) (Math.random() * (h - 120));
            restTimeItem1 = 1000;
            change = true;
        } else if (restTimeItem2 < 0 && time % 20000 > 8000 && time % 20000 < 9999 && item2 == null) {
            item2 = GLUEHBIRNE;
            item2X = 40 + (int) (Math.random() * (w - 80));
            item2Y = 90 + (int) (Math.random() * (h - 120));
            restTimeItem2 = 1000;
            change = true;
        }
    }

    public int size() {
        return cubes.size();
    }

    public String getCubeTitle(int index) {
        if (cubes.size() > index) {
            return cubes.get(index)[0];
        } else {
            return null;
        }
    }

    public String getCubeSub(int index) {
        if (cubes.size() > index) {
            return cubes.get(index)[1];
        } else {
            return null;
        }
    }

    public int getCubeX(int index) {
        if (coorinates.size() > index) {
            return coorinates.get(index)[0];
        } else {
            return -1;
        }
    }

    public int getCubeY(int index) {
        if (coorinates.size() > index) {
            return coorinates.get(index)[1];
        } else {
            return -1;
        }
    }

    public void remove(int index) {
        String[] removed = cubes.remove(index);
        coorinates.remove(index);
        if (removed != null) {
            tipp = removed[0] + " " + removed[1];
        }
    }

    public String click(int x, int y) {
        ImageAsset item1Asset = null;
        if (item1 != null) {
            item1Asset = ImageAsset.tryToGetImageAssetForName(item1);
        }
        ImageAsset item2Asset = null;
        if (item2 != null) {
            item2Asset = ImageAsset.tryToGetImageAssetForName(item2);
        }
        ImageAsset item3Asset = null;
        if (item3 != null) {
            item3Asset = ImageAsset.tryToGetImageAssetForName(item3);
        }

        if (item1Asset != null && x < item1X + (item1Asset.getWidth() * Screen.ITEM_SIZE_FAKTOR) / 2 && x > item1X - (item1Asset.getWidth() * Screen.ITEM_SIZE_FAKTOR) / 2 && y < item1Y + (item1Asset.getHeight() * Screen.ITEM_SIZE_FAKTOR) / 2 && y > item1Y - (item1Asset.getHeight() * Screen.ITEM_SIZE_FAKTOR) / 2) {
            String copy = item1;
            flag1 = flagup;
            change = true;
            return item1;
        } else if (item2Asset != null && x < item2X + (item2Asset.getWidth() * Screen.ITEM_SIZE_FAKTOR) / 2 && x > item2X - (item2Asset.getWidth() * Screen.ITEM_SIZE_FAKTOR) / 2 && y < item2Y + (item2Asset.getHeight() * Screen.ITEM_SIZE_FAKTOR) / 2 && y > item2Y - (item2Asset.getHeight() * Screen.ITEM_SIZE_FAKTOR) / 2) {
            String copy = item2;
            flag2 = flagup;
            change = true;
            return item2;
        } else if (item3Asset != null && x < item3X + (item3Asset.getWidth() * Screen.ITEM_SIZE_FAKTOR) / 2 && x > item3X - (item3Asset.getWidth() * Screen.ITEM_SIZE_FAKTOR) / 2 && y < item3Y + (item3Asset.getHeight() * Screen.ITEM_SIZE_FAKTOR) / 2 && y > item3Y - (item3Asset.getHeight() * Screen.ITEM_SIZE_FAKTOR) / 2) {
            String copy = item3;
            flag3 = flagup;
            change = true;
            return item3;
        } else if (size() > 0 && x < getCubeX(0) + 40 && x > getCubeX(0) - 40 && y < getCubeY(0) + 40 && y > getCubeY(0) - 40) {
            remove(0);
            return "match";
        } else {
            return "no match";
        }
    }

    public String getTipp() {
        return "" + tipp + " !";
    }

    static final String[][] LEVEL = {
        {"3", "+ 3"},
        {"6", "/ 3"},
        {"2", "- 1"},
        {"1", "× 44"},
        {"44", "+ 1"},
        {"45", "× 2"},
        {"90", "- 83"},
        {"7", "+ 9"},
        {"16", "/ 2"},
        {"8", "- 3"},
        {"5", "+ 3"},
        {"8", "/ 2"},
        {"4", "& 5"},
        {"45", "reverse()"},
        {"54", "& 321"},
        {"54321", "reverse()"},
        {"12345", "first(3)"},
        {"123", "last(1)"},
        {"3", "× 2"},
        {"6", "+ 1"},
        {"7", "× 2"},
        {"14", "& 022"},
        {"14022", "reverse()"},
        {"22041", "- 41"},
        {"22000", "+ 222"},
        {"22222", "+ 11111"},
        {"33333", "first(1)"},
        {"3", "& 21"},
        {"321", "reverse())"},
        {"123", "& 45"},
        {"12345", "reverse()"},
        {"54321", "+ 1"},
        {"54322", "last(2)"},
        {"22", "& 11"},
        {"2211", "reverse()"},
        {"1122", "& 0"},
        {"11220", "last(2)"},
        {"20", "- 1"},
        {"19", "+ 700"},
        {"719", "first(2)"},
        {"71", "reverse()"},
        {"17", "+ 3"},
        {"20", "+ 2"},
        {"22", "- 20"},
        {"2", "/ 2"},
        {"1", "& 2"},
        {"12", "& 4"},
        {"124", "& 6"},
        {"1246", "reverse()"},
        {"6421", "last(2)"},
        {"21", "& 0"},
        {"210", "& 12"},
        {"21012", "- 12"},
        {"21000", "last(1)"},
        {"0", "+ 99"},
        {"99", "+ 1"},
        {"100", "+ 11"},
        {"111", "last(2)"},
        {"11", "& 9"},
        {"119", "× 10"},
        {"1190", "- 1190"},
        {"0", "& ne"},
        {"0ne", "first(2)"},
        {"0n", "& line"},
        {"0nline", "last(2)"},
        {"ne", "& ro"},
        {"nero", "& 10"},
        {"nero10", "last(2)"},
        {"10", "× 50"},
        {"500", "& miles"},
        {"500miles", "last(2)"},
        {"es", "& cape"},
        {"escape", "first(3)"},
        {"esc", "last(2)"},
        {"sc", "& ore"},
        {"score", "last(2)"},
        {"re", "reverse()"},
        {"er", "& ror"},
        {"error", "first(1)"},
        {"e", "& lf"},
        {"elf", "& 11"},
        {"elf11", "last(11)"},
        {"11", "+ 2"},
        {"13", "× 2"},
        {"26", "reverse()"},
        {"62", "& 26"},
        {"6226", "first(1)"},
        {"6", "& 66"},
        {"666", ""}};
}
