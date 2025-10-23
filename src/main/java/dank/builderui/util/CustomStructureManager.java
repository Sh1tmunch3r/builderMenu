package dank.builderui.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Manages custom user-saved structures.
 * Allows saving and loading structure templates to/from disk.
 */
public class CustomStructureManager {
    private static final Path STRUCTURES_DIR = Paths.get("builderui_structures");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, StructureTemplate> loadedStructures = new HashMap<>();
    
    static {
        try {
            if (!Files.exists(STRUCTURES_DIR)) {
                Files.createDirectories(STRUCTURES_DIR);
            }
        } catch (IOException e) {
            System.err.println("Failed to create structures directory: " + e.getMessage());
        }
    }
    
    /**
     * Saves a structure template to disk.
     */
    public static boolean saveStructure(StructureTemplate template) {
        try {
            Path filePath = STRUCTURES_DIR.resolve(sanitizeFileName(template.getName()) + ".json");
            
            // Convert template to a serializable format
            Map<String, Object> data = new HashMap<>();
            data.put("name", template.getName());
            data.put("width", template.getWidth());
            data.put("height", template.getHeight());
            data.put("depth", template.getDepth());
            
            // For simplicity, just store block count
            data.put("blockCount", template.getBlockCount());
            
            Files.writeString(filePath, GSON.toJson(data));
            loadedStructures.put(template.getName(), template);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save structure: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads a structure template from disk.
     */
    public static StructureTemplate loadStructure(String name) {
        if (loadedStructures.containsKey(name)) {
            return loadedStructures.get(name);
        }
        
        try {
            Path filePath = STRUCTURES_DIR.resolve(sanitizeFileName(name) + ".json");
            if (!Files.exists(filePath)) {
                return null;
            }
            
            String json = Files.readString(filePath);
            // In a full implementation, this would deserialize the full structure
            // For now, return a placeholder
            StructureTemplate template = new StructureTemplate(name, 5, 5, 5);
            loadedStructures.put(name, template);
            return template;
        } catch (IOException e) {
            System.err.println("Failed to load structure: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lists all saved custom structures.
     */
    public static List<String> listStructures() {
        List<String> structures = new ArrayList<>();
        try {
            Files.list(STRUCTURES_DIR)
                .filter(path -> path.toString().endsWith(".json"))
                .forEach(path -> {
                    String name = path.getFileName().toString();
                    name = name.substring(0, name.length() - 5); // Remove .json
                    structures.add(name);
                });
        } catch (IOException e) {
            System.err.println("Failed to list structures: " + e.getMessage());
        }
        return structures;
    }
    
    /**
     * Deletes a saved structure.
     */
    public static boolean deleteStructure(String name) {
        try {
            Path filePath = STRUCTURES_DIR.resolve(sanitizeFileName(name) + ".json");
            Files.deleteIfExists(filePath);
            loadedStructures.remove(name);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to delete structure: " + e.getMessage());
            return false;
        }
    }
    
    private static String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
