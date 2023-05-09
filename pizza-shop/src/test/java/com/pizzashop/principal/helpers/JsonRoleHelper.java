package com.pizzashop.principal.helpers;

public enum JsonRoleHelper implements JsonHelper {
    Role("""
                    {
                        "name":"R1",
                        "description":"R1"
                    }
            """),
    RoleToUpdate("""
                    {
                        "name":"R2",
                        "description":"R2"
                    }
            """),
    RoleDescriptionChange("""
                    {
                        "name":"R2",
                        "description":"R2 Change"
                    }
            """),
    RoleAlreadyExist("""
                    {
                        "name":"R",
                        "description":"R"
                    }
            """),
    RoleWithNameNull("""
                    {
                        "name":null,
                        "description":"R"
                    }
            """),
    RoleWithNameBlank("""
                    {
                        "name":" ",
                        "description":"R"
                    }
            """),
    RoleWithNameEmpty("""
                    {
                        "name":"",
                        "description":"R"
                    }
            """);

    private final String role;

    JsonRoleHelper(String role) {
        this.role = role;
    }

    @Override
    public String getData() {
        return role;
    }
}
