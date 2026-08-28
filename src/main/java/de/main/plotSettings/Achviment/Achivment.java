package de.main.plotSettings.Achviment;

import org.bukkit.Material;
import org.enginehub.linbus.stream.token.LinToken;

import java.util.Map;


public class Achivment {

    private final String id;
    private final String title;
    private final Material material;
    private final Map<Integer,Integer> levels;
    private final Map<Integer, String> levelNames;

    public Achivment(String id, String title, Material material, Map<Integer, Integer> levels, Map<Integer, String> levelNames)
    {
        this.id = id;
        this.title = title;
        this.material = material;
        this.levels = levels;
        this.levelNames = levelNames;

    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public Material getMaterial()
    {
        return material;
    }

    public Map<Integer, Integer> getLevels()
    {
        return levels;
    }

    public Map<Integer,String> getLevelNames()
    {
        return levelNames;
    }
}
