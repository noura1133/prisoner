package prisoner.sdl;

public enum GameTypeEnum {

    DOG ("dog_level"),
    LIGHT ("light_level"),
    OFFICER ("officer_level"),
    BASIC ("basic_level");

    private final String label;

    GameTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
