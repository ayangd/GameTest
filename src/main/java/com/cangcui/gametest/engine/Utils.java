package com.cangcui.gametest.engine;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

	/**
	 * Gets the content of a resource file as string.
	 * @param fileName The name of the resource file
	 * @return the content of the file
	 * @throws Exception when something is wrong upon reading the file
	 */
    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Utils.class.getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

}