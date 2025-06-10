package org.filippostolov.gitviewer.model.enumerations;

public enum SortOption {
    CREATED("created"),
    UPDATED("updated"),
    LANGUAGE("language"),
    NAME("name"),
    WATCHERS("watchers");

    private final String value;

    SortOption(String value) {
        this.value = value;
    }


    public static SortOption from(String value) {
        for (SortOption option : values()) {
            if (option.value.equalsIgnoreCase(value)) {
                return option;
            }
        }
        return NAME; //default
    }

}
