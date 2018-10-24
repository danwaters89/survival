package com.danwaters.survival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class Character {

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

    private final Animation<TextureRegion> downWalkAnimation;
    private final Animation<TextureRegion> leftWalkAnimation;
    private final Animation<TextureRegion> rightWalkAnimation;
    private final Animation<TextureRegion> upWalkAnimation;

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
        spriteSheet = new Texture("core/assets/sprites.png");
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
        downWalkAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, tmp[0]);
        leftWalkAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, tmp[1]);
        rightWalkAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, rightTmp);
        upWalkAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, tmp[2]);
        downAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, Arrays.copyOfRange(tmp[0], 0, 1));
        leftAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, Arrays.copyOfRange(tmp[1], 0, 1));
        rightAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, Arrays.copyOfRange(rightTmp, 0, 1));
        upAnimation = new Animation<TextureRegion>(ANIM_TIME_SECS / 3, Arrays.copyOfRange(tmp[2], 0, 1));

        // Set the current direction to down
        this.currentAnimation = downWalkAnimation;
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
            if (stateTime > currentAnimation.getAnimationDuration()) {
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
            moving = true;
            if (direction != Direction.LEFT) {
                System.out.println("HERE");
                direction = Direction.LEFT;
                currentAnimation = leftAnimation;
            } else {
                if (currX - 32 >= 0) {
                    targetX = Math.max(0, currX - 32);
                    currentAnimation = leftWalkAnimation;
                }
            }
        }
    }

    public void moveRight() {
        if (!moving) {
            moving = true;
            if (direction != Direction.RIGHT) {
                direction = Direction.RIGHT;
                currentAnimation = rightAnimation;
            } else {
                if (currX + 32 < MAP_MAX_X) {
                    targetX = currX + 32;
                    currentAnimation = rightWalkAnimation;
                }
            }
        }
    }

    public void moveUp() {
        if (!moving) {
            moving = true;
            if (direction != Direction.UP) {
                direction = Direction.UP;
                currentAnimation = upAnimation;
            } else {
                if (currY + 32 < MAP_MAX_Y) {
                    targetY = currY + 32;
                    currentAnimation = upWalkAnimation;
                }
            }
        }
    }

    public void moveDown() {
        if (!moving) {
            moving = true;
            if (direction != Direction.DOWN) {
                direction = Direction.DOWN;
                currentAnimation = downAnimation;
            } else {
                if (currY - 32 >= 0) {
                    targetY = currY - 32;
                    currentAnimation = downWalkAnimation;
                }
            }
        }
    }

    private void updateCurrentPosition() {
        // TODO: This is pretty messy
        if (targetX != currX || targetY != currY) {
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
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
