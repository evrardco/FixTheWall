package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Game;

public class BigMenuTable extends Table {

    private Table content;

    public BigMenuTable(AssetManager ass, String title) {
        super();

        setWidth(Game.GAME_WIDTH * 0.9f);
        setHeight(Game.GAME_HEIGHT * 0.9f);
        setPosition(Game.GAME_WIDTH / 2f - getWidth() / 2f, Game.GAME_HEIGHT / 2f - getHeight() / 2f);
        setTouchable(Touchable.enabled);
        setBackground(new TextureRegionDrawable(ass.get("ui/background.png", Texture.class)));

        top();
        pad(50);

        BitmapFont font = ass.get("Germania120.ttf");
        add(new Label(title, new Label.LabelStyle(font, Color.WHITE))).expandX().padBottom(50).left();

        Texture imgButton = ass.get("ui/texture_button_return.png");
        Texture imgButtonDown = ass.get("ui/texture_button_return_down.png");

        Button backButton = new ImageButton(new TextureRegionDrawable(imgButton), new TextureRegionDrawable(imgButtonDown));
        backButton.addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                BigMenuTable.this.setVisible(false);
            }
        });

        add(backButton).padBottom(50);

        row();

        content = new Table();
        content.setTouchable(Touchable.enabled);
        content.top();

        ScrollPane scrollableContent = new ScrollPane(content);
        scrollableContent.setWidth(content.getWidth());
        scrollableContent.setHeight(Game.GAME_HEIGHT * 0.9f);

        add(scrollableContent).colspan(2);
    }

    public void addEntry(Actor entry) {
        content.add(entry).padBottom(25).row();
    }

}
