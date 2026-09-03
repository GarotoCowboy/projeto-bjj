package br.com.pedro.sistema.gestao.pagamento.core.models.enums;

public enum Belt {

    // Kids & Youth Division (4 to 15 years old)
    WHITE_KIDS("White (Kids)"),
    GREY_WHITE("Grey and White"),
    GREY("Grey"),
    GREY_BLACK("Grey and Black"),
    YELLOW_WHITE("Yellow and White"),
    YELLOW("Yellow"),
    YELLOW_BLACK("Yellow and Black"),
    ORANGE_WHITE("Orange and White"),
    ORANGE("Orange"),
    ORANGE_BLACK("Orange and Black"),
    GREEN_WHITE("Green and White"),
    GREEN("Green"),
    GREEN_BLACK("Green and Black"),

    // Adult Division (16+ years old)
    WHITE("White"),
    BLUE("Blue"),
    PURPLE("Purple"),
    BROWN("Brown"),
    BLACK("Black"),

    // Master & Grandmaster Division
    RED_BLACK("Coral (Red and Black)"),
    RED_WHITE("Coral (Red and White)"),
    RED("Red (Grandmaster)");

    private final String displayName;

    Belt(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
