package com.github.deetree;

import java.net.URI;

/**
 * @author Mariusz Bal
 */
record Response(String name, String body, String token, URI redirect) {}
