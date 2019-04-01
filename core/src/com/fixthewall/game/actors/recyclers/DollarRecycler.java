package com.fixthewall.game.actors.recyclers;

import com.badlogic.gdx.assets.AssetManager;
import com.fixthewall.game.actors.Dollar;

public class DollarRecycler {
    public static final int MAX_CAPACITY = 128;

    public boolean recycling;



    private Dollar[] allInstances;
    private int capacity;
    private int index;



    public DollarRecycler(int capacity){
        this.capacity = capacity > MAX_CAPACITY ? MAX_CAPACITY : capacity;
        this.allInstances = new Dollar[this.capacity];
        this.index = 0;
        this.recycling = false;
    }

    public Dollar getOne(AssetManager ass){
        index = index % capacity; //circular array
        recycling = (index == 0) && (allInstances[index] != null);

        if(!recycling)
            allInstances[index] = new Dollar(ass);

        return allInstances[index++];
    }
    public boolean isRecycling() {
        return recycling;
    }
}
