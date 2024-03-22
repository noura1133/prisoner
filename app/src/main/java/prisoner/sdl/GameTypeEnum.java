package prisoner.sdl;

public enum GameTypeEnum {

    DOG ("dog_level"),
    LIGHT ("light_level"),
    OFFICER ("officer_level");
<<<<<<< HEAD
    //BASIC ("basic_level");
=======
   // BASIC ("basic_level");
>>>>>>> 6f2b924 (buisson qui bouge)

    private final String label;

    GameTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
