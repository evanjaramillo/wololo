package com.ejar.wololo;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MessageParserTest {

    private final Random instancedRandom = new Random();

    @Test
    public void parserShouldNotParseEmptyKeys() {

        try {

            DefaultTauntProcessor tauntProcessor = new DefaultTauntProcessor();

            Map<String, String> replacements = tauntProcessor.processTaunts("/", '/');
            assertEquals("There should be no taunts in the map.", 0, replacements.size());

            replacements = tauntProcessor.processTaunts("/  ", '/');
            assertEquals("There should be no taunts in the map.", 0, replacements.size());

            try {

                tauntProcessor.processTaunts("   ", '/');
                fail("There was no activation character specified. Should throw an exception.");

            } catch (Exception e) {
                // This test should throw an exception:
                // It is up to the caller to ensure that there is at least one instance
                // of the activation character present. If there is not, we throw an exception.
            }


        } catch (Exception e) {

            e.printStackTrace();
            fail(e.getMessage());

        }

    }

    @Test
    public void parserShouldParseSingleKeys() {

        try {

            DefaultTauntProcessor tauntProcessor = new DefaultTauntProcessor();
            TauntsDatabase database = TauntsDatabase.getInstance();

            char activationChar = '/';

            for (int i = 1; i < 106; i++) {

                String message = "/" + i;

                Map<String, String> taunts = tauntProcessor.processTaunts(message, activationChar);

                assertEquals("One message key should result in a single taunt.", 1, taunts.size());
                assertEquals("Returned taunt should be in the database.", database.getTauntForInteger(i), taunts.get(message));

            }

        } catch (Exception e) {

            e.printStackTrace();
            fail(e.getMessage());

        }

    }

    @Test
    public void parserShouldParseMultipleKeyedMessages() {

        try {

            DefaultTauntProcessor tauntProcessor = new DefaultTauntProcessor();

            final int iterations = 100;

            for (int i = 1; i < iterations; i++) {

                StringBuilder messageBuilder = new StringBuilder();

                int taunts = random(1, 10);

                for (int j = 0; j < taunts; j++) {

                    messageBuilder.append("/")
                            .append(random(1, 105))
                            .append((j == taunts - 1 ? "." : " and "));

                }

                String message = messageBuilder.toString();

                Map<String, String> tauntMap = tauntProcessor.processTaunts(message, '/');

            }

        } catch (Exception e) {

            e.printStackTrace();
            fail(e.getMessage());

        }

    }

    private int random(int min, int max) {

        if (min >= max) {

            throw new IllegalArgumentException("max must be greater than min");

        }

        return this.instancedRandom.nextInt((max - min) + 1) + min;
    }

}
