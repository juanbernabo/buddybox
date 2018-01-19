package buddybox.controller;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import buddybox.api.Model;
import buddybox.api.Song;
import buddybox.api.State;

import static buddybox.ModelSingleton.dispatch;
import static buddybox.ModelSingleton.setStateListener;
import static buddybox.api.Play.FINISHED_PLAYING;

public class Player {
    private static MediaPlayer mediaPlayer;
    private static Context context;
    private static Song songPlaying;

    public static void init(Context context) {
        Player.context = context;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { @Override public void onCompletion(MediaPlayer mediaPlayer) {
            dispatch(FINISHED_PLAYING);
        }});

        setStateListener(new Model.StateListener() { @Override public void update(State state) {
            updateState(state);
        }});
    }

    private static void updateState(State state) {
        System.out.println(">>> player updateState songPlaying " + state.songPlaying + ", isPaused: " + state.isPaused);
        if (state.songPlaying == null || state.isPaused) {
            if (songPlaying != null)
                mediaPlayer.pause();
            return;
        }

        if (songPlaying == state.songPlaying) {
            mediaPlayer.start();
            return;
        }

        try {
            Uri myUri = Uri.parse(state.songPlaying.relativePath);
            mediaPlayer.pause();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, myUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        songPlaying = state.songPlaying;
    }
}