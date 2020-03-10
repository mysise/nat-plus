package org.mysise.natplus.common.attribute;

import io.netty.util.AttributeKey;
import org.mysise.natplus.common.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
