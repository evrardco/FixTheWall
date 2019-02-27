package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fixthewall.game.Game;

public class UpgradeScreen implements Screen {

            private Stage stage;
            private SpriteBatch batch;
            private Texture imgWall;
            private Texture textureFond;
            private Game game;
            private Texture imgButton;

            public UpgradeScreen(final Game game){
                this.game = game;
                batch = new SpriteBatch();
                imgWall = new Texture("theWall.png");
                textureFond = new Texture("fondWall.png");
                imgButton = new Texture("text_button.png")  ;

                stage = new Stage(game.viewport);
                Image imgFond = new Image(textureFond);
                stage.addActor(imgFond);


                //Import font
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 60;
                BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
                // don't forget to dispose to avoid memory leaks!

                //create button style
                TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
                font12.setColor(Color.RED);
                style.font = font12;

                //setup Buttons
                //button upgrade
                TiledDrawable tile = new TiledDrawable(new TextureRegionDrawable(imgButton));
                ImageTextButton.ImageTextButtonStyle imStyle =
                        new ImageTextButton.ImageTextButtonStyle(tile, tile, tile, font12);
                Button upgradeTestButton = new ImageTextButton("Upgrade", imStyle);


                Button playButton = new TextButton("Return to the game", style);



                upgradeTestButton.addListener(new ChangeListener() {
                    @Override
                    public void changed (ChangeEvent event, Actor actor) {
                        Gdx.app.log("Update Screen", "Upgrade clicked");
                    }
                });

                playButton.addListener(new ChangeListener() {
                    @Override
                    public void changed (ChangeEvent event, Actor actor) {
                        game.setScreen(new GameScreen(game));
                    }
                });


                upgradeTestButton.scaleBy(0.25f);

                Table table = new Table();

                table.add(playButton);
                table.row();
                table.add(upgradeTestButton);
                float x = game.viewport.getWorldWidth()/2f;
                float y = game.viewport.getWorldHeight() * 0.95f - table.getHeight();
                table.setPosition(x, y);
                //add button to the scene

                stage.addActor(table);

                //necessaire pour rendre le bouton clickable
                Gdx.input.setInputProcessor(stage);
            }

            @Override
            public void show() {

            }

            @Override
            public void render(float delta) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                batch.begin();
                batch.draw(imgWall, 0, 300);
                batch.end();

                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
            }

            @Override
            public void resize(int width, int height) {
                game.viewport.update(width, height, true);
            }

            @Override
            public void pause() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void hide() {

            }

            @Override
            public void dispose() {
                stage.dispose();
                batch.dispose();
                imgWall.dispose();
                imgButton.dispose();
            }
        }


