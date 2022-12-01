/**
 * Token is a class which defines the token's properties
 *
 * The fields of the class are: TOKEN, index, number - message- (combined), sessionBeginning, type.
 *
 */
package com.CouponSystemStage2.CouponSystem.Token;

import com.CouponSystemStage2.CouponSystem.Login.LoginManager;

import java.util.Date;
import java.util.Random;

public class Token {
    private int number;
    private Date sessionBeginning;
   public static int index=0;
   private String result;
   private LoginManager.ClientType type;

    public Token() {
    }

    public Date getSessionBeginning() {
        return sessionBeginning;
    }

    public static int getIndex(){
    return index++;
    }


    public String createTokenCode(LoginManager.ClientType type){
        this.number = (new Random()).nextInt(100000);
        this.sessionBeginning = new Date(System.currentTimeMillis());
        this.result="Token_"+(getIndex())+"_"+this.number;
        this.type=type;
        return this.result;
    }

    public String getResult() {
        return result;
    }

    public LoginManager.ClientType getType() {
        return type;
    }
}
