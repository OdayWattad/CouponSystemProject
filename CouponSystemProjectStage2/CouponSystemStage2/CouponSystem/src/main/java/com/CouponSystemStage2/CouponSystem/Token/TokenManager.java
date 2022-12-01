/** TokenManager is a class which generates a new token for each successful request of service from users.
 *
 * the functions and their users:
 * createToken -
 * it creates a new token with a current date and a random number, attach it to a service in the static maps and return the token.
 * getService -
 * the function looks up a service based on the received token. It returns the service if any is found.
 * checkTokens -
 * this function is scheduled once every half hour it removes an entry of service and token if half hour has passed since it was generated.
*/
        package com.CouponSystemStage2.CouponSystem.Token;

import com.CouponSystemStage2.CouponSystem.Login.LoginManager;
import com.CouponSystemStage2.CouponSystem.Services.ClientService;
import com.CouponSystemStage2.CouponSystem.Services.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {
    public static Map<Token, ClientService> tokensToServices = new HashMap<>();
    public static Map<String, Token> codeToTokens = new HashMap<>();


    public String createToken(ClientService service, LoginManager.ClientType type) {
        Token token = new Token();
        String tokenCode = token.createTokenCode(type);
        tokensToServices.put(token, service);
        codeToTokens.put(tokenCode, token);
        return tokenCode;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void checkTokens() {
        for (Map.Entry<Token, ClientService> entry : tokensToServices.entrySet()
        ) {
            if(entry==null) {
                return;
            }
            if ((((new Date().getTime()) - (entry.getKey().getSessionBeginning().getTime())) / 1000) > 60 * 30) {
                tokensToServices.remove(entry.getKey());
                codeToTokens.remove(entry.getKey().getResult());
            }
        }
    }

    public ClientService getService(String token,LoginManager.ClientType type) {
        if(codeToTokens.get(token)==null) {
            return null;
        }
        Token newToken =codeToTokens.get(token);
        System.out.println(newToken);
       if (newToken.getType().equals(type))
       {  return tokensToServices.get(newToken); }
       return null;
    }
}
