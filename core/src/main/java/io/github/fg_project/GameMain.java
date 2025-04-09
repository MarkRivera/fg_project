package io.github.fg_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.fg_project.screens.DebugScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameMain extends Game {
    @Override
    public void create() {
        this.setScreen(new DebugScreen(this));
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
    }
}
