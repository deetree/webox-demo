package com.github.deetree;

/**
 * @author Mariusz Bal
 */
record GameDto(
        int wsl,
        Dimensions dimensions,
        String oPlayer,
        String xPlayer,
        GameplayRole role
) {}
