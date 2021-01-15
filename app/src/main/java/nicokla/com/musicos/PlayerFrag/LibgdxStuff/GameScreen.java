package nicokla.com.musicos.PlayerFrag.LibgdxStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.MainAndCo.MainActivity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class GameScreen implements Screen {
    private PianoTiles pianoTiles;

    public OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch batch;

    private TextureAtlas textureAtlas;
    private TextureRegion background;
    private Stage stage;

    private float velocity = 6;
    public float delay = 40;
    public Boolean defile = true;
    public float accu;
    private ArrayList<Tile> tiles;
    float hauteurReelle;
    MainActivity mainActivity;

    public GameScreen(PianoTiles pianoTiles, MainActivity mainActivity) {
        this.pianoTiles = pianoTiles;
        this.mainActivity = mainActivity;
        tiles = new ArrayList<Tile>();
        accu = 0;
        initUtils();
    }

    public void initUtils() {
        camera = new OrthographicCamera();
        //viewport = new StretchViewport(700, 400, camera);
        viewport = new ExtendViewport(700, 20, camera);
        viewport.apply(true);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        stage = new Stage(viewport, batch);

        textureAtlas = pianoTiles.getAssetManager().get(PianoTiles.MASTER_PATH);
        background = textureAtlas.findRegion("background");
        hauteurReelle = (float) Gdx.graphics.getHeight();
    }

    public void addTileAtTime(int column, int line, int isDiese, long time) {
        final Tile tile = generateTileAtTime(column, line, isDiese, time);
//        tiles.add(tile);
        tile.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tile.playSound();
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tile.endSound();
            }
        });
        stage.addActor(tile);
    }

    public void addTile(int column, int line, int isDiese) {
        addTileAtTime(column, line, isDiese, mainActivity.sequencer.getMicrosecondPosition()/1000);
    }

    public float getCoordinateFromTime(long time){
        float time2 = (float) time;
        return ((time2 / 3000 * hauteurReelle)); // - 130f
    }

    public Tile generateTileAtTime(int column, int line, int isDiese, long time) {
        Tile tile = new Tile(column, getCoordinateFromTime(time));
//        int col = tile.getColumn();
        tile.setColor(GlobalVars.getInstance().colors[line][isDiese]);
        tile.setSound(60);
        tile.setTexture(textureAtlas.findRegion("blanc"));
        return tile;
    }

    public Tile generateTile(int column, int line, int isDiese) {
        return generateTileAtTime(column, line, isDiese, mainActivity.sequencer.getMicrosecondPosition()/1000);
    }

    public void update() {
        Gdx.input.setInputProcessor(stage);
        stage.act();
        // We update accu in order for the camera to move
        accu = getCoordinateFromTime(mainActivity.sequencer.getMicrosecondPosition()/1000);
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(fadeIn(1.5f));
    }

    @Override
    public void render(float delta) {
        camera.position.y = accu - 200; // 165
        camera.update(); // the camera is moving at its new y position

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);
        stage.draw();
        stage.act();

        this.update(); // we update accu with the time (the y position)
        stage.draw();
        for (Actor tile : stage.getActors()) {
            Tile tile2 = (Tile) tile;
            tile2.render(batch);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
