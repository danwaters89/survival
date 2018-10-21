package com.danwaters.survival;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {

    private Sprite currentSprite;
    private Direction direction;
    private int xTile;
    private int yTile;

    private final Sprite leftSprite;
    private final Sprite rightSprite;
    private final Sprite upSprite;
    private final Sprite downSprite;

    // TODO: Is this terrible?
    private final OrthographicCamera camera;

    public Character(OrthographicCamera camera) {
        // Set the camera
        this.camera = camera;

        // Create all of the sprites
        Texture texture = new Texture("core/assets/Sprites.png");
        TextureRegion region = new TextureRegion(texture, 2, 2, 32, 32);
        this.downSprite = new Sprite(region);
        region = new TextureRegion(texture, 2, 36, 32, 32);
        this.leftSprite = new Sprite(region);
        this.rightSprite = new Sprite(region);
        rightSprite.flip(true, false);
        region = new TextureRegion(texture, 2, 70, 32, 32);
        this.upSprite = new Sprite(region);

        // Set the current direction to down
        this.currentSprite = downSprite;
        direction = Direction.DOWN;

        // Set the current position to the origin
        xTile = 0;
        yTile = 0;
        setPosition();
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
            xTile = Math.min(29, xTile + 1);
        } else {
            direction = Direction.RIGHT;
            currentSprite = rightSprite;
        }
        setPosition();
    }

    public void moveUp() {
        if (direction == Direction.UP) {
            // TODO: get this number from the map
            yTile = Math.min(29, yTile + 1);
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
        // TODO: This seems terrible
        camera.position.x = xTile * 32;
        camera.position.y = yTile * 32;
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
