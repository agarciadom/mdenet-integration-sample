package com.mdenet.eol;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class ReadOnlyModel {
    public static void main(String[] args) throws Exception {
        // 1. Enable Flexmi and Emfatic support (for this example)
        final var emap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        emap.put("flexmi", new FlexmiResourceFactory());
        emap.put("emf", new EmfaticResourceFactory());

        // 2. Parse the EOL code
        EolModule eol = new EolModule();
        eol.parse(ReadOnlyModel.class.getResource("/eol/02-read_only.eol"));
        
        // 3. Add models to the repository
        EmfModel m = new EmfModel();
        m.setMetamodelFile("metamodels/project.emf");
        m.setModelFile("models/project.flexmi");
        m.setReadOnLoad(true);
        m.setStoredOnDisposal(false);
        m.load();
        eol.getContext().getModelRepository().addModel(m);

        // 4. Run the script
        try {
            eol.execute();
        } finally {
            // 5. Always dispose *all* models
            eol.getContext().getModelRepository().dispose();
        }
    }

}
