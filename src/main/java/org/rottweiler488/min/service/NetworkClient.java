package org.rottweiler488.min.service;

import java.util.concurrent.CompletableFuture;

public interface NetworkClient {
    CompletableFuture<Boolean> connect(String host, int port);
    CompletableFuture<Void> sendMessage(String text);
    CompletableFuture<Void> disconnect();
}
