package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.fixthewall.game.upgrades.AbstractUpgrade;
import com.fixthewall.game.upgrades.UpgradeManager;

public class UpgradeButton extends Actor {
    private AbstractUpgrade upgrade;
    private int len;
    private int height;
    private Texture pannelTex;
    private Table table;
    private Label cost;
    private Label level;
    private Button button;




    public UpgradeButton(AssetManager ass, AbstractUpgrade upgrade) {
        super();
        this.upgrade = upgrade;
        table = new Table();
        pannelTex = ass.get("ui/texture_upgrade_pannel.png");
        table.setBackground(new TextureRegionDrawable(pannelTex));
        Label.LabelStyle style = new Label.LabelStyle((BitmapFont)ass.get("Germania30.ttf"), Color.BLACK);
        cost = new Label(""+upgrade.getCost(), style);
        level = new Label(""+upgrade.getLevel(), style);

        //setting up Button.
        ImageTextButton.ImageTextButtonStyle imTxtStyle = new ImageTextButton.ImageTextButtonStyle(
                new TextureRegionDrawable((Texture) ass.get("ui/texture_button.png")),
                new TextureRegionDrawable((Texture) ass.get("ui/texture_button_down.png")),
                new TextureRegionDrawable((Texture) ass.get("ui/texture_button_disabled.png")),
                (BitmapFont) ass.get("Germania30.ttf")
        );

        button = new ImageTextButton("Upgrade", imTxtStyle);
        button.addListener(upgrade.getListener());


        //setting up elements in the table

        table.left().top().add(new Label(upgrade.getName(), style));
        table.padBottom(10);
        table.add(new Label(upgrade.getName(), style));
        table.padBottom(10);
        table.right().top().add(level).padBottom(10);
        table.add(button).pad(10);
        table.add(cost);
        table.setTouchable(Touchable.enabled);
        table.setVisible(true);
        table.debug();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        table.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        button.setChecked(!upgrade.isAffordable());
        button.setDisabled(!upgrade.isAffordable());
        cost.setText("Cost: "+upgrade.getCost());
        level.setText("LvL: "+upgrade.getLevel());
    }


}
