package com.seikkailupeli.States;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



//Tämä state ei ole käytössä pelissä, sillä tuotti ongelmia alkuvalikon musiikin kanssa!



public class Splash extends State{


    private Texture sTexture;// = new Texture(Gdx.files.internal("SplashImg.png"));
    private Texture splashTexture = new Texture(Gdx.files.internal("SplashImg.png"));
    private Image splash = new Image(splashTexture);
    private Stage stage = new Stage();


    public Splash(GameStateManager gsm) {
        super(gsm);
        sTexture = new Texture(Gdx.files.internal("SplashImg.png"));
        splashTexture = new Texture(Gdx.files.internal("SplashImg.png"));
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {


        stage.addActor(splash);
        splash.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(1.0f),Actions.delay(1),Actions.run(new Runnable() {
            @Override
            public void run() {
                gsm.set(new MenuState(gsm));
            }
        })));



    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //sb.begin();
        stage.act();
        stage.draw();
        sb.begin();
        sb.draw(sTexture,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.end();
        //sb.draw(splash,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //sb.end();

    }

    @Override
    public void dispose() {

    }
}
