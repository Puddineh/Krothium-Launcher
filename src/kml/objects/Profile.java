package kml.objects;

import kml.enums.ProfileType;

import javax.swing.*;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @website https://krothium.com
 *  @author DarkLBP
 */

public class Profile {
    private String id;
    private String name;
    private ProfileType type;
    private String lastVersionId = null;
    private File gameDir = null;
    private File javaDir = null;
    private String javaArgs = null;
    private Instant created = null;
    private Instant lastUsed = null;
    private Map<String, Integer> resolution = new HashMap<>();
    private JMenuItem menuItem;
    
    public Profile(String name){
        this.name = name;
        this.type = ProfileType.RELEASE;
        this.created = Instant.parse("1970-01-01T00:00:00.000");
        this.lastUsed = Instant.parse("1970-01-01T00:00:00.000");
    }
    public Profile(String id, String name, String type, String created, String lastUsed, String lastVersionId, String gameDir, String javaDir, String javaArgs, Map<String, Integer> resolution){
        if (id == null){
            this.id = UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            this.id = id;
        }
        this.name = name;
        this.lastVersionId = lastVersionId;
        if (gameDir != null){
            this.gameDir = new File(gameDir);
            if (!this.gameDir.exists() || !this.gameDir.isDirectory()) {
                this.gameDir = null;
            }
        }
        if (javaDir != null){
            this.javaDir = new File(javaDir);
            if (!this.javaDir.exists() || !this.javaDir.isFile()){
                this.javaDir = null;
            }
        }
        this.javaArgs = javaArgs;
        this.resolution = resolution;
        if (lastUsed == null) {
            this.lastUsed = Instant.parse("1970-01-01T00:00:00.000Z");
        } else {
            try {
                this.lastUsed = Instant.parse(lastUsed);
            } catch (DateTimeParseException ex){
                this.lastUsed = Instant.parse("1970-01-01T00:00:00.000Z");
            }
        }
        type = type.toLowerCase();
        switch (type){
            case "latest-release":
                this.type = ProfileType.RELEASE;
                break;
            case "latest-snapshot":
                this.type = ProfileType.SNAPSHOT;
                break;
            default:
                this.type = ProfileType.CUSTOM;
        }
        if (this.type == ProfileType.CUSTOM) {
            if (created == null) {
                this.created = Instant.parse("1970-01-01T00:00:00.000Z");
            } else {
                try {
                    this.created = Instant.parse(created);
                } catch (DateTimeParseException ex){
                    this.created = Instant.parse("1970-01-01T00:00:00.000Z");
                }
            }
        }
    }
    public String getID(){return this.id;}
    public void setName(String newName){this.name = newName;}
    public void setVersionID(String ver){this.lastVersionId = ver;}
    public String getName(){return this.name;}
    public boolean hasName(){return this.name != null && !this.name.isEmpty();}
    public ProfileType getType(){return this.type;}
    public String getVersionID(){return this.lastVersionId;}
    public boolean hasVersion(){return this.lastVersionId != null;}
    public File getGameDir(){return this.gameDir;}
    public boolean hasGameDir(){return (this.gameDir != null);}
    public void setGameDir(File dir){this.gameDir = dir;}
    public void setJavaDir(File dir){this.javaDir = dir;}
    public void setJavaArgs(String args){this.javaArgs = args;}
    public File getJavaDir(){return this.javaDir;}
    public boolean hasJavaDir(){return this.javaDir != null;}
    public String getJavaArgs(){return this.javaArgs;}
    public boolean hasJavaArgs(){return this.javaArgs != null;}
    public Instant getLastUsed(){return lastUsed;}
    public void markLastUsed(){this.lastUsed = Instant.now();}
    public boolean hasCreated(){return this.created != null;}
    public Instant getCreated(){return this.created;}
    public boolean hasResolution(){
        if (this.resolution != null){
            return (this.resolution.size() == 2);
        }
        return false;
    }
    public int getResolutionHeight(){
        if (resolution.containsKey("height")){
            return resolution.get("height");
        }
        return 0;
    }
    public int getResolutionWidth(){
        if (resolution.containsKey("width")){
            return resolution.get("width");
        }
        return 0;
    }
    public void setResolution(int w, int h){
        if (w < 0 || h < 0){
            resolution = null;
        } else {
            if (resolution == null){
                resolution = new HashMap<>();
            }
            resolution.put("width", w);
            resolution.put("height", h);
        }
    }
    public JMenuItem getMenuItem(){
        if (this.menuItem == null){
            if (this.hasName()){
                this.menuItem = new JMenuItem(this.getName());
            } else {
                this.menuItem = new JMenuItem("Unnamed Profile");
            }
        } else if (this.hasName()){
            if (!this.menuItem.getName().equals(this.getName())){
                this.menuItem.setName(this.getName());
            }
        }
        return this.menuItem;
    }
}
