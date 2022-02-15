package com.mdenet.eol;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class NoModel {

    public static void main(String[] args) {
        // 1. Create the ExlModule
        EolModule m = new EolModule();

        // 2. Run parse with the appropriate type (URI/URL/File/Path of .eol, or String with actual code
        try {
            // We use the ClassName.class.getResource(...) approach to get .eol files from our resources,
            // so we can redistribute everything as a single .jar file
            m.parse(NoModel.class.getResource("/eol/01-no_model.eol").toURI());
        } catch (Exception e) {
            // Error during parsing!
            e.printStackTrace();
            return;
        }

        // 3. Invoke execute() and print the result (for EOL, it's the return value of the last statement)
        try {
            Object result = m.execute();
            System.out.println("Result: " + result);
        } catch (EolRuntimeException e) {
            e.printStackTrace();
        }
    }
}
