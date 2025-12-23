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
    public void testLoadListJsonHistory() throws IOException {
        Path temp = Files.createTempDirectory("jsonTest");

        JsonClientHistory clientHistory = new JsonClientHistory(temp, 2);
        MessageData mesOne = new MessageData("Gigi", "", "Can i help you..?");
        MessageData mesTwo = new MessageData("Chriks", "", "No.");

        clientHistory.saveHistoryToJsonFile(List.of(mesOne, mesTwo));
        Assertions.assertEquals(List.of(mesOne, mesTwo), clientHistory.loadListOfHistoryFromJsonFile());
    }
}
