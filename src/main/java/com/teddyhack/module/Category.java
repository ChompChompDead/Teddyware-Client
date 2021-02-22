package com.teddyhack.module;

public enum Category {
    COMBAT("Combat"),
    EXPLOITS("Exploits"),
    RENDER("Render"),
    PLAYER("Player"),
    CLIENT("Client"),
    MOVEMENT("Movement");

    public String name;
    public int moduleIndex;

    Category(String name) {
        this.name = name;
    }

}