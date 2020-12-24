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

    public void addTile(int column, int line, int isDiese) {
        final Tile tile = generateTile(column, line, isDiese);
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

    public float getCoordinateFromTime(long time){
        float time2 = (float) time;
        return ((time2 / 3000 * hauteurReelle)); // - 130f
    }

    public Tile generateTile(int column, int line, int isDiese) {
        Tile tile = new Tile(column, accu);
//        int col = tile.getColumn();
        tile.setColor(GlobalVars.getInstance().colors[line][isDiese]);
        tile.setSound(60);
        tile.setTexture(textureAtlas.findRegion("blanc"));
        return tile;
    }

    public void update() {
        Gdx.input.setInputProcessor(stage);
        stage.act();
//        if(mainActivity.midiSequencer.isPlaying()) {
//        if (mainActivity.midiSequencer.isPlaying){
        accu = getCoordinateFromTime(mainActivity.sequencer.getMicrosecondPosition()/1000);
            //getCoordinateFromTime(mainActivity.midiSequencer.getTimeRel())
//            accu++;
//        }
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(fadeIn(1.5f));
    }

    @Override
    public void render(float delta) {
        //viewport.update(700, 400, false);
        camera.position.y = accu - 130;
        camera.update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        delta = Math.min(delta, 0.03f);

        Gdx.input.setInputProcessor(stage);
        stage.draw();
        stage.act();

//        batch.begin();
//        batch.draw(background, 0, 0, PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT);
//        batch.end();

        this.update();
        stage.draw();
        for (Actor tile : stage.getActors()) {
            Tile tile2 = (Tile) tile;
            tile2.render(batch);
        }
//        batch.begin();
//        batch.end();
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
