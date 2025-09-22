package io.github.Qwerybomb.GDX;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UiHandle extends Thread implements gameUtils{

    // enums for equips
    enum equips {
        FLESH(),
        EMPRESS();
    }
    enum uiState {
        VIEW,
        SPELLS,
        INVENTORY;
    }

    // set up the wand object
    public Image wand = new Image(wands.findRegion("fleshWand"));
    equips State = equips.FLESH;

    // setting up personal stage
    public Stage uiStage = new Stage(new FitViewport(640, 360));

    @Override
    public void run() {
        while(true) {
            if (State == equips.FLESH) {
                wand.setDrawable(new TextureRegionDrawable(wands.findRegion("fleshWand")));
                wand.setPosition(250, (float) (-350 + uiY.get() + (Math.cos(Math.round((float) headBob.get() / 6)) * 7)));
            } else {
                wand.setDrawable(new TextureRegionDrawable(wands.findRegion("empressWand")));
                wand.setPosition(250, (float) (-390 + uiY.get() + (Math.cos(Math.round((float) headBob.get() / 6)) * 7)));
            }
            wand.setOrigin(Align.center);
            wand.setScale(0.4f);
        }
    }

}
