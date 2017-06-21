package com.gloria.game.sprites;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
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
        for (int i = 8; i < 14; i++)
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
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 3);
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
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) )
            return  State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;

    }

    public void defineMaria(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / Maria.PPM, 64 / Maria.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Maria.PPM);
        fixtureDef.filter.categoryBits = Maria.MARIA_BIT;
        fixtureDef.filter.maskBits = Maria.DEFAULT_BIT | Maria.BRICK_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Maria.PPM, 9 / Maria.PPM), new Vector2(2 / Maria.PPM, 9 / Maria.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2body.createFixture(fixtureDef).setUserData("head");
    }
}
