package com.pizzashop.principal.helpers;

public enum JsonCategoryHelper implements JsonHelper {
    Category("""
                    {
                        "name":"C4",
                        "description":"C4"
                    }
            """),
    CategoryToUpdate("""
                    {
                        "name":"C5",
                        "description":"C5"
                    }
            """),
    CategoryDescriptionChange("""
                    {
                        "name":"C5",
                        "description":"C5 Change"
                    }
            """),
    CategoryAlreadyExist("""
                    {
                        "name":"C",
                        "description":"C"
                    }
            """),
    CategoryWithNameNull("""
                    {
                        "name":null,
                        "description":"C"
                    }
            """),
    CategoryWithNameBlank("""
                    {
                        "name":" ",
                        "description":"C"
                    }
            """),
    CategoryWithNameEmpty("""
                    {
                        "name":"",
                        "description":"C"
                    }
            """);

    private final String category;

    JsonCategoryHelper(String category) {
        this.category = category;
    }

    @Override
    public String getData() {
        return category;
    }
}
