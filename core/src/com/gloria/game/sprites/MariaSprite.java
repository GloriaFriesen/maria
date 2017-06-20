package com.gloria.game.sprites;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gloria.game.Maria;
import com.gloria.game.screens.PlayScreen;

public class MariaSprite extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion mariaStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private boolean runningRight;
    private float stateTimer;

    public MariaSprite(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("idle"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

//        Array<TextureRegion> frames = new Array<TextureRegion>();
//        for (int i = 1; i < 4; i++)
//            frames.add(new TextureRegion(getTexture(), i * 50, 0, 16, 16))

        defineMaria();
        mariaStand = new TextureRegion(getTexture(), 0, 0, 32, 32);
        setBounds(0, 0, 32 / Maria.PPM, 32 / Maria.PPM);
        setRegion(mariaStand);
    }

    public void update(float delta) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
