package com.seikkailupeli.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.seikkailupeli.SoundManager;



public class SideCharacter extends Sprite {

    public enum State{STANDING, WALKINGUP, WALKINGDOWN, WALKINGRIGHT, WALKINGLEFT}
    public State currentState;
    private State previousState;
    private float stateTimer;

    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> walkingUp;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> walkingDown;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> walkingRight;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> walkingLeft;
    private TextureRegion playerStanding;
    private TextureRegion playerStandingUp;
    private TextureRegion playerStandingRight;
    private TextureRegion playerStandingLeft;

    public World world;
    public Body b2body;
    float elapsedTime;
    public Vector2 position;

    int i = 0;


    public SideCharacter(int x,int y, World world,Texture imgEteen, Texture imgTaakse, Texture imgOikealle, Texture imgVasemmalle){

        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;


        //Animaatio alaspäin käveltäessä
        //imgEteen = new Texture("Hahmo2_Kavely_eteen.png");

        TextureRegion[][] tmp = TextureRegion.split(imgEteen,
                imgEteen.getWidth() / 3,
                imgEteen.getHeight() / 3);

        TextureRegion[] walkFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            if(i==0){
                for (int j = 0; j < 3; j++) {
                    walkFrames[index++] = tmp[i][j];
                }
            }
            else if(i==1){
                for (int j = 0; j < 1; j++) {
                    walkFrames[index++] = tmp[i][j];
                }
            }

        }

        walkingDown = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.16f, walkFrames);


        //Animaatio ylöspäin käveltäessä
        //imgTaakse = new Texture("Hahmo2_Kavely_taakse.png");

        TextureRegion[][] tmp2 = TextureRegion.split(imgTaakse,
                imgTaakse.getWidth() / 4,
                imgTaakse.getHeight() / 3);


        TextureRegion[] walkFrames2 = new TextureRegion[8];
        int index2 = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                walkFrames2[index2++] = tmp2[i][j];
            }
        }

        walkingUp = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.08f, walkFrames2);


        //Animaatio oikealle käveltäessä
        //imgOikealle = new Texture("Hahmo2_Kavely_oikea.png");

        TextureRegion[][] tmp3 = TextureRegion.split(imgOikealle,
                imgOikealle.getWidth() / 3,
                imgOikealle.getHeight() / 3);


        TextureRegion[] walkFrames3 = new TextureRegion[4];
        int index3 = 0;
        for (int i = 0; i < 2; i++) {
            if(i==0){
                for (int j = 0; j < 3; j++) {
                    walkFrames3[index3++] = tmp3[i][j];
                }
            }
            else if(i==1){
                for (int j = 0; j < 1; j++) {
                    walkFrames3[index3++] = tmp3[i][j];
                }
            }

        }

        walkingRight = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.15f, walkFrames3);


        //Animaatio vasemmalle käveltäessä
        //imgVasemmalle = new Texture("Hahmo2_Kavely_vasen.png");

        TextureRegion[][] tmp4 = TextureRegion.split(imgVasemmalle,
                imgVasemmalle.getWidth() / 3,
                imgVasemmalle.getHeight() / 3);


        TextureRegion[] walkFrames4 = new TextureRegion[4];
        int index4 = 0;
        for (int i = 0; i < 2; i++) {
            if(i==0){
                for (int j = 0; j < 3; j++) {
                    walkFrames4[index4++] = tmp4[i][j];
                }
            }
            else if(i==1){
                for (int j = 0; j < 1; j++) {
                    walkFrames4[index4++] = tmp4[i][j];
                }
            }

        }

        walkingLeft = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.15f, walkFrames4);
        //Vaihdetaan animaation suuntaa, sillä tällöin näyttää paremmalta
        walkingLeft.setPlayMode(Animation.PlayMode.LOOP_REVERSED);



        //playerCollision(x,y);
        setBounds(0,0,16, 16);

        //Hahmon paikoillaanolo eri suunnilta
        playerStanding = new TextureRegion(imgEteen,0,2400,imgEteen.getWidth()/3,imgEteen.getHeight()/3);
        playerStandingUp = new TextureRegion(imgTaakse,0,600,imgTaakse.getWidth()/4,imgTaakse.getHeight()/3);
        playerStandingRight = new TextureRegion(imgOikealle,0,1000,imgOikealle.getWidth()/3,imgOikealle.getHeight()/3);
        playerStandingLeft = new TextureRegion(imgVasemmalle,0,1000,imgVasemmalle.getWidth()/3,imgVasemmalle.getHeight()/3);


        setRegion(playerStanding);


        position = new Vector2(x-imgEteen.getWidth()/2,y-imgEteen.getHeight()/2);

    }

    public void update(float dt){

        //animation.update(dt);
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        elapsedTime += dt;
        setRegion(getFrame(dt));

    }

    public TextureRegion getFrame(float dt){
        //currentState = getState();


        TextureRegion region = playerStanding;
        switch (currentState){
            case WALKINGDOWN:
                region = walkingDown.getKeyFrame(stateTimer, true);
                //SoundManager.walkSound.play();
                i=1;
                //previousDirection = State.WALKINGDOWNDIRECTION;
                break;
            case WALKINGUP:
                region = walkingUp.getKeyFrame(stateTimer, true);
                //SoundManager.walkSound.play();
                i=2;
                // previousDirection = State.WALKINGUPDIRECTION;
                break;
            case WALKINGRIGHT:
                region = walkingRight.getKeyFrame(stateTimer, true);
                //SoundManager.walkSound.play();
                i=3;
                //previousDirection = State.WALKINGRIGHTDIRECTION;
                break;
            case WALKINGLEFT:
                region = walkingLeft.getKeyFrame(stateTimer, true);
                //SoundManager.walkSound.play();
                i=4;
                //previousDirection = State.WALKINGLEFTDIRECTION;
                break;
            case STANDING:
                if(i==1){
                    region = playerStanding;
                }
                else if(i==2){
                    region = playerStandingUp;
                }
                else if(i==3){
                    region = playerStandingRight;
                }
                else if(i==4){
                    region = playerStandingLeft;
                }
                break;
                /*switch (previousDirection){
                    case WALKINGDOWNDIRECTION:
                        region = playerStanding;
                        break;
                    case WALKINGUPDIRECTION:
                        region = playerStandingUp;
                        break;
                    case WALKINGRIGHTDIRECTION:
                        region = playerStandingRight;
                        break;
                    case WALKINGLEFTDIRECTION:
                        region = playerStandingLeft;
                        break;
                    default:
                        region = playerStanding;
                        break;
                }*/


            default:
                region = playerStanding;
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){

        // x- ja y-akseleiden itseisarvot
        float absoluteValueX = Math.abs(b2body.getLinearVelocity().x);
        float absoluteValueY = Math.abs(b2body.getLinearVelocity().y);

        //Määritellään milloin käytetään tiettyyn suuntaan menevää animaatiota
        if(b2body.getLinearVelocity().y<0 && absoluteValueY > absoluteValueX)
            return State.WALKINGDOWN;
        else if(b2body.getLinearVelocity().y>0 && absoluteValueY > absoluteValueX)
            return State.WALKINGUP;
        else if(b2body.getLinearVelocity().x>0 && absoluteValueX > absoluteValueY)
            return State.WALKINGRIGHT;
        else if(b2body.getLinearVelocity().x<0&& absoluteValueX > absoluteValueY)
            return State.WALKINGLEFT;
        else
            return State.STANDING;
    }

    //Pelaajan törmäysalue
    public void playerCollision(int x, int y){

        int radius = 36;
        BodyDef bdef = new BodyDef();
        bdef.position.set(x-radius/2,y-radius/2);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getSideCharacterTexture(float dt) {
        return getFrame(dt);
    }

    public void dispose(){

    }


}

