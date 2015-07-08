package com.example.outware.androidtdd.view.presenter;

/**
 * @author keithsmyth
 */
public interface CreatePlayerView {

    void onPlayerSaved();

    void onPlayerSaveError();

    String getName();
}
