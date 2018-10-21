package com.danwaters.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Character {

    private Sprite currentSprite;
    private Direction direction;

    private final Sprite leftSprite;
    private final Sprite rightSprite;
    private final Sprite upSprite;
    private final Sprite downSprite;

    public Character() {
        Texture texture = new Texture("core/assets/Sprites.png");
        TextureRegion region = new TextureRegion(texture, 2, 2, 32, 32);
        this.downSprite = new Sprite(region);
        region = new TextureRegion(texture, 2, 36, 32, 32);
        this.leftSprite = new Sprite(region);
        this.rightSprite = new Sprite(region);
        rightSprite.flip(true, false);
        region = new TextureRegion(texture, 2, 70, 32, 32);
        this.upSprite = new Sprite(region);
        this.currentSprite = downSprite;
        direction = Direction.DOWN;
    }

    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
    }

    public void moveLeft() {
        if (direction == Direction.LEFT) {
            currentSprite.translateX(-32);
        } else {
            direction = Direction.LEFT;
            leftSprite.setPosition(currentSprite.getX(), currentSprite.getY());
            currentSprite = leftSprite;
        }
    }

    public void moveRight() {
        if (direction == Direction.RIGHT) {
            currentSprite.translateX(32);
        } else {
            direction = Direction.RIGHT;
            rightSprite.setPosition(currentSprite.getX(), currentSprite.getY());
            currentSprite = rightSprite;
        }
    }

    public void moveUp() {
        if (direction == Direction.UP) {
            currentSprite.translateY(32);
        } else {
            direction = Direction.UP;
            upSprite.setPosition(currentSprite.getX(), currentSprite.getY());
            currentSprite = upSprite;
        }
    }

    public void moveDown() {
        if (direction == Direction.DOWN) {
            currentSprite.translateY(-32);
        } else {
            direction = Direction.DOWN;
            downSprite.setPosition(currentSprite.getX(), currentSprite.getY());
            currentSprite = downSprite;
        }
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
