package org.rottweiler488.min;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    private final Path file;

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
    }

    public void saveHistoryToJsonFile(List<MessageData> messages) {
        if (Objects.isNull(messages) || messages.isEmpty())
            return;

        File directoryFile = baseDir.toFile();

        if (!directoryFile.exists() && !directoryFile.mkdirs()) {
            System.out.println("Cannot create folder. " + directoryFile.getAbsolutePath());
        }

        try {
            mapper.writeValue(file.toFile(), messages);
        }
        catch (IOException e) {
            System.out.println("The message history could not be saved.");
        }
    }

    public List<MessageData> loadListOfHistoryFromJsonFile() {
        File absoluteFile = file.toFile();

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

    public Path getJsonFilePath() { return file; }
}
