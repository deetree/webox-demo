package com.github.deetree;

/**
 * @author Mariusz Bal
 */
record Message<T>(Event event, T payload) {}
