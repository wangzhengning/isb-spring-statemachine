package com.statemachine.examples.cdplayer;

import com.statemachine.examples.cdplayer.entity.Cd;
import com.statemachine.examples.cdplayer.entity.Library;
import com.statemachine.examples.cdplayer.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zn.wang on 17/4/25.
 */
@Component
public class CdPlayerCommands implements CommandMarker{

    @Autowired
    private CDPlayer cdPlayer;

    private Library library;

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @CliCommand(value = "cd lcd" , help = "Print CD player lcd info")
    public String lcd (){
        return cdPlayer.getLdcStatus();
    }

    @CliCommand(value =  "cd library" ,help = "List user CD library")
    public String library(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        StringBuffer buffer = new StringBuffer();
        int i1 = 0;
        for(Cd cd : library.getCollection()){
            buffer.append( i1++ + ": " + cd.getName()+"\n");
            int i2 = 0;
            for(Track track : cd.getTracks()){
                buffer.append(" " + i2 +":" + track.getName() + " "
                    + dateFormat.format(new Date(track.getLength())));
            }
        }

        return buffer.toString();
    }

    @CliCommand(value = "cd load" ,help = "Load CD into player")
    public String load(@CliOption(key = {"" , "index"}) int index){
        StringBuffer buffer = new StringBuffer();
        try{
            Cd cd = library.getCollection().get(index);
            cdPlayer.load(cd);
            buffer.append("Loading cd " + cd);
        }catch (Exception e){
            buffer.append("Cd with index " + index+ " not found , check library");
        }
        return buffer.toString();
    }

    @CliCommand(value = "cd play" , help = "Press player play button")
    public void play(){
        cdPlayer.play();
    }

    @CliCommand(value = "cd stop" ,help = "Press player stop button")
    public void stop(){
        cdPlayer.stop();
    }

    @CliCommand(value = "cd pause" , help = "Press player pause button")
    public void pause(){
        cdPlayer.pause();
    }

    @CliCommand(value = "cd eject" , help = "Press player eject button")
    public void eject(){
        cdPlayer.eject();
    }

    @CliCommand(value = "cd forward", help = "Press player forward button")
    public void forward(){
        cdPlayer.forward();
    }

    @CliCommand(value = "cd back", help = "Press player back button")
    public void back(){
        cdPlayer.back();
    }

}
