package com.pizzashop.principal.helpers;

public enum JsonProductHelper implements JsonHelper {
    Product("""
                    {
                        "name":"P6",
                        "description":"P6",
                        "amount": 1,
                        "price": 1.04,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductToUpdate("""
                    {
                        "name":"P7",
                        "description":"P7",
                        "amount": 1,
                        "price": 11.04,
                        "imageUrl": "/static/img/p.1",
                        "status": "ORDER",
                        "categoryId": 2
                    }
            """),
    ProductDescriptionChange("""
                    {
                        "name":"P7",
                        "description":"P7 Change",
                        "amount": 1,
                        "price": 17.06,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductAlreadyExist("""
                    {
                        "name":"P1",
                        "description":"P1",
                        "amount": 1,
                        "price": 17.06,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithNameNull("""
                    {
                        "name":null,
                        "description":"P",
                        "amount": 1,
                        "price": 1,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithNameBlank("""
                    {
                        "name":" ",
                        "description":"P",
                        "amount": 1,
                        "price": 1,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithNameEmpty("""
                    {
                        "name":"",
                        "description":"P",
                        "amount": 1,
                        "price": 1,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithPriceNull("""
                    {
                        "name":"P",
                        "description":"P",
                        "amount": 1,
                        "price": null,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithImageNull("""
                    {
                        "name":"P",
                        "description":"P",
                        "amount": 1,
                        "price": 10.3,
                        "imageUrl": null,
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithImageBlank("""
                    {
                        "name":"P",
                        "description":"P",
                        "amount": 1,
                        "price": 10.3,
                        "imageUrl": " ",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithImageEmpty("""
                    {
                        "name":"P",
                        "description":"P",
                        "amount": 1,
                        "price": 10.3,
                        "imageUrl": "",
                        "status": "STOCK",
                        "categoryId": 1
                    }
            """),
    ProductWithCategoryNull("""
                    {
                        "name":"P",
                        "description":"P",
                        "amount": 1,
                        "price": 10.3,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": null
                    }
            """),
    ProductWithCategoryNotFound("""
                    {
                        "name":"P",
                        "description":"P",
                        "amount": 1,
                        "price": 1.04,
                        "imageUrl": "/static/img/p.1",
                        "status": "STOCK",
                        "categoryId": 30
                    }
            """);

    private final String product;

    JsonProductHelper(String product) {
        this.product = product;
    }

    @Override
    public String getData() {
        return product;
    }
}
