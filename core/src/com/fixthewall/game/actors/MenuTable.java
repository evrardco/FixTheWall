package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Game;

public class MenuTable extends Table {

    AssetManager ass;

    public MenuTable(AssetManager ass, String title) {
        super();
        this.ass = ass;

        setWidth(Game.GAME_WIDTH * 0.9f);
        setHeight(Game.GAME_HEIGHT * 0.9f);
        setPosition(Game.GAME_WIDTH / 2f - getWidth() / 2f, Game.GAME_HEIGHT / 2f - getHeight() / 2f);
        setTouchable(Touchable.enabled);
        setBackground(new TextureRegionDrawable(ass.get("ui/white_background.png", Texture.class)));

        top();
        pad(50);

        BitmapFont font = ass.get("Germania120.ttf");
        add(new Label(title, new Label.LabelStyle(font, Color.BLACK))).expandX().padBottom(50).left();

        Texture imgButton = ass.get("ui/texture_button_return.png");
        Texture imgButtonDown = ass.get("ui/texture_button_return_down.png");

        Button volumeButton = new ImageButton(new TextureRegionDrawable(imgButton), new TextureRegionDrawable(imgButtonDown));
        volumeButton.addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                MenuTable.this.setVisible(false);
            }
        });

        add(volumeButton).padBottom(50);

        row();
    }

    public void addEntry(String description, Button button) {
        BitmapFont font = ass.get("Germania60.ttf");
        add(new Label(description, new Label.LabelStyle(font, Color.BLACK))).padBottom(25).left();
        add(button).padBottom(25);
        row();
    }

    /**
     * TODO
     * Pour faire cette méthode pour ajouter des upgrades plus facilement
     */
    /*public void addUpgradeEntry(Upgrade upgrade) {

    }*/

}
