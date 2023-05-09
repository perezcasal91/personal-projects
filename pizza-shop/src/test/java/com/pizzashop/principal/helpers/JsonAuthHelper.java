package com.pizzashop.principal.helpers;

public enum JsonAuthHelper implements JsonHelper {
    RoleAdmin("""
                    {
                        "username":"t_admin",
                        "password":"Admin1234"
                    }
            """),
    RoleUser("""
                    {
                        "username":"t_user1",
                        "password":"User1234"
                    }
            """),
    UserWithUsernameNull("""
                    {
                        "username":null,
                        "password":"Password"
                    }
            """),
    UserWithUsernameBlank("""
                    {
                        "username":" ",
                        "password":"Password"
                    }
            """),
    UserWithUsernameEmpty("""
                    {
                        "username":"",
                        "password":"Password"
                    }
            """),
    UserWithPasswordNull("""
                    {
                        "username":"user",
                        "password":null
                    }
            """),
    UserWithPasswordBlank("""
                    {
                        "username":"user",
                        "password":" "
                    }
            """),
    UserWithPasswordEmpty("""
                    {
                        "username":"user",
                        "password":""
                    }
            """);

    private final String user;

    JsonAuthHelper(String user) {
        this.user = user;
    }

    @Override
    public String getData() {
        return user;
    }
}
