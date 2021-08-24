package com.ejar.wololo;

import com.ejar.wololo.interfaces.ITauntProcessor;

import java.util.HashMap;
import java.util.Map;

public class DefaultTauntProcessor implements ITauntProcessor {

    @Override
    public Map<String, String> processTaunts(String inputMessage, char activationChar) throws Exception {

        if (!inputMessage.contains("" + activationChar)) {

            throw new Exception("No activation character present in input message.");

        }

        int position = 0;
        TauntsDatabase database = TauntsDatabase.getInstance();

        Map<String, String> tauntLut = new HashMap<>();

        while (position < inputMessage.length()) {

            /*
             * We aren't at the activation character yet.
             */
            if (inputMessage.charAt(position) != activationChar) {

                position++;
                continue;

            }

            /*
             * Increment by one and check bounds. (Removes the activation character)
             */
            if (++position >= inputMessage.length()) {

                // removing the activation character puts us at the end of the string.
                // nothing to process.
                break;

            }

            final int start = position;

            char posChar = inputMessage.charAt(position);

            if ((posChar >= '0' && posChar <= '9')) {

                do {

                    posChar = inputMessage.charAt(position);

                } while ((posChar >= '0' && posChar <= '9') && ++position < inputMessage.length());

            }

            final int end = position;

            if (start == end) {

                // there must have been no key specified.
                continue;

            }

            String tauntString = inputMessage.substring(start, end);
            int tauntId = Integer.parseInt(tauntString);

            String taunt = database.getTauntForInteger(tauntId);
            tauntLut.put(activationChar + tauntString, taunt);

            ++position;

        }

        return tauntLut;

    }

}
