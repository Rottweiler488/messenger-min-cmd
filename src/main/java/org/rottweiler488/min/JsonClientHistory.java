package org.rottweiler488.min;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.rottweiler488.min.model.MessageData;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JsonClientHistory {
    //private String serverIp = "0.0.0.0";
    private final String basePath = System.getProperty("user.dir");
    private final String directoryPath = "/chats";
    private final String fileName = "/history.json";

    private int maxLength = 50;

    public JsonClientHistory(int maxLength) { this.maxLength = maxLength; }

    public void saveHistoryToJsonFile(List<MessageData> messages) {
        if (Objects.isNull(messages) || messages.isEmpty())
            return;

        ObjectMapper mapper = new ObjectMapper();

        //TODO: Убрать?
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String directoryFilePath = basePath + directoryPath;
        File directoryFile = new File(directoryFilePath);
        if (!directoryFile.exists() && !directoryFile.mkdirs()) {
            System.out.println("Cannot create folder. " + directoryFile.getAbsolutePath());
        }

        try {
            String absoluteFilePath = directoryFilePath + fileName;
            File file = new File(absoluteFilePath);
            mapper.writeValue(file, messages);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MessageData> loadHistoryFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        String absoluteFilePath = basePath + directoryPath + fileName;
        File file = new File(absoluteFilePath);

        if (!file.exists() || file.length() == 0)
            return Collections.emptyList();

        try {
            List<MessageData> history = mapper.readValue(file, new TypeReference<List<MessageData>>() {});
            return history;
        }
        catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
