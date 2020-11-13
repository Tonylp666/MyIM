package com.tonylp.server.common.session;

import lombok.Data;

@Data
public class Session {
    private String uid;
    private String uname;

    public Session(String uid, String uname) {
        this.uid = uid;
        this.uname = uname;
    }

    public String toString(){
        return uid + ":" + uname;
    }
}
