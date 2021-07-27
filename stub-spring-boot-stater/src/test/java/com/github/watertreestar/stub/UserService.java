package com.github.watertreestar.stub;

import com.github.watertreestar.stub.annotation.ProxyStub;

@ProxyStub(handler = UserServiceHandler.class)
public interface UserService {
    void study();

    void working();
}
