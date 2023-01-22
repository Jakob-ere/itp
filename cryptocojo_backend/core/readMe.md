## Core

This module contains the backend side of the project. 
```
In core/src/main/java/core/:
```
- items folder which contains:
    - Coin.java
    - OwnedCoin.java
    - PriceFormat.java
    - Purchase.java
- login folder which contains:
    - LoginDetails.java
    - LoginUser.java
- rest folder with logic that handles api data:
    - ConnectionResponse.java
    - DecodeCoin.java
    - DecodeCoinManager.java
    - DecodeTime.java
    - DecodeTimeManager.java
- user folder with user related classes:
    - FindFile.java
    - GetUserFromFile.java
    - SaveToFile.java
    - User.java
    - UserAuth.java
    - UserValidation.java
```
In core/src/main/resources/savefiles/users:
```
- json files with individual users data
- auth folder with auth.json which is used to authenticate users. 
```
In core/src/test/java/core/: 
```
- Backend tests


### Choices
When deciding what library to use for serializing / deserializing user objects we had two options. 
We chose to use the Gson library over Jackson because Gson has a simple and easy way of serializing / deserializing with gson.toJson and gson.fromJson methods. Some of us also had some prior experience with this library.