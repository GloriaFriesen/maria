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
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

public class MariaSprite extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion mariaStand;
    private Animation<TextureRegion> mariaRun;
    private Animation<TextureRegion> mariaJump;
    private boolean runningRight;
    private float stateTimer;

    public MariaSprite(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("idle"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 8; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 32));
        mariaRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 7; i++)
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 32));
        mariaJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        mariaStand = new TextureRegion(getTexture(), 0, 0, 32, 32);

        defineMaria();
        setBounds(0, 0, 32 / Maria.PPM, 32 / Maria.PPM);
        setRegion(mariaStand);
    }

    public void update(float delta) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = mariaJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = mariaRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
                default:
                    region = mariaStand;
                    break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
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
