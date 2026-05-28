package org.rottweiler488.min;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rottweiler488.min.model.MessageData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonClientHistoryTest {
    @Test
    public void testLoadListJsonHistory() { //throws IOException {
        try {
            Path temp = Files.createTempDirectory("jsonTest");

            JsonClientHistory clientHistory = new JsonClientHistory(temp, 2);

            List<MessageData> expected = List.of(
                    new MessageData("Gigi", "", "Can i help you..?"),
                    new MessageData("Chriks", "", "No.")
            );

            System.out.println(clientHistory.getJsonFilePath());

            clientHistory.saveHistoryToJsonFile(expected);

            List<MessageData> actual = clientHistory.loadListOfHistoryFromJsonFile();

            Assertions.assertEquals(expected, actual);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
