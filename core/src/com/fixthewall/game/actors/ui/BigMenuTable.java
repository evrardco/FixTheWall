package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Game;

public class BigMenuTable extends Table {

    private static final float PADDING = 50;
    private static final float MAX_HEIGHT = Game.GAME_HEIGHT * 0.9f;
    private static final float POPUP_DURATION = 0.1f;

    private Table content;
    private boolean wrapContent;
    private boolean isPopup;
    private boolean isShowed;

    public BigMenuTable(AssetManager ass, String title) {
        super();
        this.wrapContent = true;
        this.isPopup = false;
        setup(ass, title);
    }

    public BigMenuTable(AssetManager ass, String title, boolean isPopup, boolean wrapContent) {
        super();
        this.wrapContent = wrapContent;
        this.isPopup = isPopup;
        setup(ass, title);
    }

    private void setup(AssetManager ass, String title) {
        setTouchable(Touchable.enabled);
        setBackground(new TextureRegionDrawable(ass.get("ui/background.png", Texture.class)));

        top();
        pad(PADDING);

        BitmapFont font = ass.get("Germania120.ttf");
        Label titleLabel = new Label(title, new Label.LabelStyle(font, Color.WHITE));

        if (isPopup) {
            Texture imgButton = ass.get("ui/texture_button_return.png");
            Texture imgButtonDown = ass.get("ui/texture_button_return_down.png");

            Button backButton = new ImageButton(new TextureRegionDrawable(imgButton), new TextureRegionDrawable(imgButtonDown));
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    BigMenuTable.this.toggle();
                }
            });

            add(titleLabel).expandX().padBottom(PADDING).left();
            add(backButton).padBottom(PADDING);
        } else
            add(titleLabel).expandX().padBottom(PADDING);

        row();

        if (wrapContent)
            setHeight(titleLabel.getHeight() + PADDING * 2);
        else
            setHeight(MAX_HEIGHT);

        setWidth(Game.GAME_WIDTH * 0.9f);

        if (isPopup) {
            setPosition(Game.GAME_WIDTH, Game.GAME_HEIGHT / 2f - getHeight() / 2f);
            isShowed = false;
        } else
            setPosition(Game.GAME_WIDTH / 2f - getWidth() / 2f, Game.GAME_HEIGHT / 2f - getHeight() / 2f);

        content = new Table();
        content.setTouchable(Touchable.enabled);
//        content.setDebug(true);

        ScrollPane scrollableContent = new ScrollPane(content);

        add(scrollableContent).colspan(2);
    }

    public void addEntry(Actor entry) {
        if (content.getCells().size > 0)
            content.add().padBottom(PADDING).row();
        content.add(entry).row();

        if (wrapContent) {
            // if nouvelle taille > au max arbitraire alors on va pas plus loin et il faudra scroller
            float newHeight = getHeight() + entry.getHeight() + PADDING;
            if (newHeight > MAX_HEIGHT)
                setHeight(MAX_HEIGHT);
            else
                setHeight(newHeight);
        }

        setPosition(getX(), Game.GAME_HEIGHT / 2f - getHeight() / 2f);
    }

    public void toggle() {
        if (!isPopup) return;

        if (isShowed) hide();
        else show();
    }

    public void hide() {
        isShowed = false;
        addAction(Actions.moveTo(Game.GAME_WIDTH, getY(), POPUP_DURATION));
    }

    public void show() {
        isShowed = true;
        addAction(Actions.moveTo(Game.GAME_WIDTH / 2f - getWidth() / 2f, getY(), POPUP_DURATION));
    }

}
