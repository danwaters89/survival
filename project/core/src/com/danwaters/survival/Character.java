package com.danwaters.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Character {

    private Sprite currentSprite;
    private Direction direction;
    private int xTile;
    private int yTile;

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
        xTile = 0;
        yTile = 0;
    }

    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
    }

    public void moveLeft() {
        if (direction == Direction.LEFT) {
            // TODO: get this number from the map
            xTile = Math.max(0, xTile - 1);
        } else {
            direction = Direction.LEFT;
            currentSprite = leftSprite;
        }
        setPosition();
    }

    public void moveRight() {
        if (direction == Direction.RIGHT) {
            // TODO: get this number from the map
            xTile = Math.min(30, xTile + 1);
        } else {
            direction = Direction.RIGHT;
            currentSprite = rightSprite;
        }
        setPosition();
    }

    public void moveUp() {
        if (direction == Direction.UP) {
            // TODO: get this number from the map
            yTile = Math.min(30, yTile + 1);
        } else {
            direction = Direction.UP;
            currentSprite = upSprite;
        }
        setPosition();
    }

    public void moveDown() {
        if (direction == Direction.DOWN) {
            // TODO: get this number from the map
            yTile = Math.max(0, yTile - 1);
        } else {
            direction = Direction.DOWN;
            currentSprite = downSprite;
        }
        setPosition();
    }

    private void setPosition() {
        // TODO: Get tile width from map too
        currentSprite.setPosition(xTile * 32, yTile * 32);
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
