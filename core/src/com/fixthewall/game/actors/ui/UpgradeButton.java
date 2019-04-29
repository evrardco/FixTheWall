package com.fixthewall.game.actors.ui;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.fixthewall.game.upgrades.AbstractUpgrade;
import com.fixthewall.game.upgrades.UpgradeManager;
import com.fixthewall.game.Helpers;

public class UpgradeButton extends Table {
    private AbstractUpgrade upgrade;
    private Texture pannelTex;
    private Label cost;
    private Label level;
    private Button button;




    public UpgradeButton(AssetManager ass, AbstractUpgrade upgrade) {
        super();
        this.upgrade = upgrade;

        if (upgrade.getType() == AbstractUpgrade.TYPE_HAMMER)
            pannelTex = ass.get("ui/texture_upgrade_panel_hammer.png");
        else if (upgrade.getType() == AbstractUpgrade.TYPE_WALL)
            pannelTex = ass.get("ui/texture_upgrade_panel_wall.png");
        else
            pannelTex = ass.get("ui/texture_upgrade_panel_other.png");


        this.setBackground(new TextureRegionDrawable(pannelTex));//BackGround Vert des bouttons
        Label.LabelStyle style = new Label.LabelStyle((BitmapFont)ass.get("PoetsenOne30.ttf"), Color.BLACK);
        cost = new Label(Helpers.formatBigNumbers(upgrade.getCost()), style);
        level = new Label(""+upgrade.getLevel(), style);

        //setting up Button.
        ImageTextButton.ImageTextButtonStyle imTxtStyle = new ImageTextButton.ImageTextButtonStyle(
                new TextureRegionDrawable((Texture) ass.get("ui/texture_button.png")),
                new TextureRegionDrawable((Texture) ass.get("ui/texture_button_down.png")),
                new TextureRegionDrawable((Texture) ass.get("ui/texture_button_disabled.png")),
                (BitmapFont) ass.get("PoetsenOne30.ttf")
        );
        imTxtStyle.fontColor = Color.BLACK;
        button = new ImageTextButton("Upgrade", imTxtStyle);
        button.addListener(upgrade.getListener());


        //setting up elements in the table

        this.add(new Label(upgrade.getName()+":", style)).expand().top().left().padLeft(30).padTop(30);
        this.add(level).align(Align.right).padRight(50).expand().uniform().row();
        this.add(new Label(upgrade.getDesc(), style)).align(Align.left).padLeft(30);
        this.add(button).align(Align.right).padRight(10);
        this.row();
        this.add().expand().fill();
        this.add(cost).align(Align.right).padBottom(10).padRight(50);
        this.setTouchable(Touchable.enabled);
        this.setVisible(true);


    }


    @Override
    public void act(float delta) {
        super.act(delta);
        button.setChecked(!upgrade.isAffordable());
        button.setDisabled(!upgrade.isAffordable());
        cost.setText("Cost: "+Helpers.formatBigNumbers(upgrade.getCost()));
        level.setText("LvL: "+upgrade.getLevel());
    }


}
