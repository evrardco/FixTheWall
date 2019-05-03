package com.fixthewall.game.actors.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.fixthewall.game.actors.EnnemiBaleze;
import com.fixthewall.game.logic.MexicanLogic;

public class EnnemiBalezePool {

    public static final int MAX_ENNEMIBALEZE_ON_SCREEN = 50;
    private Array<EnnemiBaleze> shown;
    private Array<EnnemiBaleze> hidden;
    private AssetManager ass;

    public EnnemiBalezePool(AssetManager ass){
        shown = new Array<EnnemiBaleze>(MAX_ENNEMIBALEZE_ON_SCREEN);
        hidden = new Array<EnnemiBaleze>(MAX_ENNEMIBALEZE_ON_SCREEN);
        this.ass = ass;
    }
    /*gets a new ennemi from the pool
     *
     */
    public EnnemiBaleze obtain(){
        if(shown.size > MAX_ENNEMIBALEZE_ON_SCREEN)
            hideOne();
        EnnemiBaleze ret = createObject();
        shown.add(ret);
        return ret;
    }

    public void free(EnnemiBaleze ennemi){
        if (shown.removeValue(ennemi, true))
            showOne();
        else
            hidden.removeValue(ennemi, true);
        ennemi.remove();
    }

    private void showOne(){
        if(hidden.size < 1) return;
        EnnemiBaleze toShow = hidden.first();
        hidden.removeValue(toShow, true);
        toShow.unhide();
        shown.add(toShow);
    }

    private void hideOne(){
        Gdx.app.log("PoolEnnemiBaleze", "hiding...");
        EnnemiBaleze toHide = shown.first();
        shown.removeValue(toHide, true);
        toHide.hide();
        hidden.add(toHide);
    }

    public void clear(){
        hidden = new Array<EnnemiBaleze>(MAX_ENNEMIBALEZE_ON_SCREEN);
        shown = new Array<EnnemiBaleze>(MAX_ENNEMIBALEZE_ON_SCREEN);
        MexicanLogic.getSingleInstance().setEnnemiBalezeCount(0);
    }

    private EnnemiBaleze createObject(){
        return new EnnemiBaleze(ass);
    }

    public Array<EnnemiBaleze> getShown() {
        return shown;
    }

}
