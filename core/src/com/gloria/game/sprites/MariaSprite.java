package com.gloria.game.sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gloria.game.Maria;

public class MariaSprite extends Sprite {
    public World world;
    public Body b2body;

    public MariaSprite(World world) {
        this.world = world;
        defineMaria();
    }

    public void defineMaria(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(16 / Maria.PPM, 16 / Maria.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Maria.PPM);

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }
}
