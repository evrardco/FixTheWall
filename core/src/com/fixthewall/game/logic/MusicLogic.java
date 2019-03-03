package com.fixthewall.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

public class MusicLogic {

    private Music[] playlist;
    private int sizePlaylist;
    private Music inPlaying;

    public MusicLogic(){
        sizePlaylist=6;
        playlist = new Music[sizePlaylist];
        playlist[0] = Gdx.audio.newMusic(Gdx.files.internal("music/song1.mp3"));
        playlist[0].setVolume(0.6f);
        playlist[1] = Gdx.audio.newMusic(Gdx.files.internal("music/song2.mp3"));
        playlist[1].setVolume(0.35f);
        playlist[2] = Gdx.audio.newMusic(Gdx.files.internal("music/song3.mp3"));
        playlist[2].setVolume(0.6f);
        playlist[3] = Gdx.audio.newMusic(Gdx.files.internal("music/song4.mp3"));
        playlist[3].setVolume(0.6f);
        playlist[4] = Gdx.audio.newMusic(Gdx.files.internal("music/song5.mp3"));
        playlist[4].setVolume(0.5f);
        playlist[5] = Gdx.audio.newMusic(Gdx.files.internal("music/song6.mp3"));
        playlist[5].setVolume(0.55f);
        inPlaying = null;
    }



    public void runPlaylist() {
        //Lancement
        inPlaying = getRandomMusic();
        inPlaying.play();
        //Gestion des transitions
        playlist[0].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                inPlaying = getRandomMusic();
                inPlaying.play();
                playlist[0].setVolume(0.6f);
            }
        });
        playlist[1].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                inPlaying = getRandomMusic();
                inPlaying.play();
                playlist[1].setVolume(0.35f);
            }
        });
        playlist[2].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                inPlaying = getRandomMusic();
                inPlaying.play();
                playlist[2].setVolume(0.6f);
            }
        });
        playlist[3].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                inPlaying = getRandomMusic();
                inPlaying.play();
                playlist[3].setVolume(0.6f);
            }
        });
        playlist[4].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                inPlaying = getRandomMusic();
                inPlaying.play();
                playlist[4].setVolume(0.5f);
            }
        });
        playlist[5].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                inPlaying = getRandomMusic();
                inPlaying.play();
                playlist[5].setVolume(0.55f);
            }
        });
        //Gestion des fondus audio
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (playlist[3].isPlaying()) {
                    if (playlist[3].getPosition() >= 254) {
                        playlist[3].setVolume(playlist[3].getVolume() - 0.05f);
                    }
                }
                if (playlist[4].isPlaying()) {
                    if (playlist[4].getPosition() >= 178) {
                        playlist[4].setVolume(playlist[4].getVolume() - 0.05f);
                    }
                }
            }
        }, 5, 0.2f, 20);
    }

    public int getSizePlaylist() {
        return sizePlaylist;
    }

    public Music getInPlaying() {
        return inPlaying;
    }

    public void play(){
        if(inPlaying!=null)
        inPlaying.play();
    }

    public void pause(){
        if(inPlaying!=null)
        inPlaying.pause();
    }

    public void stop(){
        if(inPlaying!=null)
        inPlaying.stop();
    }

    private Music getRandomMusic(){
        int rand = getRandom(sizePlaylist);
        return playlist[rand];
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

    public void dispose (){
        for (Music m : playlist) {
            m.dispose();
        }
    }
}
