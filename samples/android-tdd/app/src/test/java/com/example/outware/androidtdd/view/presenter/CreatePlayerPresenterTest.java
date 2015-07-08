package com.example.outware.androidtdd.view.presenter;

import com.example.outware.androidtdd.BuildConfig;
import com.example.outware.androidtdd.business.CreatePlayerUseCase;
import com.example.outware.androidtdd.model.Player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author keithsmyth
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class CreatePlayerPresenterTest {

    private CreatePlayerPresenter createPlayerPresenter;

    @Mock private CreatePlayerView createPlayerView;
    @Mock private CreatePlayerUseCase createPlayerUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        createPlayerPresenter = new CreatePlayerPresenter(createPlayerView, createPlayerUseCase);
    }

    private void setModelValid(boolean isValid) {
        when(createPlayerUseCase.validate(any(Player.class))).thenReturn(isValid);
    }

    private void setName(String name) {
        when(createPlayerView.getName()).thenReturn(name);
    }

    private Player capturePlayerUseCaseValidate() {
        final ArgumentCaptor<Player> args = ArgumentCaptor.forClass(Player.class);
        verify(createPlayerUseCase).validate(args.capture());
        return args.getValue();
    }

    private Player capturePlayerUseCaseSave() {
        final ArgumentCaptor<Player> args = ArgumentCaptor.forClass(Player.class);
        verify(createPlayerUseCase).save(args.capture());
        return args.getValue();
    }

    @Test
    public void save_validModel_saveComplete() {
        // arrange
        setModelValid(true);
        // act
        createPlayerPresenter.save();
        // assert
        verify(createPlayerView, never()).onPlayerSaveError();
        verify(createPlayerView).onPlayerSaved();
    }

    @Test
    public void save_invalidModel_saveError() {
        // arrange
        setModelValid(false);
        // act
        createPlayerPresenter.save();
        // assert
        verify(createPlayerView, never()).onPlayerSaved();
        verify(createPlayerView).onPlayerSaveError();
    }

    @Test
    public void save_withName_validModelForValidate() {
        // arrange
        final String expected = "a name";
        setName(expected);
        // act
        createPlayerPresenter.save();
        // assert
        final Player actual = capturePlayerUseCaseValidate();
        assertEquals(expected, actual.getName());
    }

    @Test
    public void save_withName_validModelForSave() {
        // arrange
        setModelValid(true);
        final String expected = "a name";
        setName(expected);
        // act
        createPlayerPresenter.save();
        // assert
        final Player actual = capturePlayerUseCaseSave();
        assertEquals(expected, actual.getName());
    }
}
