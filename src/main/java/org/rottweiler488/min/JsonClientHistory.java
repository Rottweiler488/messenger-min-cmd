package org.rottweiler488.min;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import org.rottweiler488.min.model.MessageData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JsonClientHistory {
    private final Path baseDir;// = Path.of(System.getProperty("user.dir"), "chats");
    private final Path file;// = baseDir.resolve("history.json");
//    private final String basePath = System.getProperty("user.dir");
//    private final String directoryPath = "/chats";
//    private String fileName = "/history.json";

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private final int maxLength;

    public JsonClientHistory(int maxLength) {
        //Просто передаёт параметры
        this(
                Path.of(System.getProperty("user.dir"), "chats"),
                maxLength
        );
    }

    public JsonClientHistory(Path directory, int maxLength) {
        baseDir = Objects.requireNonNull(directory);
        file = directory.resolve("history.json");
        this.maxLength = maxLength;

        //this.fileName = fileName.endsWith(".json") ? fileName : fileName + ".json";
    }

    public void saveHistoryToJsonFile(List<MessageData> messages) {
        if (Objects.isNull(messages) || messages.isEmpty())
            return; //Return ERROR

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

    public List<MessageData> loadListOfHistoryFromJsonFile() {
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

    public String loadStringOfHistoryFromJsonFile() throws IOException {
        if (!Files.exists(file) || Files.size(file) == 0)
            return "";

        StringBuilder builder = new StringBuilder();
        List<String> lines = Files.readAllLines(file);
        for (String line : lines){
            builder.append(line);
        }

        return builder.toString();
    }

    public Path getJsonFilePath() {
        return file;//basePath + directoryPath + fileName;
    }
}
