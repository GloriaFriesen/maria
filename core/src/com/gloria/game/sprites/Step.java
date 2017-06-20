package com.gloria.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Guest on 6/20/17.
 */

public class Step extends InteractiveTileObject {
    public Step(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
