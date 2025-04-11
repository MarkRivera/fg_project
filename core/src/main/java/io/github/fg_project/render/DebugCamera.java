package io.github.fg_project.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class DebugCamera {
    private PerspectiveCamera camera;

    public DebugCamera() {
        camera = new PerspectiveCamera();
    }

    public DebugCamera setFOV(float fovInputY) {
        if(camera == null) {
            throw new IllegalStateException("Camera must be instantiated before setting position");
        }

        camera.fieldOfView = fovInputY;
        return this;
    }

    public DebugCamera setPosition(float x, float y, float z) {
        if(camera == null) {
            throw new IllegalStateException("Camera must be instantiated before setting position");
        }

        camera.position.set(x, y, z);
        return this;
    }

    public DebugCamera pointCameraAt(float x, float y, float z) {
        if(camera == null) {
            throw new IllegalStateException("Camera must be instantiated before setting a pointing direction");
        }

        camera.lookAt(x, y, z);
        return this;
    }

    public DebugCamera setNear(float nearInput) {
        if(camera == null) {
            throw new IllegalStateException("Camera must be instantiated before setting near distance");
        }

        camera.near = nearInput;
        return this;
    }

    public DebugCamera setFar(float farInput) {
        if(camera == null) {
            throw new IllegalStateException("Camera must be instantiated before setting far distance");
        }

        camera.far = farInput;
        return this;
    }

    public PerspectiveCamera build() {
        return camera;
    }
}
