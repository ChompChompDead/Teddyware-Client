package com.teddyhack.module;

public enum Category {
    Combat("Combat"),
    Exploits("Exploits"),
    Render("Render"),
    Player("Player"),
    Client("Client"),
    Movement("Movement");

    public String name;
    public int moduleIndex;
    public boolean open = true;

    Category(String name) {
        this.name = name;
    }

}
