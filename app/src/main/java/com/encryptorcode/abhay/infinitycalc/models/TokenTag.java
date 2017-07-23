package com.encryptorcode.abhay.infinitycalc.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class TokenTag {
    private String token;
    private Tag tag;

    public TokenTag(String token, Tag tag) {
        this.token = token;
        this.tag = tag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TokenTag{" +
                "token='" + token + '\'' +
                ", tag=" + tag +
                '}';
    }

    public static List<TokenTag> asList(String[] tokens, Tag[] tags){
        List<TokenTag> list = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            list.add(new TokenTag(tokens[i],tags[i]));
        }
        return list;
    }
}
