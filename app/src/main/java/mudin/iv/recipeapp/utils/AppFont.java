package mudin.iv.recipeapp.utils;

public enum AppFont {

    TITILLIUM_SEMI_BOLD("Titillium_SemiBold.ttf"),
    TITILLIUM_LIGHT("Titillium_Ligh.ttft"),
    TITILLIUM_REGULAR("Titillium_Regular.ttf"),
    TITILLIUM_BOLD("Titillium_Bold.ttf"),
    ROBOTO_REGULAR("RobotoCondensed-Regular.ttf"),
    ROBOTO_LIGHT("RobotoCondensed-Light.ttf"),
    ROBOTO_BOLD("RobotoCondensed-Bold.ttf"),
    ROBOTO_BOLDITALIC("RobotoCondensed-BoldItalic.ttf");

    //class for using custom font
    private String value;


    AppFont(String val) {
        value = val;
    }


    @Override
    public String toString() {

        return this.value;
    }

    public static String getFontNameFromIndex(int index) {
        switch (index) {
            case 0: return TITILLIUM_SEMI_BOLD.toString();
            case 1: return TITILLIUM_LIGHT.toString();
            case 2: return TITILLIUM_REGULAR.toString();
            case 3: return TITILLIUM_BOLD.toString();
            case 4: return ROBOTO_REGULAR.toString();
            case 5: return ROBOTO_LIGHT.toString();
            case 6: return ROBOTO_BOLD.toString();
            case 7: return ROBOTO_BOLDITALIC.toString();
            default: return TITILLIUM_REGULAR.toString();
        }
    }
}
