package com.guinto.axolotl.resources;

import com.guinto.axolotl.characters.Axolotl;

import java.util.Comparator;

public class AxolotlComparator implements Comparator<Axolotl> {
    @Override
    public int compare(Axolotl o1, Axolotl o2) {
        return Float.compare(o2.getY(), o1.getY());
    }
}
