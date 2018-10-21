package com.danwaters.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {

    private final Sprite sprite;

    public Character() {
        Texture texture = new Texture("core/assets/Sprites.png");
        TextureRegion region = new TextureRegion(texture, 32, 32);
        this.sprite = new Sprite(region);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void moveLeft() {
        sprite.translateX(-32);
    }

    public void moveRight() {
        sprite.translateX(32);
    }

    public void moveUp() {
        sprite.translateY(32);
    }

    public void moveDown() {
        sprite.translateY(-32);
    }
}
