package com.bankworksystem.bankworksystem.frameworks.persistency;

public interface Serializer<T> {
    String serialize(T object);

    T deserialize(String string);
}
