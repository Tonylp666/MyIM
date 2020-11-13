package com.tonylp.server.common.attribute;

import com.tonylp.server.common.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
