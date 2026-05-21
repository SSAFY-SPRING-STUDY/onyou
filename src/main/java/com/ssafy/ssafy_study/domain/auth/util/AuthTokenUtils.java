package com.ssafy.ssafy_study.domain.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenUtils {

     final String  PREFIX_BEARER = "Bearer ";

     public static boolean isValidBearerToken(String bearerToken){
         if(bearerToken == null || bearerToken.indexOf("Bearer ") != 0){
             return false;
         }
         return true;
     }

     public static String parseBearerToken(String bearerToken){
         return bearerToken.substring(7);
     }
}
