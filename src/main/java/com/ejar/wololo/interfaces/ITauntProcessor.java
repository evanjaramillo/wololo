package com.ejar.wololo.interfaces;

import java.util.Map;

public interface ITauntProcessor {

    /**
     * Processes the input string for taunts using the activation character as a delimiter. It is
     * assumed that the input string is checked for the activation character beforehand. The replacements
     * go into the output map with the key as the taunt id and the value as the replacement.
     *
     * @param inputMessage
     *      Message to be processed for taunts.
     * @param activationChar
     *      A _single_ character that denotes the beginning of a taunt ID.
     * @return
     *      String map where keys are taunt IDs and values are replacement strings or (taunts)
     * @throws Exception
     *      Precondition not met or database error.
     */
    public Map<String, String> processTaunts(String inputMessage, char activationChar) throws Exception;

}
