package com.fixthewall.game.actors.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;

import com.fixthewall.game.actors.Worker;

public class WorkerPool {

    public static final int MAX_WORKER_ON_SCREEN = 50;
    private Array<Worker> shown;
    private Array<Worker> hidden;
    private AssetManager ass;

    public WorkerPool(AssetManager ass){
        shown = new Array<Worker>(MAX_WORKER_ON_SCREEN);
        hidden = new Array<Worker>(MAX_WORKER_ON_SCREEN);
        this.ass = ass;
    }
    /*gets a new ennemi from the pool
     *
     */
    public Worker obtain(){
        if(shown.size > MAX_WORKER_ON_SCREEN)
            hideOne();
        Worker ret = createObject();
        shown.add(ret);
        return ret;
    }

    public void free(Worker worker){
        if (shown.removeValue(worker, true))
            showOne();
        else
            hidden.removeValue(worker, true);
        worker.remove();
    }

    private void showOne(){
        if(hidden.size < 1) return;
        Worker toShow = hidden.first();
        hidden.removeValue(toShow, true);
        toShow.unhide();
        shown.add(toShow);
    }

    private void hideOne(){
        Gdx.app.log("PoolWorker", "hiding...");
        Worker toHide = shown.first();
        shown.removeValue(toHide, true);
        toHide.hide();
        hidden.add(toHide);
    }

    public void clear(){
        hidden = new Array<Worker>(MAX_WORKER_ON_SCREEN);
        shown = new Array<Worker>(MAX_WORKER_ON_SCREEN);
    }

    private Worker createObject(){
        return new Worker(ass);
    }

    public Array<Worker> getShown() {
        return shown;
    }

}
