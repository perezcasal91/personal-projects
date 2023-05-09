package com.pizzashop.principal.helpers;

public enum JsonUserHelper implements JsonHelper {
    RoleAdmin("""
                    {
                        "firstName": "User3",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user3",
                        "email": "t_user3@pizzashop.com",
                        "password": "User1234",
                        "phone": "534-565-5555",
                        "roles": "ADMIN"
                    }
            """),
    RoleAdminChange("""
                      {
                        "firstName": "User3",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user3_change",
                        "email": "t_user3@pizzashop.com",
                        "password": "User1234",
                        "phone": "534-565-5555",
                        "roles": "ADMIN"
                    }
            """),
    RolePhoneChange("""
                      {
                        "firstName": "User3",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user3_change",
                        "email": "t_user30@pizzashop.com",
                        "password": "User1234",
                        "phone": "534-565-1234",
                        "roles": "ADMIN"
                    }
            """),
    RoleAdminUser("""
                    {
                        "firstName": "User4",
                        "middleName": "Test",
                        "lastName": "Role Admin User",
                        "username": "t_user4",
                        "email": "t_user4@pizzashop.com",
                        "password": "User1234",
                        "phone": "534-565-5555",
                        "roles": "ADMIN,USER"
                    }
            """),
    UsernameExist("""
                    {
                        "firstName": "User1",
                        "middleName": "Test",
                        "lastName": "User1",
                        "username": "t_user1",
                        "email": "t_user@pizzashop.com",
                        "password": "User1234",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    EmailExist("""
                    {
                        "firstName": "User1",
                        "middleName": "Test",
                        "lastName": "User1",
                        "username": "t_user",
                        "email": "t_user1@pizzashop.com",
                        "password": "User1234",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    InvalidEmail("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "/*@343.67",
                        "password": "User1234",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithFirstNameNull("""
                    {
                        "firstName": null,
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithFirstNameBlank("""
                    {
                        "firstName": " ",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithFirstNameEmpty("""
                    {
                        "firstName": "",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithLastNameNull("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": null,
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithLastNameBlank("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": " ",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithLastNameEmpty("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithUsernameNull("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": null,
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithUsernameBlank("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": " ",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithUsernameEmpty("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithUsernameMalformed("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "user/**",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithPasswordNull("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": null,
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithPasswordBlank("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": " ",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithPasswordEmpty("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    }
            """),
    UserWithPasswordLessThan("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User",
                        "phone": "534-565-5555",
                        "roles": "USER"
                    },
            """),
    InvalidPhone("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "5-5-555",
                        "roles": "USER"
                    },
            """),
    UserWithRolesNull("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": null
                    }
            """),
    UserWithRolesBlank("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": " "
                    }
            """),
    UserWithRolesEmpty("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": ""
                    }
            """),
    UserWithRolesNotFound("""
                    {
                        "firstName": "User",
                        "middleName": "Test",
                        "lastName": "Role Admin",
                        "username": "t_user",
                        "email": "t_user@pizzashop.com",
                        "password": "User12345",
                        "phone": "534-565-5555",
                        "roles": "TEST"
                    }
            """);

    private final String user;

    JsonUserHelper(String user) {
        this.user = user;
    }

    @Override
    public String getData() {
        return user;
    }
}
