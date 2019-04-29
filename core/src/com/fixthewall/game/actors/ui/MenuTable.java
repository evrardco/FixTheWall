package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuTable extends Table {

    private final int padding = 10;

    public MenuTable(AssetManager ass, float posX, float posY, float offset) {
        super();

        setWidth(offset + padding);
        setPosition(posX - padding, posY - padding);
        setTouchable(Touchable.enabled);
        setBackground(new TextureRegionDrawable(ass.get("ui/background.png", Texture.class)));

        right();
    }

    public void addEntry(Actor entry) {
        setHeight(entry.getHeight() + padding * 2);
        setWidth(getWidth() + entry.getWidth() + padding * 2);
        if (getChildren().size < 1)
            add(entry);
        else
            add(entry).pad(padding);

    }

    public void removeEntry(int index) {
        if (getChildren().size < 1 && index >= getChildren().size) return;
        getChildren().removeIndex(index);
    }

}
