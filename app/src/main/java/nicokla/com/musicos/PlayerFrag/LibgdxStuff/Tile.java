package nicokla.com.musicos.PlayerFrag.LibgdxStuff;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import nicokla.com.musicos.MainAndCo.GlobalVars;

public class Tile extends Actor {

//    public final static float TILE_WIDTH = 100;
//            PianoTiles.WORLD_WIDTH / GameLogic.NUMBER_OF_COLUMNS;

    private TextureRegion texture;
    private Color color;
    private int column;
    public int midiNote;

    public Tile(int column, float yPos) {
        setPosition(column * 100 , yPos);
        this.column = column;
        this.texture = null;
    }

    public int getColumn() {
        return column;
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.setColor(color);
            batch.begin();
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
            batch.end();
        }
    }

    public void playSound() {
        GlobalVars.getInstance().midiPlayer.jouerNote(midiNote,80);
    }

    public void endSound() {
        GlobalVars.getInstance().midiPlayer.stopNote(midiNote);
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
        setSize(100, 50);
    }

    public void setSound(int midiNote){
        this.midiNote = midiNote;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
