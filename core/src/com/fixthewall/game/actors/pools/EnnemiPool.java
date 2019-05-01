package com.fixthewall.game.actors.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.logic.MexicanLogic;

public class EnnemiPool {
    public static final int MAX_ENNEMI_ON_SCREEN = 100;
    private Array<Ennemi> shown;
    private Array<Ennemi> hidden;
    private AssetManager ass;

    public EnnemiPool(AssetManager ass){
        shown = new Array<Ennemi>(MAX_ENNEMI_ON_SCREEN);
        hidden = new Array<Ennemi>(MAX_ENNEMI_ON_SCREEN);
        this.ass = ass;
    }
    /*gets a new ennemi from the pool
    *
    */
    public Ennemi obtain(){
      if(shown.size > MAX_ENNEMI_ON_SCREEN)
          hideOne();
      Ennemi ret = createObject();
      shown.add(ret);
      return ret;
    }

    public void free(Ennemi ennemi){
        shown.removeValue(ennemi, true);
        showOne();
    }

    private void showOne(){
        if(hidden.size < 1) return;
        Ennemi toShow = hidden.first();
        hidden.removeValue(toShow, true);
        toShow.unhide();
        shown.add(toShow);
    }

    private void hideOne(){
        Gdx.app.log("PoolEnnemi", "hiding...");
        Ennemi toHide = shown.first();
        shown.removeValue(toHide, true);
        toHide.hide();
        hidden.add(toHide);
    }

    public void clear(){
        hidden = new Array<Ennemi>(MAX_ENNEMI_ON_SCREEN);
        shown = new Array<Ennemi>(MAX_ENNEMI_ON_SCREEN);
        MexicanLogic.getSingleInstance().setEnnemiCount(0);
    }

    private Ennemi createObject(){
        return new Ennemi(ass);
    }

    public Array<Ennemi> getShown() {
        return shown;
    }
}
