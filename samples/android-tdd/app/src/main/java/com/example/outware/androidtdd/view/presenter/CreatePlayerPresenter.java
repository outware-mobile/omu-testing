package com.example.outware.androidtdd.view.presenter;

import com.example.outware.androidtdd.business.CreatePlayerUseCase;
import com.example.outware.androidtdd.model.Player;

/**
 * @author keithsmyth
 */
public class CreatePlayerPresenter {

    private final CreatePlayerView createPlayerView;
    private final CreatePlayerUseCase createPlayerUseCase;

    public CreatePlayerPresenter(CreatePlayerView createPlayerView,
                                 CreatePlayerUseCase createPlayerUseCase) {
        this.createPlayerView = createPlayerView;
        this.createPlayerUseCase = createPlayerUseCase;
    }

    public void save() {
        final Player player = new Player(createPlayerView.getName());
        if (!createPlayerUseCase.validate(player)) {
            createPlayerView.onPlayerSaveError();
            return;
        }
        createPlayerUseCase.save(player);
        createPlayerView.onPlayerSaved();
    }
}
