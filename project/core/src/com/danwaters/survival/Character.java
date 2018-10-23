package com.danwaters.survival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Character implements Disposable {

    private static final float ANIM_TIME_SECS = 0.3f;
    // TODO: Take these from map eventually
    private static final int MAP_MAX_X = 30 * 32;
    private static final int MAP_MAX_Y = 30 * 32;

    private Animation<TextureRegion> currentAnimation;
    private TextureRegion currentFrame;
    private Direction direction;
    private float currX;
    private float currY;
    private float targetX;
    private float targetY;
    private float stateTime;
    private boolean moving;

    private final Texture spriteSheet;

    private final Animation<TextureRegion> downAnimation;
    private final Animation<TextureRegion> leftAnimation;
    private final Animation<TextureRegion> rightAnimation;
    private final Animation<TextureRegion> upAnimation;

    // TODO: Is this terrible?
    private final OrthographicCamera camera;

    public Character(OrthographicCamera camera) {
        // Set the camera
        this.camera = camera;

        // Create all of the sprites
        spriteSheet = new Texture("core/assets/Sprites.png");
        TextureRegion[][] tmp = new TextureRegion[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tmp[i][j] = new TextureRegion(spriteSheet, 2 + 34 * j, 2 + 34 * i, 32, 32);
            }
        }

        // Special case for the right direction. Sort of terrible
        TextureRegion[] rightTmp = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            rightTmp[i] = new TextureRegion(spriteSheet, 2 + 34 * i, 36, 32, 32);
            rightTmp[i].flip(true, false);
        }
        downAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, tmp[0]);
        leftAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, tmp[1]);
        rightAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, rightTmp);
        upAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, tmp[2]);

        // Set the current direction to down
        this.currentAnimation = downAnimation;
        direction = Direction.DOWN;

        // Set the current position to the origin
        currX = 0;
        currY = 0;
        camera.position.x = currX;
        camera.position.y = currY;
    }

    public void draw(SpriteBatch batch) {
        if (moving) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (stateTime > ANIM_TIME_SECS) {
                currX = targetX;
                currY = targetY;
                stateTime = 0;
                moving = false;
            } else {
                updateCurrentPosition();
                camera.position.x = currX;
                camera.position.y = currY;
            }
        }
        currentFrame = currentAnimation.getKeyFrame(stateTime);
        batch.draw(currentFrame, currX, currY);
    }

    public void moveLeft() {
        if (!moving) {
            if (direction != Direction.LEFT) {
                direction = Direction.LEFT;
                currentAnimation = leftAnimation;
            } else {
                if (currX - 32 >= 0) {
                    moving = true;
                    targetX = Math.max(0, currX - 32);
                }
            }
        }
    }

    public void moveRight() {
        if (!moving) {
            if (direction != Direction.RIGHT) {
                direction = Direction.RIGHT;
                currentAnimation = rightAnimation;
            } else {
                if (currX + 32 < MAP_MAX_X) {
                    moving = true;
                    targetX = currX + 32;
                }
            }
        }
    }

    public void moveUp() {
        if (!moving) {
            if (direction != Direction.UP) {
                direction = Direction.UP;
                currentAnimation = upAnimation;
            } else {
                if (currY + 32 < MAP_MAX_Y) {
                    moving = true;
                    targetY = currY + 32;
                }
            }
        }
    }

    public void moveDown() {
        if (!moving) {
            if (direction != Direction.DOWN) {
                direction = Direction.DOWN;
                currentAnimation = downAnimation;
            } else {
                if (currY - 32 >= 0) {
                    moving = true;
                    targetY = currY - 32;
                }
            }
        }
    }

    private void updateCurrentPosition() {
        switch (direction) {
            case LEFT:
                currX -= Gdx.graphics.getDeltaTime() / ANIM_TIME_SECS * 32;
                break;
            case RIGHT:
                currX += Gdx.graphics.getDeltaTime() / ANIM_TIME_SECS * 32;
                break;
            case UP:
                currY += Gdx.graphics.getDeltaTime() / ANIM_TIME_SECS * 32;
                break;
            case DOWN:
                currY -= Gdx.graphics.getDeltaTime() / ANIM_TIME_SECS * 32;
                break;
        }
    }

    @Override
    public void dispose() {
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
