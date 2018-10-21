package com.danwaters.survival;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class SurvivalGame extends ApplicationAdapter {
    AssetManager assetManager;
    OrthographicCamera camera;
    TiledMap map;
    TiledMapRenderer mapRenderer;

	@Override
	public void create () {
	    int width = Gdx.graphics.getWidth();
	    int height = Gdx.graphics.getHeight();

		// once the asset manager is done loading
        map = new TmxMapLoader().load("core/assets/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();
	}

	@Override
	public void render () {
	    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	        camera.translate(-32, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(32, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 32);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -32);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
	}
	
	@Override
	public void dispose () {
	    map.dispose();
	}

}
