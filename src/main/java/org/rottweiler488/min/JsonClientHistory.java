package org.rottweiler488.min;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.rottweiler488.min.model.MessageData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JsonClientHistory {
    //private String serverIp = "0.0.0.0";
    private Path baseDir = Path.of(System.getProperty("user.dir"), "chats");
    private final Path file = baseDir.resolve("history.json");
//    private final String basePath = System.getProperty("user.dir");
//    private final String directoryPath = "/chats";
//    private String fileName = "/history.json";

    private int maxLength = 50;

    public JsonClientHistory(int maxLength) { this.maxLength = maxLength; }
    public JsonClientHistory(Path directory, int maxLength) {
        baseDir = directory;
        //this.fileName = fileName.endsWith(".json") ? fileName : fileName + ".json";
        this.maxLength = maxLength;
    }

    public void saveHistoryToJsonFile(List<MessageData> messages) {
        if (Objects.isNull(messages) || messages.isEmpty())
            return;

        ObjectMapper mapper = new ObjectMapper();

        //TODO: Убрать?
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //String directoryFilePath = basePath + directoryPath;
        File directoryFile = baseDir.toFile();//new File(directoryFilePath);

        if (!directoryFile.exists() && !directoryFile.mkdirs()) {
            System.out.println("Cannot create folder. " + directoryFile.getAbsolutePath());
        }

        try {
            //String absoluteFilePath = directoryFilePath + fileName;
            //File absoluteFile = new File(absoluteFilePath);
            mapper.writeValue(file.toFile(), messages);//(file, messages);
        }
        catch (IOException e) {
            System.out.println("The message history could not be saved.");
        }
    }

    public List<MessageData> loadHistoryFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        //String absoluteFilePath = basePath + directoryPath + fileName;
        File absoluteFile = file.toFile();//new File(absoluteFilePath);

        if (!absoluteFile.exists() || absoluteFile.length() == 0)
            return Collections.emptyList();

        try {
            List<MessageData> history = mapper.readValue(absoluteFile, new TypeReference<List<MessageData>>() {});
            return history;
        }
        catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Path getJsonFilePath() {
        return file;//basePath + directoryPath + fileName;
    }
}
