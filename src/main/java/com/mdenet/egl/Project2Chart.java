package com.mdenet.egl;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import com.google.common.base.Charsets;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class Project2Chart {
    public static void main(String[] args) throws Exception {
        // 1. Enable Flexmi and Emfatic support (for this example)
        final var emap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        emap.put("flexmi", new FlexmiResourceFactory());
        emap.put("emf", new EmfaticResourceFactory());

        // 2. Parse the EGL sources
        EglModule egl = new EglModule();
        egl.parse(Project2Chart.class.getResource("/egl/project2chart.egl"));

        // 3. Add model
        EmfModel model = new EmfModel();
        model.setMetamodelFile("metamodels/project.emf");
        model.setModelFile("models/project.flexmi");
        model.setReadOnLoad(true);
        model.setStoredOnDisposal(false);
        model.load();
        egl.getContext().getModelRepository().addModel(model);

        try {
            // 4. Run the template
            String html = (String) egl.execute();
            Files.write(new File("output.html").toPath(),
                Collections.singleton(html),
                Charsets.UTF_8);
        } finally {
            // 5. Dispose of the models
            egl.getContext().getModelRepository().dispose();
        }
    }
}