package com.github.deetree;

/**
 * @author Mariusz Bal
 */
public record Event<T>(GameEvent event, T payload) {}
